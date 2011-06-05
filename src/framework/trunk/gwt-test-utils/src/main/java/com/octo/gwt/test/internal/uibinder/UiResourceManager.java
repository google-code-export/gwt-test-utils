package com.octo.gwt.test.internal.uibinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;
import com.octo.gwt.test.exceptions.GwtTestResourcesException;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;

class UiResourceManager {

  public static final UiResourceManager newInstance(Object owner) {
    return new UiResourceManager(owner);
  }

  private final Object owner;

  private final Map<String, Object> resources = new HashMap<String, Object>();

  private UiResourceManager(Object owner) {
    this.owner = owner;
  }

  public Object getUiResource(String alias) {
    return resources.get(alias);
  }

  public UiBinderResource registerResource(String localName,
      Attributes attributes) {

    String alias = getResourceAlias(localName, attributes);

    if (resources.containsKey(alias)) {
      throw new GwtTestUiBinderException("Two inner resources '" + alias
          + " are declared in " + owner.getClass().getSimpleName()
          + ".ui.xml. You should add a 'field' attribute to one of them");
    }

    Class<?> clazz = getResourceClass(alias, localName, attributes);

    if (clazz == null) {
      return new UiBinderResource();
    }

    Object resource;

    if ("with".equals(localName)) {
      resource = GWT.create(clazz);
    } else {
      resource = instanciateInnerResource(alias, clazz);
    }
    resources.put(alias, resource);

    return new UiBinderResource(resource, alias, owner);
  }

  private String getResourceAlias(String localName, Attributes attributes) {
    String alias;
    alias = attributes.getValue("field");
    if (alias == null && !"with".equals(localName)) {
      alias = localName;
    }
    if (alias == null) {
      throw new GwtTestUiBinderException(
          "Cannot find the required 'field' value for tag <" + localName
              + "> in " + owner.getClass().getSimpleName() + ".ui.xml");
    }

    return alias;
  }

  private Class<?> getResourceClass(String alias, String localName,
      Attributes attributes) {
    String type = attributes.getValue("type");

    if (type == null) {
      // the resource here should be an inner <style> with no associated
      // CssResource class
      return null;
    }

    try {
      return Class.forName(type);
    } catch (ClassNotFoundException e1) {
      // it can be an inner class
      int lastIndex = type.lastIndexOf('.');
      String innerTypeName = type.substring(0, lastIndex) + "$"
          + type.substring(lastIndex + 1);
      try {
        return Class.forName(innerTypeName);
      } catch (ClassNotFoundException e2) {
        throw new GwtTestUiBinderException("Cannot find class '" + type
            + "' for resource '" + alias + "' declared in file '"
            + owner.getClass().getSimpleName() + ".ui.xml'");
      }
    }
  }

  private Object instanciateCssResource(final String alias, final Class<?> clazz) {
    InvocationHandler ih = new InvocationHandler() {
      public Object invoke(Object proxy, Method method, Object[] args)
          throws Throwable {
        if (method.getName().equals("getName")) {
          return alias;
        }

        throw new GwtTestResourcesException("Not managed method \""
            + method.getName() + "\" for generated " + clazz.getSimpleName()
            + " proxy");
      }
    };

    return Proxy.newProxyInstance(clazz.getClassLoader(),
        new Class<?>[]{clazz}, ih);
  }

  private Object instanciateImageResource(String alias, Class<?> clazz) {
    // TODO Auto-generated method stub
    return null;
  }

  private Object instanciateInnerResource(String alias, Class<?> clazz) {
    if (CssResource.class.isAssignableFrom(clazz)) {
      return instanciateCssResource(alias, clazz);
    } else if (TextResource.class.isAssignableFrom(clazz)) {
      return instanciateTextResource(alias, clazz);
    } else if (ImageResource.class.isAssignableFrom(clazz)) {
      return instanciateImageResource(alias, clazz);
    } else {
      throw new GwtTestUiBinderException(
          "Not managed UiBinder inner resource type '" + clazz.getName() + "'");
    }
  }

  private Object instanciateTextResource(String alias, Class<?> clazz) {
    // TODO Auto-generated method stub
    return null;
  }

}
