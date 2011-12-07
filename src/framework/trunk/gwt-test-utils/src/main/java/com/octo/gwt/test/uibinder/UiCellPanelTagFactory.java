package com.octo.gwt.test.uibinder;

import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Handles subclasses of CellPanel (which declare <g:cell> tags).
 * 
 * @author Gael Lazzari
 * 
 */
class UiCellPanelTagFactory implements UiWidgetTagFactory<CellPanel> {

  private static class UiCellPanelTag extends UiWidgetTag<CellPanel> {

    private static final String CELL_TAG = "cell";

    @Override
    protected void appendElement(CellPanel wrapped, Element element,
        String namespaceURI, List<IsWidget> childWidgets) {

      if (!CELL_TAG.equals(element.getTagName())
          || !UiBinderXmlUtils.CLIENTUI_NSURI.equals(namespaceURI)) {
        super.appendElement(wrapped, element, namespaceURI, childWidgets);
      } else {

        // hanle cell's attributes
        String width = element.getAttribute("width");
        if (width == null || width.trim().length() == 0) {
          width = null;
        }

        String horizontalAlignment = element.getAttribute("horizontalAlignment");
        HorizontalAlignmentConstant hConstant = null;
        if (horizontalAlignment != null
            && horizontalAlignment.trim().length() > 0) {
          hConstant = UiBinderXmlUtils.parseHorizontalAlignment(horizontalAlignment.trim());
        }

        String verticalAlignment = element.getAttribute("verticalAlignment");
        VerticalAlignmentConstant vConstant = null;
        if (verticalAlignment != null && verticalAlignment.trim().length() > 0) {
          vConstant = UiBinderXmlUtils.parseVerticalAlignment(verticalAlignment.trim());
        }

        // handle cell's child widget and set cell's attributes
        for (IsWidget widget : childWidgets) {
          wrapped.add(widget);
          if (width != null) {
            wrapped.setCellWidth(widget, width);
          }
          if (hConstant != null) {
            wrapped.setCellHorizontalAlignment(widget, hConstant);
          }
          if (vConstant != null) {
            wrapped.setCellVerticalAlignment(widget, vConstant);
          }
        }

      }
    }

    @Override
    protected void finalizeWidget(CellPanel widget) {
      // nothing to do
    }

    @Override
    protected void initializeWidget(CellPanel wrapped,
        Map<String, Object> attributes, Object owner) {
      // nothing to do
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.octo.gwt.test.uibinder.UiBinderWidgetFactory#createUiWidgetTag
   * (java.lang.Class, java.util.Map)
   */
  public UiWidgetTag<CellPanel> createUiWidgetTag(
      Class<? extends IsWidget> widgetClass, Map<String, Object> attributes) {
    if (!CellPanel.class.isAssignableFrom(widgetClass)) {
      return null;
    }

    return new UiCellPanelTag();
  }

}
