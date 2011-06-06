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
import com.octo.gwt.test.GwtTestClassLoader;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.internal.utils.PropertyContainerHelper;
import com.octo.gwt.test.internal.utils.TagAware;

public class NodeFactory {

  public static final String BODY_ELEMENT = "Body";

  private static Map<String, String> elementMap = new HashMap<String, String>();
  private static Map<String, String> elementWithSpecialTagMap = new HashMap<String, String>();

  static {
    elementMap.put("a", AnchorElement.class.getName());
    elementMap.put("area", AreaElement.class.getName());
    elementMap.put("base", BaseElement.class.getName());
    elementMap.put("body", BodyElement.class.getName());
    elementMap.put("br", BRElement.class.getName());
    elementMap.put("button", ButtonElement.class.getName());
    elementMap.put("div", DivElement.class.getName());
    elementMap.put("dl", DListElement.class.getName());
    elementMap.put("fieldset", FieldSetElement.class.getName());
    elementMap.put("form", FormElement.class.getName());
    elementMap.put("frame", FrameElement.class.getName());
    elementMap.put("frameset", FrameSetElement.class.getName());
    elementMap.put("head", HeadElement.class.getName());
    elementMap.put("hr", HRElement.class.getName());
    elementWithSpecialTagMap.put("h1", HeadingElement.class.getName());
    elementWithSpecialTagMap.put("h2", HeadingElement.class.getName());
    elementWithSpecialTagMap.put("h3", HeadingElement.class.getName());
    elementWithSpecialTagMap.put("h4", HeadingElement.class.getName());
    elementWithSpecialTagMap.put("h5", HeadingElement.class.getName());
    elementWithSpecialTagMap.put("h6", HeadingElement.class.getName());
    elementMap.put("hr", HRElement.class.getName());
    elementMap.put("iframe", IFrameElement.class.getName());
    elementMap.put("img", ImageElement.class.getName());
    elementWithSpecialTagMap.put("ins", ModElement.class.getName());
    elementWithSpecialTagMap.put("del", ModElement.class.getName());
    elementMap.put("input", InputElement.class.getName());
    elementMap.put("label", LabelElement.class.getName());
    elementMap.put("legend", LegendElement.class.getName());
    elementMap.put("li", LIElement.class.getName());
    elementMap.put("link", LinkElement.class.getName());
    elementMap.put("map", MapElement.class.getName());
    elementMap.put("meta", MetaElement.class.getName());
    elementMap.put("object", ObjectElement.class.getName());
    elementMap.put("ol", OListElement.class.getName());
    elementMap.put("optgroup", OptGroupElement.class.getName());
    elementMap.put("option", OptionElement.class.getName());
    elementMap.put("options", OptionElement.class.getName());
    elementMap.put("p", ParagraphElement.class.getName());
    elementMap.put("param", ParamElement.class.getName());
    elementMap.put("pre", PreElement.class.getName());
    elementWithSpecialTagMap.put("q", QuoteElement.class.getName());
    elementWithSpecialTagMap.put("blockquote", QuoteElement.class.getName());
    elementMap.put("script", ScriptElement.class.getName());
    elementMap.put("select", SelectElement.class.getName());
    elementMap.put("span", SpanElement.class.getName());
    elementMap.put("style", StyleElement.class.getName());
    elementMap.put("caption", TableCaptionElement.class.getName());
    elementWithSpecialTagMap.put("td", TableCellElement.class.getName());
    elementWithSpecialTagMap.put("th", TableCellElement.class.getName());
    elementWithSpecialTagMap.put("col", TableColElement.class.getName());
    elementWithSpecialTagMap.put("colgroup", TableColElement.class.getName());
    elementMap.put("table", TableElement.class.getName());
    elementMap.put("tr", TableRowElement.class.getName());
    elementWithSpecialTagMap.put("tbody", TableSectionElement.class.getName());
    elementWithSpecialTagMap.put("tfoot", TableSectionElement.class.getName());
    elementWithSpecialTagMap.put("thead", TableSectionElement.class.getName());
    elementMap.put("textarea", TextAreaElement.class.getName());
    elementMap.put("title", TitleElement.class.getName());
    elementMap.put("ul", UListElement.class.getName());
  }

  public static Document DOCUMENT;

  private NodeFactory() {
  }

  public static void reset() {
    if (DOCUMENT != null) {
      PropertyContainer bodyPc = PropertyContainerHelper.cast(
          DOCUMENT.getBody()).getProperties();
      bodyPc.clear();

      PropertyContainer documentPc = PropertyContainerHelper.cast(DOCUMENT).getProperties();
      documentPc.clear();
      DOCUMENT = null;
    }
  }

  public static Document getDocument() {
    if (DOCUMENT == null) {
      try {
        DOCUMENT = (Document) loadClass(Document.class.getName()).newInstance();
        Element e = createHTMLElement();
        DOCUMENT.appendChild(e);
        PropertyContainerHelper.setProperty(DOCUMENT, "DocumentElement", e);
      } catch (Exception e) {
        throw new RuntimeException("Unable to create Document", e);
      }
    }
    return DOCUMENT;
  }

  public static Style createStyle(Element owner) {
    try {
      return (Style) loadClass(Style.class.getName()).getConstructor(
          Element.class).newInstance(owner);
    } catch (Exception e) {
      throw new RuntimeException("Unable to create style for element <"
          + owner.getTagName() + ">" + e);
    }
  }

  public static Node createNode() {
    try {
      return (Node) loadClass(Node.class.getName()).newInstance();
    } catch (Exception e) {
      throw new RuntimeException("Unable to create node " + e);
    }
  }

  public static Text createTextNode(String data) {
    try {
      Text text = (Text) loadClass(Text.class.getName()).newInstance();
      text.setData(data);

      return text;
    } catch (Exception e) {
      throw new RuntimeException("Unable to create text " + e);
    }
  }

  private static Element createHTMLElement() {
    try {
      Element e = (Element) loadClass(Element.class.getName()).newInstance();
      PropertyContainerHelper.setProperty(e, "NodeName", "HTML");
      PropertyContainerHelper.setProperty(e, "TagName", "HTML");

      BodyElement bodyElement = (BodyElement) createElement("body");
      PropertyContainerHelper.setProperty(e, BODY_ELEMENT, bodyElement);
      e.appendChild(bodyElement);

      return e;
    } catch (Exception e) {
      throw new RuntimeException("Unable to create HTML element " + e);
    }
  }

  public static Element createElement(String tag) {
    Element elem = null;
    try {
      String elemClassName = elementMap.get(tag.toLowerCase());
      String elemClassNameWithTag = elementWithSpecialTagMap.get(tag.toLowerCase());

      if (elemClassName != null) {
        elem = (Element) loadClass(elemClassName).newInstance();
      } else if (elemClassNameWithTag != null) {
        Constructor<?> constructor = loadClass(elemClassNameWithTag).getConstructor(
            String.class);
        elem = (Element) constructor.newInstance(tag);
      }
      if (elem == null) {
        elem = new SpecificElement(tag);
      }

      // set the <body> element as default parent node
      if (!"body".equals(elem.getTagName())) {
        Document.get().getBody().appendChild(elem);
      }

      return elem;
    } catch (Exception e) {
      throw new RuntimeException("Cannot create element for tag <" + tag + ">",
          e);
    }
  }

  private static Class<?> loadClass(String className) {
    try {
      return GwtTestClassLoader.getInstance().loadClass(className);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  private static class SpecificElement extends Element implements TagAware {

    private String tag;

    public SpecificElement(String tag) {
      this.tag = tag;
    }

    public String getTag() {
      return tag;
    }

  }
}
