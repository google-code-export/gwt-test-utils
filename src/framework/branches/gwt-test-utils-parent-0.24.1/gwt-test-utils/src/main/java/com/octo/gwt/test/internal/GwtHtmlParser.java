package com.octo.gwt.test.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.google.gwt.dom.client.NodeList;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.internal.patchers.dom.NodeFactory;

public class GwtHtmlParser {

	private static Pattern STYLE_PATTERN = Pattern.compile("(.+):(.+)");

	//	public static String format(Element e) {
	//		StringBuilder sb = new StringBuilder();
	//		if (e.getChildCount() == 0) {
	//			return e.getInnerText();
	//		}
	//
	//		sb.append("<").append(e.getTagName());
	//		PropertyContainer pc = PropertyContainerHelper.cast(e).getProperties();
	//		Iterator<String> it = pc.orderedIterator();
	//		if (it.hasNext()) {
	//			sb.append(" ");
	//		}
	//		while (it.hasNext()) {
	//			String current = it.next();
	//			sb.append(current).append("=\"").append(pc.get(current)).append("\" ");
	//		}
	//
	//		return sb.toString();
	//	}

	public static NodeList<Node> parse(String html) {
		if (html == null) {
			return new OverrideNodeList<Node>();
		}

		Parser parser = Parser.createParser(html, "UTF-8");
		try {
			GwtNodeVisitor visitor = new GwtNodeVisitor();
			parser.visitAllNodesWith(visitor);

			return new OverrideNodeList<Node>(visitor.visitedNodes);
		} catch (ParserException e) {
			throw new RuntimeException("error while parsing HTML : " + html, e);
		}
	}

	private static class GwtNodeVisitor extends NodeVisitor {

		private Map<org.htmlparser.Node, Element> map = new HashMap<org.htmlparser.Node, Element>();

		public List<Node> visitedNodes;

		private GwtNodeVisitor() {
			super(true);
			visitedNodes = new ArrayList<Node>();
		}

		@Override
		public void visitStringNode(Text string) {
			Element parent = getParent(string);
			if (parent != null) {
				parent.setInnerText(string.getText());
			} else {
				visitedNodes.add(NodeFactory.createTextNode(string.getText()));
			}
		}

		@Override
		public void visitTag(Tag tag) {
			if (tag.getTagName().startsWith("!")) {
				// Commentaire ou !DOCTYPE
				return;
			}
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
			Element parent = getParent(tag);
			if (parent != null) {
				parent.appendChild(e);
			} else {
				visitedNodes.add(e);
			}

		}

		private Element getParent(org.htmlparser.Node node) {
			return map.get(node.getParent());
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
