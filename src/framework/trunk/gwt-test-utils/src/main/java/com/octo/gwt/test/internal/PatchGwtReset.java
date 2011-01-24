package com.octo.gwt.test.internal;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.impl.HistoryImpl;
import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.PatchGwtConfig;
import com.octo.gwt.test.internal.patcher.CurrencyListPatcher;
import com.octo.gwt.test.internal.patcher.GwtPatcher;
import com.octo.gwt.test.internal.patcher.HistoryImplPatcher;
import com.octo.gwt.test.internal.patcher.ImplPatcher;
import com.octo.gwt.test.internal.patcher.TimerPatcher;
import com.octo.gwt.test.internal.patcher.dom.NodeFactory;
import com.octo.gwt.test.internal.utils.PatchGwtUtils;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

public class PatchGwtReset {

	public static void reset() throws Exception {
		NodeFactory.reset();
		PatchGwtConfig.reset();
		CurrencyListPatcher.reset();
		PatchGwtUtils.reset();
		HistoryImplPatcher.reset();
		GwtPatcher.reset();
		TimerPatcher.reset();
		ImplPatcher.reset();
		GwtCreateHandlerManager.getInstance().reset();

		GwtTestReflectionUtils.getStaticAndCallClear(Timer.class, "timers");
		GwtTestReflectionUtils.getStaticAndCallClear(RootPanel.class, "rootPanels");
		GwtTestReflectionUtils.getStaticAndCallClear(RootPanel.class, "widgetsToDetach");

		Object commandExecutor = GwtTestReflectionUtils.getStaticFieldValue(Class.forName("com.google.gwt.user.client.DeferredCommand"),
				"commandExecutor");
		GwtTestReflectionUtils.callPrivateMethod(GwtTestReflectionUtils.getPrivateFieldValue(commandExecutor, "commands"), "clear");

		HistoryImpl historyImpl = GwtTestReflectionUtils.getStaticFieldValue(History.class, "impl");
		GwtTestReflectionUtils
				.callPrivateMethod(
						GwtTestReflectionUtils.getPrivateFieldValue(GwtTestReflectionUtils.getPrivateFieldValue(
								GwtTestReflectionUtils.getPrivateFieldValue(historyImpl, "handlers"), "eventBus"), "map"), "clear");

		GwtTestReflectionUtils.setStaticField(NumberFormat.class, "cachedDecimalFormat", null);
		GwtTestReflectionUtils.setStaticField(NumberFormat.class, "cachedScientificFormat", null);
		GwtTestReflectionUtils.setStaticField(NumberFormat.class, "cachedPercentFormat", null);
		GwtTestReflectionUtils.setStaticField(NumberFormat.class, "cachedCurrencyFormat", null);

		GwtTestReflectionUtils.setStaticField(Window.class, "handlers", null);
		GwtTestReflectionUtils.setStaticField(Event.class, "handlers", null);
	}

}
