package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.ui.UIObject;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(DomEvent.class)
class DomEventPatcher {

  /**
   * {@link DomEvent#fireNativeEvent(NativeEvent, HasHandlers, Element)} is
   * patched in order to simulate event bubbling.
   * 
   * @param nativeEvent
   * @param handlerSource
   * @param relativeElem
   */
  @PatchMethod
  static void fireNativeEvent(NativeEvent nativeEvent,
      HasHandlers handlerSource, Element relativeElem) {

    assert nativeEvent != null : "nativeEvent must not be null";

    Object registered = GwtReflectionUtils.getStaticFieldValue(DomEvent.class,
        "registered");

    if (registered != null) {
      final DomEvent.Type<?> typeKey = GwtReflectionUtils.callPrivateMethod(
          registered, "unsafeGet", nativeEvent.getType());

      if (typeKey != null) {

        // Store and restore native event just in case we are in recursive
        // loop.
        DomEvent<?> flyweight = GwtReflectionUtils.getPrivateFieldValue(
            typeKey, "flyweight");
        NativeEvent currentNative = GwtReflectionUtils.getPrivateFieldValue(
            flyweight, "nativeEvent");
        Element currentRelativeElem = GwtReflectionUtils.getPrivateFieldValue(
            flyweight, "relativeElem");

        flyweight.setNativeEvent(nativeEvent);
        flyweight.setRelativeElement(relativeElem);

        // fire
        handlerSource.fireEvent(flyweight);

        // fire parent elements if necessary
        bubbleEvent(relativeElem.getParentElement(), flyweight);

        flyweight.setNativeEvent(currentNative);
        flyweight.setRelativeElement(currentRelativeElem);
      }
    }

  }

  private static void bubbleEvent(Element target, DomEvent<?> flyweight) {

    if (target == null) {
      return;
    }

    boolean eventStopped = JavaScriptObjects.getBoolean(
        flyweight.getNativeEvent(), JsoProperties.EVENT_IS_STOPPED);

    if (eventStopped) {
      return;
    }

    flyweight.setRelativeElement(target);

    UIObject uiObject = JavaScriptObjects.getObject(target,
        JsoProperties.ELEM_BINDED_UIOBJECT);

    if (HasHandlers.class.isInstance(uiObject)) {
      // fire
      ((HasHandlers) uiObject).fireEvent(flyweight);
    }

    // recursive call
    bubbleEvent(target.getParentElement(), flyweight);

  }

}
