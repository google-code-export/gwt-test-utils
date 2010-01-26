package com.octo.gwt.test17;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.gwt.core.client.Duration;
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
import com.google.gwt.dom.client.FieldSetElement;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.dom.client.LegendElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.TableCaptionElement;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableColElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.TableSectionElement;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.impl.CurrencyData;
import com.google.gwt.i18n.client.impl.CurrencyList;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.impl.DOMImpl;
import com.google.gwt.user.client.impl.ElementMapperImpl;
import com.google.gwt.user.client.impl.HistoryImpl;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.google.gwt.user.client.ui.impl.FocusImplOld;
import com.octo.gwt.test17.internal.patcher.CheckBoxPatcher;
import com.octo.gwt.test17.internal.patcher.CurrencyDataPatcher;
import com.octo.gwt.test17.internal.patcher.CurrencyListPatcher;
import com.octo.gwt.test17.internal.patcher.DeferredCommandPatcher;
import com.octo.gwt.test17.internal.patcher.DocumentPatcher;
import com.octo.gwt.test17.internal.patcher.DurationPatcher;
import com.octo.gwt.test17.internal.patcher.EventPatcher;
import com.octo.gwt.test17.internal.patcher.FlexTablePatcher;
import com.octo.gwt.test17.internal.patcher.FocusImplOldPatcher;
import com.octo.gwt.test17.internal.patcher.FocusImplPatcher;
import com.octo.gwt.test17.internal.patcher.FramePatcher;
import com.octo.gwt.test17.internal.patcher.GWTPatcher;
import com.octo.gwt.test17.internal.patcher.GridPatcher;
import com.octo.gwt.test17.internal.patcher.HTMLTableCellFormatterPatcher;
import com.octo.gwt.test17.internal.patcher.HTMLTablePatcher;
import com.octo.gwt.test17.internal.patcher.HTMLTableRowFormatterPatcher;
import com.octo.gwt.test17.internal.patcher.HistoryImplPatcher;
import com.octo.gwt.test17.internal.patcher.HistoryPatcher;
import com.octo.gwt.test17.internal.patcher.ImagePatcher;
import com.octo.gwt.test17.internal.patcher.JavaScriptObjectPatcher;
import com.octo.gwt.test17.internal.patcher.ListBoxPatcher;
import com.octo.gwt.test17.internal.patcher.NumberFormatPatcher;
import com.octo.gwt.test17.internal.patcher.RootPanelPatch;
import com.octo.gwt.test17.internal.patcher.StackPanelPatcher;
import com.octo.gwt.test17.internal.patcher.TextAreaPatcher;
import com.octo.gwt.test17.internal.patcher.TextBoxPatcher;
import com.octo.gwt.test17.internal.patcher.TimerPatcher;
import com.octo.gwt.test17.internal.patcher.UIObjectPatcher;
import com.octo.gwt.test17.internal.patcher.URLPatcher;
import com.octo.gwt.test17.internal.patcher.dom.AnchorElementPatcher;
import com.octo.gwt.test17.internal.patcher.dom.DOMImplPatcher;
import com.octo.gwt.test17.internal.patcher.dom.DOMImplUserPatcher;
import com.octo.gwt.test17.internal.patcher.dom.DOMPatcher;
import com.octo.gwt.test17.internal.patcher.dom.ElementMapperImplPatcher;
import com.octo.gwt.test17.internal.patcher.dom.ElementPatcher;
import com.octo.gwt.test17.internal.patcher.dom.InputElementPatcher;
import com.octo.gwt.test17.internal.patcher.dom.NodeListPatcher;
import com.octo.gwt.test17.internal.patcher.dom.NodePatcher;
import com.octo.gwt.test17.internal.patcher.dom.PropertyHolder;
import com.octo.gwt.test17.internal.patcher.dom.StylePatcher;
import com.octo.gwt.test17.ng.AutomaticElementSubclasser;

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

	public static void patch(Class<?> clazz, IPatcher patcher) throws Exception {
		if (alreadyPatched.contains(clazz.getCanonicalName())) {
			return;
		}
		alreadyPatched.add(clazz.getCanonicalName());
		PatchUtils.patch(clazz, patcher);
	}

	public static void setGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		GWTPatcher.gwtCreateHandler = gwtCreateHandler;
	}

	public static boolean areAssertionEnabled() {
		boolean enabled = false;
		assert enabled = true;
		return enabled;
	}

	public static void reset() throws Exception {
		PropertyHolder.clear();
		LOCALE = null;
		HistoryImplPatcher.clear();
		CurrencyListPatcher.reset();
		PatchUtils.clearSequenceReplacement();
		GWTPatcher.createClass.clear();
		GWTPatcher.gwtCreateHandler = null;
		GWTPatcher.gwtLogHandler = null;

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

		PatchUtils.patch(CheckBox.class, new CheckBoxPatcher());
		PatchUtils.patch(CurrencyData.class, new CurrencyDataPatcher());
		PatchUtils.patch(CurrencyList.class, new CurrencyListPatcher());
		PatchUtils.patch(DeferredCommand.class, new DeferredCommandPatcher());
		PatchUtils.patch(Document.class, new DocumentPatcher());
		PatchUtils.patch(Duration.class, new DurationPatcher());
		PatchUtils.patch(Event.class, new EventPatcher());
		PatchUtils.patch(FlexTable.class, new FlexTablePatcher());
		PatchUtils.patch(FocusImpl.class, new FocusImplPatcher());
		PatchUtils.patch(FocusImplOld.class, new FocusImplOldPatcher());
		PatchUtils.patch(Frame.class, new FramePatcher());
		PatchUtils.patch(Grid.class, new GridPatcher());
		PatchUtils.patch(History.class, new HistoryPatcher());
		PatchUtils.patch(HistoryImpl.class, new HistoryImplPatcher());
		PatchUtils.patch(HTMLTable.class, new HTMLTablePatcher());
		PatchUtils.patch(HTMLTable.CellFormatter.class, new HTMLTableCellFormatterPatcher());
		PatchUtils.patch(HTMLTable.RowFormatter.class, new HTMLTableRowFormatterPatcher());
		PatchUtils.patch(Image.class, new ImagePatcher());
		PatchUtils.patch(ListBox.class, new ListBoxPatcher());
		PatchUtils.patch(NumberFormat.class, new NumberFormatPatcher());
		PatchUtils.patch(RootPanel.class, new RootPanelPatch());
		PatchUtils.patch(StackPanel.class, new StackPanelPatcher());
		PatchUtils.patch(TextArea.class, new TextAreaPatcher());
		PatchUtils.patch(TextBox.class, new TextBoxPatcher());
		PatchUtils.patch(Timer.class, new TimerPatcher());
		PatchUtils.patch(UIObject.class, new UIObjectPatcher());
		PatchUtils.patch(URL.class, new URLPatcher());

		PatchUtils.patch(AnchorElement.class, new AnchorElementPatcher());
		PatchUtils.patch(AreaElement.class, null);
		PatchUtils.patch(BaseElement.class, null);
		PatchUtils.patch(BodyElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(BRElement.class, null);
		PatchUtils.patch(ButtonElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(DivElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(DListElement.class, null);
		PatchUtils.patch(Class.forName(PatchConstants.CLIENT_DOM_IMPL_CLASS_NAME), new DOMImplPatcher());
		PatchUtils.patch(DOMImpl.class, new DOMImplUserPatcher());
		PatchUtils.patch(DOM.class, new DOMPatcher());
		PatchUtils.patch(Element.class, new ElementPatcher());
		PatchUtils.patch(ElementMapperImpl.class, new ElementMapperImplPatcher());
		PatchUtils.patch(FieldSetElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(FormElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(IFrameElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(ImageElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(InputElement.class, new InputElementPatcher());
		PatchUtils.patch(LabelElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(LegendElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(NodeList.class, new NodeListPatcher());
		PatchUtils.patch(Node.class, new NodePatcher());
		PatchUtils.patch(OptionElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(SelectElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(SpanElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(Style.class, new StylePatcher());
		PatchUtils.patch(TableCaptionElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(TableCellElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(TableColElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(TableElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(TableRowElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(TableSectionElement.class, new AutomaticElementSubclasser());
		PatchUtils.patch(TextAreaElement.class, new AutomaticElementSubclasser());

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
