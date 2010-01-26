package com.google.gwt.dom.client;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.octo.gwt.test17.internal.overrides.OverrideHeadingElement;
import com.octo.gwt.test17.internal.overrides.OverrideModElement;
import com.octo.gwt.test17.internal.overrides.OverrideQuoteElement;
import com.octo.gwt.test17.internal.overrides.OverrideTableCellElement;
import com.octo.gwt.test17.internal.overrides.OverrideTableColElement;
import com.octo.gwt.test17.internal.overrides.OverrideTableSectionElement;
import com.octo.gwt.test17.internal.patcher.dom.PropertyHolder;
import com.octo.gwt.test17.ng.AutomaticSubclasser;

public class NodeFactory {

	private static Map<String, Class<? extends Element>> elementMap = new HashMap<String, Class<? extends Element>>();
	private static final Pattern H_PATTERN = Pattern.compile("^h[123456]$");
	
	private static Map<String, Class<? extends Element>> subclassedMap = new HashMap<String, Class<? extends Element>>();

	static {

		subclassedMap.put("a", AnchorElement.class);
		elementMap.put("area", AreaElement.class);
		elementMap.put("base", BaseElement.class);
		subclassedMap.put(BodyElement.TAG, BodyElement.class);
		elementMap.put("br", BRElement.class);
		subclassedMap.put("button", ButtonElement.class);
		subclassedMap.put("div", DivElement.class);
		elementMap.put("dl", DListElement.class);
		subclassedMap.put("fieldset", FieldSetElement.class);
		subclassedMap.put("form", FormElement.class);
		elementMap.put("frame", FrameElement.class);
		elementMap.put("frameset", FrameSetElement.class);
		elementMap.put("head", HeadElement.class);
		elementMap.put("heading", HeadingElement.class);
		elementMap.put("hr", HRElement.class);
		subclassedMap.put("iframe", IFrameElement.class);
		subclassedMap.put("img", ImageElement.class);
		subclassedMap.put("input", InputElement.class);
		subclassedMap.put("label", LabelElement.class);
		subclassedMap.put("legend", LegendElement.class);
		elementMap.put("li", LIElement.class);
		elementMap.put("link", LinkElement.class);
		elementMap.put("map", MapElement.class);
		elementMap.put("meta", MetaElement.class);
		elementMap.put("object", ObjectElement.class);
		elementMap.put("ol", OListElement.class);
		elementMap.put("optgroup", OptGroupElement.class);
		subclassedMap.put("option", OptionElement.class);
		subclassedMap.put("options", OptionElement.class);
		elementMap.put("p", ParagraphElement.class);
		elementMap.put("param", ParamElement.class);
		elementMap.put("pre", PreElement.class);
		elementMap.put("q", QuoteElement.class);
		elementMap.put("script", ScriptElement.class);
		subclassedMap.put("select", SelectElement.class);
		subclassedMap.put("span", SpanElement.class);
		elementMap.put("style", StyleElement.class);
		subclassedMap.put("caption", TableCaptionElement.class);
		subclassedMap.put("td", TableCellElement.class);
		subclassedMap.put("th", TableCellElement.class);
		subclassedMap.put("col", TableColElement.class);
		subclassedMap.put("colgroup", TableColElement.class);
		subclassedMap.put("table", TableElement.class);
		subclassedMap.put("tr", TableRowElement.class);
		subclassedMap.put("tbody", TableSectionElement.class);
		subclassedMap.put("tfoot", TableSectionElement.class);
		subclassedMap.put("thead", TableSectionElement.class);
		subclassedMap.put("textarea", TextAreaElement.class);
		elementMap.put("title", TitleElement.class);
		elementMap.put("ul", UListElement.class);
	}

	public static final Document DOCUMENT = new Document();

	private NodeFactory() {

	}

	public static Document getDocument() {
		if (!PropertyHolder.get(DOCUMENT).containsKey("Body")) {
			try {
				PropertyHolder.get(DOCUMENT).put("Body", createElement("body"));
			} catch (Exception e) {
				throw new RuntimeException("Error while creating Document <body> element", e);
			}
		}
		return DOCUMENT;

	}

	public static Text createText() {
		return new Text();
	}

	public static Node createNode() {
		return new Node();
	}

	public static Element createElement(String tag) {
		try {
			Element elem;
		
			
			Class<?> subClazz = subclassedMap.get(tag);
			
			if (H_PATTERN.matcher(tag).matches()) {
				elem = new OverrideHeadingElement(tag);
			} else if (ModElement.TAG_INS.equals(tag) || ModElement.TAG_DEL.equals(tag)) {
				elem = new OverrideModElement(tag);
			} else if (QuoteElement.TAG_BLOCKQUOTE.equals(tag) || QuoteElement.TAG_Q.equals(tag)) {
				elem = new OverrideQuoteElement(tag);
			} else if (TableCellElement.TAG_TD.equals(tag) || TableCellElement.TAG_TH.equals(tag)) {
				elem = new OverrideTableCellElement(tag);
			} else if (TableColElement.TAG_COL.equals(tag) || TableColElement.TAG_COLGROUP.equals(tag)) {
				elem = new OverrideTableColElement(tag);
			} else if (TableSectionElement.TAG_TBODY.equals(tag) || TableSectionElement.TAG_TFOOT.equals(tag)
					|| TableSectionElement.TAG_THEAD.equals(tag)) {
				elem = new OverrideTableSectionElement(tag);
			} else if (subClazz != null) {
				elem = (Element) AutomaticSubclasser.map.get(subClazz).newInstance();
			}
			else {
				Class<? extends Element> clazz = elementMap.get(tag);
	
				if (clazz == null)
					throw new RuntimeException("Cannot create element for tag <" + tag + ">");
	
				elem = clazz.newInstance();
			}
	
			// set the <body> element as default parent node
			if (!BodyElement.TAG.equals(elem.getTagName())) {
				Document.get().getBody().appendChild(elem);
			}
	
			return elem;
		}
		catch(Exception e) {
			throw new RuntimeException("Cannot create element for tag <" + tag + ">", e);
		}

	}
}
