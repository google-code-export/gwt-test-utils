package com.googlecode.gwt.test.internal.patchers;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;

@PatchClass(KeyPressEvent.class)
public class KeyPressEventPatcher {

  @PatchMethod
  public static char getCharCode(KeyPressEvent keypressEvent, NativeEvent e) {
    return (char) e.getKeyCode();
  }

}
