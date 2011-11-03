package com.octo.gwt.test.uibinder;

import java.util.List;

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

    private void handleDockLayoutPanelSpecifics(DockLayoutPanel wrapped,
        Element child, List<Widget> childWidgets) {
      String tagName = child.getTagName();
      if ("center".equals(tagName)) {
        for (Widget childCenterWidget : childWidgets) {
          wrapped.add(childCenterWidget);
        }
      } else if ("north".equals(tagName)) {
        String size = child.getPropertyString("size");
        wrapped.addNorth(childWidgets.get(0), Double.valueOf(size));
      } else if ("south".equals(tagName)) {
        String size = child.getPropertyString("size");
        wrapped.addSouth(childWidgets.get(0), Double.valueOf(size));
      } else if ("west".equals(tagName)) {
        String size = child.getPropertyString("size");
        wrapped.addWest(childWidgets.get(0), Double.valueOf(size));
      } else if ("east".equals(tagName)) {
        String size = child.getPropertyString("size");
        wrapped.addEast(childWidgets.get(0), Double.valueOf(size));
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
