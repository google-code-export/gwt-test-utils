package com.octo.gwt.test17;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.FrameElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.impl.CurrencyData;
import com.google.gwt.i18n.client.impl.CurrencyList;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.impl.DOMImpl;
import com.google.gwt.user.client.impl.ElementMapperImpl;
import com.google.gwt.user.client.impl.HistoryImpl;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.google.gwt.user.client.ui.impl.FocusImplOld;
import com.google.gwt.user.client.ui.impl.TextBoxImpl;
import com.octo.gwt.test17.internal.PatchAnchorElement;
import com.octo.gwt.test17.internal.PatchCheckBox;
import com.octo.gwt.test17.internal.PatchComplexPanel;
import com.octo.gwt.test17.internal.PatchCurrencyList;
import com.octo.gwt.test17.internal.PatchDOM;
import com.octo.gwt.test17.internal.PatchDOMImpl;
import com.octo.gwt.test17.internal.PatchDeferredCommand;
import com.octo.gwt.test17.internal.PatchDocument;
import com.octo.gwt.test17.internal.PatchDuration;
import com.octo.gwt.test17.internal.PatchElement;
import com.octo.gwt.test17.internal.PatchElementMapperImpl;
import com.octo.gwt.test17.internal.PatchFlexTable;
import com.octo.gwt.test17.internal.PatchFrame;
import com.octo.gwt.test17.internal.PatchGrid;
import com.octo.gwt.test17.internal.PatchHTMLTable;
import com.octo.gwt.test17.internal.PatchImage;
import com.octo.gwt.test17.internal.PatchInputElement;
import com.octo.gwt.test17.internal.PatchLabelElement;
import com.octo.gwt.test17.internal.PatchListBox;
import com.octo.gwt.test17.internal.PatchMainGWT;
import com.octo.gwt.test17.internal.PatchNode;
import com.octo.gwt.test17.internal.PatchNodeList;
import com.octo.gwt.test17.internal.PatchNumberFormat;
import com.octo.gwt.test17.internal.PatchTextArea;
import com.octo.gwt.test17.internal.PatchTextBox;
import com.octo.gwt.test17.internal.PatchTextBoxImpl;
import com.octo.gwt.test17.internal.PatchTimer;
import com.octo.gwt.test17.internal.PatchUIObject;
import com.octo.gwt.test17.internal.PatchURL;
import com.octo.gwt.test17.internal.dom.UserElement;
import com.octo.gwt.test17.internal.overrides.OverrideAnchorElement;
import com.octo.gwt.test17.internal.overrides.OverrideEvent;
import com.octo.gwt.test17.internal.overrides.OverrideFrameElement;
import com.octo.gwt.test17.internal.overrides.OverrideHistory;
import com.octo.gwt.test17.internal.overrides.OverrideImageElement;
import com.octo.gwt.test17.internal.overrides.OverrideInputElement;
import com.octo.gwt.test17.internal.overrides.OverrideLabelElement;
import com.octo.gwt.test17.internal.overrides.OverrideOptionElement;
import com.octo.gwt.test17.internal.overrides.OverrideSelectElement;
import com.octo.gwt.test17.internal.overrides.OverrideStyle;
import com.octo.gwt.test17.internal.overrides.OverrideTextAreaElement;

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
		PatchMainGWT.gwtCreateHandler = gwtCreateHandler;
	}

	public static boolean areAssertionEnabled() {
		boolean enabled = false;
		assert enabled = true;
		return enabled;
	}

	public static void reset() throws Exception {
		LOCALE = null;
		PatchCurrencyList.reset();
		PatchUtils.clearSequenceReplacement();
		OverrideHistory.reset();
		PatchMainGWT.createClass.clear();
		PatchMainGWT.gwtCreateHandler = null;
		PatchMainGWT.gwtLogHandler = null;

		WidgetCollection widgetCollection = ReflectionUtils.getPrivateFieldValue(RootPanel.get(), "children");
		Widget[] array = ReflectionUtils.getPrivateFieldValue(widgetCollection, "array");
		for (int i = 0; i < array.length; i++) {
			array[i] = null;
		}
		ReflectionUtils.setPrivateField(widgetCollection, "size", 0);

		ReflectionUtils.getStaticAndCallClear(Timer.class, "timers");
		ReflectionUtils.getStaticAndCallClear(RootPanel.class, "rootPanels");
		ReflectionUtils.getStaticAndCallClear(RootPanel.class, "widgetsToDetach");

		Object commandExecutor = ReflectionUtils.getStaticFieldValue(Class.forName("com.google.gwt.user.client.DeferredCommand"), "commandExecutor");
		ReflectionUtils.callClear(ReflectionUtils.getPrivateFieldValue(commandExecutor, "commands"));

		HistoryImpl historyImpl = ReflectionUtils.getStaticFieldValue(History.class, "impl");
		ReflectionUtils.callClear(ReflectionUtils.getPrivateFieldValue(ReflectionUtils.getPrivateFieldValue(ReflectionUtils.getPrivateFieldValue(
				historyImpl, "handlers"), "registry"), "map"));

		ReflectionUtils.setStaticField(NumberFormat.class, "cachedDecimalFormat", null);
		ReflectionUtils.setStaticField(NumberFormat.class, "cachedScientificFormat", null);
		ReflectionUtils.setStaticField(NumberFormat.class, "cachedPercentFormat", null);
		ReflectionUtils.setStaticField(NumberFormat.class, "cachedCurrencyFormat", null);

		ReflectionUtils.setStaticField(Window.class, "handlers", null);
		ReflectionUtils.setStaticField(Event.class, "handlers", null);

		PatchTimer.clear();
	}

	public static void addCreateClass(Class<?> classLiteral, Object object) {
		PatchMainGWT.createClass.put(classLiteral, object);
	}

	public static void addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		PatchMainGWT.gwtCreateHandler = gwtCreateHandler;
	}

	public static void patch(Class<?> clazz, Patch[] listOfPatch) throws Exception {
		if (alreadyPatched.contains(clazz.getCanonicalName())) {
			return;
		}
		alreadyPatched.add(clazz.getCanonicalName());
		PatchUtils.applyPatches(clazz, listOfPatch);
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

		hasBeenPatched = true;

		PatchUtils.applyPatches(GWT.class,
				new Patch[] { new PatchMethod("log", staticCall(PatchMainGWT.class, "log", "$1, $2")),
						new PatchMethod("create", staticCall(PatchMainGWT.class, "create", "$1")),
						new PatchMethod("getHostPageBaseURL", "\"getHostPageBaseURL/getModuleName\""),
						new PatchMethod("getModuleName", "\"getModuleName\""), });

		PatchUtils.applyPatches(UIObject.class, new Patch[] {
				new PatchMethod("setElement", "setElement(" + staticCall(PatchUIObject.class, "cast", "$1") + ")").setFinal(),
				new PatchMethod("updatePrimaryAndDependentStyleNames", staticCall(PatchUIObject.class, "updatePrimaryAndDependentStyleNames",
						"$1, $2")),
				new PatchMethod("setTitle", staticCall(PatchUIObject.class, "setPropertyOnElement", "this.getElement(), \"title\", $1")),
				new PatchMethod("getTitle", staticCall(PatchUIObject.class, "getPropertyOnElement", "this.getElement(), \"title\"")),
				new PatchMethod("setVisible", staticCall(PatchUIObject.class, "setPropertyOnElement", "$1, \"visible\", $2")).setNative(),
				new PatchMethod("isVisible", staticCall(PatchUIObject.class, "getPropertyOnElementBoolean", "$1, \"visible\"")).setNative(),
				new PatchMethod("getStyleName", staticCall(PatchUIObject.class, "getStyleName", "$1")).setStatic(),
				new PatchMethod("getElement", staticCall(PatchUIObject.class, "cast", "element")), new PatchMethod("getAbsoluteLeft", "0"),
				new PatchMethod("getAbsoluteTop", "0"), new PatchMethod("extractLengthValue", "1.0"),
				new PatchMethod("setWidth", staticCall(PatchUIObject.class, "setPropertyOnElement", "this.getElement(), \"width\", $1")),
				new PatchMethod("setHeight", staticCall(PatchUIObject.class, "setPropertyOnElement", "this.getElement(), \"height\", $1")), });

		PatchUtils.applyPatches(Widget.class, new Patch[] { new PatchMethod("onAttach", "") });

		PatchUtils.applyPatches(FocusImplOld.class, new Patch[] {
				new PatchMethod("setTabIndex", cast(UserElement.class, "$1") + ".setOverrideTabIndex($2)"),
				new PatchMethod("createBlurHandler", "null"),
				new PatchMethod("createFocusHandler", "null"),
				new PatchMethod("createMouseHandler", "null"),
				new PatchMethod("createFocusable",
						staticCall(PatchUIObject.class, "cast", staticCall(PatchDOMImpl.class, "createElement", "\"div\""))),
				new PatchMethod("focus", ""), });

		PatchUtils.applyPatches(FocusImpl.class, new Patch[] {
				new PatchMethod("setTabIndex", cast(UserElement.class, "$1") + ".setOverrideTabIndex($2)"),
				new PatchMethod("getTabIndex", castAndCall(UserElement.class, "getOverrideTabIndex")), new PatchMethod("focus", ""),
				new PatchMethod("blur", ""), });

		PatchUtils.applyPatches(Document.class, new Patch[] { new PatchMethod("get", staticCall(PatchDocument.class, "get")),
				new PatchMethod("getBody", staticCall(PatchDocument.class, "getBody")),
				new PatchMethod("createImageElement", staticCall(PatchDocument.class, "createImageElement")),
				new PatchMethod("getCompatMode", "return \"BackCompat\""), new PatchMethod("getClientWidth", "return 0;"),
				new PatchMethod("getClientHeight", "return 0;"), new PatchMethod("getScrollLeft", "return 0;"),
				new PatchMethod("getScrollTop", "return 0;"), });

		PatchUtils.applyPatches(getClass(PatchConstants.CLIENT_DOM_IMPL_CLASS_NAME), new Patch[] {
				new PatchMethod("createElement", staticCall(PatchDOMImpl.class, "createElement", "$2")),
				new PatchMethod("createButtonElement", staticCall(PatchDOMImpl.class, "createButtonElement", "$1, $2")),
				new PatchMethod("createInputElement", "new " + OverrideInputElement.class.getCanonicalName() + "($1)"),
				new PatchMethod("eventGetType", staticCall(PatchDOMImpl.class, "eventGetType", "$1")),
				new PatchMethod("getParentElement", castAndCall(UserElement.class, "getOverrideParent")),
				new PatchMethod("eventGetButton", castAndCall(OverrideEvent.class, "getOverrideButton")),
				new PatchMethod("eventGetShiftKey", castAndCall(OverrideEvent.class, "isOverrideShiftKey")),
				new PatchMethod("eventGetMetaKey", castAndCall(OverrideEvent.class, "isOverrideMetaKey")),
				new PatchMethod("eventGetCtrlKey", castAndCall(OverrideEvent.class, "isOverrideCtrlKey")),
				new PatchMethod("eventGetAltKey", castAndCall(OverrideEvent.class, "isOverrideAltKey")),
				new PatchMethod("selectAdd", staticCall(PatchDOMImpl.class, "selectAdd", "$1, $2, $3")),
				new PatchMethod("selectGetOptions", staticCall(PatchDOMImpl.class, "selectGetOptions", "$1")),
				new PatchMethod("imgSetSrc", staticCall(PatchUIObject.class, "getPropertyOnElement", "$1, \"imgSrc\"")),
				new PatchMethod("getBodyOffsetLeft", "0"), new PatchMethod("getBodyOffsetTop", "0"),
				new PatchMethod("eventGetKeyCode", castAndCall(OverrideEvent.class, "getOverrideKeyCode")), });

//		PatchUtils.applyPatches(Button.class, new Patch[] { new PatchMethod("adjustType", ""),
//		//			new Patch("click", "onBrowserEvent(new " + OverrideEvent.class.getCanonicalName() + "(" + Event.class.getCanonicalName()
//				//					+ ".ONCLICK));").setArgsLen(0) 
//				});

		PatchUtils.applyPatches(com.google.gwt.dom.client.Element.class,
				new Patch[] {
						new PatchMethod("setId", castThisAndCall(UserElement.class, "setOverrideId", "$1")),
						new PatchMethod("getId", castThisAndCall(UserElement.class, "getOverrideId")),
						new PatchMethod("setAttribute", castThisAndCall(UserElement.class, "setOverrideAttribute", "$1, $2")),
						new PatchMethod("getAttribute", castThisAndCall(UserElement.class, "getOverrideAttribute", "$1")),
						new PatchMethod("setInnerHTML", castThisAndCall(UserElement.class, "setOverrideInnerHtml", "$1")),
						new PatchMethod("getInnerHTML", castThisAndCall(UserElement.class, "getOverrideInnerHtml")),
						new PatchMethod("setClassName", castThisAndCall(UserElement.class, "setOverrideClassName", "$1")),
						new PatchMethod("getClassName", castThisAndCall(UserElement.class, "getOverrideClassName")),
						new PatchMethod("setInnerText", castThisAndCall(UserElement.class, "setOverrideInnerText", "$1")),
						new PatchMethod("getInnerText", castThisAndCall(UserElement.class, "getOverrideInnerText")),
						new PatchMethod("getStyle", castThisAndCall(UserElement.class, "getOverrideStyle")),
						new PatchMethod("setPropertyDouble", castThisAndCall(UserElement.class, "setOverrideProperty", "$1, Double.toString($2)")),
						new PatchMethod("setPropertyInt", castThisAndCall(UserElement.class, "setOverrideProperty", "$1, Integer.toString($2)")),
						new PatchMethod("setPropertyBoolean", castThisAndCall(UserElement.class, "setOverrideProperty", "$1, Boolean.toString($2)")),
						new PatchMethod("setPropertyString", castThisAndCall(UserElement.class, "setOverrideProperty", "$1, $2")),
						new PatchMethod("getPropertyDouble", "Double.parseDouble(" + castThisAndCall(UserElement.class, "getOverrideProperty", "$1")
								+ ")"),
						new PatchMethod("getPropertyInt", castThisAndCall(UserElement.class, "getOverridePropertyInt", "$1")),
						new PatchMethod("getPropertyBoolean", "Boolean.parseBoolean("
								+ castThisAndCall(UserElement.class, "getOverrideProperty", "$1") + ")"),
						new PatchMethod("getPropertyString", castThisAndCall(UserElement.class, "getOverrideProperty", "$1")),
						//new PatchMethod("isOrHasChild", castThisAndCall(UserElement.class, "isOrHasChild", "$1")),
						new PatchMethod("getFirstChildElement", staticCall(PatchElement.class, "getFirstChildElement", "this")),
						new PatchMethod("getTagName", staticCall(PatchElement.class, "getTagName", "this")), });

		PatchUtils.applyPatches(DOMImpl.class, new Patch[] { new PatchMethod("getEventsSunk", "return 1;"),
				new PatchMethod("eventGetTypeInt", staticCall(PatchDOMImpl.class, "eventGetTypeInt", "$1"), new Class[] { String.class }),
				new PatchMethod("setEventListener", "return;"), });

		PatchUtils.applyPatches(JavaScriptObject.class, new Patch[] { new PatchMethod("cast", staticCall(PatchUIObject.class, "cast", "this")), });

		PatchUtils.applyPatches(Event.class, new Patch[] {
				new PatchMethod("getTarget", staticCall(OverrideEvent.class, "overrideCast", "this") + ".getOverrideTargetElement()"),
				new PatchMethod("getRelatedTarget", staticCall(OverrideEvent.class, "overrideCast", "this") + ".getOverrideTargetElement()"), });

		PatchUtils.applyPatches(Node.class, new Patch[] { new PatchMethod("appendChild", staticCall(PatchNode.class, "appendChild", "this, $1")),
				new PatchMethod("cloneNode", castThisAndCall(UserElement.class, "overrideClone", "$1")),
				new PatchMethod("removeChild", staticCall(PatchNode.class, "removeChild", "this, $1")),
				new PatchMethod("getFirstChild", staticCall(PatchNode.class, "getFirstChild", "this")),
				new PatchMethod("is", staticCall(PatchNode.class, "is", "$1")), });

		PatchUtils.applyPatches(Style.class, new Patch[] {
				new PatchMethod("getPropertyImpl", castThisAndCall(OverrideStyle.class, "getOverridePropertyImpl", "$1")),
				new PatchMethod("setPropertyImpl", castThisAndCall(OverrideStyle.class, "setOverridePropertyImpl", "$1, $2")),
				//new PatchMethod("setPropertyPx", castThisAndCall(OverrideStyle.class, "setOverridePropertyPx", "$1, $2")), });
		 });

		PatchUtils.applyPatches(DOM.class, new Patch[] { new PatchMethod("createUniqueId", staticCall(PatchDOM.class, "createUniqueId", "")), });

		PatchUtils.applyPatches(HistoryImpl.class, new Patch[] {
				new PatchMethod("getToken", OverrideHistory.class.getCanonicalName() + ".getToken()"),
				new PatchMethod("setToken", OverrideHistory.class.getCanonicalName() + ".setToken($1)"), new PatchMethod("init", "return true;"),
				new PatchMethod("nativeUpdate", "return;"), });

		PatchUtils.applyPatches(Timer.class, new Patch[] { new PatchMethod("cancel", ""),
				new PatchMethod("schedule", staticCall(PatchTimer.class, "schedule", "this, $1")),
				new PatchMethod("scheduleRepeating", staticCall(PatchTimer.class, "scheduleRepeating", "this, $1")), });

		PatchUtils.applyPatches(DeferredCommand.class, new Patch[] { new PatchMethod("addCommand", staticCall(PatchDeferredCommand.class,
				"immediateCommand", "$1")).setArgClasses(new Class[] { Command.class }) });

		PatchUtils.applyPatches(RootPanel.class, new Patch[] { new PatchMethod("getBodyElement", staticCall(PatchUIObject.class, "cast", staticCall(
				PatchDOMImpl.class, "createElement", "\"body\""))), });

		PatchUtils.applyPatches(History.class, new Patch[] { new PatchMethod("back", OverrideHistory.class.getCanonicalName() + ".back()"), });

		PatchUtils.applyPatches(ListBox.class, new Patch[] {
				new PatchMethod("getSelectElement", staticCall(PatchListBox.class, "getSelectElement", "this")),
				new PatchMethod("clear", staticCall(PatchListBox.class, "clearListBox", "this")) });

		PatchUtils.applyPatches(OptionElement.class, new Patch[] {
				new PatchMethod("setText", castThisAndCall(OverrideOptionElement.class, "setOverrideText", "$1")),
				new PatchMethod("getText", castThisAndCall(OverrideOptionElement.class, "getOverrideText")),
				new PatchMethod("setValue", castThisAndCall(OverrideOptionElement.class, "setOverrideValue", "$1")),
				new PatchMethod("getValue", castThisAndCall(OverrideOptionElement.class, "getOverrideValue")), });

		PatchUtils.applyPatches(SelectElement.class, new Patch[] {
				new PatchMethod("getSelectedIndex", castThisAndCall(OverrideSelectElement.class, "getOverrideSelectedIndex")),
				new PatchMethod("setSelectedIndex", castThisAndCall(OverrideSelectElement.class, "setOverrideSelectedIndex", "$1")),
				new PatchMethod("getSize", castThisAndCall(OverrideSelectElement.class, "getOverrideSize")),
				new PatchMethod("setSize", castThisAndCall(OverrideSelectElement.class, "setOverrideSize", "$1")),
				new PatchMethod("getName", castThisAndCall(OverrideSelectElement.class, "getOverrideName")),
				new PatchMethod("setName", castThisAndCall(OverrideSelectElement.class, "setOverrideName", "$1")),
				new PatchMethod("isMultiple", castThisAndCall(OverrideSelectElement.class, "isOverrideMultiple")),
				new PatchMethod("setMultiple", castThisAndCall(OverrideSelectElement.class, "setOverrideMultiple", "$1")), });

		PatchUtils.applyPatches(NodeList.class, new Patch[] {
				new PatchMethod("getLength", staticCall(PatchNodeList.class, "getLengthUserNodeList", "this")),
				new PatchMethod("getItem", staticCall(PatchNodeList.class, "getItemUserNodeList", "this, $1")) });

		PatchUtils.applyPatches(HTMLTable.class, new Patch[] {
				new PatchMethod("getDOMRowCount", staticCall(PatchHTMLTable.class, "getDOMRowCount", "this"), new Class[] {}),
				new PatchMethod("getDOMCellCount", staticCall(PatchHTMLTable.class, "getDOMCellCount", "$1, $2"), new Class[] { Element.class,
						Integer.TYPE }), });

		PatchUtils.applyPatches(Class.forName(HTMLTable.class.getCanonicalName() + "$RowFormatter"), new Patch[] { new PatchMethod("getRow",
				staticCall(PatchHTMLTable.class, "getRowRowFormatter", "$1, $2")), });

		PatchUtils.applyPatches(FlexTable.class,
				new Patch[] { new PatchMethod("addCells", staticCall(PatchFlexTable.class, "addCells", "$1, $2, $3")), });

		PatchUtils.applyPatches(HTMLTable.CellFormatter.class, new Patch[] { new PatchMethod("getCellElement", staticCall(PatchHTMLTable.class,
				"getCellElement", "$1, $2, $3")), });

		PatchUtils.applyPatches(ElementMapperImpl.class, new Patch[] {
				new PatchMethod("setIndex", staticCall(PatchElementMapperImpl.class, "setWidgetIndex", "$1, $2")),
				new PatchMethod("getIndex", staticCall(PatchElementMapperImpl.class, "getWidgetIndex", "$1")),
				new PatchMethod("clearIndex", staticCall(PatchElementMapperImpl.class, "clearWidgetIndex", "$1")) });

		PatchUtils.applyPatches(Grid.class, new Patch[] { new PatchMethod("addRows", staticCall(PatchGrid.class, "addRows", "$1, $2, $3")) });

		PatchUtils.applyPatches(InputElement.class, new Patch[] { new PatchMethod("focus", ""),
				new PatchMethod("as", staticCall(PatchInputElement.class, "as", "$1")),
				new PatchMethod("setTabIndex", castThisAndCall(OverrideInputElement.class, "setOverrideTabIndex", "$1")),
				new PatchMethod("isDefaultChecked", castThisAndCall(OverrideInputElement.class, "isOverrideDefaultChecked")),
				new PatchMethod("setDefaultChecked", castThisAndCall(OverrideInputElement.class, "setOverrideDefaultChecked", "$1")),
				new PatchMethod("setChecked", castThisAndCall(OverrideInputElement.class, "setOverrideChecked", "$1")),
				new PatchMethod("isDisabled", castThisAndCall(OverrideInputElement.class, "isOverrideDisabled")),
				new PatchMethod("setDisabled", castThisAndCall(OverrideInputElement.class, "setOverrideDisabled", "$1")),
				new PatchMethod("setMaxLength", castThisAndCall(OverrideInputElement.class, "setOverrideMaxLength", "$1")),
				new PatchMethod("getMaxLength", castThisAndCall(OverrideInputElement.class, "getOverrideMaxLength")),
				new PatchMethod("setName", castThisAndCall(OverrideInputElement.class, "setOverrideName", "$1")),
				new PatchMethod("getName", castThisAndCall(OverrideInputElement.class, "getOverrideName")),
				new PatchMethod("setValue", castThisAndCall(OverrideInputElement.class, "setOverrideValue", "$1")),
				new PatchMethod("getValue", castThisAndCall(OverrideInputElement.class, "getOverrideValue")),
				new PatchMethod("setTabIndex", castThisAndCall(OverrideInputElement.class, "setOverrideTabIndex", "$1")),
				new PatchMethod("getTabIndex", castThisAndCall(OverrideInputElement.class, "getOverrideTabIndex")),
				new PatchMethod("setAccessKey", castThisAndCall(OverrideInputElement.class, "setOverrideAccessKey", "$1")),
				new PatchMethod("getAccessKey", castThisAndCall(OverrideInputElement.class, "getOverrideAccessKey")), });

		PatchUtils.applyPatches(AnchorElement.class, new Patch[] { new PatchMethod("as", staticCall(PatchAnchorElement.class, "as", "$1")),
				new PatchMethod("setTabIndex", castThisAndCall(OverrideAnchorElement.class, "setOverrideTabIndex", "$1")),
				new PatchMethod("getTabIndex", castThisAndCall(OverrideAnchorElement.class, "getOverrideTabIndex")),
				new PatchMethod("setName", castThisAndCall(OverrideAnchorElement.class, "setOverrideName", "$1")),
				new PatchMethod("getName", castThisAndCall(OverrideAnchorElement.class, "getOverrideName")),
				new PatchMethod("setTarget", castThisAndCall(OverrideAnchorElement.class, "setOverrideTarget", "$1")),
				new PatchMethod("getTarget", castThisAndCall(OverrideAnchorElement.class, "getOverrideTarget")),
				new PatchMethod("setHref", castThisAndCall(OverrideAnchorElement.class, "setOverrideHref", "$1")),
				new PatchMethod("getHref", castThisAndCall(OverrideAnchorElement.class, "getOverrideHref")),
				new PatchMethod("setAccessKey", castThisAndCall(OverrideAnchorElement.class, "setOverrideAccessKey", "$1")),
				new PatchMethod("focus", ""), new PatchMethod("blur", ""), });

		PatchUtils.applyPatches(LabelElement.class, new Patch[] { new PatchMethod("as", staticCall(PatchLabelElement.class, "as", "$1")),
				new PatchMethod("setHtmlFor", castThisAndCall(OverrideLabelElement.class, "setOverrideHtmlFor", "$1")),
				new PatchMethod("getHtmlFor", castThisAndCall(OverrideLabelElement.class, "getOverrideHtmlFor")), });

		PatchUtils.applyPatches(Image.class,
				new Patch[] { new PatchMethod("getImageElement", staticCall(PatchImage.class, "getImageElement", "this")) });

		PatchUtils.applyPatches(ImageElement.class, new Patch[] {
				new PatchMethod("setSrc", castThisAndCall(OverrideImageElement.class, "setOverrideSrc", "$1")),
				new PatchMethod("getSrc", castThisAndCall(OverrideImageElement.class, "getOverrideSrc")),
				new PatchMethod("getHeight", castThisAndCall(OverrideImageElement.class, "getOverrideHeight")),
				new PatchMethod("getWidth", castThisAndCall(OverrideImageElement.class, "getOverrideWidth")), });

		PatchUtils.applyPatches(Frame.class,
				new Patch[] { new PatchMethod("getFrameElement", staticCall(PatchFrame.class, "getFrameElement", "this")), });

		PatchUtils.applyPatches(FrameElement.class, new Patch[] {
				new PatchMethod("setSrc", castThisAndCall(OverrideFrameElement.class, "setOverrideSrc", "$1")),
				new PatchMethod("getSrc", castThisAndCall(OverrideFrameElement.class, "getOverrideSrc")), });

		PatchUtils
				.applyPatches(CurrencyList.class, new Patch[] { new PatchMethod("getDefault", staticCall(PatchCurrencyList.class, "getDefault")), });

		PatchUtils.applyPatches(CurrencyData.class, new Patch[] {
				new PatchMethod("getCurrencyCode", staticCall(PatchCurrencyList.class, "getCurrencyCode", "this")),
				new PatchMethod("getCurrencySymbol", staticCall(PatchCurrencyList.class, "getCurrencySymbol", "this")),
				new PatchMethod("getFlagsAndPrecision", staticCall(PatchCurrencyList.class, "getFlagsAndPrecision", "this")), });

		PatchUtils.applyPatches(TextBox.class, new Patch[] { new PatchMethod("getInputElement", staticCall(PatchTextBox.class, "getInputElement",
				"this")), });

		PatchUtils.applyPatches(TextArea.class, new Patch[] { new PatchMethod("getTextAreaElement", staticCall(PatchTextArea.class,
				"getTextAreaElement", "this")), });

		PatchUtils.applyPatches(TextAreaElement.class, new Patch[] {
				new PatchMethod("setRows", castThisAndCall(OverrideTextAreaElement.class, "setOverrideRows", "$1")),
				new PatchMethod("getRows", castThisAndCall(OverrideTextAreaElement.class, "getOverrideRows")), });

		PatchUtils.applyPatches(CheckBox.class, new Patch[] { new PatchMethod("getName", staticCall(PatchCheckBox.class, "getName", "this")),
				new PatchMethod("setName", staticCall(PatchCheckBox.class, "setName", "this, $1")),
				new PatchMethod("getText", staticCall(PatchCheckBox.class, "getText", "this")),
				new PatchMethod("setText", staticCall(PatchCheckBox.class, "setText", "this, $1")),
				new PatchMethod("getHTML", staticCall(PatchCheckBox.class, "getHTML", "this")),
				new PatchMethod("setHTML", staticCall(PatchCheckBox.class, "setHTML", "this, $1")), });

		PatchUtils.applyPatches(RadioButton.class,
				new Patch[] { new PatchMethod("setName", staticCall(PatchCheckBox.class, "setName", "this, $1")), });

		PatchUtils.applyPatches(URL.class, new Patch[] { new PatchMethod("encodeComponentImpl", staticCall(PatchURL.class, "urlEncode", "$1")) });

		PatchUtils.applyPatches(Duration.class, new Patch[] { new PatchMethod("currentTimeMillis", staticCall(PatchDuration.class,
				"getTimeInMillisec")) });

		PatchUtils.applyPatches(ComplexPanel.class, new Patch[] { new PatchMethod("getChildren", staticCall(PatchComplexPanel.class, "getChildren",
				"this")), });
		
		PatchUtils.applyPatches(TextBoxImpl.class, new Patch[] {
			new PatchMethod("getCursorPos", staticCall(PatchTextBoxImpl.class, "getCursorPos", "this, $1")),
			new PatchMethod("setSelectionRange", staticCall(PatchTextBoxImpl.class, "setSelectionRange", "this, $1, $2")),
		});
		
		PatchUtils.applyPatches(NumberFormat.class, new Patch[] {
			new PatchMethod("toFixed", staticCall(PatchNumberFormat.class, "toFixed", "$1, $2")),
		});
	}

	private static String staticCall(Class<?> clazz, String methodName, String args) {
		return clazz.getCanonicalName() + "." + methodName + "(" + args + ")";
	}

	private static String staticCall(Class<?> clazz, String methodName) {
		return staticCall(clazz, methodName, "");
	}

	private static String castThisAndCall(Class<?> clazz, String methodName, String... params) {
		return internalCastThisAndCall(true, clazz, methodName, params);
	}

	private static String castAndCall(Class<?> clazz, String methodName, String... params) {
		return internalCastThisAndCall(false, clazz, methodName, params);
	}

	private static String cast(Class<?> clazz, String o) {
		return clazz.getCanonicalName() + ".overrideCast(" + o + ")";
	}

	private static String internalCastThisAndCall(boolean useThis, Class<?> clazz, String methodName, String... params) {
		String p = useThis ? "this" : "$1";
		String s = cast(clazz, p) + "." + methodName + "(";
		if (params.length > 0) {
			s += params[0];
		}
		s += ")";
		return s;
	}

	private static Class<?> getClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static Locale getLocale() {
		return LOCALE;
	}

	public static void setLocale(Locale locale) {
		LOCALE = locale;
	}

	public static void setLogHandler(IGWTLogHandler gwtLogHandler) {
		PatchMainGWT.gwtLogHandler = gwtLogHandler;
	}

}
