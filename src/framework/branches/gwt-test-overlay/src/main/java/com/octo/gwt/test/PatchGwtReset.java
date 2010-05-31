package com.octo.gwt.test;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.impl.HistoryImpl;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;
import com.octo.gwt.test.internal.patcher.GWTPatcher;
import com.octo.gwt.test.internal.patcher.dom.JavaScriptObjectFactory;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;
import com.octo.gwt.test.utils.PatchGwtUtils;


public class PatchGwtReset {

	public static void reset() throws Exception {
		JavaScriptObjectFactory.reset();
		PatchGwtUtils.reset();
		GWTPatcher.reset();

		WidgetCollection widgetCollection = GwtTestReflectionUtils.getPrivateFieldValue(RootPanel.get(), "children");
		Widget[] array = GwtTestReflectionUtils.getPrivateFieldValue(widgetCollection, "array");
		for (int i = 0; i < array.length; i++) {
			array[i] = null;
		}
		GwtTestReflectionUtils.setPrivateFieldValue(widgetCollection, "size", 0);

		GwtTestReflectionUtils.getStaticAndCallClear(Timer.class, "timers");
		GwtTestReflectionUtils.getStaticAndCallClear(RootPanel.class, "rootPanels");
		GwtTestReflectionUtils.getStaticAndCallClear(RootPanel.class, "widgetsToDetach");

		Object commandExecutor = GwtTestReflectionUtils.getStaticFieldValue(Class.forName("com.google.gwt.user.client.DeferredCommand"), "commandExecutor");
		GwtTestReflectionUtils.callClear(GwtTestReflectionUtils.getPrivateFieldValue(commandExecutor, "commands"));

		HistoryImpl historyImpl = GwtTestReflectionUtils.getStaticFieldValue(History.class, "impl");
		GwtTestReflectionUtils.callClear(GwtTestReflectionUtils.getPrivateFieldValue(GwtTestReflectionUtils.getPrivateFieldValue(GwtTestReflectionUtils.getPrivateFieldValue(
				historyImpl, "handlers"), "registry"), "map"));

		GwtTestReflectionUtils.setStaticField(NumberFormat.class, "cachedDecimalFormat", null);
		GwtTestReflectionUtils.setStaticField(NumberFormat.class, "cachedScientificFormat", null);
		GwtTestReflectionUtils.setStaticField(NumberFormat.class, "cachedPercentFormat", null);
		GwtTestReflectionUtils.setStaticField(NumberFormat.class, "cachedCurrencyFormat", null);

		GwtTestReflectionUtils.setStaticField(Window.class, "handlers", null);
		GwtTestReflectionUtils.setStaticField(Event.class, "handlers", null);
	}

	
}
