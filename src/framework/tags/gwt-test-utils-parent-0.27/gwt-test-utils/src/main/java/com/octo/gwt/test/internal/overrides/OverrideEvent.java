package com.octo.gwt.test.internal.overrides;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event;

public class OverrideEvent extends Event {

  private boolean overrideAltKey;

  /**
   * A bit-field, defined by {@link NativeEvent#BUTTON_LEFT},
   * {@link NativeEvent#BUTTON_MIDDLE}, and {@link NativeEvent#BUTTON_RIGHT}
   */
  private int overrideButton;

  private boolean overrideCtrlKey;

  private int overrideKeyCode;

  private boolean overrideMetaKey;

  private boolean overrideShiftKey;

  private Element overrideTargetElement;

  private int overrideType;

  private boolean preventDefault;

  public OverrideEvent(int type) {
    this(type, NativeEvent.BUTTON_LEFT);
  }

  protected OverrideEvent(int type, Element targetElement) {
    this.overrideTargetElement = targetElement;
    this.overrideType = type;
    this.overrideMetaKey = false;
    this.overrideCtrlKey = false;
    this.overrideShiftKey = false;
    this.overrideAltKey = false;
    this.overrideKeyCode = 0;
  }

  protected OverrideEvent(int type, int button) {
    this.overrideType = type;
    this.overrideButton = button;
    this.overrideMetaKey = false;
    this.overrideCtrlKey = false;
    this.overrideShiftKey = false;
    this.overrideAltKey = false;
    this.overrideKeyCode = 0;
  }

  public int getOverrideButton() {
    return overrideButton;
  }

  public int getOverrideKeyCode() {
    return overrideKeyCode;
  }

  public Element getOverrideTargetElement() {
    return overrideTargetElement;
  }

  public int getOverrideType() {
    return overrideType;
  }

  public boolean isOverrideAltKey() {
    return overrideAltKey;
  }

  public boolean isOverrideCtrlKey() {
    return overrideCtrlKey;
  }

  public boolean isOverrideMetaKey() {
    return overrideMetaKey;
  }

  public boolean isOverrideShiftKey() {
    return overrideShiftKey;
  }

  public boolean isPreventDefault() {
    return preventDefault;
  }

  public OverrideEvent setOverrideAltKey(boolean overrideAltKey) {
    this.overrideAltKey = overrideAltKey;
    return this;
  }

  public void setOverrideButton(int overrideButton) {
    this.overrideButton = overrideButton;
  }

  public OverrideEvent setOverrideCtrlKey(boolean overrideCtrlKey) {
    this.overrideCtrlKey = overrideCtrlKey;
    return this;
  }

  public OverrideEvent setOverrideKeyCode(int overrideKeyCode) {
    this.overrideKeyCode = overrideKeyCode;
    return this;
  }

  public OverrideEvent setOverrideMetaKey(boolean overrideMetaKey) {
    this.overrideMetaKey = overrideMetaKey;
    return this;
  }

  public OverrideEvent setOverrideShiftKey(boolean overrideShiftKey) {
    this.overrideShiftKey = overrideShiftKey;
    return this;
  }

  public void setOverrideTargetElement(Element overrideTargetElement) {
    this.overrideTargetElement = overrideTargetElement;
  }

  public OverrideEvent setOverrideType(int overrideType) {
    this.overrideType = overrideType;
    return this;
  }

  public void setPreventDefault(boolean preventDefault) {
    this.preventDefault = preventDefault;
  }
}
