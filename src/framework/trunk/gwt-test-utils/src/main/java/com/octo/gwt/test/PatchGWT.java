package com.octo.gwt.test;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.impl.Impl;
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
import com.google.gwt.dom.client.FrameElement;
import com.google.gwt.dom.client.FrameSetElement;
import com.google.gwt.dom.client.HRElement;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.dom.client.LegendElement;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.dom.client.MapElement;
import com.google.gwt.dom.client.MetaElement;
import com.google.gwt.dom.client.ModElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OListElement;
import com.google.gwt.dom.client.ObjectElement;
import com.google.gwt.dom.client.OptGroupElement;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.dom.client.ParamElement;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.dom.client.QuoteElement;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.dom.client.TableCaptionElement;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableColElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.TableSectionElement;
import com.google.gwt.dom.client.Text;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.dom.client.TitleElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.CurrencyList;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.impl.CurrencyDataImpl;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window.Location;
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
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.google.gwt.user.client.ui.impl.FocusImplStandard;
import com.google.gwt.user.client.ui.impl.TextBoxImpl;
import com.octo.gwt.test.internal.patcher.CheckBoxPatcher;
import com.octo.gwt.test.internal.patcher.CookiesPatcher;
import com.octo.gwt.test.internal.patcher.CurrencyDataImplPatcher;
import com.octo.gwt.test.internal.patcher.CurrencyListPatcher;
import com.octo.gwt.test.internal.patcher.DeferredCommandPatcher;
import com.octo.gwt.test.internal.patcher.DurationPatcher;
import com.octo.gwt.test.internal.patcher.EventPatcher;
import com.octo.gwt.test.internal.patcher.FlexTablePatcher;
import com.octo.gwt.test.internal.patcher.FocusImplPatcher;
import com.octo.gwt.test.internal.patcher.FocusImplStandardPatcher;
import com.octo.gwt.test.internal.patcher.FramePatcher;
import com.octo.gwt.test.internal.patcher.GridPatcher;
import com.octo.gwt.test.internal.patcher.GwtPatcher;
import com.octo.gwt.test.internal.patcher.HTMLTableCellFormatterPatcher;
import com.octo.gwt.test.internal.patcher.HTMLTablePatcher;
import com.octo.gwt.test.internal.patcher.HTMLTableRowFormatterPatcher;
import com.octo.gwt.test.internal.patcher.HistoryImplPatcher;
import com.octo.gwt.test.internal.patcher.HistoryPatcher;
import com.octo.gwt.test.internal.patcher.ImagePatcher;
import com.octo.gwt.test.internal.patcher.ImplPatcher;
import com.octo.gwt.test.internal.patcher.JavaScriptObjectPatcher;
import com.octo.gwt.test.internal.patcher.ListBoxPatcher;
import com.octo.gwt.test.internal.patcher.LocationPatcher;
import com.octo.gwt.test.internal.patcher.NumberFormatPatcher;
import com.octo.gwt.test.internal.patcher.PopupPanelPatcher;
import com.octo.gwt.test.internal.patcher.RootPanelPatcher;
import com.octo.gwt.test.internal.patcher.StackPanelPatcher;
import com.octo.gwt.test.internal.patcher.StyleInjectorPatcher;
import com.octo.gwt.test.internal.patcher.TextAreaPatcher;
import com.octo.gwt.test.internal.patcher.TextBoxImplPatcher;
import com.octo.gwt.test.internal.patcher.TextBoxPatcher;
import com.octo.gwt.test.internal.patcher.TimerPatcher;
import com.octo.gwt.test.internal.patcher.UIObjectPatcher;
import com.octo.gwt.test.internal.patcher.URLPatcher;
import com.octo.gwt.test.internal.patcher.dom.DOMImplPatcher;
import com.octo.gwt.test.internal.patcher.dom.DOMImplUserPatcher;
import com.octo.gwt.test.internal.patcher.dom.DOMPatcher;
import com.octo.gwt.test.internal.patcher.dom.DocumentPatcher;
import com.octo.gwt.test.internal.patcher.dom.ElementMapperImplPatcher;
import com.octo.gwt.test.internal.patcher.dom.ElementPatcher;
import com.octo.gwt.test.internal.patcher.dom.InputElementPatcher;
import com.octo.gwt.test.internal.patcher.dom.NodeListPatcher;
import com.octo.gwt.test.internal.patcher.dom.NodePatcher;
import com.octo.gwt.test.internal.patcher.dom.SelectElementPatcher;
import com.octo.gwt.test.internal.patcher.dom.StylePatcher;
import com.octo.gwt.test.internal.patcher.tools.AutomaticElementSubclasser;
import com.octo.gwt.test.internal.patcher.tools.AutomaticSubclasser;
import com.octo.gwt.test.internal.patcher.tools.AutomaticTagSubclasser;
import com.octo.gwt.test.utils.PatchGwtUtils;
import com.octo.gwt.test.utils.PatchGwtWithJavaAgent;

