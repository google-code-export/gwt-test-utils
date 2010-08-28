package com.octo.gwt.test.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Attribute;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;

public class GwtHtmlParser {

	private static Pattern STYLE_PATTERN = Pattern.compile("(.+):(.+)");

	public static String format(Element e) {
		StringBuilder sb = new StringBuilder();
		sb.append("<").append(e.getTagName());

		return sb.toString();
	}

	public static void setInnerHTML(Element root, String html) {

		// remove old root childs
		OverrideNodeList<Node> list = (OverrideNodeList<Node>) root.getChildNodes();
		list.getList().clear();

		if (html != null) {
			Parser parser = Parser.createParser(html, "UTF-8");
			try {
				parser.visitAllNodesWith(new GwtNodeVisitor(root));
			} catch (ParserException e) {
				throw new RuntimeException("error while parsing <" + root.getTagName() + "> element's innerHTML : " + html, e);
			}
		}
	}

	private static class GwtNodeVisitor extends NodeVisitor {

		private Map<org.htmlparser.Node, Element> map = new HashMap<org.htmlparser.Node, Element>();
		private Element root;

		private GwtNodeVisitor(Element root) {
			super(true);
			this.root = root;
		}

		@Override
		public void visitStringNode(Text string) {
			Element parent = getParent(string.getParent());
			parent.setInnerText(string.getText());
		}

		@Override
		public void visitTag(Tag tag) {
			Element e = Document.get().createElement(tag.getTagName());
			map.put(tag, e);
			for (Object o : tag.getAttributesEx()) {
				Attribute a = (Attribute) o;
				if ("id".equalsIgnoreCase(a.getName())) {
					e.setId(a.getValue());
				} else if ("style".equalsIgnoreCase(a.getName())) {
					processStyle(e, a.getValue());
				} else if ("class".equalsIgnoreCase(a.getName())) {
					e.setClassName(a.getValue());
				} else if (!a.isEmpty() && !a.isWhitespace() && a.isValued()) {
					e.setAttribute(a.getName(), a.getValue());
				}
			}
			Element parent = getParent(tag.getParent());
			parent.appendChild(e);

		}

		private Element getParent(org.htmlparser.Node node) {
			Element parent = map.get(node);
			return parent != null ? parent : root;
		}

		private void processStyle(Element e, String value) {
			String[] styles = value.split("\\s*;\\s*");
			for (String style : styles) {
				Matcher m = STYLE_PATTERN.matcher(style);
				if (m.matches()) {
					e.getStyle().setProperty(m.group(1), m.group(2));
				}
			}

		}

	}

}
