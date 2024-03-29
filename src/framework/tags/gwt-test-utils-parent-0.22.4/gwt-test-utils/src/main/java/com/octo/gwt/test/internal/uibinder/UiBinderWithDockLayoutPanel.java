package com.octo.gwt.test.internal.uibinder;

import java.util.List;

import org.xml.sax.Attributes;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;

class UiBinderWithDockLayoutPanel extends UiBinderWidget<DockLayoutPanel> {

  UiBinderWithDockLayoutPanel(DockLayoutPanel wrapped, Attributes attributes,
      UiBinderTag parentTag, Object owner, UiResourceManager resourceManager) {
    super(wrapped, attributes, parentTag, owner, resourceManager);
  }

  @Override
  protected void appendElement(DockLayoutPanel wrapped, Element child) {
    String nsURI = JavaScriptObjects.getString(child,
        JsoProperties.XML_NAMESPACE);

    if (nsURI.length() == 0) {
      super.appendElement(wrapped, child);
    } else {
      List<Widget> childWidgets = JavaScriptObjects.getObject(child,
          JsoProperties.UIBINDER_CHILD_WIDGETS_LIST);

      if (childWidgets != null && childWidgets.size() > 0) {
        handleDockLayoutPanelSpecifics(wrapped, child, childWidgets);
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
      double size = child.getPropertyDouble("size");
      wrapped.addNorth(childWidgets.get(0), size);
    } else if ("south".equals(tagName)) {
      double size = child.getPropertyDouble("size");
      wrapped.addSouth(childWidgets.get(0), size);
    } else if ("west".equals(tagName)) {
      double size = child.getPropertyDouble("size");
      wrapped.addWest(childWidgets.get(0), size);
    } else if ("east".equals(tagName)) {
      double size = child.getPropertyDouble("size");
      wrapped.addEast(childWidgets.get(0), size);
    }

  }
}
