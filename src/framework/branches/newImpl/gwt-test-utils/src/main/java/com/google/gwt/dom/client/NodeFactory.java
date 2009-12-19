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

public class NodeFactory {

	private static Map<String, Class<? extends Element>> elementMap = new HashMap<String, Class<? extends Element>>();
	private static final Pattern H_PATTERN = Pattern.compile("^h[123456]$");

	static {

		elementMap.put("a", AnchorElement.class);
		elementMap.put("area", AreaElement.class);
		elementMap.put("base", BaseElement.class);
		elementMap.put(BodyElement.TAG, BodyElement.class);
		elementMap.put("br", BRElement.class);
		elementMap.put("button", ButtonElement.class);
		elementMap.put("div", DivElement.class);
		elementMap.put("dl", DListElement.class);
		elementMap.put("fieldset", FieldSetElement.class);
		elementMap.put("form", FormElement.class);
		elementMap.put("frame", FrameElement.class);
		elementMap.put("frameset", FrameSetElement.class);
		elementMap.put("head", HeadElement.class);
		elementMap.put("heading", HeadingElement.class);
		elementMap.put("hr", HRElement.class);
		elementMap.put("iframe", IFrameElement.class);
		elementMap.put("img", ImageElement.class);
		elementMap.put("input", InputElement.class);
		elementMap.put("label", LabelElement.class);
		elementMap.put("legend", LegendElement.class);
		elementMap.put("li", LIElement.class);
		elementMap.put("link", LinkElement.class);
		elementMap.put("map", MapElement.class);
		elementMap.put("meta", MetaElement.class);
		elementMap.put("object", ObjectElement.class);
		elementMap.put("ol", OListElement.class);
		elementMap.put("optgroup", OptGroupElement.class);
		elementMap.put("option", OptionElement.class);
		elementMap.put("options", OptionElement.class);
		elementMap.put("p", ParagraphElement.class);
		elementMap.put("param", ParamElement.class);
		elementMap.put("pre", PreElement.class);
		elementMap.put("q", QuoteElement.class);
		elementMap.put("script", ScriptElement.class);
		elementMap.put("select", SelectElement.class);
		elementMap.put("span", SpanElement.class);
		elementMap.put("style", StyleElement.class);
		elementMap.put("caption", TableCaptionElement.class);
		elementMap.put("td", TableCellElement.class);
		elementMap.put("th", TableCellElement.class);
		elementMap.put("col", TableColElement.class);
		elementMap.put("colgroup", TableColElement.class);
		elementMap.put("table", TableElement.class);
		elementMap.put("tr", TableRowElement.class);
		elementMap.put("tbody", TableSectionElement.class);
		elementMap.put("tfoot", TableSectionElement.class);
		elementMap.put("thead", TableSectionElement.class);
		elementMap.put("textarea", TextAreaElement.class);
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

	public static Element createElement(String tag) throws Exception {
		Element elem;

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
		} else {
			Class<? extends Element> clazz = elementMap.get(tag);

			if (clazz == null)
				throw new Exception("Cannot create element for tag <" + tag + ">");

			elem = clazz.newInstance();
		}

		// set the <body> element as default parent node
		if (!BodyElement.TAG.equals(elem.getTagName())) {
			Document.get().getBody().appendChild(elem);
		}

		return elem;

	}
}
