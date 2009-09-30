package com.octo.gwt.test17;

import java.util.List;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test17.overrides.OverrideEvent;

/**
 * Class which provide reflection utilities on {@link Widget} classes.
 * @author GLZ
 *
 */
public class WidgetUtils {
	
	public static List<MenuItem> getMenuItems(MenuBar menuBar) {
		return ReflectionUtils.getPrivateFieldValue(menuBar, "items");
	}
	
	public static boolean hasStyle(UIObject object, String styleName) {
		return object.getStyleName().contains(styleName);
	}

	public static void click(Widget target) {
		if (target instanceof CheckBox) {
			CheckBox checkBox = (CheckBox) target;
			checkBox.setValue(!checkBox.getValue());
		} 

		target.onBrowserEvent(new OverrideEvent(Event.ONCLICK));
	}

	public static void click(MenuBar parent, MenuItem clickedItem) {
		parent.onBrowserEvent(new OverrideEvent(Event.ONCLICK, clickedItem.getElement()));
	}
	
	public static void blur(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONBLUR));
	}
	
	public static void focus(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONFOCUS));
	}
	
	public static void change(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONCHANGE));
	}
	
	public static void mouseMove(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONMOUSEMOVE));
	}
	
	public static void mouseDown(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONMOUSEDOWN));
	}
	
	public static void mouseUp(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONMOUSEUP));
	}
	
	public static void mouseWheel(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONMOUSEWHEEL));
	}

}