public class PatchGWT {

	/**
	 * Indicate if gwt has been patch
	 */
	private static boolean hasBeenPatched = false;
	
	/**
	 * List of already patched custom classes
	 */
	private static List<String> alreadyPatched = new ArrayList<String>();

	public static void patch(Class<?> clazz, IPatcher patcher) throws Exception {
		if (alreadyPatched.contains(clazz.getCanonicalName())) {
			return;
		}
		alreadyPatched.add(clazz.getCanonicalName());
		PatchGwtWithJavaAgent.patch(clazz, patcher);
	}

	public static void init() throws Exception {
		if (hasBeenPatched) {
			return;
		}

		PatchGwtWithJavaAgent.init();
		
		PatchGwtUtils.initLoadPropertiesMethod();

		PatchGwtWithJavaAgent.patch(GWT.class, new GwtPatcher());
		PatchGwtWithJavaAgent.patch(JavaScriptObject.class, new JavaScriptObjectPatcher());

		PatchGwtWithJavaAgent.patch(CheckBox.class, new CheckBoxPatcher());
		PatchGwtWithJavaAgent.patch(Cookies.class, new CookiesPatcher());
		PatchGwtWithJavaAgent.patch(CurrencyDataImpl.class, new CurrencyDataImplPatcher());
		PatchGwtWithJavaAgent.patch(CurrencyList.class, new CurrencyListPatcher());
		PatchGwtWithJavaAgent.patch(DeferredCommand.class, new DeferredCommandPatcher());
		PatchGwtWithJavaAgent.patch(Document.class, new DocumentPatcher());
		PatchGwtWithJavaAgent.patch(Duration.class, new DurationPatcher());
		PatchGwtWithJavaAgent.patch(Event.class, new EventPatcher());
		PatchGwtWithJavaAgent.patch(FlexTable.class, new FlexTablePatcher());
		PatchGwtWithJavaAgent.patch(FocusImpl.class, new FocusImplPatcher());
		PatchGwtWithJavaAgent.patch(FocusImplStandard.class, new FocusImplStandardPatcher());
		PatchGwtWithJavaAgent.patch(Frame.class, new FramePatcher());
		PatchGwtWithJavaAgent.patch(Grid.class, new GridPatcher());
		PatchGwtWithJavaAgent.patch(History.class, new HistoryPatcher());
		PatchGwtWithJavaAgent.patch(HistoryImpl.class, new HistoryImplPatcher());
		PatchGwtWithJavaAgent.patch(HTMLTable.class, new HTMLTablePatcher());
		PatchGwtWithJavaAgent.patch(HTMLTable.CellFormatter.class, new HTMLTableCellFormatterPatcher());
		PatchGwtWithJavaAgent.patch(HTMLTable.RowFormatter.class, new HTMLTableRowFormatterPatcher());
		PatchGwtWithJavaAgent.patch(Image.class, new ImagePatcher());
		PatchGwtWithJavaAgent.patch(Impl.class, new ImplPatcher());
		PatchGwtWithJavaAgent.patch(ListBox.class, new ListBoxPatcher());
		PatchGwtWithJavaAgent.patch(Location.class, new LocationPatcher());
		PatchGwtWithJavaAgent.patch(NumberFormat.class, new NumberFormatPatcher());
		PatchGwtWithJavaAgent.patch(PopupPanel.class, new PopupPanelPatcher());
		PatchGwtWithJavaAgent.patch(RootPanel.class, new RootPanelPatcher());
		PatchGwtWithJavaAgent.patch(StackPanel.class, new StackPanelPatcher());
		PatchGwtWithJavaAgent.patch(StyleInjector.class, new StyleInjectorPatcher());
		PatchGwtWithJavaAgent.patch(TextArea.class, new TextAreaPatcher());
		PatchGwtWithJavaAgent.patch(TextBox.class, new TextBoxPatcher());
		PatchGwtWithJavaAgent.patch(TextBoxImpl.class, new TextBoxImplPatcher());
		PatchGwtWithJavaAgent.patch(Timer.class, new TimerPatcher());
		PatchGwtWithJavaAgent.patch(UIObject.class, new UIObjectPatcher());
		PatchGwtWithJavaAgent.patch(URL.class, new URLPatcher());

		PatchGwtWithJavaAgent.patch(AnchorElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(AreaElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(BaseElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(BodyElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(BRElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(ButtonElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(DivElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(DListElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(Class.forName("com.google.gwt.dom.client.DOMImpl"), new DOMImplPatcher());
		PatchGwtWithJavaAgent.patch(DOMImpl.class, new DOMImplUserPatcher());
		PatchGwtWithJavaAgent.patch(DOM.class, new DOMPatcher());
		PatchGwtWithJavaAgent.patch(Element.class, new ElementPatcher());
		PatchGwtWithJavaAgent.patch(ElementMapperImpl.class, new ElementMapperImplPatcher());
		PatchGwtWithJavaAgent.patch(FieldSetElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(FrameElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(FrameSetElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(FormElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(HeadElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(HRElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(HeadingElement.class, new AutomaticTagSubclasser());
		PatchGwtWithJavaAgent.patch(IFrameElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(ImageElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(InputElement.class, new InputElementPatcher());
		PatchGwtWithJavaAgent.patch(LIElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(LabelElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(LegendElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(LinkElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(NodeList.class, new NodeListPatcher());
		PatchGwtWithJavaAgent.patch(Node.class, new NodePatcher());
		PatchGwtWithJavaAgent.patch(MapElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(MetaElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(ModElement.class, new AutomaticTagSubclasser());
		PatchGwtWithJavaAgent.patch(ObjectElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(OptionElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(OListElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(OptGroupElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(ParagraphElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(ParamElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(PreElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(QuoteElement.class, new AutomaticTagSubclasser());
		PatchGwtWithJavaAgent.patch(ScriptElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(StyleElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(SelectElement.class, new SelectElementPatcher());
		PatchGwtWithJavaAgent.patch(SpanElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(Style.class, new StylePatcher());
		PatchGwtWithJavaAgent.patch(TableCaptionElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(TableCellElement.class, new AutomaticTagSubclasser());
		PatchGwtWithJavaAgent.patch(TableColElement.class, new AutomaticTagSubclasser());
		PatchGwtWithJavaAgent.patch(TableElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(TableRowElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(TableSectionElement.class, new AutomaticTagSubclasser());
		PatchGwtWithJavaAgent.patch(TextAreaElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(Text.class, new AutomaticSubclasser());
		PatchGwtWithJavaAgent.patch(TitleElement.class, new AutomaticElementSubclasser());
		PatchGwtWithJavaAgent.patch(UListElement.class, new AutomaticElementSubclasser());

		hasBeenPatched = true;
	}
	
}
