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
class UiBinderCellPanelFactory implements UiBinderWidgetFactory {

  private static class UiBinderCellPanel extends UiBinderWidget<CellPanel> {

    private static final String CELL_TAG = "cell";

    public UiBinderCellPanel(CellPanel wrapped, Map<String, Object> attributes,
        UiBinderTag parentTag, Object owner, UiResourceManager resourceManager) {
      super(wrapped, attributes, parentTag, owner, resourceManager);
    }

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
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.octo.gwt.test.uibinder.UiBinderWidgetFactory#createUiBinderWidget(com
   * .google.gwt.user.client.ui.IsWidget, java.util.Map,
   * com.octo.gwt.test.uibinder.UiBinderTag, java.lang.Object,
   * com.octo.gwt.test.uibinder.UiResourceManager)
   */
  public UiBinderWidget<? extends IsWidget> createUiBinderWidget(
      IsWidget widget, Map<String, Object> attributes, UiBinderTag parentTag,
      Object owner, UiResourceManager resourceManager) {

    if (!CellPanel.class.isInstance(widget)) {
      return null;
    }

    return new UiBinderCellPanel((CellPanel) widget, attributes, parentTag,
        owner, resourceManager);
  }

}
