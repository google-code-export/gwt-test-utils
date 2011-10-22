package com.octo.gwt.test.uibinder;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.Converter;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.UiBinderBeanUtils;

/**
 * Utility class for UiBinder stuff. <strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
@SuppressWarnings("rawtypes")
class UiBinderUtils {

  private static final String DATA_TAG = "data";
  private static final String FIELD_ATTR_NAME = "field";
  private static final String IMAGE_TAG = "image";
  private static final String MSG_TAG = "msg";

  private static final Set<String> RESOURCE_TAGS = new HashSet<String>() {

    private static final long serialVersionUID = -619331154951585990L;

    {
      add(DATA_TAG);
      add(IMAGE_TAG);
      add(STYLE_TAG);
      add(WITH_TAG);
    }
  };

  private static final String STYLE_TAG = "style";
  private static final String TYPE_ATTR_NAME = "type";

  private static final String UIBINDER_NSURI = "urn:ui:com.google.gwt.uibinder";
  private static final String UIBINDER_TAG = "UiBinder";

  private static final String WITH_TAG = "with";

  static {
    UiBinderBeanUtils.registerConverter(new Converter() {

      public Object convert(Class type, Object value) {
        return GwtReflectionUtils.getStaticFieldValue(
            HasHorizontalAlignment.class, value.toString());
      }
    }, HorizontalAlignmentConstant.class);

    UiBinderBeanUtils.registerConverter(new Converter() {

      public Object convert(Class type, Object value) {
        return GwtReflectionUtils.getStaticFieldValue(
            HasVerticalAlignment.class, value.toString());
      }
    }, VerticalAlignmentConstant.class);
  }

  public static String getEffectiveStyleName(String style) {
    style = style.replaceAll("[\\{\\}\\s]", "");
    String[] array = style.split("\\.");
    return array[array.length - 1];
  }

  public static boolean isDataTag(String nameSpaceURI, String tagName) {
    return DATA_TAG.equals(tagName) && nameSpaceURI != null
        && nameSpaceURI.equals(UIBINDER_NSURI);
  }

  public static boolean isImageTag(String nameSpaceURI, String tagName) {
    return IMAGE_TAG.equals(tagName) && nameSpaceURI != null
        && nameSpaceURI.equals(UIBINDER_NSURI);
  }

  public static boolean isMsgTag(String nameSpaceURI, String tagName) {
    return MSG_TAG.equals(tagName) && nameSpaceURI != null
        && nameSpaceURI.equals(UIBINDER_NSURI);
  }

  public static boolean isResourceTag(String nameSpaceURI, String tagName) {
    return nameSpaceURI != null && nameSpaceURI.equals(UIBINDER_NSURI)
        && RESOURCE_TAGS.contains(tagName);
  }

  public static boolean isStyleTag(String nameSpaceURI, String tagName) {
    return STYLE_TAG.equals(tagName) && nameSpaceURI != null
        && nameSpaceURI.equals(UIBINDER_NSURI);
  }

  public static boolean isTypeAttribute(String nameSpaceURI, String attrName) {
    return TYPE_ATTR_NAME.equals(attrName)
        && nameSpaceURI.equals(UIBINDER_NSURI);
  }

  public static boolean isUiBinderTag(String nameSpaceURI, String tagName) {
    return UIBINDER_TAG.equals(tagName) && nameSpaceURI != null
        && nameSpaceURI.equals(UIBINDER_NSURI);
  }

  public static boolean isUiFieldAttribute(String nameSpaceURI, String attrName) {
    return FIELD_ATTR_NAME.equals(attrName)
        && nameSpaceURI.equals(UIBINDER_NSURI);
  }

  public static boolean isWithTag(String nameSpaceURI, String tagName) {
    return WITH_TAG.equals(tagName) && nameSpaceURI != null
        && nameSpaceURI.equals(UIBINDER_NSURI);
  }

  private UiBinderUtils() {

  }

}
