package com.octo.gwt.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class SafeHtmlTest extends GwtTestTest {

  interface MyTemplate extends SafeHtmlTemplates {

    @Template("<div style=\"outline:none;\">{0}</div>")
    SafeHtml div(SafeHtml contents);

    @Template("<div style=\"outline:none;\" tabindex=\"{0}\" accessKey=\"{1}\">{2}</div>")
    SafeHtml divFocusableWithKey(int tabIndex, char accessKey, SafeHtml contents);

    @Template("<td class=\"{0}\">{1}</td>")
    SafeHtml td(String classes, SafeHtml contents);

    @Template("<td class=\"{0}\" align=\"{1}\" valign=\"{2}\">{3}</td>")
    SafeHtml tdBothAlign(String classes, String hAlign, String vAlign,
        SafeHtml contents);

    @Template("<td class=\"{0}\" align=\"{1}\">{2}</td>")
    SafeHtml tdHorizontalAlign(String classes, String hAlign, SafeHtml contents);

    @Template("<td class=\"{0}\" valign=\"{1}\">{2}</td>")
    SafeHtml tdVerticalAlign(String classes, String vAlign, SafeHtml contents);

    @Template("<table><tfoot>{0}</tfoot></table>")
    SafeHtml tfoot(SafeHtml rowHtml);

    @Template("<th colspan=\"{0}\" class=\"{1}\">{2}</th>")
    SafeHtml th(int colspan, String classes, SafeHtml contents);
  }

  private static MyTemplate TEMPLATE = GWT.create(MyTemplate.class);

  @Test
  public void template() {
    // Arrange
    SafeHtmlBuilder builder = new SafeHtmlBuilder();
    builder.appendEscaped("this is a test");

    // Act
    SafeHtml result = TEMPLATE.div(builder.toSafeHtml());

    // Assert
    assertEquals("<div style=\"outline:none;\">this is a test</div>",
        result.asString());
  }

}
