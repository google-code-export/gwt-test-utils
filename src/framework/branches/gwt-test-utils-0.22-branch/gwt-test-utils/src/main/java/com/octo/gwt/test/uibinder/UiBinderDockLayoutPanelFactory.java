package com.octo.gwt.test.uibinder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Handles <g:DockLayoutPanel /> tags.
 * 
 * @author Gael Lazzari
 * 
 */
class UiBinderDockLayoutPanelFactory implements UiBinderWidgetFactory {

  private static class UiBinderDockLayoutPanel extends
      UiBinderWidget<DockLayoutPanel> {

    private final List<Widget> centerWidgets = new ArrayList<Widget>();
    private double eastSize;
    private Widget eastWidget;
    private double northSize;
    private Widget northWidget;
    private double southSize;
    private Widget southWidget;
    private double westSize;
    private Widget westWidget;

    private UiBinderDockLayoutPanel(DockLayoutPanel wrapped,
        Attributes attributes, UiBinderTag parentTag, Object owner,
        UiResourceManager resourceManager) {
      super(wrapped, attributes, parentTag, owner, resourceManager);
    }

    @Override
    protected void appendElement(DockLayoutPanel wrapped, Element element,
        String namespaceURI, List<Widget> childWidgets) {

      if (!UiBinderXmlUtils.CLIENTUI_NSURI.equals(namespaceURI)) {
        super.appendElement(wrapped, element, namespaceURI, childWidgets);
      } else {

        if (childWidgets.size() > 0) {
          handleDockLayoutPanelSpecifics(wrapped, element, childWidgets);
        }
      }
    }

    @Override
    protected void finalizeWidget(DockLayoutPanel widget) {
      super.finalizeWidget(widget);

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
      for (Widget centerWidget : centerWidgets) {
        widget.add(centerWidget);
      }
    }

    private void handleDockLayoutPanelSpecifics(DockLayoutPanel wrapped,
        Element child, List<Widget> childWidgets) {
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

  public UiBinderWidget<? extends Widget> createUiBinderWidget(Widget widget,
      Attributes attributes, UiBinderTag parentTag, Object owner,
      UiResourceManager resourceManager) {

    if (!DockLayoutPanel.class.isInstance(widget)) {
      return null;
    }

    return new UiBinderDockLayoutPanel((DockLayoutPanel) widget, attributes,
        parentTag, owner, resourceManager);
  }
}
