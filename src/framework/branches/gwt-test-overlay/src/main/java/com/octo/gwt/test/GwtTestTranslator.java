package com.octo.gwt.test;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;
import javassist.Translator;

import com.google.gwt.core.client.GWT;
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

	public void processPatches(ClassPool cp) throws Exception {
		PatchGwt.setClassPool(cp);
		PatchGwt.patch(GWT.class, new GWTPatcher());
		
		// all DOM stuff
		
		PatchGwt.patch(NativeEvent.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(Event.class,  new AutomaticPatcher());
		PatchGwt.patch(EventTarget.class, new AutomaticPatcher());
		PatchGwt.patch(AnchorElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(AreaElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(BaseElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(BodyElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(BRElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(ButtonElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(DivElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(DListElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(DOMImpl.class, new DOMImplUserPatcher());
		PatchGwt.patch(Element.class, new ElementPatcher());
		PatchGwt.patch(ElementMapperImpl.class, new ElementMapperImplPatcher());
		PatchGwt.patch(FieldSetElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(FrameElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(FrameSetElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(FormElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(HeadElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(HRElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(HeadingElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(IFrameElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(ImageElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(InputElement.class, new InputElementPatcher());
		PatchGwt.patch(LIElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(LabelElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(LegendElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(LinkElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(NodeList.class, new NodeListPatcher());
		PatchGwt.patch(Node.class, new NodePatcher());
		PatchGwt.patch(MapElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(MetaElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(ModElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(ObjectElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(OptionElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(OListElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(OptGroupElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(ParagraphElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(ParamElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(PreElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(QuoteElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(ScriptElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(StyleElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(SelectElement.class, new SelectElementPatcher());
		PatchGwt.patch(SpanElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(Style.class, new StylePatcher());
		PatchGwt.patch(TableCaptionElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(TableCellElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(TableColElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(TableElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(TableRowElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(TableSectionElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(TextAreaElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(Text.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(TitleElement.class, new AutomaticGetAndSetPatcher());
		PatchGwt.patch(UListElement.class, new AutomaticGetAndSetPatcher());
		
		
		PatchGwt.patch(PatchConstants.CLIENT_DOM_IMPL_CLASS_NAME, new DOMImplPatcher());
	
		PatchGwt.patch(Document.class, new DocumentPatcher());
	}

	public void onLoad(ClassPool cp, String className) throws NotFoundException, CannotCompileException {
		System.out.println("onLoad : " + className);
	}
}
