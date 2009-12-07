package com.octo.gwt.test17;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.AreaElement;
import com.google.gwt.dom.client.BRElement;
import com.google.gwt.dom.client.BaseElement;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DListElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.octo.gwt.test17.internal.overrides.OverrideHistory;
import com.octo.gwt.test17.internal.patcher.AnchorElementPatcher;
import com.octo.gwt.test17.internal.patcher.DOMImplPatcher;
import com.octo.gwt.test17.internal.patcher.DocumentPatcher;
import com.octo.gwt.test17.internal.patcher.ElementPatcher;
import com.octo.gwt.test17.internal.patcher.GWTPatcher;
import com.octo.gwt.test17.internal.patcher.InputElementPatcher;
import com.octo.gwt.test17.internal.patcher.JavaScriptObjectPatcher;
import com.octo.gwt.test17.internal.patcher.NodeListPatcher;
import com.octo.gwt.test17.internal.patcher.NodePatcher;
import com.octo.gwt.test17.internal.patcher.StylePatcher;

public class PatchGWT {

	/**
	 * Indicate if gwt has been patch
	 */
	private static boolean hasBeenPatched = false;

	private static Locale LOCALE = null;

	/**
	 * List of already patched custom classes
	 */
	private static List<String> alreadyPatched = new ArrayList<String>();

	public static void setGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		GWTPatcher.gwtCreateHandler = gwtCreateHandler;
	}

	public static boolean areAssertionEnabled() {
		boolean enabled = false;
		assert enabled = true;
		return enabled;
	}

	public static void reset() throws Exception {
		LOCALE = null;
		//		PatchCurrencyList.reset();
		PatchUtils.clearSequenceReplacement();
		OverrideHistory.reset();
		GWTPatcher.createClass.clear();
		GWTPatcher.gwtCreateHandler = null;
		GWTPatcher.gwtLogHandler = null;

		//		WidgetCollection widgetCollection = ReflectionUtils.getPrivateFieldValue(RootPanel.get(), "children");
		//		Widget[] array = ReflectionUtils.getPrivateFieldValue(widgetCollection, "array");
		//		for (int i = 0; i < array.length; i++) {
		//			array[i] = null;
		//		}
		//		ReflectionUtils.setPrivateField(widgetCollection, "size", 0);
		//
		//		ReflectionUtils.getStaticAndCallClear(Timer.class, "timers");
		//		ReflectionUtils.getStaticAndCallClear(RootPanel.class, "rootPanels");
		//		ReflectionUtils.getStaticAndCallClear(RootPanel.class, "widgetsToDetach");
		//
		//		Object commandExecutor = ReflectionUtils.getStaticFieldValue(Class.forName("com.google.gwt.user.client.DeferredCommand"), "commandExecutor");
		//		ReflectionUtils.callClear(ReflectionUtils.getPrivateFieldValue(commandExecutor, "commands"));
		//
		//		HistoryImpl historyImpl = ReflectionUtils.getStaticFieldValue(History.class, "impl");
		//		ReflectionUtils.callClear(ReflectionUtils.getPrivateFieldValue(ReflectionUtils.getPrivateFieldValue(ReflectionUtils.getPrivateFieldValue(
		//				historyImpl, "handlers"), "registry"), "map"));
		//
		//		ReflectionUtils.setStaticField(NumberFormat.class, "cachedDecimalFormat", null);
		//		ReflectionUtils.setStaticField(NumberFormat.class, "cachedScientificFormat", null);
		//		ReflectionUtils.setStaticField(NumberFormat.class, "cachedPercentFormat", null);
		//		ReflectionUtils.setStaticField(NumberFormat.class, "cachedCurrencyFormat", null);
		//
		//		ReflectionUtils.setStaticField(Window.class, "handlers", null);
		//		ReflectionUtils.setStaticField(Event.class, "handlers", null);

		//		PatchTimer.clear();
	}

	public static void addCreateClass(Class<?> classLiteral, Object object) {
		GWTPatcher.createClass.put(classLiteral, object);
	}

	public static void addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		GWTPatcher.gwtCreateHandler = gwtCreateHandler;
	}

	public static void init() throws Exception {
		if (hasBeenPatched) {
			return;
		}

		try {
			Class.forName("com.octo.gwt.test17.bootstrap.Startup");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unable to load com.octo.gwt.test17.bootstrap.Startup.Startup class, you probably forgot to "
					+ "add the JVM parameter: -javaagent:target/bootstrap.jar");
		}

		PatchUtils.initRedefineMethod();
		PatchUtils.initLoadPropertiesMethod();
		PatchUtils.patchFinalizeMethod();

		PatchUtils.patch(GWT.class, new GWTPatcher());
		PatchUtils.patch(JavaScriptObject.class, new JavaScriptObjectPatcher());
		PatchUtils.patch(Document.class, new DocumentPatcher());
		PatchUtils.patch(Style.class, new StylePatcher());
		PatchUtils.patch(AnchorElement.class, new AnchorElementPatcher());
		PatchUtils.patch(AreaElement.class, null);
		PatchUtils.patch(BaseElement.class, null);
		PatchUtils.patch(BodyElement.class, null);
		PatchUtils.patch(BRElement.class, null);
		PatchUtils.patch(ButtonElement.class, null);
		PatchUtils.patch(DivElement.class, null);
		PatchUtils.patch(DListElement.class, null);
		PatchUtils.patch(Element.class, new ElementPatcher());
		PatchUtils.patch(InputElement.class, new InputElementPatcher());
		PatchUtils.patch(Class.forName(PatchConstants.CLIENT_DOM_IMPL_CLASS_NAME), new DOMImplPatcher());
		PatchUtils.patch(NodeList.class, new NodeListPatcher());
		PatchUtils.patch(Node.class, new NodePatcher());

		hasBeenPatched = true;
	}

	public static Locale getLocale() {
		return LOCALE;
	}

	public static void setLocale(Locale locale) {
		LOCALE = locale;
	}

	public static void setLogHandler(IGWTLogHandler gwtLogHandler) {
		GWTPatcher.gwtLogHandler = gwtLogHandler;
	}

}
