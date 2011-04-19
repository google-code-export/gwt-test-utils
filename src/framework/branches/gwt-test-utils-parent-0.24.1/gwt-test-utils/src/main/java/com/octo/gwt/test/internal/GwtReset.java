package com.octo.gwt.test.internal;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.impl.HistoryImpl;
import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.GwtConfig;
import com.octo.gwt.test.internal.patchers.CurrencyListPatcher;
import com.octo.gwt.test.internal.patchers.GwtPatcher;
import com.octo.gwt.test.internal.patchers.HistoryImplPatcher;
import com.octo.gwt.test.internal.patchers.ImplPatcher;
import com.octo.gwt.test.internal.patchers.TimerPatcher;
import com.octo.gwt.test.internal.patchers.dom.NodeFactory;
import com.octo.gwt.test.internal.utils.GwtPatcherUtils;
import com.octo.gwt.test.internal.utils.i18n.GwtPropertiesHelper;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class GwtReset {

	public static void reset() throws Exception {
		NodeFactory.reset();
		GwtConfig.reset();
		CurrencyListPatcher.reset();
		GwtPatcherUtils.reset();
		GwtPropertiesHelper.get().reset();
		HistoryImplPatcher.reset();
		GwtPatcher.reset();
		TimerPatcher.reset();
		ImplPatcher.reset();
		GwtCreateHandlerManager.getInstance().reset();

		GwtReflectionUtils.getStaticAndCallClear(Timer.class, "timers");
		GwtReflectionUtils.getStaticAndCallClear(RootPanel.class, "rootPanels");
		GwtReflectionUtils.getStaticAndCallClear(RootPanel.class, "widgetsToDetach");

		Object commandExecutor = GwtReflectionUtils.getStaticFieldValue(Class.forName("com.google.gwt.user.client.DeferredCommand"),
				"commandExecutor");
		GwtReflectionUtils.callPrivateMethod(GwtReflectionUtils.getPrivateFieldValue(commandExecutor, "commands"), "clear");

		HistoryImpl historyImpl = GwtReflectionUtils.getStaticFieldValue(History.class, "impl");
		GwtReflectionUtils
				.callPrivateMethod(
						GwtReflectionUtils.getPrivateFieldValue(
								GwtReflectionUtils.getPrivateFieldValue(GwtReflectionUtils.getPrivateFieldValue(historyImpl, "handlers"), "eventBus"),
								"map"), "clear");

		GwtReflectionUtils.setStaticField(NumberFormat.class, "cachedDecimalFormat", null);
		GwtReflectionUtils.setStaticField(NumberFormat.class, "cachedScientificFormat", null);
		GwtReflectionUtils.setStaticField(NumberFormat.class, "cachedPercentFormat", null);
		GwtReflectionUtils.setStaticField(NumberFormat.class, "cachedCurrencyFormat", null);

		GwtReflectionUtils.setStaticField(Window.class, "handlers", null);
		GwtReflectionUtils.setStaticField(Event.class, "handlers", null);
	}

}
