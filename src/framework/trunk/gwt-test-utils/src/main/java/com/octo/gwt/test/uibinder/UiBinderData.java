package com.octo.gwt.test.uibinder;

import java.net.URL;
import java.util.Map;

import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.internal.resources.ResourcePrototypeProxyBuilder;

/**
 * Handles <ui:data /> tags.
 * 
 * @author Gael Lazzari
 * 
 */
class UiBinderData extends UiBinderResourceTag {

  UiBinderData(ResourcePrototypeProxyBuilder builder, String alias,
      UiBinderTag parentTag, Object owner, Map<String, Object> resources,
      Map<String, Object> attributes) {
    super(builder, alias, parentTag, owner, resources);

    // handle "src" attribute
    String src = (String) attributes.get("src");
    builder.resourceURL(computeImageURL(owner, src));
  }

  @Override
  protected Object buildObject(ResourcePrototypeProxyBuilder builder) {
    return builder.build();
  }

  private URL computeImageURL(Object owner, String src) {
    URL dataURL = owner.getClass().getResource(src);

    if (dataURL == null) {
      throw new GwtTestUiBinderException("Cannot find binary file with src=\""
          + src + "\" declared in " + owner.getClass().getSimpleName()
          + ".ui.xml");
    }

    return dataURL;
  }
}
