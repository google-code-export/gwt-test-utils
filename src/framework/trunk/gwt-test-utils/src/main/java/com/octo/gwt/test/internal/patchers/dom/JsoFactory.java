package com.octo.gwt.test.internal.patchers.dom;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
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
import com.octo.gwt.test.GwtTest;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.GwtTestDomException;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.internal.GwtConfig;
import com.octo.gwt.test.internal.GwtHtmlParser;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.internal.utils.PropertyContainerUtils;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class JsoFactory {

  public static Document DOCUMENT;

  private static final Map<String, Class<? extends Element>> elementMap = new TreeMap<String, Class<? extends Element>>();

  static {
    elementMap.put("a", AnchorElement.class);
    elementMap.put("area", AreaElement.class);
    elementMap.put("base", BaseElement.class);
    elementMap.put("body", BodyElement.class);
    elementMap.put("br", BRElement.class);
    elementMap.put("button", ButtonElement.class);
    elementMap.put("div", DivElement.class);
    elementMap.put("dl", DListElement.class);
    elementMap.put("fieldset", FieldSetElement.class);
    elementMap.put("form", FormElement.class);
    elementMap.put("frame", FrameElement.class);
    elementMap.put("frameset", FrameSetElement.class);
    elementMap.put("head", HeadElement.class);
    elementMap.put("hr", HRElement.class);
    elementMap.put("h1", HeadingElement.class);
    elementMap.put("h2", HeadingElement.class);
    elementMap.put("h3", HeadingElement.class);
    elementMap.put("h4", HeadingElement.class);
    elementMap.put("h5", HeadingElement.class);
    elementMap.put("h6", HeadingElement.class);
    elementMap.put("hr", HRElement.class);
    elementMap.put("iframe", IFrameElement.class);
    elementMap.put("img", ImageElement.class);
    elementMap.put("ins", ModElement.class);
    elementMap.put("del", ModElement.class);
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
    elementMap.put("blockquote", QuoteElement.class);
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

  public static Element createElement(String tag) {
    try {
      Class<? extends Element> clazz = elementMap.get(tag.toLowerCase());

      if (clazz == null) {
        clazz = Element.class;
      }

      Element elem = GwtReflectionUtils.instantiateClass(clazz);

      PropertyContainerUtils.setProperty(elem, DOMProperties.TAG_NAME, tag);

      if (tag.equalsIgnoreCase("html")) {
        PropertyContainerUtils.setProperty(elem, DOMProperties.NODE_NAME,
            "HTML");
      }

      return elem;
    } catch (Exception e) {
      throw new GwtTestDomException("Cannot create element for tag <" + tag
          + ">", e);
    }
  }

  public static <T extends Node> NodeList<T> createNodeList() {
    return createNodeList(new ArrayList<T>());
  }

  @SuppressWarnings("unchecked")
  public static <T extends Node> NodeList<T> createNodeList(List<T> innerList) {
    NodeList<T> nodeList = createObject(NodeList.class);

    PropertyContainerUtils.setProperty(nodeList,
        DOMProperties.NODE_LIST_INNER_LIST, innerList);

    return nodeList;
  }

  public static <T extends JavaScriptObject> T createObject(Class<T> jsoClass) {
    // TODO : need to work with => JavaScriptObject.createObject().cast()
    return GwtReflectionUtils.instantiateClass(jsoClass);
  }

  public static Style createStyle(Element owner) {
    Constructor<Style> cons;
    try {
      cons = Style.class.getConstructor(Element.class);
    } catch (Exception e) {
      throw new GwtTestDomException("Unable to create style for element <"
          + owner.getTagName() + ">" + e);
    }
    return GwtReflectionUtils.instantiateClass(cons, owner);
  }

  public static Text createTextNode(String data) {
    try {
      Text text = GwtReflectionUtils.instantiateClass(Text.class);
      text.setData(data);

      return text;
    } catch (Exception e) {
      throw new GwtTestDomException("Unable to create text " + e);
    }
  }

  public static Document getDocument() {
    if (DOCUMENT == null) {
      try {
        DOCUMENT = createObject(Document.class);
        Element e = parseHTMLElement();
        DOCUMENT.appendChild(e);
        PropertyContainerUtils.setProperty(DOCUMENT,
            DOMProperties.DOCUMENT_ELEMENT, e);
      } catch (Exception e) {
        if (GwtTestException.class.isInstance(e)) {
          throw (GwtTestException) e;
        } else {
          throw new GwtTestDomException("Unable to create Document", e);
        }
      }
    }
    return DOCUMENT;
  }

  public static void reset() {
    if (DOCUMENT != null) {
      PropertyContainer bodyPc = PropertyContainerUtils.cast(DOCUMENT.getBody()).getProperties();
      bodyPc.clear();

      PropertyContainer documentPc = PropertyContainerUtils.cast(DOCUMENT).getProperties();
      documentPc.clear();
      DOCUMENT = null;
    }
  }

  private static Element findHTMLElement(String hostPagePath,
      NodeList<Node> nodes) {
    int i = 0;
    while (i < nodes.getLength()) {
      Node node = nodes.getItem(i);
      if (Node.ELEMENT_NODE == node.getNodeType()) {
        Element e = node.cast();
        if ("html".equalsIgnoreCase(e.getTagName())) {
          return e;
        }
      }
      i++;
    }
    throw new GwtTestDomException("Cannot find a root <html> element in file '"
        + hostPagePath + "'");
  }

  private static String getHostPageHTML(String hostPagePath) {

    InputStream is = JsoFactory.class.getClassLoader().getResourceAsStream(
        hostPagePath);

    if (is == null) {
      try {
        is = new FileInputStream(hostPagePath);
      } catch (FileNotFoundException e) {
        // handle just after
      }
    }
    if (is == null) {
      throw new GwtTestConfigurationException("Cannot find file '"
          + hostPagePath + "', please override "
          + GwtTest.class.getSimpleName()
          + ".getHostPagePath() method correctly (see "
          + ClassLoader.class.getSimpleName()
          + ".getResourceAsStream(string name))");
    }
    BufferedReader br = null;
    try {
      br = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }

      return sb.toString();
    } catch (IOException e) {
      throw new GwtTestConfigurationException(
          "Error while reading module HTML host page '" + hostPagePath + "'", e);
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          // don't care
        }
      }
    }

  }

  private static Element parseHTMLElement() {
    String hostPagePath = GwtConfig.get().getHostPagePath();

    if (hostPagePath == null) {
      throw new GwtTestConfigurationException(
          "Cannot find the actual HTML host page for module '"
              + GWT.getModuleName()
              + "'. You should override "
              + GwtTest.class.getName()
              + ".getHostPagePath(String moduleFullQualifiedName) method to specify it.");
    }

    // parsing of the host page
    String html = getHostPageHTML(hostPagePath);
    NodeList<Node> nodes = GwtHtmlParser.parse(html);
    return findHTMLElement(hostPagePath, nodes);
  }

  private JsoFactory() {
  }
}
