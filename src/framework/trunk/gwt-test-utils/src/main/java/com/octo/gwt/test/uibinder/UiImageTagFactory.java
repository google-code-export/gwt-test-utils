package com.octo.gwt.test.uibinder;

import java.util.Map;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;

class UiImageTagFactory implements UiWidgetTagFactory<Image> {

  private static class UiImageTag extends UiWidgetTag<Image> {

    @Override
    protected void finalizeWidget(Image widget) {
      // noting to do
    }

    @Override
    protected void initializeWidget(Image wrapped,
        Map<String, Object> attributes, Object owner) {
      // nothing to do
    }

    @Override
    protected Image instanciate(Class<? extends Image> widgetClass,
        Map<String, Object> attributes, Object owner) {

      if (widgetClass == Image.class) {

        ImageResource imageResource = (ImageResource) attributes.get("resource");
        if (imageResource != null) {
          return new Image(imageResource);
        }

        String url = (String) attributes.get("url");
        if (url != null) {
          return new Image(url);
        }

      }

      // unable to use custom constructor or is a subclass of Image, so use
      // default mechanism
      return super.instanciate(widgetClass, attributes, owner);
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.octo.gwt.test.uibinder.UiBinderWidgetFactory#createUiWidgetTag
   * (java.lang.Class, java.util.Map)
   */
  public UiWidgetTag<Image> createUiWidgetTag(
      Class<? extends IsWidget> widgetClass, Map<String, Object> attributes) {

    if (Image.class.isAssignableFrom(widgetClass)) {
      return new UiImageTag();
    }

    return null;
  }

}
