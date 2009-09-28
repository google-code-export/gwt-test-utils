package com.octo.gwt.test17;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.Style;
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
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.google.gwt.user.client.ui.impl.FocusImplOld;
import com.octo.gwt.test17.internal.PatchDOM;
import com.octo.gwt.test17.internal.PatchDOMImpl;
import com.octo.gwt.test17.internal.PatchDeferredCommand;
import com.octo.gwt.test17.internal.PatchDocument;
import com.octo.gwt.test17.internal.PatchElement;
import com.octo.gwt.test17.internal.PatchElementMapperImpl;
import com.octo.gwt.test17.internal.PatchFlexTable;
import com.octo.gwt.test17.internal.PatchGrid;
import com.octo.gwt.test17.internal.PatchHTMLTable;
import com.octo.gwt.test17.internal.PatchListBox;
import com.octo.gwt.test17.internal.PatchMainGWT;
import com.octo.gwt.test17.internal.PatchNode;
import com.octo.gwt.test17.internal.PatchNodeList;
import com.octo.gwt.test17.internal.PatchUIObject;
import com.octo.gwt.test17.internal.dom.UserElement;
import com.octo.gwt.test17.overrides.OverrideEvent;
import com.octo.gwt.test17.overrides.OverrideHistory;
import com.octo.gwt.test17.overrides.OverrideInputElement;
import com.octo.gwt.test17.overrides.OverrideOptionElement;
import com.octo.gwt.test17.overrides.OverrideSelectElement;
import com.octo.gwt.test17.overrides.OverrideStyle;

public class PatchGWT {

	/**
	 * Indicate if gwt has been patch
	 */
	private static boolean hasBeenPatched = false;

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
		RootPanel.get();
		OverrideHistory.reset();
		PatchMainGWT.createClass.clear();
		PatchMainGWT.gwtCreateHandler = null;

		ReflectionUtils.getStaticAndCallClear(Timer.class, "timers");
		ReflectionUtils.getStaticAndCallClear(RootPanel.class, "rootPanels");
		ReflectionUtils.getStaticAndCallClear(RootPanel.class, "widgetsToDetach");

		Object commandExecutor = ReflectionUtils.getStaticFieldValue(Class.forName("com.google.gwt.user.client.DeferredCommand"), "commandExecutor");
		ReflectionUtils.callClear(ReflectionUtils.getPrivateFieldValue(commandExecutor, "commands"));

