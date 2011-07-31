package com.octo.gwt.test.internal.uibinder;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
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

    Class<?> type = getResourceType(alias, localName, attributes);

    if ("with".equals(localName)) {
      // special resource <ui:with>
      Object resource = GWT.create(type);
      resources.put(alias, resource);
      return new UiBinderWith(resource);

    }

    ResourcePrototypeProxyBuilder builder = ResourcePrototypeProxyBuilder.createBuilder(
        type, owner.getClass());
    // common properties
    builder.name(alias);

    if ("style".equals(localName)) {
      // <ui:style>
      return new UiBinderStyle(builder, alias, parentTag, owner, resources);

    } else if ("image".equals(localName)) {
      // <ui:image>
      return new UiBinderImage(builder, alias, parentTag, owner, resources,
          attributes);

    } else if ("data".equals(localName)) {
      // <ui:data>
      return new UiBinderData(builder, alias, parentTag, owner, resources,
          attributes);

    } else {
      throw new GwtTestUiBinderException("resource <" + localName
          + "> element is not yet implemented by gwt-test-utils");
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

  private Class<?> getResourceType(String alias, String localName,
      Attributes attributes) {
    String type = attributes.getValue("type");

    if (type == null && "image".equals(localName)) {
      // special code for <ui:image> with no 'type' attribute
      return ImageResource.class;
    } else if (type == null && "style".equals(localName)) {
      // special code for <ui:style> with no 'type' attribute
      return CssResource.class;
    } else if (type == null && "data".equals(localName)) {
      // special code for <ui:data> with no 'type' attribute
      return DataResource.class;
    } else if (type == null) {
      throw new GwtTestUiBinderException("<" + localName
          + "> element declared in " + owner.getClass().getSimpleName()
          + ".ui.xml must specify a 'type' attribute");
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
