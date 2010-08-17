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
import com.octo.gwt.test.patcher.PropertyContainer;
import com.octo.gwt.test.patcher.PropertyContainerHelper;

public class NodeFactory {

	public static final String BODY_ELEMENT = "Body";

	private static Map<String, String> subclassedMap = new HashMap<String, String>();
	private static Map<String, String> subclassedMapWithTag = new HashMap<String, String>();

	static {
		subclassedMap.put("a", AnchorElement.class.getName());
		subclassedMap.put("area", AreaElement.class.getName());
		subclassedMap.put("base", BaseElement.class.getName());
		subclassedMap.put("body", BodyElement.class.getName());
		subclassedMap.put("br", BRElement.class.getName());
		subclassedMap.put("button", ButtonElement.class.getName());
		subclassedMap.put("div", DivElement.class.getName());
		subclassedMap.put("dl", DListElement.class.getName());
		subclassedMap.put("fieldset", FieldSetElement.class.getName());
		subclassedMap.put("form", FormElement.class.getName());
		subclassedMap.put("frame", FrameElement.class.getName());
		subclassedMap.put("frameset", FrameSetElement.class.getName());
		subclassedMap.put("head", HeadElement.class.getName());
		subclassedMap.put("hr", HRElement.class.getName());
		subclassedMapWithTag.put("h1", HeadingElement.class.getName());
		subclassedMapWithTag.put("h2", HeadingElement.class.getName());
		subclassedMapWithTag.put("h3", HeadingElement.class.getName());
		subclassedMapWithTag.put("h4", HeadingElement.class.getName());
		subclassedMapWithTag.put("h5", HeadingElement.class.getName());
		subclassedMapWithTag.put("h6", HeadingElement.class.getName());
		subclassedMap.put("hr", HRElement.class.getName());
		subclassedMap.put("iframe", IFrameElement.class.getName());
		subclassedMap.put("img", ImageElement.class.getName());
		subclassedMapWithTag.put("ins", ModElement.class.getName());
		subclassedMapWithTag.put("del", ModElement.class.getName());
		subclassedMap.put("input", InputElement.class.getName());
		subclassedMap.put("label", LabelElement.class.getName());
		subclassedMap.put("legend", LegendElement.class.getName());
		subclassedMap.put("li", LIElement.class.getName());
		subclassedMap.put("link", LinkElement.class.getName());
		subclassedMap.put("map", MapElement.class.getName());
		subclassedMap.put("meta", MetaElement.class.getName());
		subclassedMap.put("object", ObjectElement.class.getName());
		subclassedMap.put("ol", OListElement.class.getName());
		subclassedMap.put("optgroup", OptGroupElement.class.getName());
		subclassedMap.put("option", OptionElement.class.getName());
		subclassedMap.put("options", OptionElement.class.getName());
		subclassedMap.put("p", ParagraphElement.class.getName());
		subclassedMap.put("param", ParamElement.class.getName());
		subclassedMap.put("pre", PreElement.class.getName());
		subclassedMapWithTag.put("q", QuoteElement.class.getName());
		subclassedMapWithTag.put("blockquote", QuoteElement.class.getName());
		subclassedMap.put("script", ScriptElement.class.getName());
		subclassedMap.put("select", SelectElement.class.getName());
		subclassedMap.put("span", SpanElement.class.getName());
		subclassedMap.put("style", StyleElement.class.getName());
		subclassedMap.put("caption", TableCaptionElement.class.getName());
		subclassedMapWithTag.put("td", TableCellElement.class.getName());
		subclassedMapWithTag.put("th", TableCellElement.class.getName());
		subclassedMapWithTag.put("col", TableColElement.class.getName());
		subclassedMapWithTag.put("colgroup", TableColElement.class.getName());
		subclassedMap.put("table", TableElement.class.getName());
		subclassedMap.put("tr", TableRowElement.class.getName());
		subclassedMapWithTag.put("tbody", TableSectionElement.class.getName());
		subclassedMapWithTag.put("tfoot", TableSectionElement.class.getName());
		subclassedMapWithTag.put("thead", TableSectionElement.class.getName());
		subclassedMap.put("textarea", TextAreaElement.class.getName());
		subclassedMap.put("title", TitleElement.class.getName());
		subclassedMap.put("ul", UListElement.class.getName());
	}

	public static Document DOCUMENT;

	private NodeFactory() {
	}

	public static void reset() {
		if (DOCUMENT != null) {
			PropertyContainer bodyPc = PropertyContainerHelper.cast(DOCUMENT.getBody()).getProperties();
			bodyPc.clear();

			PropertyContainer documentPc = PropertyContainerHelper.cast(DOCUMENT).getProperties();
			documentPc.clear();
			DOCUMENT = null;
		}
	}

	public static Document getDocument() {
		if (DOCUMENT == null) {
			try {
				DOCUMENT = (Document) PropertyContainerHelper.getPropertyContainerAware(Document.class.getName()).newInstance();
				PropertyContainerHelper.setProperty(DOCUMENT, "DocumentElement", createHTMLElement());
			} catch (Exception e) {
				throw new RuntimeException("Unable to create Document", e);
			}
		}
		return DOCUMENT;
	}

	public static Style createStyle() {
		try {
			return (Style) PropertyContainerHelper.getPropertyContainerAware(Style.class.getName()).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Unable to create text " + e);
		}
	}

	public static Node createNode() {
		try {
			return (Node) PropertyContainerHelper.getPropertyContainerAware(Node.class.getName()).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Unable to create node " + e);
		}
	}

	public static Text createTextNode(String data) {
		try {
			Text text = (Text) PropertyContainerHelper.getPropertyContainerAware(Text.class.getName()).newInstance();
			text.setData(data);

			return text;
		} catch (Exception e) {
			throw new RuntimeException("Unable to create text " + e);
		}
	}

	private static Element createHTMLElement() {
		try {
			Element e = (Element) PropertyContainerHelper.getPropertyContainerAware(Element.class.getName()).newInstance();
			PropertyContainerHelper.setProperty(e, "NodeName", "HTML");
			PropertyContainerHelper.setProperty(e, "TagName", "HTML");

			PropertyContainerHelper.setProperty(e, BODY_ELEMENT, createElement("body"));

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
				elem = (Element) PropertyContainerHelper.getPropertyContainerAware(subClassName).newInstance();
			} else if (subClassNameWithTag != null) {
				Constructor<?> constructor = PropertyContainerHelper.getPropertyContainerAware(subClassNameWithTag).getConstructor(String.class);
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
