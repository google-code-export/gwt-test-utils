package com.octo.gwt.test.uibinder.widget;

import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.uibinder.UiWidgetTag;
import com.octo.gwt.test.uibinder.UiWidgetTagFactory;

/**
 * Handles <g:TabLayoutPanel /> tags.
 * 
 * @author Gael Lazzari
 * 
 */
public class UiTabLayoutPanelTagFactory implements
    UiWidgetTagFactory<TabLayoutPanel> {

  private static class UiTabLayoutPanelTag extends UiWidgetTag<TabLayoutPanel> {

    @Override
    protected void finalizeWidget(TabLayoutPanel widget) {
      // TODO Auto-generated method stub

    }

    @Override
    protected void initializeWidget(TabLayoutPanel wrapped,
        Map<String, Object> attributes, Object owner) {
      // TODO Auto-generated method stub

    }

    @Override
    protected TabLayoutPanel instanciate(
        Class<? extends TabLayoutPanel> widgetClass,
        Map<String, Object> attributes, Object owner) {

      if (widgetClass == TabLayoutPanel.class) {
        String barHeight = (String) attributes.get("barHeight");

        if (barHeight == null) {
          throw new GwtTestUiBinderException(
              "Missing mandatory attribute 'barHeight' in a TabLayoutPanel declared in "
                  + owner.getClass().getSimpleName() + ".ui.xml file");
        }

        String unit = (String) attributes.get("unit");
        Unit styleUnit = unit != null ? Unit.valueOf(unit) : Unit.PX;

        return new TabLayoutPanel(Double.valueOf(barHeight), styleUnit);
      }

      // use default instanciation system
      return super.instanciate(widgetClass, attributes, owner);
    }

  }

  public UiWidgetTag<TabLayoutPanel> createUiWidgetTag(
      Class<? extends IsWidget> widgetClass, Map<String, Object> attributes) {

    if (TabLayoutPanel.class.isAssignableFrom(widgetClass)) {
      return new UiTabLayoutPanelTag();
    }

    return null;
  }

}
