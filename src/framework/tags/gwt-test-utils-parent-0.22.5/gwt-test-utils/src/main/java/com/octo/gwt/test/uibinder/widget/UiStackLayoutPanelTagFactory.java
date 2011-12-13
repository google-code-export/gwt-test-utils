package com.octo.gwt.test.uibinder.widget;

import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.uibinder.UiBinderXmlUtils;
import com.octo.gwt.test.uibinder.UiObjectTag;
import com.octo.gwt.test.uibinder.UiObjectTagFactory;

/**
 * Handles &lt;g:StackLayoutPanel> tags.
 * 
 * @author Gael Lazzari
 * 
 */
public class UiStackLayoutPanelTagFactory implements
    UiObjectTagFactory<StackLayoutPanel> {

  private static class UiStackLayoutPanelTag extends
      UiObjectTag<StackLayoutPanel> {

    @Override
    protected void appendElement(StackLayoutPanel wrapped, Element element,
        String namespaceURI, List<Widget> childWidgets) {

      if (!UiBinderXmlUtils.CLIENTUI_NSURI.equals(namespaceURI)) {
        super.appendElement(wrapped, element, namespaceURI, childWidgets);
      } else {
        handleStackLayoutPanelSpecifics(wrapped, element, childWidgets);
      }
    }

    @Override
    protected void finalizeObject(StackLayoutPanel widget) {
      // nothing to do
    }

    @Override
    protected void initializeObject(StackLayoutPanel wrapped,
        Map<String, Object> attributes, Object owner) {
      // nothing to do
    }

    @Override
    protected StackLayoutPanel instanciate(
        Class<? extends StackLayoutPanel> clazz,
        Map<String, Object> attributes, Object owner) {

      if (clazz != StackLayoutPanel.class) {
        // use default instanciation system
        return super.instanciate(clazz, attributes, owner);
      }

      String unit = (String) attributes.get("unit");
      Unit styleUnit = unit != null ? Unit.valueOf(unit) : Unit.PX;

      return new StackLayoutPanel(styleUnit);
    }

    private void handleStackLayoutPanelSpecifics(StackLayoutPanel wrapped,
        Element element, List<Widget> childWidgets) {

      if ("stack".equals(element.getTagName())) {
        NodeList<Element> headers = element.getElementsByTagName("header");
        if (headers.getLength() == 1 && childWidgets.size() == 1) {
          // case of "header"
          String header = headers.getItem(0).getInnerHTML();
          String headerSize = headers.getItem(0).getAttribute("size");
          wrapped.add(childWidgets.get(0), header, Double.valueOf(headerSize));
        } else {
          NodeList<Element> customHeaders = element.getElementsByTagName("customHeader");
          if (customHeaders.getLength() == 1 && childWidgets.size() == 1) {
            // case of "customHeader"
            List<Widget> customHeaderChilds = UiBinderXmlUtils.getChildWidgets(customHeaders.getItem(0));
            if (customHeaderChilds.size() == 1) {
              String customHeaderSize = customHeaders.getItem(0).getAttribute(
                  "size");
              wrapped.add(childWidgets.get(0), customHeaderChilds.get(0),
                  Double.valueOf(customHeaderSize));
            }
          }
        }
      }

    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.octo.gwt.test.uibinder.UiWidgetTagFactory#createUiObjectTag(java.lang
   * .Class, java.util.Map)
   */
  public UiObjectTag<StackLayoutPanel> createUiObjectTag(Class<?> clazz,
      Map<String, Object> attributes) {

    if (StackLayoutPanel.class.isAssignableFrom(clazz)) {
      return new UiStackLayoutPanelTag();
    }

    return null;
  }

}
