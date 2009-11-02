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
import com.google.gwt.i18n.client.impl.CurrencyData;
import com.google.gwt.i18n.client.impl.CurrencyList;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.impl.DOMImpl;
import com.google.gwt.user.client.impl.ElementMapperImpl;
import com.google.gwt.user.client.impl.HistoryImpl;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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
import com.octo.gwt.test17.internal.PatchAnchorElement;
import com.octo.gwt.test17.internal.PatchCheckBox;
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
import com.octo.gwt.test17.internal.PatchListBox;
import com.octo.gwt.test17.internal.PatchMainGWT;
import com.octo.gwt.test17.internal.PatchNode;
import com.octo.gwt.test17.internal.PatchNodeList;
import com.octo.gwt.test17.internal.PatchTextArea;
import com.octo.gwt.test17.internal.PatchTextBox;
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
		OverrideHistory.reset();
		PatchMainGWT.createClass.clear();
		PatchMainGWT.gwtCreateHandler = null;

		WidgetCollection widgetCollection = ReflectionUtils.getPrivateFieldValue(RootPanel.get(), "children");
		Widget[] array = ReflectionUtils.getPrivateFieldValue(widgetCollection, "array");
		for(int i = 0; i < array.length; i ++) {
			array[i] = null;
		}
		ReflectionUtils.setPrivateField(widgetCollection, "size", 0);

		ReflectionUtils.getStaticAndCallClear(Timer.class, "timers");
		ReflectionUtils.getStaticAndCallClear(RootPanel.class, "rootPanels");
		ReflectionUtils.getStaticAndCallClear(RootPanel.class, "widgetsToDetach");

		Object commandExecutor = ReflectionUtils.getStaticFieldValue(Class.forName("com.google.gwt.user.client.DeferredCommand"), "commandExecutor");
		ReflectionUtils.callClear(ReflectionUtils.getPrivateFieldValue(commandExecutor, "commands"));

		HistoryImpl historyImpl = ReflectionUtils.getStaticFieldValue(History.class, "impl");
		ReflectionUtils.callClear(ReflectionUtils.getPrivateFieldValue(ReflectionUtils.getPrivateFieldValue(ReflectionUtils.getPrivateFieldValue(historyImpl, "handlers"), "registry"), "map"));
		
		PatchTimer.clear();
	}

	public static void addCreateClass(Class<?> classLiteral, Object object) {
		PatchMainGWT.createClass.put(classLiteral, object);
	}

	public static void addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		PatchMainGWT.gwtCreateHandler = gwtCreateHandler;
	}

	public static void patchClazz(Class<?> clazz, Patch[] listOfPatch) throws Exception {
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

		PatchUtils.initRedefineMethod();

		hasBeenPatched = true;

		PatchUtils.applyPatches(GWT.class, new Patch[] {
			new Patch("create", staticCall(PatchMainGWT.class, "create", "$1")),
			new Patch("getHostPageBaseURL", "\"getHostPageBaseURL/getModuleName\""), 
			new Patch("getModuleName", "\"getModuleName\""),
		});

		PatchUtils.applyPatches(UIObject.class, new Patch[] { 
			new Patch("setElement", "setElement(" + staticCall(PatchUIObject.class, "cast", "$1") + ")").setFinal(),
			new Patch("setStylePrimaryName", staticCall(PatchUIObject.class, "setPropertyOnElement", "$1, \"stylePrimaryName\", $2")).setStatic(),
			new Patch("getStylePrimaryName", staticCall(PatchUIObject.class, "getPropertyOnElement", "$1, \"stylePrimaryName\"")).setStatic(),
			new Patch("setTitle", staticCall(PatchUIObject.class, "setPropertyOnElement", "this.getElement(), \"title\", $1")),
			new Patch("getTitle", staticCall(PatchUIObject.class, "getPropertyOnElement", "this.getElement(), \"title\"")),
			new Patch("setVisible", staticCall(PatchUIObject.class, "setPropertyOnElement", "$1, \"visible\", $2")).setNative(),
			new Patch("isVisible", staticCall(PatchUIObject.class, "getPropertyOnElementBoolean", "$1, \"visible\"")).setNative(),
			new Patch("getStyleName", staticCall(PatchUIObject.class, "getStyleName", "$1")).setStatic(),
			new Patch("setStyleName", staticCall(PatchUIObject.class, "setStyleName", "$1, $2, $3")).setArgClasses(new Class<?>[] {com.google.gwt.dom.client.Element.class, String.class, Boolean.TYPE }),
			new Patch("setStyleName", staticCall(PatchUIObject.class, "setPropertyOnElement", "$1, \"className\", $2")).setArgClasses(new Class<?>[] {com.google.gwt.dom.client.Element.class, String.class}),
			new Patch("getElement", staticCall(PatchUIObject.class, "cast", "element")), 
			new Patch("getAbsoluteLeft", "0"), 
			new Patch("getAbsoluteTop", "0"),
			new Patch("extractLengthValue", "1.0"),  	
		});

		PatchUtils.applyPatches(Widget.class, new Patch[] { 
			new Patch("onAttach", "") 
		});


		PatchUtils.applyPatches(FocusImplOld.class, new Patch[] { 
			new Patch("setTabIndex", cast(UserElement.class, "$1") + ".setOverrideTabIndex($2)"), 
			new Patch("createBlurHandler", "null"), 
			new Patch("createFocusHandler", "null"),
			new Patch("createMouseHandler", "null"), 
			new Patch("createFocusable", staticCall(PatchUIObject.class, "cast", staticCall(PatchDOMImpl.class, "createElement", "\"div\""))),
			new Patch("focus", ""),
		});

		PatchUtils.applyPatches(FocusImpl.class, new Patch[] { 
			new Patch("setTabIndex", cast(UserElement.class, "$1") + ".setOverrideTabIndex($2)"),
			new Patch("getTabIndex", castAndCall(UserElement.class, "getOverrideTabIndex")), 
			new Patch("focus", ""),
			new Patch("blur", ""),
		});


		PatchUtils.applyPatches(Document.class, new Patch[] {
			new Patch("get", staticCall(PatchDocument.class, "get")),
			new Patch("getBody", staticCall(PatchDocument.class, "getBody")),
			new Patch("createImageElement", staticCall(PatchDocument.class, "createImageElement")),
			new Patch("getCompatMode", "return \"BackCompat\""),
			new Patch("getClientWidth", "return 0;"), 
			new Patch("getClientHeight", "return 0;"),
			new Patch("getScrollLeft", "return 0;"), 
			new Patch("getScrollTop", "return 0;"), 
		});

		PatchUtils.applyPatches(getClass(PatchConstants.CLIENT_DOM_IMPL_CLASS_NAME), new Patch[] {
			new Patch("createElement", staticCall(PatchDOMImpl.class, "createElement", "$2")),
			new Patch("createInputElement", "new " + OverrideInputElement.class.getCanonicalName() + "($1)"),
			new Patch("eventGetType", staticCall(PatchDOMImpl.class, "eventGetType", "$1")),
			new Patch("getParentElement", castAndCall(UserElement.class, "getOverrideParent")),
			new Patch("eventGetButton", castAndCall(OverrideEvent.class, "getOverrideButton")),
			new Patch("eventGetShiftKey", castAndCall(OverrideEvent.class, "isOverrideShiftKey")),
			new Patch("eventGetMetaKey", castAndCall(OverrideEvent.class, "isOverrideMetaKey")),
			new Patch("eventGetCtrlKey", castAndCall(OverrideEvent.class, "isOverrideCtrlKey")),
			new Patch("eventGetAltKey", castAndCall(OverrideEvent.class, "isOverrideAltKey")),
			new Patch("selectAdd", staticCall(PatchDOMImpl.class, "selectAdd", "$1, $2, $3")), 
			new Patch("selectGetOptions", staticCall(PatchDOMImpl.class, "selectGetOptions", "$1")),
			new Patch("imgSetSrc", staticCall(PatchUIObject.class, "getPropertyOnElement", "$1, \"imgSrc\"")),
			new Patch("getBodyOffsetLeft", "0"), 
			new Patch("getBodyOffsetTop", "0"),
			new Patch("eventGetKeyCode", castAndCall(OverrideEvent.class, "getOverrideKeyCode")),
			//			new Patch("setEventListener", "return;"), 

		});

		PatchUtils.applyPatches(Button.class, new Patch[] {
			new Patch("adjustType", ""),
			//			new Patch("click", "onBrowserEvent(new " + OverrideEvent.class.getCanonicalName() + "(" + Event.class.getCanonicalName()
			//					+ ".ONCLICK));").setArgsLen(0) 
		});

		PatchUtils.applyPatches(com.google.gwt.dom.client.Element.class, new Patch[] { 
			new Patch("setId", castThisAndCall(UserElement.class, "setOverrideId", "$1")),
			new Patch("getId", castThisAndCall(UserElement.class, "getOverrideId")),
			new Patch("setAttribute", castThisAndCall(UserElement.class, "setOverrideAttribute", "$1, $2")),
			new Patch("getAttribute", castThisAndCall(UserElement.class, "getOverrideAttribute", "$1")),
			new Patch("setInnerHTML", castThisAndCall(UserElement.class, "setOverrideInnerHtml", "$1")),
			new Patch("getInnerHTML", castThisAndCall(UserElement.class, "getOverrideInnerHtml")),
			new Patch("setInnerText", castThisAndCall(UserElement.class, "setOverrideInnerText", "$1")),
			new Patch("getInnerText", castThisAndCall(UserElement.class, "getOverrideInnerText")),
			new Patch("getStyle", castThisAndCall(UserElement.class, "getOverrideStyle")),
			new Patch("setPropertyDouble", castThisAndCall(UserElement.class, "setOverrideProperty", "$1, Double.toString($2)")),
			new Patch("setPropertyInt", castThisAndCall(UserElement.class, "setOverrideProperty", "$1, Integer.toString($2)")),
			new Patch("setPropertyBoolean", castThisAndCall(UserElement.class, "setOverrideProperty", "$1, Boolean.toString($2)")),
			new Patch("setPropertyString", castThisAndCall(UserElement.class, "setOverrideProperty", "$1, $2")),
			new Patch("getPropertyDouble", "Double.parseDouble(" + castThisAndCall(UserElement.class, "getOverrideProperty", "$1") + ")"),
			new Patch("getPropertyInt", castThisAndCall(UserElement.class, "getOverridePropertyInt", "$1")),
			new Patch("getPropertyBoolean", "Boolean.parseBoolean(" + castThisAndCall(UserElement.class, "getOverrideProperty", "$1") + ")"),
			new Patch("getPropertyString", castThisAndCall(UserElement.class, "getOverrideProperty", "$1")), 
			new Patch("isOrHasChild", castThisAndCall(UserElement.class, "isOrHasChild", "$1")),
			new Patch("getFirstChildElement", staticCall(PatchElement.class, "getFirstChildElement", "this")),
			new Patch("getTagName", staticCall(PatchElement.class, "getTagName", "this")),
		});

		PatchUtils.applyPatches(DOMImpl.class, new Patch[] { 
			new Patch("getEventsSunk", "return 1;"),
			new Patch("eventGetTypeInt", staticCall(PatchDOMImpl.class, "eventGetTypeInt", "$1"), new Class[] { String.class}),	
			//new Patch("eventGetTypeInt", staticCall(PatchDOMImpl.class, "eventGetTypeInt", "$1"), new Class[] { Event.class}),
			//			new Patch("eventGetShiftKey", castAndCall(OverrideEvent.class, "isOverrideShiftKey")),
			//			new Patch("eventGetMetaKey", castAndCall(OverrideEvent.class, "isOverrideMetaKey")),
			//			new Patch("eventGetCtrlKey", castAndCall(OverrideEvent.class, "isOverrideCtrlKey")),
			//			new Patch("eventGetAltKey", castAndCall(OverrideEvent.class, "isOverrideAltKey")),
			//			new Patch("eventGetKeyCode", castAndCall(OverrideEvent.class, "getOverrideKeyCode")),
			new Patch("setEventListener", "return;"), 
		});

		PatchUtils.applyPatches(JavaScriptObject.class, new Patch[] { 
			new Patch("cast", staticCall(PatchUIObject.class, "cast", "this")), 
			//			new Patch("hashCode", "super.hashCode()"),
			//			new Patch("createFunction", "null"), 
		});

		PatchUtils.applyPatches(Event.class, new Patch[] { 
			new Patch("getTarget", staticCall(OverrideEvent.class, "overrideCast", "this") + ".getOverrideTargetElement()"), 
		});

		PatchUtils.applyPatches(Node.class, new Patch[] { 
			new Patch("appendChild", staticCall(PatchNode.class, "appendChild", "this, $1")),
			new Patch("cloneNode", castThisAndCall(UserElement.class, "overrideClone", "$1")),
			new Patch("removeChild", staticCall(PatchNode.class, "removeChild", "this, $1")),
			new Patch("getFirstChild", staticCall(PatchNode.class, "getFirstChild", "this")),
			new Patch("is", staticCall(PatchNode.class, "is", "$1")),
		});

		PatchUtils.applyPatches(Style.class, new Patch[] { 
			new Patch("getProperty", castThisAndCall(OverrideStyle.class, "getOverrideProperty", "$1")),
			new Patch("setProperty", castThisAndCall(OverrideStyle.class, "setOverrideProperty", "$1, $2")), 
			new Patch("setPropertyPx", castThisAndCall(OverrideStyle.class, "setOverridePropertyPx", "$1, $2")), 
		});

		PatchUtils.applyPatches(DOM.class, new Patch[] { 
			new Patch("createUniqueId", staticCall(PatchDOM.class, "createUniqueId", "")),
			//new Patch("getFirstChild", staticCall("getFirstChild", "$1")) 
		});

		PatchUtils.applyPatches(HistoryImpl.class, new Patch[] { 
			new Patch("getToken", OverrideHistory.class.getCanonicalName() + ".getToken()"),
			new Patch("setToken", OverrideHistory.class.getCanonicalName() + ".setToken($1)"),
			new Patch("init", "return true;"),
			new Patch("nativeUpdate", "return;"),
		});

		PatchUtils.applyPatches(Timer.class, new Patch[] {
			new Patch("cancel", ""),
			new Patch("schedule", staticCall(PatchTimer.class, "schedule", "this, $1")),
			new Patch("scheduleRepeating", staticCall(PatchTimer.class, "scheduleRepeating", "this, $1")),
		});

		PatchUtils.applyPatches(DeferredCommand.class, 
				new Patch[] { new Patch("addCommand", staticCall(PatchDeferredCommand.class, "immediateCommand", "$1")).setArgClasses(new Class[] { Command.class })
		});

		PatchUtils.applyPatches(RootPanel.class, new Patch[] {
			new Patch("getBodyElement", staticCall(PatchUIObject.class, "cast", staticCall(PatchDOMImpl.class, "createElement", "\"body\""))),
			//new Patch("get", "if (" + staticCall("isReinit", "") + ") { rootPanels.clear(); widgetsToDetach.clear(); } return get(null);")
		});

		PatchUtils.applyPatches(History.class,
				new Patch[] { new Patch("back", OverrideHistory.class.getCanonicalName() + ".back()"),
		});

		PatchUtils.applyPatches(ListBox.class, new Patch[] { 
			new Patch("getSelectElement", staticCall(PatchListBox.class, "getSelectElement", "this")),
			new Patch("clear", staticCall(PatchListBox.class, "clearListBox", "this"))
		});

		PatchUtils.applyPatches(OptionElement.class, new Patch[] { 
			new Patch("setText", castThisAndCall(OverrideOptionElement.class, "setOverrideText", "$1")),
			new Patch("getText", castThisAndCall(OverrideOptionElement.class, "getOverrideText")),
			new Patch("setValue", castThisAndCall(OverrideOptionElement.class, "setOverrideValue", "$1")),
			new Patch("getValue", castThisAndCall(OverrideOptionElement.class, "getOverrideValue")), 
		});

		PatchUtils.applyPatches(SelectElement.class, new Patch[] {
			new Patch("getSelectedIndex", castThisAndCall(OverrideSelectElement.class, "getOverrideSelectedIndex")),
			new Patch("setSelectedIndex", castThisAndCall(OverrideSelectElement.class, "setOverrideSelectedIndex", "$1")), 
			new Patch("getSize", castThisAndCall(OverrideSelectElement.class, "getOverrideSize")), 
			new Patch("setSize", castThisAndCall(OverrideSelectElement.class, "setOverrideSize", "$1")),
		});

		PatchUtils.applyPatches(NodeList.class, new Patch[] { 
			new Patch("getLength", staticCall(PatchNodeList.class, "getLengthUserNodeList", "this")),
			new Patch("getItem", staticCall(PatchNodeList.class, "getItemUserNodeList", "this, $1")) 
		});

		PatchUtils.applyPatches(HTMLTable.class, new Patch[] { 
			new Patch("getDOMRowCount", staticCall(PatchHTMLTable.class, "getDOMRowCount", "this"), new Class[] {}),
			new Patch("getDOMCellCount", staticCall(PatchHTMLTable.class, "getDOMCellCount", "$1, $2"), 
					new Class[] { Element.class, Integer.TYPE }), 
		});

		PatchUtils.applyPatches(Class.forName(HTMLTable.class.getCanonicalName() + "$RowFormatter"), new Patch[] { 
			new Patch("getRow", staticCall(PatchHTMLTable.class, "getRowRowFormatter", "$1, $2")), 
		});

		PatchUtils.applyPatches(FlexTable.class, new Patch[] { 
			new Patch("addCells", staticCall(PatchFlexTable.class, "addCells", "$1, $2, $3")), 
		});

		PatchUtils.applyPatches(HTMLTable.CellFormatter.class, new Patch[] { 
			new Patch("getCellElement", staticCall(PatchHTMLTable.class, "getCellElement", "$1, $2, $3")), 
		});

		PatchUtils.applyPatches(ElementMapperImpl.class, new Patch[] { 
			new Patch("setIndex", staticCall(PatchElementMapperImpl.class, "setWidgetIndex", "$1, $2")),
			new Patch("getIndex", staticCall(PatchElementMapperImpl.class, "getWidgetIndex", "$1")),
			new Patch("clearIndex", staticCall(PatchElementMapperImpl.class, "clearWidgetIndex", "$1")) 
		});

		PatchUtils.applyPatches(Grid.class, new Patch[] { 
			new Patch("addRows", staticCall(PatchGrid.class, "addRows", "$1, $2, $3")) 
		});

		PatchUtils.applyPatches(InputElement.class, new Patch[] {
			new Patch("as", staticCall(PatchInputElement.class, "as", "$1")), 
			new Patch("setTabIndex", castThisAndCall(OverrideInputElement.class, "setOverrideTabIndex", "$1")),
			new Patch("isDefaultChecked", castThisAndCall(OverrideInputElement.class, "isOverrideDefaultChecked")),
			new Patch("setDefaultChecked", castThisAndCall(OverrideInputElement.class, "setOverrideDefaultChecked", "$1")),
			new Patch("setChecked", castThisAndCall(OverrideInputElement.class, "setOverrideChecked", "$1")),
			new Patch("isDisabled", castThisAndCall(OverrideInputElement.class, "isOverrideDisabled")),
			new Patch("setDisabled", castThisAndCall(OverrideInputElement.class, "setOverrideDisabled", "$1")),
			new Patch("setMaxLength", castThisAndCall(OverrideInputElement.class, "setOverrideMaxLength", "$1")),
			new Patch("getMaxLength", castThisAndCall(OverrideInputElement.class, "getOverrideMaxLength")),
			new Patch("setName", castThisAndCall(OverrideInputElement.class, "setOverrideName", "$1")),
			new Patch("getName", castThisAndCall(OverrideInputElement.class, "getOverrideName")),
			new Patch("setValue", castThisAndCall(OverrideInputElement.class, "setOverrideValue", "$1")),
			new Patch("getValue", castThisAndCall(OverrideInputElement.class, "getOverrideValue")),
			new Patch("setTabIndex", castThisAndCall(OverrideInputElement.class, "setOverrideTabIndex", "$1")),
			new Patch("getTabIndex", castThisAndCall(OverrideInputElement.class, "getOverrideTabIndex")),
			new Patch("setAccessKey", castThisAndCall(OverrideInputElement.class, "setOverrideAccessKey", "$1")),
			new Patch("getAccessKey", castThisAndCall(OverrideInputElement.class, "getOverrideAccessKey")),
		});

		PatchUtils.applyPatches(AnchorElement.class, new Patch[] {
			new Patch("as", staticCall(PatchAnchorElement.class, "as", "$1")), 
			new Patch("setTabIndex", castThisAndCall(OverrideAnchorElement.class, "setOverrideTabIndex", "$1")),
			new Patch("setHref", castThisAndCall(OverrideAnchorElement.class, "setOverrideHref", "$1")),
			new Patch("getHref", castThisAndCall(OverrideAnchorElement.class, "getOverrideHref")),
			new Patch("setAccessKey", castThisAndCall(OverrideAnchorElement.class, "setOverrideAccessKey", "$1")),
			new Patch("focus", ""),
		});

		PatchUtils.applyPatches(LabelElement.class, new Patch[] {
			new Patch("setHtmlFor", ""),
		});

		PatchUtils.applyPatches(Image.class, new Patch[] {
			new Patch("getImageElement", staticCall(PatchImage.class, "getImageElement", "this")),
		});
		
		PatchUtils.applyPatches(ImageElement.class, new Patch[] { 
			new Patch("setSrc", castThisAndCall(OverrideImageElement.class, "setOverrideSrc", "$1")),
			new Patch("getSrc", castThisAndCall(OverrideImageElement.class, "getOverrideSrc")), 
		});

		PatchUtils.applyPatches(Frame.class, new Patch[] {
			new Patch("getFrameElement", staticCall(PatchFrame.class, "getFrameElement", "this")),
		});

		PatchUtils.applyPatches(FrameElement.class, new Patch[] { 
			new Patch("setSrc", castThisAndCall(OverrideFrameElement.class, "setOverrideSrc", "$1")),
			new Patch("getSrc", castThisAndCall(OverrideFrameElement.class, "getOverrideSrc")), 
		});

		PatchUtils.applyPatches(CurrencyList.class, new Patch[] { 
			new Patch("getDefault", "new " + CurrencyData.class.getCanonicalName() + "()"), 
		});

		PatchUtils.applyPatches(CurrencyData.class, new Patch[] { 
			new Patch("getFlagsAndPrecision", "0"), 
			new Patch("getCurrencyCode", "\"USD\""), 
			new Patch("getCurrencySymbol", "\"$\"") 
		});

		PatchUtils.applyPatches(TextBox.class, new Patch[] { 
			new Patch("getInputElement", staticCall(PatchTextBox.class, "getInputElement", "this")),
		});

		PatchUtils.applyPatches(TextArea.class, new Patch[] { 
			new Patch("getTextAreaElement", staticCall(PatchTextArea.class, "getTextAreaElement", "this")),
		});

		PatchUtils.applyPatches(TextAreaElement.class, new Patch[] { 
			new Patch("setRows", castThisAndCall(OverrideTextAreaElement.class, "setOverrideRows", "$1")),
			new Patch("getRows", castThisAndCall(OverrideTextAreaElement.class, "getOverrideRows")),
		});

		PatchUtils.applyPatches(CheckBox.class, new Patch[] { 
			new Patch("getName", staticCall(PatchCheckBox.class, "getName", "this")),
		});

		PatchUtils.applyPatches(RadioButton.class, new Patch[] { 
			new Patch("setName", staticCall(PatchCheckBox.class, "setName", "this, $1")),
		});

		PatchUtils.applyPatches(URL.class, new Patch[] { 
			new Patch("encodeComponentImpl", staticCall(PatchURL.class, "urlEncode", "$1")) 
		});	
		
		PatchUtils.applyPatches(Duration.class, new Patch[] { 
			new Patch("currentTimeMillis", staticCall(PatchDuration.class, "getTimeInMillisec")) 
		});
	}

	private static String staticCall(Class<?> clazz, String methodName, String args) {
		return  clazz.getCanonicalName() + "." + methodName + "(" + args + ")";
	}

	private static String staticCall(Class<?> clazz, String methodName) {
		return  staticCall(clazz, methodName, "");
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

	public static void setInstanceCreator(InstanceCreator instanceCreator) {
		PatchUtils.INSTANCE_CREATOR = instanceCreator;
	}

}
