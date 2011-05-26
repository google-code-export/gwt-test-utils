package com.octo.gwt.test.internal.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.octo.gwt.test.exceptions.GwtTestDomException;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;

/**
 * 
 * HTML parser used by gwt-test-utils. It relies on htmlparser. <strong>For
 * internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class GwtHtmlParser {

  private static class GwtNodeVisitor extends NodeVisitor {

    public List<Node> visitedNodes;

    private Element currentParent;
    private final Map<org.htmlparser.Node, Element> tagMap = new HashMap<org.htmlparser.Node, Element>();

    private GwtNodeVisitor() {
      super(true);
      visitedNodes = new ArrayList<Node>();
    }

    @Override
    public void visitStringNode(Text string) {
      if (currentParent != null) {
        currentParent.setInnerText(string.getText());
        // the parent element has been treated
        currentParent = null;
      } else {
        visitedNodes.add(JavaScriptObjects.newText(string.getText()));
      }
    }

    @Override
    public void visitTag(Tag tag) {
      if (tag.getTagName().startsWith("!")) {
        // comment or !DOCTYPE
        return;
      }
      Element e = Document.get().createElement(tag.getTagName());
      tagMap.put(tag, e);
      for (Object o : tag.getAttributesEx()) {
        Attribute a = (Attribute) o;
        if ("id".equalsIgnoreCase(a.getName())) {
          e.setId(a.getValue());
        } else if ("style".equalsIgnoreCase(a.getName())) {
          StyleUtils.setStyle(e.getStyle(), a.getValue());
        } else if ("class".equalsIgnoreCase(a.getName())) {
          e.setClassName(a.getValue());
        } else if (!a.isEmpty() && !a.isWhitespace() && a.isValued()) {
          e.setAttribute(a.getName(), a.getValue());
        }
      }

      Element parent = tagMap.get(tag.getParent());
      if (parent != null) {
        parent.appendChild(e);
      } else {
        visitedNodes.add(e);
      }

      // if (tag.getChildren() != null) {
      currentParent = e;
      // }

    }
  }

  public static NodeList<Node> parse(String html) {
    List<Node> innerList;
    if (html == null) {
      innerList = new ArrayList<Node>();
    } else {
      Parser parser = Parser.createParser(html, "UTF-8");
      try {
        GwtNodeVisitor visitor = new GwtNodeVisitor();

        // fill the visitor.visitedNodes
        parser.visitAllNodesWith(visitor);

        innerList = visitor.visitedNodes;
      } catch (ParserException e) {
        throw new GwtTestDomException("error while parsing HTML : " + html, e);
      }
    }

    return JavaScriptObjects.newNodeList(innerList);
  }
}
