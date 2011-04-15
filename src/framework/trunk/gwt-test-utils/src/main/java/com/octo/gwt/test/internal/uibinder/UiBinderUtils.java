package com.octo.gwt.test.internal.uibinder;

public class UiBinderUtils {

  private static final String UIBINDER_NSURI = "urn:ui:com.google.gwt.uibinder";

  public static boolean isUiBinderField(String nameSpaceURI, String attrName) {
    return "field".equals(attrName) && nameSpaceURI.equals(UIBINDER_NSURI);
  }

  public static boolean isUiBinderTag(String nameSpaceURI, String tagName) {
    return "UiBinder".equals(tagName) && nameSpaceURI != null
        && nameSpaceURI.equals(UIBINDER_NSURI);
  }

  private UiBinderUtils() {

  }

}
