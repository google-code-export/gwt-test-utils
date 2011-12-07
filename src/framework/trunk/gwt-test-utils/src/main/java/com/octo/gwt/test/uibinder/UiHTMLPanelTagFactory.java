package com.octo.gwt.test.uibinder;

import java.util.Map;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;

class UiHTMLPanelTagFactory implements UiWidgetTagFactory<HTMLPanel> {

  private static class UiHTMLPanelTag extends UiWidgetTag<HTMLPanel> {

    @Override
    protected void finalizeWidget(HTMLPanel widget) {
      // nothing to do
    }

    @Override
    protected void initializeWidget(HTMLPanel wrapped,
        Map<String, Object> attributes, Object owner) {
      // nothing to do
    }

    @Override
    protected HTMLPanel instanciate(Class<? extends HTMLPanel> widgetClass,
        Map<String, Object> attributes, Object owner) {

      if (widgetClass == HTMLPanel.class) {
        return new HTMLPanel("");
      }

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
  public UiWidgetTag<HTMLPanel> createUiWidgetTag(
      Class<? extends IsWidget> widgetClass, Map<String, Object> attributes) {

    if (HTMLPanel.class.isAssignableFrom(widgetClass)) {
      return new UiHTMLPanelTag();
    }

    return null;
  }

}
