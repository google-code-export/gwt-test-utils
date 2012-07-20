package com.googlecode.gwt.test.internal.resources;

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
import com.googlecode.gwt.test.exceptions.GwtTestResourcesException;
import com.googlecode.gwt.test.internal.GwtClassPool;

class ClientBundleProxyFactory {
   private static class ClientBundleMethodsRegistry {

      private final CtClass ctClass;
      private final Map<Method, List<URL>> resourceURLMap = new HashMap<Method, List<URL>>();

      public ClientBundleMethodsRegistry(Class<? extends ClientBundle> clazz) {
         ctClass = GwtClassPool.getCtClass(clazz);
      }

      public List<URL> getResourceURL(Method method) throws Exception {
         List<URL> resourceURLs = resourceURLMap.get(method);
         if (resourceURLs == null) {
            resourceURLs = computeResourceURLs(method);
            resourceURLMap.put(method, resourceURLs);
         }

         return resourceURLs;
      }

      private List<URL> computeResourceURLs(Method method) throws NotFoundException,
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
            String baseDir = declaringClass.getPackage().getName().replaceAll("\\.", "/") + "/";
            String resourceNameWithPackage = resourceName.startsWith(baseDir) ? resourceName
                     : baseDir + resourceName;

            if (computeExtensions) {
               String[] extensions = getResourceDefaultExtensions(method);

               for (String extension : extensions) {
                  // try to find the file directly in the classpath
                  String possibleFile = resourceName + extension;
                  URL url = this.getClass().getClassLoader().getResource(possibleFile);
                  if (url != null) {
                     existingFiles.add(url);
                  } else {
                     // try to find the file relative to the ClientBundle
                     // subinterface's package
                     String possibleFileWithPackage = resourceNameWithPackage + extension;
                     URL urlWithPackage = this.getClass().getClassLoader().getResource(
                              possibleFileWithPackage);
                     if (urlWithPackage != null) {
                        existingFiles.add(urlWithPackage);
                     }
                  }
               }
            } else {
               URL url = this.getClass().getClassLoader().getResource(resourceName);
               if (url != null) {
                  existingFiles.add(url);
               } else {
                  // try to find the file relative to the ClientBundle
                  // subinterface's package
                  URL urlWithPackage = this.getClass().getClassLoader().getResource(
                           resourceNameWithPackage);
                  if (urlWithPackage != null) {
                     existingFiles.add(urlWithPackage);
                  }
               }

            }
         }

         if (existingFiles.isEmpty()) {
            throw new GwtTestResourcesException("No resource file found for method '"
                     + ctClass.getName() + "." + method.getName() + "()'");
         }

         return existingFiles;

      }

      private String[] getResourceDefaultExtensions(Method method) {
         DefaultExtensions annotation = method.getReturnType().getAnnotation(
                  DefaultExtensions.class);
         if (annotation == null) {
            throw new GwtTestResourcesException(
                     method.getReturnType().getSimpleName()
                              + " does not define a default extension for resource file. You should use a correct @"
                              + Source.class.getSimpleName() + " annotation on "
                              + method.getDeclaringClass().getSimpleName() + "." + method.getName()
                              + "() method");
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

   public static <T extends ClientBundle> ClientBundleProxyFactory getFactory(Class<T> clazz) {
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

         private final Map<Method, Object> cache = new HashMap<Method, Object>();

         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Object result = cache.get(method);

            if (result == null) {
               // create a ResourcePrototypeProxyBuilder with the good args
               Class<?> resourcePrototypeClass = method.getReturnType();
               String name = method.getName();
               List<URL> resourceURL = methodRegistry.getResourceURL(method);

               ResourcePrototypeProxyBuilder builder = ResourcePrototypeProxyBuilder.createBuilder(
                        resourcePrototypeClass, proxiedClass).name(name).resourceURLs(resourceURL);

               result = builder.build();
               cache.put(method, result);
            }

            return result;

         }

      };
      return (T) Proxy.newProxyInstance(proxiedClass.getClassLoader(),
               new Class<?>[]{proxiedClass}, ih);
   }

}
