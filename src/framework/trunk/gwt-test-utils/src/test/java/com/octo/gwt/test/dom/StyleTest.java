package com.octo.gwt.test.dom;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.ListStyleType;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.TextDecoration;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.ui.Button;
import com.octo.gwt.test.AbstractGwtTest;
import com.octo.gwt.test.utils.WidgetUtils;

public class StyleTest extends AbstractGwtTest {

	@Test
	public void checkStyles() {
		// Setup
		Button b = new Button();
		b.setStylePrimaryName("toto");
		b.addStyleName("tata");
		b.addStyleName("titi");

		// Tests & Asserts
		Assert.assertEquals("toto", b.getStylePrimaryName());
		Assert.assertEquals(true, WidgetUtils.hasStyle(b, "tata"));
		Assert.assertEquals(true, WidgetUtils.hasStyle(b, "titi"));
		Assert.assertEquals(true, WidgetUtils.hasStyle(b, "toto"));
	}

	@Test
	public void checkEmptyStyle() {
		// Setup
		Button button = new Button();
		Style style = button.getElement().getStyle();

		// Tests
		Assert.assertEquals("", style.getBackgroundColor());
		Assert.assertEquals("", style.getBackgroundImage());
		Assert.assertEquals("", style.getBorderColor());
		Assert.assertEquals("", style.getBorderWidth());
		Assert.assertEquals("", style.getBottom());
		Assert.assertEquals("", style.getColor());
		Assert.assertEquals("", style.getCursor());
		Assert.assertEquals("", style.getDisplay());
		Assert.assertEquals("", style.getFontSize());
		Assert.assertEquals("", style.getFontStyle());
		Assert.assertEquals("", style.getFontWeight());
		Assert.assertEquals("", style.getHeight());
		Assert.assertEquals("", style.getLeft());
		Assert.assertEquals("", style.getListStyleType());
		Assert.assertEquals("", style.getMargin());
		Assert.assertEquals("", style.getMarginBottom());
		Assert.assertEquals("", style.getMarginLeft());
		Assert.assertEquals("", style.getMarginRight());
		Assert.assertEquals("", style.getMarginTop());
		Assert.assertEquals("", style.getOpacity());
		Assert.assertEquals("", style.getOverflow());
		Assert.assertEquals("", style.getPadding());
		Assert.assertEquals("", style.getPaddingBottom());
		Assert.assertEquals("", style.getPaddingLeft());
		Assert.assertEquals("", style.getPaddingRight());
		Assert.assertEquals("", style.getPaddingTop());
		Assert.assertEquals("", style.getPosition());
		Assert.assertEquals("", style.getProperty("empty"));
		Assert.assertEquals("", style.getRight());
		Assert.assertEquals("", style.getTextDecoration());
		Assert.assertEquals("", style.getTop());
		Assert.assertEquals("", style.getVerticalAlign());
		Assert.assertEquals("", style.getVisibility());
		Assert.assertEquals("", style.getWidth());
		Assert.assertEquals("", style.getZIndex());
	}

	@Test
	public void checkOpacity() {
		// Setup
		Button button = new Button();
		Style style = button.getElement().getStyle();

		// Test 1
		style.setOpacity(1.0);

		// Assert 1
		Assert.assertEquals("1", style.getOpacity());
		Assert.assertEquals("opacity: 1;", button.getElement().getAttribute("style"));

		// Test 2
		style.setOpacity(0.94);

		// Assert 2
		Assert.assertEquals("0.94", style.getOpacity());
		Assert.assertEquals("opacity: 0.94;", button.getElement().getAttribute("style"));

		// Test 3
		style.clearOpacity();
		Assert.assertEquals("", button.getElement().getAttribute("style"));

		// Assert 3
		Assert.assertEquals("", style.getOpacity());
	}

	@Test
	public void checkFloat() {
		// Setup
		Button button = new Button();
		Style style = button.getElement().getStyle();

		// Test 1
		style.setFloat(Style.Float.RIGHT);

		// Assert 1
		Assert.assertEquals(Style.Float.RIGHT.getCssName(), style.getProperty("float"));
		Assert.assertEquals("float: right;", button.getElement().getAttribute("style"));

		// Test 2
		style.clearFloat();

		// Assert 2
		Assert.assertEquals("", style.getProperty("float"));
		Assert.assertEquals("", button.getElement().getAttribute("style"));
	}

