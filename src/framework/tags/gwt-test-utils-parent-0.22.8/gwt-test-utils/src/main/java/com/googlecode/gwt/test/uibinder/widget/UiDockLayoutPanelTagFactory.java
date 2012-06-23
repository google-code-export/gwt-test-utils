package com.googlecode.gwt.test.uibinder.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.test.uibinder.UiBinderXmlUtils;
import com.googlecode.gwt.test.uibinder.UiObjectTag;
import com.googlecode.gwt.test.uibinder.UiObjectTagFactory;

/**
 * Handles &lt;g:DockLayoutPanel> tags.
 * 
 * @author Gael Lazzari
 * 
 */
public class UiDockLayoutPanelTagFactory implements
    UiObjectTagFactory<DockLayoutPanel> {

  private static class UiDockLayoutPanelTag extends
      UiObjectTag<DockLayoutPanel> {

    private final List<Widget> centerWidgets = new ArrayList<Widget>();
    private double eastSize;
    private Widget eastWidget;
    private double northSize;
    private Widget northWidget;
    private double southSize;
    private Widget southWidget;
    private double westSize;
    private Widget westWidget;

    @Override
    protected void appendElement(DockLayoutPanel wrapped, Element element,
        String namespaceURI, List<Widget> childWidgets) {

      if (!UiBinderXmlUtils.CLIENTUI_NSURI.equals(namespaceURI)) {
        super.appendElement(wrapped, element, namespaceURI, childWidgets);
      } else {
        handleDockLayoutPanelSpecifics(wrapped, element, childWidgets);
      }
    }

    @Override
    protected void finalizeObject(DockLayoutPanel widget) {

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

    @Override
    protected void initializeObject(DockLayoutPanel wrapped,
        Map<String, Object> attributes, Object owner) {
      // nothing to do

    }

    @Override
    protected DockLayoutPanel instanciate(
        Class<? extends DockLayoutPanel> clazz, Map<String, Object> attributes,
        Object owner) {

      if (clazz == DockLayoutPanel.class) {
        String unit = (String) attributes.get("unit");
        Unit styleUnit = unit != null ? Unit.valueOf(unit) : Unit.PX;

        return new DockLayoutPanel(styleUnit);
      }

      // use default instanciation system
      return super.instanciate(clazz, attributes, owner);
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

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.uibinder.UiObjectTagFactory#createUiObjectTag
   * (java.lang.Class, java.util.Map)
   */
  public UiObjectTag<DockLayoutPanel> createUiObjectTag(Class<?> clazz,
      Map<String, Object> attributes) {

    if (!DockLayoutPanel.class.isAssignableFrom(clazz)) {
      return null;
    }

    return new UiDockLayoutPanelTag();
  }

}
