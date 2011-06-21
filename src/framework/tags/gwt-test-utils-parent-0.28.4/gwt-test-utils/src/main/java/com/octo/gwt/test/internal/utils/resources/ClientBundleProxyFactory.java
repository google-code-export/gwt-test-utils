package com.octo.gwt.test.internal.utils.resources;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.CtClass;
import javassist.NotFoundException;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.resources.ext.DefaultExtensions;
import com.octo.gwt.test.exceptions.GwtTestResourcesException;
import com.octo.gwt.test.internal.GwtClassPool;

class ClientBundleProxyFactory {
  private static class ClientBundleMethodsRegistry {

    private final CtClass ctClass;
    private final Map<Method, URL> resourceURLs = new HashMap<Method, URL>();

    public ClientBundleMethodsRegistry(Class<? extends ClientBundle> clazz) {
      ctClass = GwtClassPool.getCtClass(clazz);
    }

    public URL getResourceURL(Method method) throws Exception {
      URL resourceURL = resourceURLs.get(method);
      if (resourceURL == null) {
        resourceURL = computeResourceURL(method);
        resourceURLs.put(method, resourceURL);
      }

      return resourceURL;
    }

    private URL computeResourceURL(Method method) throws NotFoundException,
        URISyntaxException {

      List<ResourceFileEntry> filesSimpleNames = new ArrayList<ResourceFileEntry>();
      boolean computeExtensions = false;
      Source source = method.getAnnotation(Source.class);

      if (source != null) {
        for (String value : source.value()) {
          filesSimpleNames.add(new ResourceFileEntry(value, method));
        }
      }

      if (filesSimpleNames.isEmpty()) {
        // no @Source annotation detected
        filesSimpleNames.add(new ResourceFileEntry(method.getName(), method));
        computeExtensions = true;
      }

      List<URL> existingFiles = new ArrayList<URL>();

      for (ResourceFileEntry resourceEntry : filesSimpleNames) {
        String resourceName = resourceEntry.resourceName;
        Class<?> declaringClass = resourceEntry.resourceMethod.getDeclaringClass();
        String baseDir = declaringClass.getPackage().getName().replaceAll(
            "\\.", "/")
            + "/";
        String fileName = (resourceName.startsWith(baseDir)) ? resourceName
            : baseDir + resourceName;

        if (computeExtensions) {
          String[] extensions = getResourceDefaultExtensions(method);

          for (String extension : extensions) {
            String possibleFile = fileName + extension;
            URL url = this.getClass().getClassLoader().getResource(possibleFile);
            if (url != null) {
              existingFiles.add(url);
            }
          }
        } else {
          URL url = this.getClass().getClassLoader().getResource(fileName);
          if (url != null) {
            existingFiles.add(url);
          }
        }
      }

      if (existingFiles.isEmpty()) {
        throw new GwtTestResourcesException(
            "No resource file found for method '" + ctClass.getName() + "."
                + method.getName() + "()'");
      } else if (existingFiles.size() > 1) {
        throw new GwtTestResourcesException(
            "Too many resource files found for method '" + ctClass.getName()
                + "." + method.getName() + "()'");
      }

      return existingFiles.get(0);

    }

    private String[] getResourceDefaultExtensions(Method method) {
      DefaultExtensions annotation = method.getReturnType().getAnnotation(
          DefaultExtensions.class);
      if (annotation == null) {
        throw new GwtTestResourcesException(
            method.getReturnType().getSimpleName()
                + " does not define a default extension for resource file. You should use a correct @"
                + Source.class.getSimpleName() + " annotation on "
                + method.getDeclaringClass().getSimpleName() + "."
                + method.getName() + "() method");
      } else {
        return annotation.value();
      }
    }

  }

  private static class ResourceFileEntry {

    private final Method resourceMethod;
    private final String resourceName;

    public ResourceFileEntry(String resourceName, Method resourceMethod) {
      this.resourceName = resourceName;
      this.resourceMethod = resourceMethod;
    }
  }

  private static Map<String, ClientBundleProxyFactory> factoryMap = new HashMap<String, ClientBundleProxyFactory>();

  public static <T extends ClientBundle> ClientBundleProxyFactory getFactory(
      Class<T> clazz) {
    ClientBundleProxyFactory factory = factoryMap.get(clazz.getName());
    if (factory == null) {
      factory = new ClientBundleProxyFactory(clazz);
      factoryMap.put(clazz.getName(), factory);
    }

    return factory;
  }

  private final ClientBundleMethodsRegistry methodRegistry;

  private final Class<? extends ClientBundle> proxiedClass;

  private ClientBundleProxyFactory(Class<? extends ClientBundle> proxiedClass) {
    this.proxiedClass = proxiedClass;
    methodRegistry = new ClientBundleMethodsRegistry(proxiedClass);
  }

  @SuppressWarnings("unchecked")
  public <T extends ClientBundle> T createProxy() {
    InvocationHandler ih = new InvocationHandler() {

      public Object invoke(Object proxy, Method method, Object[] args)
          throws Throwable {

        // create a ResourcePrototypeProxyBuilder with the good args
        Class<?> resourcePrototypeClass = method.getReturnType();
        String name = method.getName();
        URL resourceURL = methodRegistry.getResourceURL(method);

        ResourcePrototypeProxyBuilder builder = ResourcePrototypeProxyBuilder.createBuilder(
            resourcePrototypeClass, proxiedClass).name(name).resourceURL(
            resourceURL);

        return builder.build();
      }

    };
    return (T) Proxy.newProxyInstance(proxiedClass.getClassLoader(),
        new Class<?>[]{proxiedClass}, ih);
  }

}