	@Test
	public void checkProperties() {
		// Setup
		Button button = new Button();
		Style style = button.getElement().getStyle();

		// Test 1
		style.setBackgroundColor("black");
		style.setBackgroundImage("/img.png");
		style.setBorderColor("blue");
		style.setBorderWidth(1.0, Unit.EM);
		style.setBottom(4.0, Unit.PX);
		style.setColor("red");
		style.setCursor(Style.Cursor.E_RESIZE);
		style.setFloat(Float.LEFT);
		style.setDisplay(Display.INLINE_BLOCK);
		style.setFontSize(10.5, Style.Unit.CM);
		style.setFontStyle(Style.FontStyle.NORMAL);
		style.setFontWeight(Style.FontWeight.BOLD);
		style.setHeight(3.1, Style.Unit.PC);
		style.setLeft(40, Style.Unit.IN);
		style.setListStyleType(ListStyleType.CIRCLE);
		style.setMargin(30.5, Style.Unit.PCT);
		style.setMarginBottom(29, Style.Unit.PT);
		style.setMarginLeft(47, Style.Unit.EX);
		style.setMarginRight(3, Style.Unit.MM);
		style.setMarginTop(10.3, Style.Unit.CM);
		style.setOverflow(Overflow.SCROLL);
		style.setPadding(10, Style.Unit.PX);
		style.setPaddingBottom(11, Style.Unit.PX);
		style.setPaddingLeft(12, Style.Unit.PX);
		style.setPaddingRight(13, Style.Unit.PX);
		style.setPaddingTop(14, Style.Unit.PX);
		style.setPosition(Position.RELATIVE);
		style.setProperty("string", "stringvalue");
		style.setProperty("doubleUnit", 17.2, Style.Unit.CM);
		style.setRight(13.4, Style.Unit.CM);
		style.setTextDecoration(TextDecoration.OVERLINE);
		style.setTop(7.77, Style.Unit.PC);
		style.setVerticalAlign(VerticalAlign.MIDDLE);
		style.setVisibility(Visibility.VISIBLE);
		style.setWidth(3.5, Style.Unit.PX);
		style.setZIndex(1000);

		// Assert 1
		Assert.assertEquals(
				"background-color: black; background-image: /img.png; border-color: blue; bottom: 4px; color: red; cursor: e-resize; float: left; display: inline-block; font-size: 10.5cm; font-style: normal; font-weight: bold; height: 3.1pc; left: 40in; list-style-type: circle; margin: 30.5%; margin-bottom: 29pt; margin-left: 47ex; margin-right: 3mm; margin-top: 10.3cm; overflow: scroll; padding: 10px; padding-bottom: 11px; padding-left: 12px; padding-right: 13px; padding-top: 14px; position: relative; string: stringvalue; double-unit: 17.2cm; right: 13.4cm; text-decoration: overline; top: 7.77pc; vertical-align: middle; visibility: visible; width: 3.5px; z-index: 1000;",
				button.getElement().getAttribute("style"));
		Assert.assertEquals("black", style.getBackgroundColor());
		Assert.assertEquals("/img.png", style.getBackgroundImage());
		Assert.assertEquals("blue", style.getBorderColor());
		Assert.assertEquals("1em", style.getBorderWidth());
		Assert.assertEquals("4px", style.getBottom());
		Assert.assertEquals("red", style.getColor());
		Assert.assertEquals(Style.Cursor.E_RESIZE.getCssName(), style.getCursor());
		Assert.assertEquals(Display.INLINE_BLOCK.getCssName(), style.getDisplay());
		Assert.assertEquals("10.5cm", style.getFontSize());
		Assert.assertEquals(Style.FontStyle.NORMAL.getCssName(), style.getFontStyle());
		Assert.assertEquals(Style.FontWeight.BOLD.getCssName(), style.getFontWeight());
		Assert.assertEquals("3.1pc", style.getHeight());
		Assert.assertEquals("40in", style.getLeft());
		Assert.assertEquals(ListStyleType.CIRCLE.getCssName(), style.getListStyleType());
		Assert.assertEquals("30.5%", style.getMargin());
		Assert.assertEquals("29pt", style.getMarginBottom());
		Assert.assertEquals("47ex", style.getMarginLeft());
		Assert.assertEquals("3mm", style.getMarginRight());
		Assert.assertEquals("10.3cm", style.getMarginTop());
		Assert.assertEquals(Overflow.SCROLL.getCssName(), style.getOverflow());
		Assert.assertEquals("10px", style.getPadding());
		Assert.assertEquals("11px", style.getPaddingBottom());
		Assert.assertEquals("12px", style.getPaddingLeft());
		Assert.assertEquals("13px", style.getPaddingRight());
		Assert.assertEquals("14px", style.getPaddingTop());
		Assert.assertEquals(Position.RELATIVE.getCssName(), style.getPosition());
		Assert.assertEquals("stringvalue", style.getProperty("string"));
		Assert.assertEquals("17.2cm", style.getProperty("doubleUnit"));
		Assert.assertEquals("13.4cm", style.getRight());
		Assert.assertEquals(TextDecoration.OVERLINE.getCssName(), style.getTextDecoration());
		Assert.assertEquals("7.77pc", style.getTop());
		Assert.assertEquals(VerticalAlign.MIDDLE.getCssName(), style.getVerticalAlign());
		Assert.assertEquals(Visibility.VISIBLE.getCssName(), style.getVisibility());
		Assert.assertEquals("3.5px", style.getWidth());
		Assert.assertEquals("1000", style.getZIndex());

		// Test2
		style.clearBackgroundColor();
		style.clearBackgroundImage();
		style.clearBorderColor();
		style.clearBorderWidth();
		style.clearBottom();
		style.clearColor();
		style.clearCursor();
		style.clearDisplay();
		style.clearFloat();
		style.clearFontSize();
		style.clearFontStyle();
		style.clearFontWeight();
		style.clearHeight();
		style.clearLeft();
		style.clearListStyleType();
		style.clearMargin();
		style.clearMarginBottom();
		style.clearMarginLeft();
		style.clearMarginRight();
		style.clearMarginTop();
		style.clearOverflow();
		style.clearPadding();
		style.clearPaddingBottom();
		style.clearPaddingLeft();
		style.clearPaddingRight();
		style.clearPaddingTop();
		style.clearPosition();
		style.clearProperty("string");
		style.clearProperty("doubleUnit");
		style.clearRight();
		style.clearTextDecoration();
		style.clearTop();
		style.clearVisibility();
		style.clearWidth();
		style.clearZIndex();

		// Assert 2
		// the only style we didn't remove in the test
		Assert.assertEquals("vertical-align: middle;", button.getElement().getAttribute("style"));
		Assert.assertEquals("", style.getBackgroundColor());
		Assert.assertEquals("", style.getBackgroundImage());
		Assert.assertEquals("", style.getBorderColor());
		Assert.assertEquals("", style.getBorderWidth());
		Assert.assertEquals("", style.getBottom());
		Assert.assertEquals("", style.getColor());
		Assert.assertEquals("", style.getCursor());
		Assert.assertEquals("", style.getDisplay());
		Assert.assertEquals("", style.getFontSize());
		Assert.assertEquals("", style.getFontStyle());
		Assert.assertEquals("", style.getFontWeight());
		Assert.assertEquals("", style.getHeight());
		Assert.assertEquals("", style.getLeft());
		Assert.assertEquals("", style.getListStyleType());
		Assert.assertEquals("", style.getMargin());
		Assert.assertEquals("", style.getMarginBottom());
		Assert.assertEquals("", style.getMarginLeft());
		Assert.assertEquals("", style.getMarginRight());
		Assert.assertEquals("", style.getMarginTop());
		Assert.assertEquals("", style.getOverflow());
		Assert.assertEquals("", style.getPadding());
		Assert.assertEquals("", style.getPaddingBottom());
		Assert.assertEquals("", style.getPaddingLeft());
		Assert.assertEquals("", style.getPaddingRight());
		Assert.assertEquals("", style.getPaddingTop());
		Assert.assertEquals("", style.getPosition());
		Assert.assertEquals("", style.getProperty("string"));
		Assert.assertEquals("", style.getProperty("doubleUnit"));
		Assert.assertEquals("", style.getRight());
		Assert.assertEquals("", style.getTextDecoration());
		Assert.assertEquals("", style.getTop());
		Assert.assertEquals("", style.getVisibility());
		Assert.assertEquals("", style.getWidth());
		Assert.assertEquals("", style.getZIndex());

	}
}
