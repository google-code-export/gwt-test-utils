package com.octo.gwt.test.uibinder.widget;

import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.octo.gwt.test.uibinder.UiBinderXmlUtils;
import com.octo.gwt.test.uibinder.UiWidgetTag;
import com.octo.gwt.test.uibinder.UiWidgetTagFactory;

/**
 * Handles <g:LayoutPanel /> tags.
 * 
 * @author Gael Lazzari
 * 
 */
public class UiLayoutPanelTagFactory implements UiWidgetTagFactory<LayoutPanel> {

  private static class UiLayoutPanelTag extends UiWidgetTag<LayoutPanel> {

    @Override
    protected void appendElement(LayoutPanel wrapped, Element element,
        String namespaceURI, List<IsWidget> childWidgets) {

      if (!UiBinderXmlUtils.CLIENTUI_NSURI.equals(namespaceURI)) {
        super.appendElement(wrapped, element, namespaceURI, childWidgets);
      } else {

        if (childWidgets.size() > 0) {
          handleLayoutPanelSpecifics(wrapped, element, childWidgets);
        }
      }
    }

    @Override
    protected void finalizeWidget(LayoutPanel widget) {
      // nothing to do
    }

    @Override
    protected void initializeWidget(LayoutPanel wrapped,
        Map<String, Object> attributes, Object owner) {
      // nothing to do
    }

    private void handleLayoutPanelSpecifics(LayoutPanel wrapped,
        Element element, List<IsWidget> childWidgets) {

      // The valid sets of horizontal constraints are:
      // (none) : Fill the parent's horizontal axis
      // left, width : Fixed width, positioned from parent's left edge
      // right, width : Fixed width, positioned from parent's right edge
      // left, right : Width implied by fixed distance from parent's left and
      // right edges

      // The valid sets of vertical constraints are:
      // (none) : Fill the parent's vertical axis
      // top, height : Fixed height, positioned from parent's top edge
      // bottom, height : Fixed height, positioned from parent's bottom edge
      // top, bottom : Height implied by fixed distance from parent's top and
      // bottom edges

      // The values of constraint attributes can be any valid CSS length, such
      // as 1px, 3em, or 0 (zero lengths require no units).

      // String left = element.getAttribute("left");
      // String right = element.getAttribute("right");
      // String width = element.getAttribute("width");
      //
      // String top = element.getAttribute("top");
      // String bottom = element.getAttribute("bottom");
      // String height = element.getAttribute("height");

      for (IsWidget child : childWidgets) {
        wrapped.add(child);
        // TODO : handle layer size and position data
      }

    }

  }

  public UiWidgetTag<LayoutPanel> createUiWidgetTag(
      Class<? extends IsWidget> widgetClass, Map<String, Object> attributes) {

    if (LayoutPanel.class.isAssignableFrom(widgetClass)) {
      return new UiLayoutPanelTag();
    }

    return null;
  }

}
