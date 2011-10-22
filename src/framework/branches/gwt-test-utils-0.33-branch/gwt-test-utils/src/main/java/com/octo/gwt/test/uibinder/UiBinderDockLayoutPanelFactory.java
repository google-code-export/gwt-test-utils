package com.octo.gwt.test.uibinder;

import java.util.List;

import org.xml.sax.Attributes;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;

/**
 * Handles <g:DockLayoutPanel /> tags.
 * 
 * @author Gael Lazzari
 * 
 */
class UiBinderDockLayoutPanelFactory implements UiBinderWidgetFactory {

  private static class UiBinderWithDockLayoutPanel extends
      UiBinderWidget<DockLayoutPanel> {

    private UiBinderWithDockLayoutPanel(DockLayoutPanel wrapped,
        Attributes attributes, UiBinderTag parentTag, Object owner,
        UiResourceManager resourceManager) {
      super(wrapped, attributes, parentTag, owner, resourceManager);
    }

    @Override
    protected void appendElement(DockLayoutPanel wrapped, Element child) {
      String nsURI = JavaScriptObjects.getString(child,
          JsoProperties.XML_NAMESPACE);

      if (nsURI.length() == 0) {
        super.appendElement(wrapped, child);
      } else {
        List<IsWidget> childWidgets = JavaScriptObjects.getObject(child,
            JsoProperties.UIBINDER_CHILD_WIDGETS_LIST);

        if (childWidgets != null && childWidgets.size() > 0) {
          handleDockLayoutPanelSpecifics(wrapped, child, childWidgets);
        }
      }
    }

    private void handleDockLayoutPanelSpecifics(DockLayoutPanel wrapped,
        Element child, List<IsWidget> childWidgets) {
      String tagName = child.getTagName();
      if ("center".equals(tagName)) {
        for (IsWidget childCenterWidget : childWidgets) {
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

  public UiBinderWidget<? extends IsWidget> createUiBinderWidget(
      IsWidget widget, Attributes attributes, UiBinderTag parentTag,
      Object owner, UiResourceManager resourceManager) {

    if (!DockLayoutPanel.class.isInstance(widget)) {
      return null;
    }

    return new UiBinderWithDockLayoutPanel((DockLayoutPanel) widget,
        attributes, parentTag, owner, resourceManager);
  }

}
