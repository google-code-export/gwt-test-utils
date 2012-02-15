package com.octo.gwt.test.internal.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RadioButton;
import com.octo.gwt.test.internal.AfterTestCallback;
import com.octo.gwt.test.internal.AfterTestCallbackManager;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * RadioButton instance Manager, to emulate radigroup behaviour. <strong>For
 * internal use only.<strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class RadioButtonManager implements AfterTestCallback {

  private static RadioButtonManager INSTANCE;

  public static void onInstanciation(RadioButton rb) {
    get().register(rb);
  }

  public static void onRadioGroupChanged(RadioButton rb, Boolean value,
      boolean fireEvents) {
    if (value != null && value) {
      for (RadioButton radioButton : get().getRadioButtons(rb.getName())) {
        if (!rb.equals(radioButton) && radioButton.getValue()) {
          radioButton.setValue(false, fireEvents);
        }
      }
    }
  }

  public static void onSetName(RadioButton rb, String newName) {
    get().setName(rb, newName);
  }

  private static RadioButtonManager get() {
    if (INSTANCE == null) {
      INSTANCE = new RadioButtonManager();
    }

    return INSTANCE;
  }

  private final Map<String, Set<RadioButton>> map = new HashMap<String, Set<RadioButton>>();

  private RadioButtonManager() {
    AfterTestCallbackManager.get().registerCallback(this);
  }

  public void afterTest() throws Throwable {
    map.clear();
  }

  private Set<RadioButton> getRadioButtons(String radioGroupName) {
    Set<RadioButton> set = map.get(radioGroupName);
    if (set == null) {
      set = new HashSet<RadioButton>();
      map.put(radioGroupName, set);
    }

    return set;
  }

  private void register(RadioButton rb) {
    getRadioButtons(rb.getName()).add(rb);
  }

  private void setName(RadioButton rb, String newName) {
    // 1. remove from the old radiogroup
    getRadioButtons(rb.getName()).remove(rb);

    // 2. set the new name
    Element input = DOM.createInputRadio(newName);
    GwtReflectionUtils.callPrivateMethod(rb, "replaceInputElement", input);

    // 3. register in the new radiogroup
    register(rb);
  }

}
