/*
 * Copyright 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.googlecode.gwt.test.internal.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.dev.cfg.BindingProperty;
import com.google.gwt.dev.cfg.ConfigurationProperty;
import com.google.gwt.dev.cfg.ModuleDef;
import com.google.gwt.dev.cfg.PropertyPermutations;
import com.google.gwt.dev.cfg.StaticPropertyOracle;
import com.google.gwt.dev.javac.CompilationState;
import com.google.gwt.dev.javac.CompiledClass;
import com.google.gwt.dev.shell.ModuleSpaceHost;
import com.google.gwt.dev.util.Name;
import com.google.gwt.junit.client.WithProperties;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import com.googlecode.gwt.test.internal.GwtFactory;
import com.googlecode.gwt.test.internal.GwtTestDataHolder;

/**
 * This is a wrapper around GWT's compilation tools
 */
public class GeneratorCreateHandler {

   private static Map<String, ModuleSpaceHost> moduleSpaceHosts = new HashMap<String, ModuleSpaceHost>();

   protected Map<String, CompiledClass> compiledClassMap = new HashMap<String, CompiledClass>();

   private final CompilationState compilationState;
   private ModuleDef gwtModule;

   private final TreeLogger logger = TreeLogger.NULL;
   private final Map<String, String> propertiesOverrides;

   private PropertyOracle propertyOracle;

   public GeneratorCreateHandler(CompilationState compilationState,
            ModuleSpaceHost moduleSpaceHost, Map<String, String> propertiesOverrides) {
      this.compilationState = compilationState;
      this.propertiesOverrides = ImmutableMap.copyOf(propertiesOverrides);

   }

   public CompiledClass compile(String literalName) {
      CompiledClass compiledClass = compiledClassMap.get(literalName);
      if (compiledClass != null) {
         logger.log(Type.INFO, "Using cached resource for " + literalName);
         return compiledClass;
      }

      if (gwtModule == null) {
         throw new RuntimeException("Gwt module is not set.");
      }

      logger.log(Type.INFO, "Generating " + literalName);

      String className;
      try {
         className = getModuleSpaceHost().rebind(logger, literalName);
      } catch (UnableToCompleteException e) {
         throw new RuntimeException(e);
      }

      String internalName = Name.BinaryName.toInternalName(className);

      compiledClass = compilationState.getClassFileMap().get(internalName);

      if (compiledClass != null) {
         compiledClassMap.put(literalName, compiledClass);
      }

      return compiledClass;
   }

   public Class<?> generate(Class<?> classLiteral) {
      CompiledClass compiledClass = compile(classLiteral.getCanonicalName());
      if (compiledClass == null) {
         throw new RuntimeException("Could not generate class " + classLiteral);
      }
      try {
         return GwtFactory.get().getClassLoader().loadClass(compiledClass.getInternalName());
      } catch (ClassNotFoundException e) {
         throw new RuntimeException(e);
      }
   }

   public ModuleDef getGwtModule() {
      return gwtModule;
   }

   public <T> T instantiateGeneratedClass(Class<T> classLiteral) {
      Class<?> c = generate(classLiteral);

      try {
         return classLiteral.cast(c.newInstance());
      } catch (InstantiationException e) {
         throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
         throw new RuntimeException(e);
      }
   }

   protected PropertyOracle getPropertyOracle() {
      if (propertyOracle == null) {
         PropertyPermutations permutations = new PropertyPermutations(gwtModule.getProperties(),
                  gwtModule.getActiveLinkerNames());

         SortedSet<ConfigurationProperty> configPropSet = gwtModule.getProperties().getConfigurationProperties();
         ConfigurationProperty[] configProps = configPropSet.toArray(new ConfigurationProperty[configPropSet.size()]);

         BindingProperty[] orderedProperties = permutations.getOrderedProperties();

         String[] processedProperties = replaceOrderedPropertyValues(orderedProperties,
                  permutations.getOrderedPropertyValues(0));

         propertyOracle = new StaticPropertyOracle(orderedProperties, processedProperties,
                  configProps);
      }

      return propertyOracle;
   }

   protected void printClassLoaderChain(ClassLoader loader) {
      while (loader != null) {
         System.out.print("- " + loader.getClass().getCanonicalName());
         loader = loader.getParent();
      }
      System.out.println();
   }

   private ModuleSpaceHost getModuleSpaceHost() {
      WithProperties withProperties = GwtTestDataHolder.get().getCurrentWithProperties();
      String serializedProperties = serialize(withProperties);

      ModuleSpaceHost moduleSpaceHost = moduleSpaceHosts.get(serializedProperties);

      return moduleSpaceHost;
   }

   /**
    * Replace (if propertiesOverrides is specified) the property values based on
    * the specified overrides. Must only be called when propertiesOverrides is
    * not null (which is not supposed to be possible).
    */
   private String[] replaceOrderedPropertyValues(BindingProperty[] orderedProperties,
            String[] orderedOriginalValues) {

      assert propertiesOverrides != null;

      String[] result = new String[orderedProperties.length];
      for (int i = 0; i < orderedProperties.length; i++) {
         if (propertiesOverrides.containsKey(orderedProperties[i].getName())) {
            result[i] = propertiesOverrides.get(orderedProperties[i].getName());
         } else {
            result[i] = orderedOriginalValues[i];
         }
      }
      return result;
   }

   private String serialize(WithProperties withProperties) {
      // TODO Auto-generated method stub
      return null;
   }
}
