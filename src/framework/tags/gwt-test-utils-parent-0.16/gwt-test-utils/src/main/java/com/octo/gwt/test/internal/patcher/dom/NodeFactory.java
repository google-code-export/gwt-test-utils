package com.octo.gwt.test.internal.patcher.dom;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

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
import com.octo.gwt.test.internal.patcher.tools.PropertyContainer;
import com.octo.gwt.test.internal.patcher.tools.SubClassedHelper;

public class NodeFactory {

	public static final String BODY_ELEMENT = "Body";

	private static Map<String, String> subclassedMap = new HashMap<String, String>();
	private static Map<String, String> subclassedMapWithTag = new HashMap<String, String>();

	static {
		subclassedMap.put("a", AnchorElement.class.getCanonicalName());
		subclassedMap.put("area", AreaElement.class.getCanonicalName());
		subclassedMap.put("base", BaseElement.class.getCanonicalName());
		subclassedMap.put("body", BodyElement.class.getCanonicalName());
		subclassedMap.put("br", BRElement.class.getCanonicalName());
		subclassedMap.put("button", ButtonElement.class.getCanonicalName());
		subclassedMap.put("div", DivElement.class.getCanonicalName());
		subclassedMap.put("dl", DListElement.class.getCanonicalName());
		subclassedMap.put("fieldset", FieldSetElement.class.getCanonicalName());
		subclassedMap.put("form", FormElement.class.getCanonicalName());
		subclassedMap.put("frame", FrameElement.class.getCanonicalName());
		subclassedMap.put("frameset", FrameSetElement.class.getCanonicalName());
		subclassedMap.put("head", HeadElement.class.getCanonicalName());
		subclassedMap.put("hr", HRElement.class.getCanonicalName());
		subclassedMapWithTag.put("h1", HeadingElement.class.getCanonicalName());
		subclassedMapWithTag.put("h2", HeadingElement.class.getCanonicalName());
		subclassedMapWithTag.put("h3", HeadingElement.class.getCanonicalName());
		subclassedMapWithTag.put("h4", HeadingElement.class.getCanonicalName());
		subclassedMapWithTag.put("h5", HeadingElement.class.getCanonicalName());
		subclassedMapWithTag.put("h6", HeadingElement.class.getCanonicalName());
		subclassedMap.put("hr", HRElement.class.getCanonicalName());
		subclassedMap.put("iframe", IFrameElement.class.getCanonicalName());
		subclassedMap.put("img", ImageElement.class.getCanonicalName());
		subclassedMapWithTag.put("ins", ModElement.class.getCanonicalName());
		subclassedMapWithTag.put("del", ModElement.class.getCanonicalName());
		subclassedMap.put("input", InputElement.class.getCanonicalName());
		subclassedMap.put("label", LabelElement.class.getCanonicalName());
		subclassedMap.put("legend", LegendElement.class.getCanonicalName());
		subclassedMap.put("li", LIElement.class.getCanonicalName());
		subclassedMap.put("link", LinkElement.class.getCanonicalName());
		subclassedMap.put("map", MapElement.class.getCanonicalName());
		subclassedMap.put("meta", MetaElement.class.getCanonicalName());
		subclassedMap.put("object", ObjectElement.class.getCanonicalName());
		subclassedMap.put("ol", OListElement.class.getCanonicalName());
		subclassedMap.put("optgroup", OptGroupElement.class.getCanonicalName());
		subclassedMap.put("option", OptionElement.class.getCanonicalName());
		subclassedMap.put("options", OptionElement.class.getCanonicalName());
		subclassedMap.put("p", ParagraphElement.class.getCanonicalName());
		subclassedMap.put("param", ParamElement.class.getCanonicalName());
		subclassedMap.put("pre", PreElement.class.getCanonicalName());
		subclassedMapWithTag.put("q", QuoteElement.class.getCanonicalName());
		subclassedMapWithTag.put("blockquote", QuoteElement.class.getCanonicalName());
		subclassedMap.put("script", ScriptElement.class.getCanonicalName());
		subclassedMap.put("select", SelectElement.class.getCanonicalName());
		subclassedMap.put("span", SpanElement.class.getCanonicalName());
		subclassedMap.put("style", StyleElement.class.getCanonicalName());
		subclassedMap.put("caption", TableCaptionElement.class.getCanonicalName());
		subclassedMapWithTag.put("td", TableCellElement.class.getCanonicalName());
		subclassedMapWithTag.put("th", TableCellElement.class.getCanonicalName());
		subclassedMapWithTag.put("col", TableColElement.class.getCanonicalName());
		subclassedMapWithTag.put("colgroup", TableColElement.class.getCanonicalName());
		subclassedMap.put("table", TableElement.class.getCanonicalName());
		subclassedMap.put("tr", TableRowElement.class.getCanonicalName());
		subclassedMapWithTag.put("tbody", TableSectionElement.class.getCanonicalName());
		subclassedMapWithTag.put("tfoot", TableSectionElement.class.getCanonicalName());
		subclassedMapWithTag.put("thead", TableSectionElement.class.getCanonicalName());
		subclassedMap.put("textarea", TextAreaElement.class.getCanonicalName());
		subclassedMap.put("title", TitleElement.class.getCanonicalName());
		subclassedMap.put("ul", UListElement.class.getCanonicalName());
	}