		HistoryImpl historyImpl = ReflectionUtils.getStaticFieldValue(History.class, "impl");
		ReflectionUtils.callClear(ReflectionUtils.getPrivateFieldValue(ReflectionUtils.getPrivateFieldValue(ReflectionUtils.getPrivateFieldValue(historyImpl, "handlers"), "registry"), "map"));
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
		});

		PatchUtils.applyPatches(UIObject.class, new Patch[] { 
			new Patch("setElement", "setElement(" + staticCall(PatchUIObject.class, "cast", "$1") + ")").setFinal(),
			new Patch("setStylePrimaryName", staticCall(PatchUIObject.class, "setPropertyOnElement", "$1, \"stylePrimaryName\", $2")).setStatic(),
			new Patch("getStylePrimaryName", staticCall(PatchUIObject.class, "getPropertyOnElement", "$1, \"stylePrimaryName\"")).setStatic(),
			new Patch("setVisible", staticCall(PatchUIObject.class, "setPropertyOnElement", "$1, \"visible\", $2")).setNative(),
			new Patch("isVisible", staticCall(PatchUIObject.class, "getPropertyOnElementBoolean", "$1, \"visible\"")).setNative(),
			new Patch("getStyleName", staticCall(PatchUIObject.class, "getStyleName", "$1")).setStatic(),
			//						new Patch("getElement", staticCall("cast", "element")), new Patch("getAbsoluteLeft", "0"), new Patch("getAbsoluteTop", "0"),
			new Patch("extractLengthValue", "1.0"),  	
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
			//			new Patch("getTabIndex", castAndCall(UserElement.class, "getOverrideTabIndex")), 
			//			new Patch("focus", "") 
		});


		PatchUtils.applyPatches(Document.class, new Patch[] {
			new Patch("get", staticCall(PatchDocument.class, "get")),
			new Patch("getBody", staticCall(PatchDocument.class, "getBody")),
			//new Patch("createImageElement", staticCall(PatchDocument.class, "createImageElement"))
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
			//			new Patch("eventGetKeyCode", castAndCall(OverrideEvent.class, "getOverrideKeyCode")),
			//			new Patch("setEventListener", "return;"), 

		});

		PatchUtils.applyPatches(Button.class, new Patch[] {
			new Patch("adjustType", ""),
			//			new Patch("click", "onBrowserEvent(new " + OverrideEvent.class.getCanonicalName() + "(" + Event.class.getCanonicalName()
			//					+ ".ONCLICK));").setArgsLen(0) 
		});

		PatchUtils.applyPatches(com.google.gwt.dom.client.Element.class, new Patch[] { 
			//			new Patch("setId", castThisAndCall(UserElement.class, "setOverrideId", "$1")),
			//			new Patch("getId", castThisAndCall(UserElement.class, "getOverrideId")),
			new Patch("setAttribute", castThisAndCall(UserElement.class, "setOverrideAttribute", "$1, $2")),
			new Patch("getAttribute", castThisAndCall(UserElement.class, "getOverrideAttribute", "$1")),
			new Patch("setInnerHTML", castThisAndCall(UserElement.class, "setOverrideInnerHtml", "$1")),
			new Patch("getInnerHTML", castThisAndCall(UserElement.class, "getOverrideInnerHtml")),
			new Patch("setInnerText", castThisAndCall(UserElement.class, "setOverrideInnerText", "$1")),
			new Patch("getInnerText", castThisAndCall(UserElement.class, "getOverrideInnerText")),
			new Patch("getStyle", castThisAndCall(UserElement.class, "getOverrideStyle")),
			//			new Patch("setPropertyDouble", castThisAndCall(UserElement.class, "setOverrideProperty", "$1, Double.toString($2)")),
			new Patch("setPropertyInt", castThisAndCall(UserElement.class, "setOverrideProperty", "$1, Integer.toString($2)")),
			new Patch("setPropertyBoolean", castThisAndCall(UserElement.class, "setOverrideProperty", "$1, Boolean.toString($2)")),
			new Patch("setPropertyString", castThisAndCall(UserElement.class, "setOverrideProperty", "$1, $2")),
			//			new Patch("getPropertyDouble", "Double.parseDouble(" + castThisAndCall(UserElement.class, "getOverrideProperty", "$1") + ")"),
			new Patch("getPropertyInt", castThisAndCall(UserElement.class, "getOverridePropertyInt", "$1")),
			new Patch("getPropertyBoolean", "Boolean.parseBoolean(" + castThisAndCall(UserElement.class, "getOverrideProperty", "$1") + ")"),
			new Patch("getPropertyString", castThisAndCall(UserElement.class, "getOverrideProperty", "$1")), 
			new Patch("isOrHasChild", castThisAndCall(UserElement.class, "isOrHasChild", "$1")),
			new Patch("getFirstChildElement", staticCall(PatchElement.class, "getFirstChildElement", "this")),
			new Patch("getTagName", staticCall(PatchElement.class, "getTagName", "this")),
		});


		//		Patch patchArgEvent = 
		//			new Patch("eventGetTypeInt", staticCall(PatchDOMImpl.class, "eventGetTypeInt", "$1"));
		//		patchArgEvent.setArgClasses(new Class[] { Event.class});
		//


		PatchUtils.applyPatches(DOMImpl.class, new Patch[] { 
			//			new Patch("getEventsSunk", "return 1;"),
			//			patchArgEvent,
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
		});

		PatchUtils.applyPatches(Style.class, new Patch[] { 
			new Patch("getProperty", castThisAndCall(OverrideStyle.class, "getOverrideProperty", "$1")),
			new Patch("setProperty", castThisAndCall(OverrideStyle.class, "setOverrideProperty", "$1, $2")), 
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
			new Patch("createTimeout", "0"),
			new Patch("clearTimeout", "")
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

		PatchUtils.applyPatches(FlexTable.class, new Patch[] { 
			new Patch("addCells", staticCall(PatchFlexTable.class, "addCells", "$1, $2, $3")), 
		});

		PatchUtils.applyPatches(HTMLTable.CellFormatter.class, new Patch[] { 
			new Patch("getCellElement", staticCall(PatchHTMLTable.class, "getCellElement", "$1, $2, $3")), 
		});

		PatchUtils.applyPatches(ElementMapperImpl.class, new Patch[] { 
			new Patch("setIndex", staticCall(PatchElementMapperImpl.class, "setWidgetIndex", "$1, $2")),
			new Patch("getIndex", staticCall(PatchElementMapperImpl.class, "getWidgetIndex", "$1")),
			//new Patch("clearIndex", staticCall(PatchElementMapperImpl.class, "clearWidgetIndex", "$1")) 
		});

		PatchUtils.applyPatches(Grid.class, new Patch[] { 
			new Patch("addRows", staticCall(PatchGrid.class, "addRows", "$1, $2, $3")) 
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

}
