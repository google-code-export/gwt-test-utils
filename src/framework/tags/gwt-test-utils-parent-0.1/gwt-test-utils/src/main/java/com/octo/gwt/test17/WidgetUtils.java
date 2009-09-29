package com.octo.gwt.test17;

import java.util.List;

import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

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

}
