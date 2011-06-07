package com.octo.gwt.test.internal.patchers;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(KeyPressEvent.class)
public class KeyPressEventPatcher extends AutomaticPatcher {

  @PatchMethod
  public static char getCharCode(KeyPressEvent keypressEvent, NativeEvent e) {
    return (char) e.getKeyCode();
  }

}
