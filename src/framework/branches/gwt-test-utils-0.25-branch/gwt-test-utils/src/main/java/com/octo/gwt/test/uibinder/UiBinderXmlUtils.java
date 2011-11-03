package com.octo.gwt.test.uibinder;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * Utility class for UiBinder XML stuff.
 * 
 * @author Gael Lazzari
 * 
 */
public class UiBinderXmlUtils {

  public static final String CLIENTUI_NSURI = "urn:import:com.google.gwt.user.client.ui";
  public static final String DATA_TAG = "data";
  public static final String FIELD_ATTR_NAME = "field";
  public static final String IMAGE_TAG = "image";
  public static final String IMPORT_TAG = "import";
  public static final String MSG_TAG = "msg";
  public static final String UIBINDER_NSURI = "urn:ui:com.google.gwt.uibinder";

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
  private static final String UIBINDER_TAG = "UiBinder";
  private static final String WITH_TAG = "with";

  public static String getEffectiveStyleName(String style) {
    style = style.replaceAll("[\\{\\}\\s]", "");
    String[] array = style.split("\\.");
    return array[array.length - 1];
  }

  public static boolean isDataTag(String nameSpaceURI, String tagName) {
    return DATA_TAG.equals(tagName) && UIBINDER_NSURI.equals(nameSpaceURI);
  }

  public static boolean isImageTag(String nameSpaceURI, String tagName) {
    return IMAGE_TAG.equals(tagName) && UIBINDER_NSURI.equals(nameSpaceURI);
  }

  public static boolean isImportTag(String nameSpaceURI, String tagName) {
    return IMPORT_TAG.equals(tagName) && UIBINDER_NSURI.equals(nameSpaceURI);
  }

  public static boolean isMsgTag(String nameSpaceURI, String tagName) {
    return MSG_TAG.equals(tagName) && UIBINDER_NSURI.equals(nameSpaceURI);
  }

  public static boolean isResourceTag(String nameSpaceURI, String tagName) {
    return UIBINDER_NSURI.equals(nameSpaceURI)
        && RESOURCE_TAGS.contains(tagName);
  }

  public static boolean isStyleTag(String nameSpaceURI, String tagName) {
    return STYLE_TAG.equals(tagName) && UIBINDER_NSURI.equals(nameSpaceURI);
  }

  public static boolean isTypeAttribute(String nameSpaceURI, String attrName) {
    return TYPE_ATTR_NAME.equals(attrName)
        && UIBINDER_NSURI.equals(nameSpaceURI);
  }

  public static boolean isUiBinderTag(String nameSpaceURI, String tagName) {
    return UIBINDER_TAG.equals(tagName) && UIBINDER_NSURI.equals(nameSpaceURI);
  }

  public static boolean isUiFieldAttribute(String nameSpaceURI, String attrName) {
    return FIELD_ATTR_NAME.equals(attrName)
        && UIBINDER_NSURI.equals(nameSpaceURI);
  }

  public static boolean isWithTag(String nameSpaceURI, String tagName) {
    return WITH_TAG.equals(tagName) && UIBINDER_NSURI.equals(nameSpaceURI);
  }

  public static HorizontalAlignmentConstant parseHorizontalAlignment(
      String horizontalAlignment) {
    return GwtReflectionUtils.getStaticFieldValue(HasHorizontalAlignment.class,
        horizontalAlignment);
  }

  public static VerticalAlignmentConstant parseVerticalAlignment(
      String verticalAlignment) {
    return GwtReflectionUtils.getStaticFieldValue(HasVerticalAlignment.class,
        verticalAlignment);
  }

  private UiBinderXmlUtils() {

  }

}
