package com.octo.gwt.test.gin;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.AbstractModule;
import com.google.inject.Binding;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.spi.DefaultElementVisitor;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.Element;
import com.google.inject.spi.Elements;
import com.google.inject.spi.HasDependencies;
import com.google.inject.spi.InjectionPoint;
import com.octo.gwt.test.exceptions.GwtTestPatchException;

/**
 * Module to be added dynamically to substitute a GIN Injector with a Guice
 * Injector, in order to add all required bindings to call GWT's deferred
 * binding fallback.
 * 
 * @author Alex Dobjanschi
 * @author Gael Lazzari
 * 
 */
class DeferredBindingModule extends AbstractModule {

  private static class DeferredBindingProvider implements Provider<Object> {

    private final Class<?> clazzToInstanciate;

    public DeferredBindingProvider(Class<? extends Ginjector> ginInjectorClass,
        Class<?> clazzToInstanciate) {
      if (clazzToInstanciate.getName().endsWith("Async")) {
        try {
          this.clazzToInstanciate = Class.forName(clazzToInstanciate.getName().substring(
              0, clazzToInstanciate.getName().length() - 5));
        } catch (ClassNotFoundException e) {
          throw new GwtTestPatchException(
              "Error while trying to create a Guice provider for injector '"
                  + ginInjectorClass.getName() + "'", e);
        }
      } else {
        this.clazzToInstanciate = clazzToInstanciate;
      }
    }

    public Object get() {
      // call GWT deferred binding, which is patch by gwt-test-utils to call
      // GwtCreateHandlerManager
      return GWT.create(clazzToInstanciate);
    }

  }

  private static final Logger LOGGER = LoggerFactory.getLogger(DeferredBindingModule.class);

  private final Set<Class<?>> bindedClasses;
  private final Set<Class<?>> classesToInstanciate;

  private final Class<? extends Ginjector> ginInjectorClass;

  DeferredBindingModule(Class<? extends Ginjector> ginInjectorClass,
      Module[] modules) {

    this.ginInjectorClass = ginInjectorClass;
    List<Element> elements = Elements.getElements(modules);
    this.classesToInstanciate = collectClassesToInstanciate(ginInjectorClass);
    this.classesToInstanciate.addAll(collectDependencies(elements));
    this.bindedClasses = collectBindedClasses(elements);

  }

  @SuppressWarnings({"unchecked"})
  @Override
  protected void configure() {

    for (Class<?> toInstanciate : classesToInstanciate) {
      if (!bindedClasses.contains(toInstanciate)) {
        bind((Class<Object>) toInstanciate).toProvider(
            new DeferredBindingProvider(ginInjectorClass, toInstanciate));
      }
    }
  }

  private Set<Class<?>> collectBindedClasses(List<Element> elements) {
    final Set<Class<?>> bindedClasses = new HashSet<Class<?>>();

    for (Element e : elements) {
      e.acceptVisitor(new DefaultElementVisitor<Void>() {
        @Override
        public <T> Void visit(Binding<T> binding) {
          bindedClasses.add(binding.getKey().getTypeLiteral().getRawType());
          return null;
        }
      });
    }

    return bindedClasses;
  }

  private Set<Class<?>> collectClassesToInstanciate(Class<?> classLiteral) {

    Set<Class<?>> classesToInstanciate = new HashSet<Class<?>>();

    for (Method m : classLiteral.getMethods()) {
      if (m.getGenericParameterTypes().length > 0) {
        // This method has non-zero argument list. We cannot do anything
        // about it, so inform developer and continue
        LOGGER.warn("skipping method '" + m.toGenericString()
            + "' because it has non-zero argument list");
        continue;
      }

      Class<?> literal = m.getReturnType();

      classesToInstanciate.add(literal);

    }

    return classesToInstanciate;
  }

  private Set<Class<?>> collectDependencies(List<Element> elements) {
    final Set<Class<?>> dependencies = new HashSet<Class<?>>();
    for (Element e : elements) {
      e.acceptVisitor(new DefaultElementVisitor<Void>() {
        @Override
        public <T> Void visit(Binding<T> binding) {
          LOGGER.debug("visiting binding " + binding.toString());

          if (binding instanceof HasDependencies) {
            HasDependencies deps = (HasDependencies) binding;
            for (Dependency<?> d : deps.getDependencies()) {
              InjectionPoint point = InjectionPoint.forConstructorOf(d.getKey().getTypeLiteral());
              dependencies.addAll(getDependencies(point));
            }
          } else {
            // At least try to fix the dependecies for untargeted bindings
            InjectionPoint point = InjectionPoint.forConstructorOf(binding.getKey().getTypeLiteral());
            dependencies.addAll(getDependencies(point));

          }

          return null;
        }
      });
    }

    return dependencies;
  }

  private Set<Class<?>> getDependencies(InjectionPoint point) {
    Set<Class<?>> dependencies = new HashSet<Class<?>>();
    for (Dependency<?> d1 : point.getDependencies()) {
      Class<?> classLiteral = d1.getKey().getTypeLiteral().getRawType();
      dependencies.add(classLiteral);
    }

    return dependencies;
  }
}
