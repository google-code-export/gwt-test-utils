package com.octo.gwt.test.internal.patcher.dom;

import java.util.HashMap;
import java.util.Map;

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
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.dom.client.TableCaptionElement;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableColElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.TableSectionElement;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.dom.client.TitleElement;
import com.google.gwt.dom.client.UListElement;
import com.octo.gwt.test.internal.patcher.tools.PropertyContainerAwareHelper;

public class JavaScriptObjectFactory {
	
	private static Document DOCUMENT;
	private static Map<String, Class<? extends Element>> map = new HashMap<String, Class<? extends Element>>();

	static {
		map.put("a", AnchorElement.class);
		map.put("area", AreaElement.class);
		map.put("base", BaseElement.class);
		map.put("body", BodyElement.class);
		map.put("br", BRElement.class);
		map.put("button", ButtonElement.class);
		map.put("div", DivElement.class);
		map.put("dl", DListElement.class);
		map.put("fieldset", FieldSetElement.class);
		map.put("form", FormElement.class);
		map.put("frame", FrameElement.class);
		map.put("frameset", FrameSetElement.class);
		map.put("head", HeadElement.class);
		map.put("hr", HRElement.class);
		map.put("h1", HeadingElement.class);
		map.put("h2", HeadingElement.class);
		map.put("h3", HeadingElement.class);
		map.put("h4", HeadingElement.class);
		map.put("h5", HeadingElement.class);
		map.put("h6", HeadingElement.class);
		map.put("hr", HRElement.class);
		map.put("iframe", IFrameElement.class);
		map.put("img", ImageElement.class);
		map.put("ins", ModElement.class);
		map.put("del", ModElement.class);
		map.put("input", InputElement.class);
		map.put("label", LabelElement.class);
		map.put("legend", LegendElement.class);
		map.put("li", LIElement.class);
		map.put("link", LinkElement.class);
		map.put("map", MapElement.class);
		map.put("meta", MetaElement.class);
		map.put("object", ObjectElement.class);
		map.put("ol", OListElement.class);
		map.put("optgroup", OptGroupElement.class);
		map.put("option", OptionElement.class);
		map.put("options", OptionElement.class);
		map.put("p", ParagraphElement.class);
		map.put("param", ParamElement.class);
		map.put("pre", PreElement.class);
		map.put("q", QuoteElement.class);
		map.put("blockquote", QuoteElement.class);
		map.put("script", ScriptElement.class);
		map.put("select", SelectElement.class);
		map.put("span", SpanElement.class);
		map.put("style", StyleElement.class);
		map.put("caption", TableCaptionElement.class);
		map.put("td", TableCellElement.class);
		map.put("th", TableCellElement.class);
		map.put("col", TableColElement.class);
		map.put("colgroup", TableColElement.class);
		map.put("table", TableElement.class);
		map.put("tr", TableRowElement.class);
		map.put("tbody", TableSectionElement.class);
		map.put("tfoot", TableSectionElement.class);
		map.put("thead", TableSectionElement.class);
		map.put("textarea", TextAreaElement.class);
		map.put("title", TitleElement.class);
		map.put("ul", UListElement.class);
	}

	private JavaScriptObjectFactory() {
	}

	public static void reset() {
		if (DOCUMENT != null) {
			PropertyContainerAwareHelper.getPropertyContainer(DOCUMENT).clear();
			PropertyContainerAwareHelper.setProperty(DOCUMENT, DocumentPatcher.BODY_PROPERTY, createElement("body"));
		}
	}

	public static Document getDocument() {
		if (DOCUMENT == null) {
			try {
				DOCUMENT = Document.class.newInstance();
			} catch (Exception e) {
				throw new RuntimeException("Unable to create Document", e);
			}
		}
		return DOCUMENT;
	}

	@SuppressWarnings("unchecked")
	public static <T extends JavaScriptObject> T createJavaScriptObject(Class<T> jsoClass) {
		try {
			Class<T> effectiveClass = (Class<T>) Thread.currentThread().getContextClassLoader().loadClass(jsoClass.getCanonicalName() + "$");
			return effectiveClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Unable to create instance of \'" + jsoClass.getSimpleName() + "\' :" + e);
		}
	}

	public static Element createElement(String tag) {
		if (tag == null) {
			throw new IllegalArgumentException("Cannot create element with null tag");
		}

		try {
			Element elem = null;

			Class<? extends Element> clazz = map.get(tag);

			if (clazz != null) {
				elem = createJavaScriptObject(clazz);
				PropertyContainerAwareHelper.setProperty(elem, "TagName", tag);
			} else {
				throw new IllegalArgumentException("Cannot create element for tag <" + tag + ">");
			}

			// set the <body> element as default parent node
			if (!"body".equals(tag)) {
				Document.get().getBody().appendChild(elem);
			}

			return elem;
		} catch (Exception e) {
			throw new RuntimeException("Cannot create element for tag <" + tag + ">", e);
		}

	}
}
