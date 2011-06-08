package com.octo.gwt.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.octo.gwt.test.utils.events.Browser;

public class BrowserTest extends GwtTestTest {

  private Button b;
  private Cell clickedCell;
  private int keyDownCount;
  private int keyUpCount;
  private boolean onBlurTriggered;
  private boolean onChangeTriggered;

  private boolean tested;

  @Test
  public void checkBlurEvent() {
    tested = false;;
    b.addBlurHandler(new BlurHandler() {

      public void onBlur(BlurEvent event) {
        tested = !tested;

        Assert.assertEquals(b.getElement(),
            event.getNativeEvent().getEventTarget());
        Assert.assertNull(event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // simule the event
    Browser.blur(b);

    Assert.assertTrue("onBlur event was not triggered", tested);
  }

  @Test
  public void checkClickEvent() {
    tested = false;;
    b.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        tested = !tested;

        Assert.assertEquals(b.getElement(),
            event.getNativeEvent().getEventTarget());
        Assert.assertNull(event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // simule the event
    Browser.click(b);

    Assert.assertTrue("onClick event was not triggered", tested);
  }

  @Test
  public void checkClickOnComplexPanel() {

    // Set up
    tested = false;
    final Anchor a = new Anchor();
    final ComplexPanel panel = new StackPanel() {

      @Override
      public void onBrowserEvent(com.google.gwt.user.client.Event event) {
        super.onBrowserEvent(event);

        if (DOM.eventGetType(event) == Event.ONCLICK) {
          tested = !tested;
          Assert.assertNull(event.getRelatedEventTarget());
        }

        Assert.assertEquals(a.getElement(), event.getEventTarget());
      };
    };

    panel.add(a);

    // Test
    Browser.click(panel, 0);

    // Assert
    Assert.assertTrue("onClick event was not triggered", tested);
  }

  @Test
  public void checkClickOnGrid() {
    // Setup
    final Grid g = new Grid(2, 2);
    final Anchor a = new Anchor();
    g.setWidget(1, 1, a);

    g.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        clickedCell = g.getCellForEvent(event);

        Assert.assertEquals(a.getElement(),
            event.getNativeEvent().getEventTarget());
        Assert.assertNull(event.getNativeEvent().getRelatedEventTarget());
      }
    });

    // Test
    Browser.click(g, 1, 1);

    // Assert
    Assert.assertEquals(1, clickedCell.getRowIndex());
    Assert.assertEquals(1, clickedCell.getCellIndex());
  }

  @Test
  public void checkClickOnSuggestBox() {
    // Setup
    MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
    oracle.add("suggestion 1");
    oracle.add("suggestion 2");
    SuggestBox box = new SuggestBox(oracle);

    // Test
    Browser.fillText(box, "sug");
    Browser.click(box, 1);

    // Assert
    Assert.assertEquals("suggestion 2", box.getText());
  }

  @Test
  public void checkEmptyText_LongPressFalse() {
    // Setup
    onChangeTriggered = false;
    onBlurTriggered = false;
    keyDownCount = 0;
    keyUpCount = 0;
    String initialText = "1234";

    final TextBox tb = new TextBox();
    tb.setText(initialText);

    tb.addChangeHandler(new ChangeHandler() {

      public void onChange(ChangeEvent event) {
        onChangeTriggered = true;
      }
    });

    tb.addBlurHandler(new BlurHandler() {

      public void onBlur(BlurEvent event) {
        onBlurTriggered = true;
      }
    });

    tb.addKeyPressHandler(new KeyPressHandler() {

      public void onKeyPress(KeyPressEvent event) {
        Assert.fail("no keyPress event should be triggered when pressing backspace button");
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        Assert.assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyUpCount++;
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        Assert.assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyDownCount++;
      }
    });

    // Test
    Browser.emptyText(tb, false);

    // Asserts
    // the textbox value should be updated
    Assert.assertEquals("", tb.getText());
    Assert.assertEquals(4, keyDownCount);
    Assert.assertEquals(4, keyUpCount);
    Assert.assertTrue(onBlurTriggered);
    Assert.assertTrue(onChangeTriggered);
  }

  @Test
  public void checkEmptyText_LongPressTrue() {
    // Setup
    onChangeTriggered = false;
    onBlurTriggered = false;
    keyDownCount = 0;
    keyUpCount = 0;
    String initialText = "1234";

    final TextBox tb = new TextBox();
    tb.setText(initialText);

    tb.addChangeHandler(new ChangeHandler() {

      public void onChange(ChangeEvent event) {
        onChangeTriggered = true;
      }
    });

    tb.addBlurHandler(new BlurHandler() {

      public void onBlur(BlurEvent event) {
        onBlurTriggered = true;
      }
    });

    tb.addKeyPressHandler(new KeyPressHandler() {

      public void onKeyPress(KeyPressEvent event) {
        Assert.fail("no keyPress event should be triggered when pressing backspace button");
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        Assert.assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyUpCount++;
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        Assert.assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyDownCount++;
      }
    });

    // Test
    Browser.emptyText(tb, true);

    // Asserts
    // the textbox value should be updated
    Assert.assertEquals("", tb.getText());
    Assert.assertEquals(4, keyDownCount);
    Assert.assertEquals(1, keyUpCount);
    Assert.assertTrue(onBlurTriggered);
    Assert.assertTrue(onChangeTriggered);
  }

  @Test
  public void checkEmptyText_LongPressTrue_Does_Not_Update_When_KeyDown_PreventDefault() {
    // Setup
    onChangeTriggered = false;
    onBlurTriggered = false;
    keyDownCount = 0;
    keyUpCount = 0;
    String initialText = "1234";

    final TextBox tb = new TextBox();
    tb.setText(initialText);

    tb.addChangeHandler(new ChangeHandler() {

      public void onChange(ChangeEvent event) {
        onChangeTriggered = true;
      }
    });

    tb.addBlurHandler(new BlurHandler() {

      public void onBlur(BlurEvent event) {
        onBlurTriggered = true;
      }
    });

    tb.addKeyPressHandler(new KeyPressHandler() {

      public void onKeyPress(KeyPressEvent event) {
        Assert.fail("no keyPress event should be triggered when pressing backspace button");
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        Assert.assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyUpCount++;
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        Assert.assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyDownCount++;

        event.preventDefault();
      }
    });

    // Test
    Browser.emptyText(tb, true);

    // Asserts
    // the textbox value should not be updated
    Assert.assertEquals("1234", tb.getText());
    Assert.assertEquals(4, keyDownCount);
    Assert.assertEquals(1, keyUpCount);
    Assert.assertTrue(onBlurTriggered);
    Assert.assertFalse(onChangeTriggered);
  }

  @Test
  public void checkFillText() {
    // Setup
    onChangeTriggered = false;
    onBlurTriggered = false;
    String textToFill = "some text";

    final List<Character> keyUpChars = new ArrayList<Character>();
    final List<Character> keyDownChars = new ArrayList<Character>();
    final List<Character> keyPressChars = new ArrayList<Character>();

    final TextBox tb = new TextBox();

    tb.addChangeHandler(new ChangeHandler() {

      public void onChange(ChangeEvent event) {
        onChangeTriggered = true;
      }
    });

    tb.addBlurHandler(new BlurHandler() {

      public void onBlur(BlurEvent event) {
        onBlurTriggered = true;
      }
    });

    tb.addKeyPressHandler(new KeyPressHandler() {

      public void onKeyPress(KeyPressEvent event) {
        // Assert that onKeyPress is triggered before onKeyUp and after
        // onKeyDown
        Assert.assertEquals(keyPressChars.size(), keyUpChars.size());
        Assert.assertEquals(keyPressChars.size() + 1, keyDownChars.size());

        keyPressChars.add(event.getCharCode());
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        // Assert that onKeyUp is triggered after onKeyDown and onKeyPress
        Assert.assertEquals(keyUpChars.size() + 1, keyDownChars.size());
        Assert.assertEquals(keyUpChars.size() + 1, keyPressChars.size());

        keyUpChars.add((char) event.getNativeKeyCode());
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        // Assert that onKeyDown is triggered before onKeyPress and onKeyUp
        Assert.assertEquals(keyDownChars.size(), keyPressChars.size());
        Assert.assertEquals(keyDownChars.size(), keyUpChars.size());

        keyDownChars.add((char) event.getNativeKeyCode());
      }
    });

    // Test
    Browser.fillText(tb, textToFill);

    // Asserts
    Assert.assertEquals(textToFill, tb.getText());
    Assert.assertEquals(textToFill, tb.getValue());
    assertTextFilledCorrectly(textToFill, keyDownChars);
    assertTextFilledCorrectly(textToFill, keyPressChars);
    assertTextFilledCorrectly(textToFill, keyUpChars);
    Assert.assertTrue(onBlurTriggered);
    Assert.assertTrue(onChangeTriggered);
  }

  @Test
  public void checkFillText_Does_Not_Update_When_KeyDown_PreventDefault() {
    // Setup
    onChangeTriggered = false;
    onBlurTriggered = false;
    String initialText = "intial text";
    String textToFill = "some text which will not be filled";

    final List<Character> keyUpChars = new ArrayList<Character>();
    final List<Character> keyDownChars = new ArrayList<Character>();
    final List<Character> keyPressChars = new ArrayList<Character>();

    final TextBox tb = new TextBox();
    tb.setText(initialText);

    tb.addChangeHandler(new ChangeHandler() {

      public void onChange(ChangeEvent event) {
        onChangeTriggered = true;
      }
    });

    tb.addBlurHandler(new BlurHandler() {

      public void onBlur(BlurEvent event) {
        onBlurTriggered = true;
      }
    });

    tb.addKeyPressHandler(new KeyPressHandler() {

      public void onKeyPress(KeyPressEvent event) {
        // Assert that onKeyPress is triggered before onKeyUp and after
        // onKeyDown
        Assert.assertEquals(keyPressChars.size(), keyUpChars.size());
        Assert.assertEquals(keyPressChars.size() + 1, keyDownChars.size());

        keyPressChars.add(event.getCharCode());
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        // Assert that onKeyUp is triggered after onKeyDown and onKeyPress
        Assert.assertEquals(keyUpChars.size() + 1, keyDownChars.size());
        Assert.assertEquals(keyUpChars.size() + 1, keyPressChars.size());

        keyUpChars.add((char) event.getNativeKeyCode());
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        // Assert that onKeyDown is triggered before onKeyPress and onKeyUp
        Assert.assertEquals(keyDownChars.size(), keyPressChars.size());
        Assert.assertEquals(keyDownChars.size(), keyUpChars.size());

        keyDownChars.add((char) event.getNativeKeyCode());

        // prevent the keydown event : the textbox value should not be updated
        event.preventDefault();
      }
    });

    // Test
    Browser.fillText(tb, textToFill);

    // Asserts
    // the textbox value should not be updated
    Assert.assertEquals(initialText, tb.getText());
    Assert.assertEquals(initialText, tb.getValue());
    Assert.assertFalse(onChangeTriggered);

    assertTextFilledCorrectly(textToFill, keyDownChars);
    assertTextFilledCorrectly(textToFill, keyPressChars);
    assertTextFilledCorrectly(textToFill, keyUpChars);
    Assert.assertTrue(onBlurTriggered);
  }

  @Test
  public void checkFillText_Does_Not_Update_When_KeyPress_PreventDefault() {
    // Setup
    onChangeTriggered = false;
    onBlurTriggered = false;
    String initialText = "intial text";
    String textToFill = "some text which will not be filled";

    final List<Character> keyUpChars = new ArrayList<Character>();
    final List<Character> keyDownChars = new ArrayList<Character>();
    final List<Character> keyPressChars = new ArrayList<Character>();

    final TextBox tb = new TextBox();
    tb.setText(initialText);

    tb.addChangeHandler(new ChangeHandler() {

      public void onChange(ChangeEvent event) {
        onChangeTriggered = true;
      }
    });

    tb.addBlurHandler(new BlurHandler() {

      public void onBlur(BlurEvent event) {
        onBlurTriggered = true;
      }
    });

    tb.addKeyPressHandler(new KeyPressHandler() {

      public void onKeyPress(KeyPressEvent event) {
        // Assert that onKeyPress is triggered before onKeyUp and after
        // onKeyDown
        Assert.assertEquals(keyPressChars.size(), keyUpChars.size());
        Assert.assertEquals(keyPressChars.size() + 1, keyDownChars.size());

        keyPressChars.add(event.getCharCode());

        // prevent the keyPress event : the textbox value should not be updated
        event.preventDefault();
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        // Assert that onKeyUp is triggered after onKeyDown and onKeyPress
        Assert.assertEquals(keyUpChars.size() + 1, keyDownChars.size());
        Assert.assertEquals(keyUpChars.size() + 1, keyPressChars.size());

        keyUpChars.add((char) event.getNativeKeyCode());
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        // Assert that onKeyDown is triggered before onKeyPress and onKeyUp
        Assert.assertEquals(keyDownChars.size(), keyPressChars.size());
        Assert.assertEquals(keyDownChars.size(), keyUpChars.size());

        keyDownChars.add((char) event.getNativeKeyCode());
      }
    });

    // Test
    Browser.fillText(tb, textToFill);

    // Asserts
    // the textbox value should not be updated
    Assert.assertEquals(initialText, tb.getText());
    Assert.assertEquals(initialText, tb.getValue());
    Assert.assertFalse(onChangeTriggered);

    assertTextFilledCorrectly(textToFill, keyDownChars);
    assertTextFilledCorrectly(textToFill, keyPressChars);
    assertTextFilledCorrectly(textToFill, keyUpChars);
    Assert.assertTrue(onBlurTriggered);
  }

  @Test
  public void checkFillText_EmptyShouldThrowsAnError() {
    // Setup
    final TextBox tb = new TextBox();
    tb.setText("test");

    // Test
    try {
      Browser.fillText(tb, "");
      Assert.fail();
    } catch (Exception e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
      Assert.assertEquals(
          "Cannot fill a null or empty text. If you intent to remove some text, use 'Browser.emptyText(..)' instead",
          e.getMessage());
    }
  }

  @Test
  public void checkFillText_NullShouldThrowsAnError() {
    // Setup
    final TextBox tb = new TextBox();
    tb.setText("test");

    // Test
    try {
      Browser.fillText(tb, null);
      Assert.fail();
    } catch (Exception e) {
      Assert.assertEquals(IllegalArgumentException.class, e.getClass());
      Assert.assertEquals(
          "Cannot fill a null or empty text. If you intent to remove some text, use 'Browser.emptyText(..)' instead",
          e.getMessage());
    }
  }

  @Test
  public void checkFillText_Still_Update_When_KeyUp_PreventDefault() {
    // Setup
    onChangeTriggered = false;
    onBlurTriggered = false;
    String initialText = "intial text";
    String textToFill = "some text which will not be filled";

    final List<Character> keyUpChars = new ArrayList<Character>();
    final List<Character> keyDownChars = new ArrayList<Character>();
    final List<Character> keyPressChars = new ArrayList<Character>();

    final TextBox tb = new TextBox();
    tb.setText(initialText);

    tb.addChangeHandler(new ChangeHandler() {

      public void onChange(ChangeEvent event) {
        onChangeTriggered = true;
      }
    });

    tb.addBlurHandler(new BlurHandler() {

      public void onBlur(BlurEvent event) {
        onBlurTriggered = true;
      }
    });

    tb.addKeyPressHandler(new KeyPressHandler() {

      public void onKeyPress(KeyPressEvent event) {
        // Assert that onKeyPress is triggered before onKeyUp and after
        // onKeyDown
        Assert.assertEquals(keyPressChars.size(), keyUpChars.size());
        Assert.assertEquals(keyPressChars.size() + 1, keyDownChars.size());

        keyPressChars.add(event.getCharCode());
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        // Assert that onKeyUp is triggered after onKeyDown and onKeyPress
        Assert.assertEquals(keyUpChars.size() + 1, keyDownChars.size());
        Assert.assertEquals(keyUpChars.size() + 1, keyPressChars.size());

        keyUpChars.add((char) event.getNativeKeyCode());

        // prevent the keyUp event : the textbox value should be updated
        event.preventDefault();
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        // Assert that onKeyDown is triggered before onKeyPress and onKeyUp
        Assert.assertEquals(keyDownChars.size(), keyPressChars.size());
        Assert.assertEquals(keyDownChars.size(), keyUpChars.size());

        keyDownChars.add((char) event.getNativeKeyCode());
      }
    });

    // Test
    Browser.fillText(tb, textToFill);

    // Asserts
    // the textbox value should be updated
    Assert.assertEquals(textToFill, tb.getText());
    Assert.assertEquals(textToFill, tb.getValue());
    Assert.assertTrue(onChangeTriggered);

    assertTextFilledCorrectly(textToFill, keyDownChars);
    assertTextFilledCorrectly(textToFill, keyPressChars);
    assertTextFilledCorrectly(textToFill, keyUpChars);
    Assert.assertTrue(onBlurTriggered);
  }

  @Test
  public void checkFocusEvent() {
    tested = false;;
    b.addFocusHandler(new FocusHandler() {

      public void onFocus(FocusEvent event) {
        tested = !tested;

        Assert.assertEquals(b.getElement(),
            event.getNativeEvent().getEventTarget());
        Assert.assertNull(event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // simule the event
    Browser.focus(b);

    Assert.assertTrue("onFocus event was not triggered", tested);
  }

  @Test
  public void checkKeyDownEvent() {
    tested = false;;
    b.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          tested = !tested;
        }

        Assert.assertEquals(b.getElement(),
            event.getNativeEvent().getEventTarget());
        Assert.assertNull(event.getNativeEvent().getRelatedEventTarget());
      }
    });

    // Test
    Browser.keyDown(b, KeyCodes.KEY_ESCAPE);
    // Assert
    Assert.assertFalse("onKeyDown event should not be triggered", tested);

    // Test 2
    Browser.keyDown(b, KeyCodes.KEY_ENTER);
    // Assert 2
    Assert.assertTrue("onKeyDown event was not triggered", tested);
  }

  @Test
  public void checkKeyPressEvent() {
    tested = false;;
    b.addKeyPressHandler(new KeyPressHandler() {

      public void onKeyPress(KeyPressEvent event) {
        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
          tested = !tested;
        }

        Assert.assertEquals(b.getElement(),
            event.getNativeEvent().getEventTarget());
        Assert.assertNull(event.getNativeEvent().getRelatedEventTarget());
      }
    });

    // Test
    Browser.keyDown(b, KeyCodes.KEY_ESCAPE);
    // Assert
    Assert.assertFalse("onKeyPress event should not be triggered", tested);

    // Test 2
    Browser.keyPress(b, KeyCodes.KEY_ENTER);
    // Assert 2
    Assert.assertTrue("onKeyPress event was not triggered", tested);
  }

  @Test
  public void checkKeyUpEvent() {
    tested = false;;
    b.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          tested = !tested;
        }

        Assert.assertEquals(b.getElement(),
            event.getNativeEvent().getEventTarget());
        Assert.assertNull(event.getNativeEvent().getRelatedEventTarget());
      }
    });

    // Test
    Browser.keyDown(b, KeyCodes.KEY_ESCAPE);
    // Assert
    Assert.assertFalse("onKeyUp event should not be triggered", tested);

    // Test 2
    Browser.keyUp(b, KeyCodes.KEY_ENTER);
    // Assert 2
    Assert.assertTrue("onKeyUp event was not triggered", tested);
  }

  @Test
  public void checkMouseDownEvent() {
    tested = false;;
    b.addMouseDownHandler(new MouseDownHandler() {

      public void onMouseDown(MouseDownEvent event) {
        tested = !tested;

        Assert.assertEquals(b.getElement(),
            event.getNativeEvent().getEventTarget());
        Assert.assertNull(event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // simule the event
    Browser.mouseDown(b);

    Assert.assertTrue("onMouseDown event was not triggered", tested);
  }

  @Test
  public void checkMouseMoveEvent() {
    tested = false;;
    b.addMouseMoveHandler(new MouseMoveHandler() {

      public void onMouseMove(MouseMoveEvent event) {
        tested = !tested;

        Assert.assertEquals(b.getElement(),
            event.getNativeEvent().getEventTarget());
        Assert.assertNull(event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // simule the event
    Browser.mouseMove(b);

    Assert.assertTrue("onMouseMove event was not triggered", tested);
  }

  @Test
  public void checkMouseOutEvent() {
    tested = false;

    b.addMouseOutHandler(new MouseOutHandler() {

      public void onMouseOut(MouseOutEvent event) {
        tested = !tested;

        Assert.assertEquals(b.getElement(),
            event.getNativeEvent().getEventTarget());
        Assert.assertEquals(b.getParent().getElement(),
            event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // simule the event
    Browser.mouseOut(b);

    Assert.assertTrue("onMouseOut event was not triggered", tested);
  }

  @Test
  public void checkMouseOverEvent() {
    tested = false;;
    b.addMouseOverHandler(new MouseOverHandler() {

      public void onMouseOver(MouseOverEvent event) {
        tested = !tested;

        Assert.assertEquals(b.getElement(),
            event.getNativeEvent().getEventTarget());
        Assert.assertEquals(b.getParent().getElement(),
            event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // simule the event
    Browser.mouseOver(b);

    Assert.assertTrue("onMouseOver event was not triggered", tested);
  }

  @Test
  public void checkMouseUpEvent() {
    tested = false;;
    b.addMouseUpHandler(new MouseUpHandler() {

      public void onMouseUp(MouseUpEvent event) {
        tested = !tested;

        Assert.assertEquals(b.getElement(),
            event.getNativeEvent().getEventTarget());
        Assert.assertNull(event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // simule the event
    Browser.mouseUp(b);

    Assert.assertTrue("onMouseUp event was not triggered", tested);
  }

  @Test
  public void checkMouseWheelEvent() {
    tested = false;;
    b.addMouseWheelHandler(new MouseWheelHandler() {

      public void onMouseWheel(MouseWheelEvent event) {
        tested = !tested;

        Assert.assertEquals(b.getElement(),
            event.getNativeEvent().getEventTarget());
        Assert.assertNull(event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // simule the event
    Browser.mouseWheel(b);

    Assert.assertTrue("onMouseWheel event was not triggered", tested);
  }

  @Test
  public void checkRemoveText() {
    // Setup
    onChangeTriggered = false;
    onBlurTriggered = false;
    keyDownCount = 0;
    keyUpCount = 0;
    String initialText = "1234";

    final TextBox tb = new TextBox();
    tb.setText(initialText);

    tb.addChangeHandler(new ChangeHandler() {

      public void onChange(ChangeEvent event) {
        onChangeTriggered = true;
      }
    });

    tb.addBlurHandler(new BlurHandler() {

      public void onBlur(BlurEvent event) {
        onBlurTriggered = true;
      }
    });

    tb.addKeyPressHandler(new KeyPressHandler() {

      public void onKeyPress(KeyPressEvent event) {
        Assert.fail("no keyPress event should be triggered when pressing backspace button");
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        Assert.assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyUpCount++;
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        Assert.assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyDownCount++;
      }
    });

    // Test
    Browser.removeText(tb, 2);

    // Asserts
    // the textbox value should be updated
    Assert.assertEquals("12", tb.getText());
    Assert.assertEquals(2, keyDownCount);
    Assert.assertEquals(2, keyUpCount);
    Assert.assertTrue(onBlurTriggered);
    Assert.assertTrue(onChangeTriggered);
  }

  @Test
  public void checkRemoveText_Does_Not_Update_When_KeyDown_PreventDefault() {
    // Setup
    onChangeTriggered = false;
    onBlurTriggered = false;
    keyDownCount = 0;
    keyUpCount = 0;
    String initialText = "1234";

    final TextBox tb = new TextBox();
    tb.setText(initialText);

    tb.addChangeHandler(new ChangeHandler() {

      public void onChange(ChangeEvent event) {
        onChangeTriggered = true;
      }
    });

    tb.addBlurHandler(new BlurHandler() {

      public void onBlur(BlurEvent event) {
        onBlurTriggered = true;
      }
    });

    tb.addKeyPressHandler(new KeyPressHandler() {

      public void onKeyPress(KeyPressEvent event) {
        Assert.fail("no keyPress event should be triggered when pressing backspace button");
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        Assert.assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyUpCount++;
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        Assert.assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyDownCount++;

        event.preventDefault();
      }
    });

    // Test
    Browser.removeText(tb, 2);

    // Asserts
    // the textbox value should be updated
    Assert.assertEquals("1234", tb.getText());
    Assert.assertEquals(2, keyDownCount);
    Assert.assertEquals(2, keyUpCount);
    Assert.assertTrue(onBlurTriggered);
    Assert.assertFalse(onChangeTriggered);
  }

  @Before
  public void setupBrowserTest() {
    b = new Button();
    RootPanel.get().add(b);
  }

  private void assertTextFilledCorrectly(String filledText,
      List<Character> typedChars) {

    Assert.assertEquals(filledText.length(), typedChars.size());

    for (int i = 0; i < filledText.length(); i++) {
      Assert.assertEquals((Object) filledText.charAt(i), typedChars.get(i));
    }

  }
}
