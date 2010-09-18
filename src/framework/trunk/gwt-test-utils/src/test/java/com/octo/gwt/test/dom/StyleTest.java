package com.octo.gwt.test.dom;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
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
		Style style = new Button().getElement().getStyle();

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
		Style style = new Button().getElement().getStyle();

		// Test 1
		style.setOpacity(1.0);

		// Assert 1
		Assert.assertEquals("1.0", style.getOpacity());

		// Test 2
		style.clearOpacity();

		// Assert 2
		Assert.assertEquals("", style.getOpacity());
	}

	@Test
	public void checkFloat() {
		// Setup
		Style style = new Button().getElement().getStyle();

		// Test 1
		style.setFloat(Style.Float.RIGHT);

		// Assert 1
		Assert.assertEquals(Style.Float.RIGHT.getCssName(), style.getProperty("float"));

		// Test 2
		style.clearFloat();

		// Assert 2
		Assert.assertEquals("", style.getProperty("float"));
	}

	@Test
	public void checkProperties() {
		// Setup
		Style style = new Button().getElement().getStyle();

		// Test 1
		style.setBackgroundColor("black");
		style.setBackgroundImage("/img.png");
		style.setBorderColor("blue");
		style.setBorderWidth(1.0, Unit.EM);
		style.setBottom(4.0, Unit.PX);
		style.setColor("red");
		style.setCursor(Style.Cursor.E_RESIZE);
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
		Assert.assertEquals("black", style.getBackgroundColor());
		Assert.assertEquals("/img.png", style.getBackgroundImage());
		Assert.assertEquals("blue", style.getBorderColor());
		Assert.assertEquals("1.0em", style.getBorderWidth());
		Assert.assertEquals("4.0px", style.getBottom());
		Assert.assertEquals("red", style.getColor());
		Assert.assertEquals(Style.Cursor.E_RESIZE.getCssName(), style.getCursor());
		Assert.assertEquals(Display.INLINE_BLOCK.getCssName(), style.getDisplay());
		Assert.assertEquals("10.5cm", style.getFontSize());
		Assert.assertEquals(Style.FontStyle.NORMAL.getCssName(), style.getFontStyle());
		Assert.assertEquals(Style.FontWeight.BOLD.getCssName(), style.getFontWeight());
		Assert.assertEquals("3.1pc", style.getHeight());
		Assert.assertEquals("40.0in", style.getLeft());
		Assert.assertEquals(ListStyleType.CIRCLE.getCssName(), style.getListStyleType());
		Assert.assertEquals("30.5%", style.getMargin());
		Assert.assertEquals("29.0pt", style.getMarginBottom());
		Assert.assertEquals("47.0ex", style.getMarginLeft());
		Assert.assertEquals("3.0mm", style.getMarginRight());
		Assert.assertEquals("10.3cm", style.getMarginTop());
		Assert.assertEquals(Overflow.SCROLL.getCssName(), style.getOverflow());
		Assert.assertEquals("10.0px", style.getPadding());
		Assert.assertEquals("11.0px", style.getPaddingBottom());
		Assert.assertEquals("12.0px", style.getPaddingLeft());
		Assert.assertEquals("13.0px", style.getPaddingRight());
		Assert.assertEquals("14.0px", style.getPaddingTop());
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
