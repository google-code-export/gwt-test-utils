package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.PotentialElement;
import com.google.gwt.user.client.ui.UIObject;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(PotentialElement.class)
class PotentialElementPatcher {

  @PatchMethod
  static PotentialElement build(UIObject o, String tagName) {
    PotentialElement e = JavaScriptObjects.newObject(PotentialElement.class);
    Element wrappedElement = JavaScriptObjects.newElement(tagName,
        o.getElement().getOwnerDocument());
    JavaScriptObjects.setProperty(e, JsoProperties.POTENTIALELEMENT_TAG, true);
    JavaScriptObjects.setProperty(e,
        JsoProperties.POTENTIALELEMENT_WRAPPED_ELEMENT, wrappedElement);
    JavaScriptObjects.setProperty(e, JsoProperties.POTENTIALELEMENT_UIOBJECT, o);

    return e;
  }

  @PatchMethod
  static boolean isPotential(JavaScriptObject o) {
    return JavaScriptObjects.getBoolean(o, JsoProperties.POTENTIALELEMENT_TAG);
  }

  @PatchMethod
  static Element resolve(Element maybePotential) {
    if (isPotential(maybePotential)) {
      UIObject o = JavaScriptObjects.getObject(maybePotential,
          JsoProperties.POTENTIALELEMENT_UIOBJECT);
      GwtReflectionUtils.callPrivateMethod(o, "resolvePotentialElement");

      return JavaScriptObjects.getObject(maybePotential,
          JsoProperties.POTENTIALELEMENT_WRAPPED_ELEMENT);

    } else {
      return maybePotential;
    }
  }

  @PatchMethod
  static Element setResolver(PotentialElement pe, UIObject resolver) {
    JavaScriptObjects.setProperty(pe, JsoProperties.POTENTIALELEMENT_UIOBJECT,
        resolver);

    return pe;

  }

}
