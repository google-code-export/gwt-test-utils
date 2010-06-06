package com.octo.gwt.test;

import java.util.HashMap;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.Translator;

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

public class GwtTranslator implements Translator {

	public static boolean debug = "true".equals(System.getProperty(GwtTranslator.class.getCanonicalName() + ".debug"));
	
	private void log(String log) {
		if (debug) {
			System.err.println(log);
		}
	}
	
	public void onLoad(ClassPool pool, String className) throws NotFoundException, CannotCompileException {
		try {
			IPatcher patcher = map.get(className);
			if (patcher != null) {
				log("Load class " + className + ", use patcher " + patcher.getClass().getCanonicalName());
				CtClass clazz = pool.get(className);
				log("Patch class " + className);
				PatchGwtUtils.patch(clazz, patcher);
				log("Class loaded & patched " + className);
			}
			else {
				log("Load class " + className + ", no patch");
			}
		} catch (Exception e) {
			throw new CannotCompileException(e);
		}
	}

	public void start(ClassPool pool) throws NotFoundException, CannotCompileException {
	}
	
	private Map<String, IPatcher> map = new HashMap<String, IPatcher>();
	
	public void addPatch(String className, IPatcher patcher) {
		map.put(className, patcher);
	}
	
	public GwtTranslator() {
		map.put(GWT.class.getCanonicalName(), new GwtPatcher());
		map.put(JavaScriptObject.class.getCanonicalName(), new JavaScriptObjectPatcher());

		map.put(CheckBox.class.getCanonicalName(), new CheckBoxPatcher());
		map.put(Cookies.class.getCanonicalName(), new CookiesPatcher());
		map.put(CurrencyDataImpl.class.getCanonicalName(), new CurrencyDataImplPatcher());
		map.put(CurrencyList.class.getCanonicalName(), new CurrencyListPatcher());
		map.put(DeferredCommand.class.getCanonicalName(), new DeferredCommandPatcher());
		map.put(Document.class.getCanonicalName(), new DocumentPatcher());
		map.put(Duration.class.getCanonicalName(), new DurationPatcher());
		map.put(Event.class.getCanonicalName(), new EventPatcher());
		map.put(FlexTable.class.getCanonicalName(), new FlexTablePatcher());
		map.put(FocusImpl.class.getCanonicalName(), new FocusImplPatcher());
		map.put(FocusImplStandard.class.getCanonicalName(), new FocusImplStandardPatcher());
		map.put(Frame.class.getCanonicalName(), new FramePatcher());
		map.put(Grid.class.getCanonicalName(), new GridPatcher());
		map.put(History.class.getCanonicalName(), new HistoryPatcher());
		map.put(HistoryImpl.class.getCanonicalName(), new HistoryImplPatcher());
		map.put(HTMLTable.class.getCanonicalName(), new HTMLTablePatcher());
		map.put(HTMLTable.class.getCanonicalName() + "$CellFormatter", new HTMLTableCellFormatterPatcher());
		map.put(HTMLTable.class.getCanonicalName() + "$RowFormatter", new HTMLTableRowFormatterPatcher());
		map.put(Image.class.getCanonicalName(), new ImagePatcher());
		map.put(Impl.class.getCanonicalName(), new ImplPatcher());
		map.put(ListBox.class.getCanonicalName(), new ListBoxPatcher());
		map.put(Window.class.getCanonicalName() + "$Location", new LocationPatcher());
		map.put(NumberFormat.class.getCanonicalName(), new NumberFormatPatcher());
		map.put(PopupPanel.class.getCanonicalName(), new PopupPanelPatcher());
		map.put(RootPanel.class.getCanonicalName(), new RootPanelPatcher());
		map.put(StackPanel.class.getCanonicalName(), new StackPanelPatcher());
		map.put(StyleInjector.class.getCanonicalName(), new StyleInjectorPatcher());
		map.put(TextArea.class.getCanonicalName(), new TextAreaPatcher());
		map.put(TextBox.class.getCanonicalName(), new TextBoxPatcher());
		map.put(TextBoxImpl.class.getCanonicalName(), new TextBoxImplPatcher());
		map.put(Timer.class.getCanonicalName(), new TimerPatcher());
		map.put(UIObject.class.getCanonicalName(), new UIObjectPatcher());
		map.put(URL.class.getCanonicalName(), new URLPatcher());

		map.put(AnchorElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(AreaElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(BaseElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(BodyElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(BRElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(ButtonElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(DivElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(DListElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put("com.google.gwt.dom.client.DOMImpl", new DOMImplPatcher());
		map.put(DOMImpl.class.getCanonicalName(), new DOMImplUserPatcher());
		map.put(DOM.class.getCanonicalName(), new DOMPatcher());
		map.put(Element.class.getCanonicalName(), new ElementPatcher());
		map.put(ElementMapperImpl.class.getCanonicalName(), new ElementMapperImplPatcher());
		map.put(FieldSetElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(FrameElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(FrameSetElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(FormElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(HeadElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(HRElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(HeadingElement.class.getCanonicalName(), new AutomaticTagSubclasser());
		map.put(IFrameElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(ImageElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(InputElement.class.getCanonicalName(), new InputElementPatcher());
		map.put(LIElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(LabelElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(LegendElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(LinkElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(NodeList.class.getCanonicalName(), new NodeListPatcher());
		map.put(Node.class.getCanonicalName(), new NodePatcher());
		map.put(MapElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(MetaElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(ModElement.class.getCanonicalName(), new AutomaticTagSubclasser());
		map.put(ObjectElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(OptionElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(OListElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(OptGroupElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(ParagraphElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(ParamElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(PreElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(QuoteElement.class.getCanonicalName(), new AutomaticTagSubclasser());
		map.put(ScriptElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(StyleElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(SelectElement.class.getCanonicalName(), new SelectElementPatcher());
		map.put(SpanElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(Style.class.getCanonicalName(), new StylePatcher());
		map.put(TableCaptionElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(TableCellElement.class.getCanonicalName(), new AutomaticTagSubclasser());
		map.put(TableColElement.class.getCanonicalName(), new AutomaticTagSubclasser());
		map.put(TableElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(TableRowElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(TableSectionElement.class.getCanonicalName(), new AutomaticTagSubclasser());
		map.put(TextAreaElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(Text.class.getCanonicalName(), new AutomaticSubclasser());
		map.put(TitleElement.class.getCanonicalName(), new AutomaticElementSubclasser());
		map.put(UListElement.class.getCanonicalName(), new AutomaticElementSubclasser());
	}

}
