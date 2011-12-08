package com.octo.gwt.test.uibinder.widget;

import java.util.Map;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.IsWidget;
import com.octo.gwt.test.uibinder.UiWidgetTag;
import com.octo.gwt.test.uibinder.UiWidgetTagFactory;

/**
 * Handles <g:DateLabel /> tags.
 * 
 * @author Gael Lazzari
 * 
 */
public class UiDateLabelTagFactory implements UiWidgetTagFactory<DateLabel> {

  private static class UiDateLabelTag extends UiWidgetTag<DateLabel> {

    @Override
    protected void finalizeWidget(DateLabel widget) {
      // nothing to do
    }

    @Override
    protected void initializeWidget(DateLabel wrapped,
        Map<String, Object> attributes, Object owner) {
      // nothing to do
    }

    @Override
    protected DateLabel instanciate(Class<? extends DateLabel> widgetClass,
        Map<String, Object> attributes, Object owner) {

      if (widgetClass == DateLabel.class) {
        DateTimeFormat format = (DateTimeFormat) attributes.get("format");

        if (format != null) {
          return new DateLabel(format);
        }

        String predefinedFormat = (String) attributes.get("predefinedFormat");
        if (predefinedFormat != null) {
          PredefinedFormat predef = PredefinedFormat.valueOf(predefinedFormat);
          return new DateLabel(DateTimeFormat.getFormat(predef));
        }

        String customFormat = (String) attributes.get("customFormat");
        if (customFormat != null) {
          return new DateLabel(DateTimeFormat.getFormat(customFormat));
        }

      }

      // unable to use custom constructor or is a subclass of DateLabel, so use
      // default mechanism
      return super.instanciate(widgetClass, attributes, owner);
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.uibinder.UiBinderWidgetFactory#createUiWidgetTag
   * (java.lang.Class, java.util.Map)
   */
  public UiWidgetTag<DateLabel> createUiWidgetTag(
      Class<? extends IsWidget> widgetClass, Map<String, Object> attributes) {

    if (DateLabel.class.isAssignableFrom(widgetClass)) {
      return new UiDateLabelTag();
    }

    return null;
  }

}
