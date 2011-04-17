package com.octo.gwt.test.internal.uibinder.objects;

import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;

public class UiBinderWithDockLayoutPanel extends
    UiBinderWidget<DockLayoutPanel> {

  public UiBinderWithDockLayoutPanel(DockLayoutPanel wrapped,
      Attributes attributes, Object owner, Map<String, Object> resources) {
    super(wrapped, attributes, owner, resources);
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

  @Override
  protected void appendElement(DockLayoutPanel wrapped, Element child) {
    String nsURI = JavaScriptObjects.getJsoProperties(child).getString(
        JsoProperties.XML_NAMESPACE);

    if (nsURI.length() == 0) {
      super.appendElement(wrapped, child);
    } else {
      List<Widget> childWidgets = JavaScriptObjects.getJsoProperties(child).getObject(
          JsoProperties.UIBINDER_CHILD_WIDGETS_LIST);

      if (childWidgets != null && childWidgets.size() > 0) {
        handleDockLayoutPanelSpecifics(wrapped, child, childWidgets);
      }
    }
  }
}
