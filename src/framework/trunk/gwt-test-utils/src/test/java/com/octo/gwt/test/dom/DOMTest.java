package com.octo.gwt.test.dom;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.FieldSetElement;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.dom.client.LegendElement;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.TableCaptionElement;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableColElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.TableSectionElement;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Image;
import com.octo.gwt.test.GwtTestTest;

public class DOMTest extends GwtTestTest {

  @Test
  public void checkCreateAnchor() {
    // Act
    AnchorElement elem = AnchorElement.as(DOM.createAnchor());

    // Assert
    Assert.assertEquals("a", elem.getTagName());
  }

  @Test
  public void checkCreateButton() {
    // Act
    ButtonElement elem = ButtonElement.as(DOM.createButton());

    // Assert
    Assert.assertEquals("button", elem.getTagName());
  }

  @Test
  public void checkCreateCaption() {
    // Act
    TableCaptionElement elem = TableCaptionElement.as(DOM.createCaption());

    // Assert
    Assert.assertEquals("caption", elem.getTagName());
  }

  @Test
  public void checkCreateCol() {
    // Act
    TableColElement elem = TableColElement.as(DOM.createCol());

    // Assert
    Assert.assertEquals("col", elem.getTagName());
  }

  @Test
  public void checkCreateColGroup() {
    // Act
    TableColElement elem = TableColElement.as(DOM.createColGroup());

    // Assert
    Assert.assertEquals("colgroup", elem.getTagName());
  }

  @Test
  public void checkCreateDiv() {
    // Act
    DivElement elem = DivElement.as(DOM.createDiv());

    // Assert
    Assert.assertEquals("div", elem.getTagName());
  }

  @Test
  public void checkCreateElement() {
    // Act
    DivElement elem = DivElement.as(DOM.createElement("div"));

    // Assert
    Assert.assertEquals("div", elem.getTagName());
  }

  @Test
  public void checkCreateFieldSet() {
    // Act
    FieldSetElement elem = FieldSetElement.as(DOM.createFieldSet());

    // Assert
    Assert.assertEquals("fieldset", elem.getTagName());
  }

  @Test
  public void checkCreateForm() {
    // Act
    FormElement elem = FormElement.as(DOM.createForm());

    // Assert
    Assert.assertEquals("form", elem.getTagName());
  }

  @Test
  public void checkCreateIFrame() {
    // Act
    IFrameElement elem = IFrameElement.as(DOM.createIFrame());

    // Assert
    Assert.assertEquals("iframe", elem.getTagName());
  }

  @Test
  public void checkCreateImg() {
    // Act
    ImageElement elem = ImageElement.as(DOM.createImg());

    // Assert
    Assert.assertEquals("img", elem.getTagName());
  }

  @Test
  public void checkCreateInputCheck() {
    // Act
    InputElement elem = InputElement.as(DOM.createInputCheck());

    // Assert
    Assert.assertEquals("input", elem.getTagName());
    Assert.assertEquals("checkbox", elem.getType());
  }

  @Test
  public void checkCreateInputPassword() {
    // Act
    InputElement elem = InputElement.as(DOM.createInputPassword());

    // Assert
    Assert.assertEquals("input", elem.getTagName());
    Assert.assertEquals("password", elem.getType());
  }

  @Test
  public void checkCreateInputRadio() {
    // Act
    InputElement elem = InputElement.as(DOM.createInputRadio("test"));

    // Assert
    Assert.assertEquals("input", elem.getTagName());
    Assert.assertEquals("test", elem.getName());
  }

  @Test
  public void checkCreateInputText() {
    // Act
    InputElement elem = InputElement.as(DOM.createInputText());

    // Assert
    Assert.assertEquals("input", elem.getTagName());
    Assert.assertEquals("text", elem.getType());
  }

  @Test
  public void checkCreateLabel() {
    // Act
    LabelElement elem = LabelElement.as(DOM.createLabel());

    // Assert
    Assert.assertEquals("label", elem.getTagName());
  }

  @Test
  public void checkCreateLegend() {
    // Act
    LegendElement elem = LegendElement.as(DOM.createLegend());

    // Assert
    Assert.assertEquals("legend", elem.getTagName());
  }

  @Test
  public void checkCreateOption() {
    // Act
    OptionElement elem = OptionElement.as(DOM.createOption());

    // Assert
    Assert.assertEquals("option", elem.getTagName());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void checkCreateOptions() {
    // Act
    OptionElement elem = OptionElement.as(DOM.createOptions());

    // Assert
    Assert.assertEquals("options", elem.getTagName());
  }

  @Test
  public void checkCreateSelect() {
    // Act
    SelectElement elem = SelectElement.as(DOM.createSelect());

    // Assert
    Assert.assertEquals("select", elem.getTagName());
    Assert.assertFalse("Simple SelectElement should not be multiple",
        elem.isMultiple());
  }

  @Test
  public void checkCreateSelectMultiple() {
    // Act
    SelectElement elem = SelectElement.as(DOM.createSelect(true));

    // Assert
    Assert.assertEquals("select", elem.getTagName());
    Assert.assertTrue("SelectElement should be multiple", elem.isMultiple());
  }

  @Test
  public void checkCreateSpan() {
    // Act
    SpanElement elem = SpanElement.as(DOM.createSpan());

    // Assert
    Assert.assertEquals("span", elem.getTagName());
  }

  @Test
  public void checkCreateTable() {
    // Act
    TableElement elem = TableElement.as(DOM.createTable());

    // Assert
    Assert.assertEquals("table", elem.getTagName());
  }

  @Test
  public void checkCreateTBody() {
    // Act
    TableSectionElement elem = TableSectionElement.as(DOM.createTBody());

    // Assert
    Assert.assertEquals("tbody", elem.getTagName());
  }

  @Test
  public void checkCreateTD() {
    // Act
    TableCellElement elem = TableCellElement.as(DOM.createTD());

    // Assert
    Assert.assertEquals("td", elem.getTagName());
  }

  @Test
  public void checkCreateTextArea() {
    // Act
    TextAreaElement elem = TextAreaElement.as(DOM.createTextArea());

    // Assert
    Assert.assertEquals("textarea", elem.getTagName());
  }

  @Test
  public void checkCreateTFoot() {
    // Act
    TableSectionElement elem = TableSectionElement.as(DOM.createTFoot());

    // Assert
    Assert.assertEquals("tfoot", elem.getTagName());
  }

  @Test
  public void checkCreateTH() {
    // Act
    TableCellElement elem = TableCellElement.as(DOM.createTH());

    // Assert
    Assert.assertEquals("th", elem.getTagName());
  }

  @Test
  public void checkCreateTHead() {
    // Act
    TableSectionElement elem = TableSectionElement.as(DOM.createTHead());

    // Assert
    Assert.assertEquals("thead", elem.getTagName());
  }

  @Test
  public void checkCreateTR() {
    // Act
    TableRowElement elem = TableRowElement.as(DOM.createTR());

    // Assert
    Assert.assertEquals("tr", elem.getTagName());
  }

  @Test
  public void checkImageSrc() {
    // Arrange
    Image img = new Image();
    ImageElement elem = img.getElement().cast();

    // Act
    DOM.setImgSrc(img.getElement(), "http://test/image.gif");
    String imageSrc = DOM.getImgSrc(img.getElement());

    // Assert
    Assert.assertEquals("http://test/image.gif", elem.getSrc());
    Assert.assertEquals("http://test/image.gif", imageSrc);
  }

}