	public static Document DOCUMENT;

	private NodeFactory() {
	}

	public static void reset() {
		if (DOCUMENT != null) {
			PropertyContainer bodyPc = SubClassedHelper.getSubClassedObjectOrNull(DOCUMENT.getBody()).getOverrideProperties();
			bodyPc.clear();

			PropertyContainer documentPc = SubClassedHelper.getSubClassedObjectOrNull(DOCUMENT).getOverrideProperties();
			documentPc.clear();
			DOCUMENT = null;
		}
	}

	public static Document getDocument() {
		if (DOCUMENT == null) {
			try {
				DOCUMENT = (Document) SubClassedHelper.getSubClass(Document.class.getCanonicalName()).newInstance();
				SubClassedHelper.setProperty(DOCUMENT, "DocumentElement", createHTMLElement());
			} catch (Exception e) {
				throw new RuntimeException("Unable to create Document", e);
			}
		}
		return DOCUMENT;
	}

	public static Style createStyle() {
		try {
			return (Style) SubClassedHelper.getSubClass(Style.class.getCanonicalName()).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Unable to create text " + e);
		}
	}

	public static Node createNode() {
		try {
			return (Node) SubClassedHelper.getSubClass(Node.class.getCanonicalName()).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Unable to create node " + e);
		}
	}

	public static Text createTextNode(String data) {
		try {
			Text text = (Text) SubClassedHelper.getSubClass(Text.class.getCanonicalName()).newInstance();
			text.setData(data);
			SubClassedHelper.setProperty(text, "NodeType", Node.TEXT_NODE);

			return text;
		} catch (Exception e) {
			throw new RuntimeException("Unable to create text " + e);
		}
	}

	private static Element createHTMLElement() {
		try {
			Element e = (Element) SubClassedHelper.getSubClass(Element.class.getCanonicalName()).newInstance();
			SubClassedHelper.setProperty(e, "NodeName", "HTML");
			SubClassedHelper.setProperty(e, "TagName", "HTML");
			SubClassedHelper.setProperty(e, "NodeType", Node.DOCUMENT_NODE);

			SubClassedHelper.setProperty(e, BODY_ELEMENT, createElement("body"));

			return e;
		} catch (Exception e) {
			throw new RuntimeException("Unable to create HTML element " + e);
		}
	}

	public static Element createElement(String tag) {
		try {
			Element elem = null;

			String subClassName = subclassedMap.get(tag);
			String subClassNameWithTag = subclassedMapWithTag.get(tag);

			if (subClassName != null) {
				elem = (Element) SubClassedHelper.getSubClass(subClassName).newInstance();
			} else if (subClassNameWithTag != null) {
				Constructor<?> constructor = SubClassedHelper.getSubClass(subClassNameWithTag).getConstructor(String.class);
				elem = (Element) constructor.newInstance(tag);
			}
			if (elem == null) {
				throw new RuntimeException("Cannot create element for tag <" + tag + ">");
			}

			// set the <body> element as default parent node
			if (!"body".equals(elem.getTagName())) {
				Document.get().getBody().appendChild(elem);
			}

			return elem;
		} catch (Exception e) {
			throw new RuntimeException("Cannot create element for tag <" + tag + ">", e);
		}

	}
}
