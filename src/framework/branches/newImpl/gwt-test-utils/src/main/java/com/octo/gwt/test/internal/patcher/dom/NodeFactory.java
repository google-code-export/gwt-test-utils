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
import com.octo.gwt.test.internal.patcher.tools.SubClassedHelper;

public class NodeFactory {

	private static Map<String, Class<?>> subclassedMap = new HashMap<String, Class<?>>();
	private static Map<String, Class<?>> subclassedMapWithTag = new HashMap<String, Class<?>>();

	static {

		subclassedMap.put("a", AnchorElement.class);
		subclassedMap.put("area", AreaElement.class);
		subclassedMap.put("base", BaseElement.class);
		subclassedMap.put("body", BodyElement.class);
		subclassedMap.put("br", BRElement.class);
		subclassedMap.put("button", ButtonElement.class);
		subclassedMap.put("div", DivElement.class);
		subclassedMap.put("dl", DListElement.class);
		subclassedMap.put("fieldset", FieldSetElement.class);
		subclassedMap.put("form", FormElement.class);
		subclassedMap.put("frame", FrameElement.class);
		subclassedMap.put("frameset", FrameSetElement.class);
		subclassedMap.put("head", HeadElement.class);
		subclassedMap.put("hr", HRElement.class);
		subclassedMapWithTag.put("h1", HeadingElement.class);
		subclassedMapWithTag.put("h2", HeadingElement.class);
		subclassedMapWithTag.put("h3", HeadingElement.class);
		subclassedMapWithTag.put("h4", HeadingElement.class);
		subclassedMapWithTag.put("h5", HeadingElement.class);
		subclassedMapWithTag.put("h6", HeadingElement.class);
		subclassedMap.put("hr", HRElement.class);
		subclassedMap.put("iframe", IFrameElement.class);
		subclassedMap.put("img", ImageElement.class);
		subclassedMapWithTag.put("ins", ModElement.class);
		subclassedMapWithTag.put("del", ModElement.class);
		subclassedMap.put("input", InputElement.class);
		subclassedMap.put("label", LabelElement.class);
		subclassedMap.put("legend", LegendElement.class);
		subclassedMap.put("li", LIElement.class);
		subclassedMap.put("link", LinkElement.class);
		subclassedMap.put("map", MapElement.class);
		subclassedMap.put("meta", MetaElement.class);
		subclassedMap.put("object", ObjectElement.class);
		subclassedMap.put("ol", OListElement.class);
		subclassedMap.put("optgroup", OptGroupElement.class);
		subclassedMap.put("option", OptionElement.class);
		subclassedMap.put("options", OptionElement.class);
		subclassedMap.put("p", ParagraphElement.class);
		subclassedMap.put("param", ParamElement.class);
		subclassedMap.put("pre", PreElement.class);
		subclassedMapWithTag.put("q", QuoteElement.class);
		subclassedMapWithTag.put("blockquote", QuoteElement.class);
		subclassedMap.put("script", ScriptElement.class);
		subclassedMap.put("select", SelectElement.class);
		subclassedMap.put("span", SpanElement.class);
		subclassedMap.put("style", StyleElement.class);
		subclassedMap.put("caption", TableCaptionElement.class);
		subclassedMapWithTag.put("td", TableCellElement.class);
		subclassedMapWithTag.put("th", TableCellElement.class);
		subclassedMapWithTag.put("col", TableColElement.class);
		subclassedMapWithTag.put("colgroup", TableColElement.class);
		subclassedMap.put("table", TableElement.class);
		subclassedMap.put("tr", TableRowElement.class);
		subclassedMapWithTag.put("tbody", TableSectionElement.class);
		subclassedMapWithTag.put("tfoot", TableSectionElement.class);
		subclassedMapWithTag.put("thead", TableSectionElement.class);
		subclassedMap.put("textarea", TextAreaElement.class);
		subclassedMap.put("title", TitleElement.class);
		subclassedMap.put("ul", UListElement.class);
	}

	public static Document DOCUMENT;

	private NodeFactory() {}

	public static void clearDom() {
		if (DOCUMENT != null) {
			SubClassedHelper.getSubClassedObjectOrNull(DOCUMENT).getOverrideProperties().clear();
			SubClassedHelper.setProperty(DOCUMENT, DocumentPatcher.BODY_PROPERTY, createElement("body"));		
		}
	}

	public static Document getDocument() {
		if (DOCUMENT == null) {
			try {
				DOCUMENT = (Document) SubClassedHelper.getSubClass(Document.class).newInstance();
			}
			catch(Exception e) {
				throw new RuntimeException("Unable to create Document", e);
			}
		}
		return DOCUMENT;
	}

	public static Text createText() {
		try {
			return (Text) SubClassedHelper.getSubClass(Text.class).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Unable to create text " + e);
		}
	}

	public static Style createStyle() {
		try {
			return (Style) SubClassedHelper.getSubClass(Style.class).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Unable to create text " + e);
		}
	}


	public static Node createNode() {
		try {
			return (Node) SubClassedHelper.getSubClass(Node.class).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Unable to create node " + e);
		}
	}

	public static Element createElement(String tag) {
		try {
			Element elem = null;

			Class<?> subClazz = subclassedMap.get(tag);
			Class<?> subClazzWithTag = subclassedMapWithTag.get(tag);

			if (subClazz != null) {
				elem = (Element) SubClassedHelper.getSubClass(subClazz).newInstance();
			}
			else if (subClazzWithTag != null) {
				Constructor<?> constructor = SubClassedHelper.getSubClass(subClazzWithTag).getConstructor(String.class);
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
		}
		catch(Exception e) {
			throw new RuntimeException("Cannot create element for tag <" + tag + ">", e);
		}

	}
}
