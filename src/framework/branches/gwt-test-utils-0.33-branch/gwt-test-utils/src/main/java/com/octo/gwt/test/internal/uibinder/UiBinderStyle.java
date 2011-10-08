package com.octo.gwt.test.internal.uibinder;

import java.util.Map;

import com.google.gwt.resources.client.CssResource;
import com.octo.gwt.test.internal.resources.ResourcePrototypeProxyBuilder;

/**
 * Handle <ui:style> tag with a "type" attribute to specify a
 * {@link CssResource} subtype.
 * 
 * @author Gael Lazzari
 * 
 */
class UiBinderStyle extends UiBinderResourceTag {

  private final StringBuilder text;

  UiBinderStyle(ResourcePrototypeProxyBuilder builder, String alias,
      UiBinderTag parentTag, Object owner, Map<String, Object> resources) {
    super(builder, alias, parentTag, owner, resources);
    this.text = new StringBuilder();
  }

  @Override
  public void appendText(String text) {
    this.text.append(text.trim());
  }

  @Override
  protected Object buildObject(ResourcePrototypeProxyBuilder builder) {
    return builder.text(text.toString()).build();
  }

}
