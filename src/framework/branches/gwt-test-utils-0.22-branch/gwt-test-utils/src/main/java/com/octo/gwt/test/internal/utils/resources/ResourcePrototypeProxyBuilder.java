package com.octo.gwt.test.internal.utils.resources;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ResourcePrototype;
import com.google.gwt.resources.client.TextResource;
import com.octo.gwt.test.exceptions.GwtTestResourcesException;

/**
 * Builder in charge of the creation of {@link ResourcePrototype} proxies.
 * <strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class ResourcePrototypeProxyBuilder {

  public static ResourcePrototypeProxyBuilder createBuilder(
      Class<?> proxiedClass, Class<?> ownerClass) {
    return new ResourcePrototypeProxyBuilder(proxiedClass, ownerClass);
  }

  private String name;
  private final Class<?> ownerClass;
  private final Class<?> proxiedClass;
  private String text;

  private URL url;

  private ResourcePrototypeProxyBuilder(Class<?> proxiedClass,
      Class<?> ownerClass) {
    if (!proxiedClass.isInterface()) {
      throw new GwtTestResourcesException(
          "Cannot create a resource proxy instance for '"
              + proxiedClass.getName() + "' because it is not an interface");
    }
    this.proxiedClass = proxiedClass;
    this.ownerClass = ownerClass;
  }

  public Object build() {

    ResourcePrototypeCallback callback;

    if (TextResource.class.isAssignableFrom(proxiedClass)) {
      callback = (url != null) ? new TextResourceCallback(url)
          : new TextResourceCallback(text);
    } else if (CssResource.class.isAssignableFrom(proxiedClass)) {
      callback = (url != null) ? new CssResourceCallback(url)
          : new CssResourceCallback(text);
    } else if (DataResource.class.isAssignableFrom(proxiedClass)) {
      callback = new DataResourceCallback(computeUrl(url, ownerClass));
    } else if (ImageResource.class.isAssignableFrom(proxiedClass)) {
      callback = new ImageResourceCallback(computeUrl(url, ownerClass));
    } else {
      throw new GwtTestResourcesException(
          "Not managed return type for ClientBundle : "
              + proxiedClass.getName());
    }

    InvocationHandler resourceInvocationHandler = generateInvocationHandler(callback);
    return Proxy.newProxyInstance(this.getClass().getClassLoader(),
        new Class<?>[]{proxiedClass}, resourceInvocationHandler);
  }

  public ResourcePrototypeProxyBuilder name(String name) {
    this.name = name;
    return this;
  }

  public ResourcePrototypeProxyBuilder resourceURL(URL url) {
    this.url = url;
    return this;
  }

  public ResourcePrototypeProxyBuilder text(String text) {
    this.text = text;
    return this;
  }

  private String computeUrl(URL resourceURL, Class<?> resourceClass) {
    String packagePath = resourceClass.getPackage().getName().replaceAll("\\.",
        "/");

    int startIndex = resourceURL.getPath().indexOf(packagePath);

    if (startIndex == -1) {
      if (resourceClass.getInterfaces() != null
          && resourceClass.getInterfaces().length == 1) {
        return computeUrl(resourceURL, resourceClass.getInterfaces()[0]);
      } else {
        throw new GwtTestResourcesException(
            "Cannot compute the relative URL of resource '" + resourceURL + "'");
      }
    }
    String resourceRelativePath = resourceURL.getPath().substring(startIndex);

    return GWT.getModuleBaseURL() + resourceRelativePath;
  }

  private InvocationHandler generateInvocationHandler(
      final ResourcePrototypeCallback callback) {
    InvocationHandler ih = new InvocationHandler() {
      public Object invoke(Object proxy, Method method, Object[] args)
          throws Throwable {
        if (method.getName().equals("getName")) {
          return name;
        } else if (callback != null) {
          Object result = callback.call(method, args);
          if (result != null) {
            return result;
          }
        }
        throw new GwtTestResourcesException("Not managed method '"
            + method.getName() + "' for generated '" + proxiedClass.getName()
            + "' proxy");
      }
    };

    return ih;
  }
}
