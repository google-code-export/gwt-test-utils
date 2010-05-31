package com.octo.gwt.test;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;
import javassist.Translator;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.dom.client.EventTarget;
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
import com.google.gwt.dom.client.NativeEvent;
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
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.impl.DOMImpl;
import com.google.gwt.user.client.impl.ElementMapperImpl;
import com.octo.gwt.test.internal.patcher.GWTPatcher;
import com.octo.gwt.test.internal.patcher.ImplPatcher;
import com.octo.gwt.test.internal.patcher.dom.DOMImplPatcher;
import com.octo.gwt.test.internal.patcher.dom.DOMImplUserPatcher;
import com.octo.gwt.test.internal.patcher.dom.DocumentPatcher;
import com.octo.gwt.test.internal.patcher.dom.ElementMapperImplPatcher;
import com.octo.gwt.test.internal.patcher.dom.ElementPatcher;
import com.octo.gwt.test.internal.patcher.dom.InputElementPatcher;
import com.octo.gwt.test.internal.patcher.dom.NodeListPatcher;
import com.octo.gwt.test.internal.patcher.dom.NodePatcher;
import com.octo.gwt.test.internal.patcher.dom.SelectElementPatcher;
import com.octo.gwt.test.internal.patcher.dom.StylePatcher;
import com.octo.gwt.test.internal.patcher.tools.AutomaticGetAndSetPatcher;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;

public class GwtTestTranslator implements Translator {

	private boolean hasBeenPatched = false;

	public void start(ClassPool cp) throws NotFoundException, CannotCompileException {
		if (hasBeenPatched) {
			return;
		}

		try {
			processPatches(cp);
			hasBeenPatched = true;
		} catch (Exception e) {
			if (e instanceof NotFoundException) {
				throw (NotFoundException) e;
			} else if (e instanceof CannotCompileException) {
				throw (CannotCompileException) e;
			} else if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}
			throw new RuntimeException(e);
		}
	}

	public static void processPatches(ClassPool cp) throws Exception {
		PatchGwt.addPatcher(GWT.class, new GWTPatcher());
		PatchGwt.addPatcher(Impl.class, new ImplPatcher());
		
		// all DOM stuff

        PatchGwt.addPatcher(Node.class, new NodePatcher());
        PatchGwt.addPatcher(Element.class, new ElementPatcher());
        PatchGwt.addPatcher(com.google.gwt.user.client.Element.class,  new AutomaticPatcher());
		PatchGwt.addPatcher(NativeEvent.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(Event.class,  new AutomaticPatcher());
		PatchGwt.addPatcher(EventTarget.class, new AutomaticPatcher());
		PatchGwt.addPatcher(AnchorElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(AreaElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(BaseElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(BodyElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(BRElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(ButtonElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(DivElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(DListElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(DOMImpl.class, new DOMImplUserPatcher());
		PatchGwt.addPatcher(ElementMapperImpl.class, new ElementMapperImplPatcher());
		PatchGwt.addPatcher(FieldSetElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(FrameElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(FrameSetElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(FormElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(HeadElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(HRElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(HeadingElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(IFrameElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(ImageElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(InputElement.class, new InputElementPatcher());
		PatchGwt.addPatcher(LIElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(LabelElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(LegendElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(LinkElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(NodeList.class, new NodeListPatcher());
		PatchGwt.addPatcher(MapElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(MetaElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(ModElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(ObjectElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(OptionElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(OListElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(OptGroupElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(ParagraphElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(ParamElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(PreElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(QuoteElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(ScriptElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(StyleElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(SelectElement.class, new SelectElementPatcher());
		PatchGwt.addPatcher(SpanElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(Style.class, new StylePatcher());
		PatchGwt.addPatcher(TableCaptionElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(TableCellElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(TableColElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(TableElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(TableRowElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(TableSectionElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(TextAreaElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(Text.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(TitleElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.addPatcher(UListElement.class, new AutomaticGetAndSetPatcher());
		
		
		PatchGwt.addPatcher(PatchConstants.CLIENT_DOM_IMPL_CLASS_NAME, new DOMImplPatcher());
	
		PatchGwt.addPatcher(Document.class, new DocumentPatcher());
		
		PatchGwt.patch();
	}

	public void onLoad(ClassPool cp, String className) throws NotFoundException, CannotCompileException {
		System.out.println("onLoad : " + className);
		if ("com.google.gwt.dom.client.Node".equals(className)) {
		    System.out.println("Gotcha");
		}
	}
}
