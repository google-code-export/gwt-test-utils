package com.octo.gwt.test.uibinder.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.octo.gwt.test.uibinder.UiBinderXmlUtils;
import com.octo.gwt.test.uibinder.UiWidgetTag;
import com.octo.gwt.test.uibinder.UiWidgetTagFactory;

/**
 * Handles <g:DockLayoutPanel /> tags.
 * 
 * @author Gael Lazzari
 * 
 */
public class UiDockLayoutPanelTagFactory implements
    UiWidgetTagFactory<DockLayoutPanel> {

  private static class UiDockLayoutPanelTag extends
      UiWidgetTag<DockLayoutPanel> {

    private final List<IsWidget> centerWidgets = new ArrayList<IsWidget>();
    private double eastSize;
    private IsWidget eastWidget;
    private double northSize;
    private IsWidget northWidget;
    private double southSize;
    private IsWidget southWidget;
    private double westSize;
    private IsWidget westWidget;

    @Override
    protected void appendElement(DockLayoutPanel wrapped, Element element,
        String namespaceURI, List<IsWidget> childWidgets) {

      if (!UiBinderXmlUtils.CLIENTUI_NSURI.equals(namespaceURI)) {
        super.appendElement(wrapped, element, namespaceURI, childWidgets);
      } else {
        handleDockLayoutPanelSpecifics(wrapped, element, childWidgets);
      }
    }

    @Override
    protected void finalizeWidget(DockLayoutPanel widget) {

      if (northWidget != null) {
        widget.addNorth(northWidget, northSize);
      }
      if (southWidget != null) {
        widget.addSouth(southWidget, southSize);
      }
      if (eastWidget != null) {
        widget.addEast(eastWidget, eastSize);
      }
      if (westWidget != null) {
        widget.addWest(westWidget, westSize);
      }
      for (IsWidget centerWidget : centerWidgets) {
        widget.add(centerWidget);
      }
    }

    @Override
    protected void initializeWidget(DockLayoutPanel wrapped,
        Map<String, Object> attributes, Object owner) {
      // nothing to do

    }

    @Override
    protected DockLayoutPanel instanciate(
        Class<? extends DockLayoutPanel> widgetClass,
        Map<String, Object> attributes, Object owner) {

      if (widgetClass == DockLayoutPanel.class) {
        String unit = (String) attributes.get("unit");
        Unit styleUnit = unit != null ? Unit.valueOf(unit) : Unit.PX;

        return new DockLayoutPanel(styleUnit);
      } else if (widgetClass == SplitLayoutPanel.class) {

        String splitterSize = (String) attributes.get("splitterSize");

        return splitterSize != null ? new SplitLayoutPanel(
            Integer.valueOf(splitterSize)) : new SplitLayoutPanel();

      }

      // use default instanciation system
      return super.instanciate(widgetClass, attributes, owner);
    }

    private void handleDockLayoutPanelSpecifics(DockLayoutPanel wrapped,
        Element child, List<IsWidget> childWidgets) {
      String tagName = child.getTagName();
      if ("center".equals(tagName)) {
        centerWidgets.addAll(childWidgets);
      } else if ("north".equals(tagName)) {
        String size = child.getPropertyString("size");
        northSize = StringUtils.isEmpty(size) ? 0 : Double.valueOf(size);
        northWidget = childWidgets.get(0);
      } else if ("south".equals(tagName)) {
        String size = child.getPropertyString("size");
        southSize = StringUtils.isEmpty(size) ? 0 : Double.valueOf(size);
        southWidget = childWidgets.get(0);
      } else if ("west".equals(tagName)) {
        String size = child.getPropertyString("size");
        westSize = StringUtils.isEmpty(size) ? 0 : Double.valueOf(size);
        westWidget = childWidgets.get(0);
      } else if ("east".equals(tagName)) {
        String size = child.getPropertyString("size");
        eastSize = StringUtils.isEmpty(size) ? 0 : Double.valueOf(size);
        eastWidget = childWidgets.get(0);
      }

    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.uibinder.UiBinderWidgetFactory#createUiWidgetTag
   * (java.lang.Class, java.util.Map)
   */
  public UiWidgetTag<DockLayoutPanel> createUiWidgetTag(
      Class<? extends IsWidget> widgetClass, Map<String, Object> attributes) {

    if (!DockLayoutPanel.class.isAssignableFrom(widgetClass)) {
      return null;
    }

    return new UiDockLayoutPanelTag();
  }

}
