package com.octo.gwt.test.internal.uibinder;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.internal.utils.resources.ResourcePrototypeProxyBuilder;

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

  public UiBinderTag registerResource(String localName, Attributes attributes,
      UiBinderTag parentTag) {

    String alias = getResourceAlias(localName, attributes);

    if (resources.containsKey(alias)) {
      throw new GwtTestUiBinderException("Two inner resources '" + alias
          + " are declared in " + owner.getClass().getSimpleName()
          + ".ui.xml. You should add a 'field' attribute to one of them");
    }

    Class<?> clazz = getResourceClass(alias, localName, attributes);

    if (clazz == null) {
      return new UiBinderIgnoreTag(null);
    }

    Object resource;

    if ("with".equals(localName)) {
      resource = GWT.create(clazz);
      resources.put(alias, resource);
      return new UiBinderIgnoreTag(resource);

    } else {
      ResourcePrototypeProxyBuilder builder = ResourcePrototypeProxyBuilder.createBuilder(
          clazz, owner.getClass()).name(alias);

      return new UiBinderInnerResource(builder, alias, parentTag, owner,
          resources);

    }
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

}
