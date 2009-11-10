package com.octo.gwt.test17.internal.overrides;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;

public class OverrideEvent extends Event {

	private int overrideType;

	private boolean overrideMetaKey;

	private boolean overrideCtrlKey;

	private boolean overrideShiftKey;

	private boolean overrideAltKey;

	private int overrideKeyCode;

	/**
	 * A bit-field, defined by {@link NativeEvent#BUTTON_LEFT},
	 *         {@link NativeEvent#BUTTON_MIDDLE}, and
	 *         {@link NativeEvent#BUTTON_RIGHT}
	 */
	private int overrideButton;

	private Element overrideTargetElement;

	public OverrideEvent(int type) {
		this(type, NativeEvent.BUTTON_LEFT);
	}
	
	public OverrideEvent(int type, int button) {
		this.overrideType = type;
		this.overrideButton = button;
		this.overrideMetaKey = false;
		this.overrideCtrlKey = false;
		this.overrideShiftKey = false;
		this.overrideAltKey = false;
		this.overrideKeyCode = 0;
	}

	public OverrideEvent(int type, Element targetElement) {
		this.overrideTargetElement = targetElement;
		this.overrideType = type;
		this.overrideMetaKey = false;
		this.overrideCtrlKey = false;
		this.overrideShiftKey = false;
		this.overrideAltKey = false;
		this.overrideKeyCode = 0;
	}

	public int getOverrideType() {
		return overrideType;
	}

	public OverrideEvent setOverrideType(int overrideType) {
		this.overrideType = overrideType;
		return this;
	}

	public boolean isOverrideMetaKey() {
		return overrideMetaKey;
	}

	public OverrideEvent setOverrideMetaKey(boolean overrideMetaKey) {
		this.overrideMetaKey = overrideMetaKey;
		return this;
	}

	public boolean isOverrideCtrlKey() {
		return overrideCtrlKey;
	}

	public OverrideEvent setOverrideCtrlKey(boolean overrideCtrlKey) {
		this.overrideCtrlKey = overrideCtrlKey;
		return this;
	}

	public boolean isOverrideShiftKey() {
		return overrideShiftKey;
	}

	public OverrideEvent setOverrideShiftKey(boolean overrideShiftKey) {
		this.overrideShiftKey = overrideShiftKey;
		return this;
	}

	public boolean isOverrideAltKey() {
		return overrideAltKey;
	}

	public OverrideEvent setOverrideAltKey(boolean overrideAltKey) {
		this.overrideAltKey = overrideAltKey;
		return this;
	}

	public int getOverrideKeyCode() {
		return overrideKeyCode;
	}

	public OverrideEvent setOverrideKeyCode(int overrideKeyCode) {
		this.overrideKeyCode = overrideKeyCode;
		return this;
	}

	public Element getOverrideTargetElement() {
		return overrideTargetElement;
	}

	public void setOverrideTargetElement(Element overrideTargetElement) {
		this.overrideTargetElement = overrideTargetElement;
	}
	
	public int getOverrideButton() {
		return overrideButton;
	}

	public void setOverrideButton(int overrideButton) {
		this.overrideButton = overrideButton;
	}

	public static OverrideEvent overrideCast(Object o) {
		if (o instanceof OverrideEvent) {
			OverrideEvent e = (OverrideEvent) o;
			return e;
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

}
