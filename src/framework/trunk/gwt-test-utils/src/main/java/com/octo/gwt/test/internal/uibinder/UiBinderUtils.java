package com.octo.gwt.test.internal.uibinder;

import java.util.HashSet;
import java.util.Set;

class UiBinderUtils {

  private static final String FIELD_ATTR_NAME = "field";
  private static final String MSG_TAG = "msg";
  private static final Set<String> RESOURCE_TAGS = new HashSet<String>();
  private static final String TYPE_ATTR_NAME = "type";
  private static final String UIBINDER_NSURI = "urn:ui:com.google.gwt.uibinder";
  private static final String UIBINDER_TAG = "UiBinder";

  static {
    RESOURCE_TAGS.add("data");
    RESOURCE_TAGS.add("img");
    RESOURCE_TAGS.add("with");
  }

  public static String getEffectiveClassName(String style) {
    style = style.replaceAll("[\\{\\}\\s]", "");
    String[] array = style.split("\\.");
    return array[array.length - 1];
  }

  public static boolean isMsgTag(String nameSpaceURI, String tagName) {
    return MSG_TAG.equals(tagName) && nameSpaceURI != null
        && nameSpaceURI.equals(UIBINDER_NSURI);
  }

  public static boolean isResourceTag(String nameSpaceURI, String tag) {
    return UIBINDER_NSURI.equals(nameSpaceURI) && RESOURCE_TAGS.contains(tag);
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

  private UiBinderUtils() {

  }

}
