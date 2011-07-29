package com.octo.gwt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

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
import com.octo.gwt.test.utils.events.EventBuilder;

public class BrowserTest extends GwtTestTest {

  private Button b;
  private Cell clickedCell;
  private int keyDownCount;
  private int keyUpCount;
  private boolean onBlurTriggered;
  private boolean onChangeTriggered;

  private boolean tested;

  @Before
  public void beforeBrowserTest() {
    b = new Button();
    RootPanel.get().add(b);
  }

  @Test
  public void blur() {
    // Arrange
    tested = false;;
    b.addBlurHandler(new BlurHandler() {

      public void onBlur(BlurEvent event) {
        tested = !tested;

        assertEquals(b.getElement(), event.getNativeEvent().getEventTarget());
        assertNull(event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // Act
    Browser.blur(b);

    // Assert
    assertTrue("onBlur event was not triggered", tested);
  }

  @Test
  public void click() {
    // Arrange
    tested = false;
    b.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        tested = !tested;

        assertEquals(b.getElement(), event.getNativeEvent().getEventTarget());
        assertNull(event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // Act
    Browser.click(b);

    // Assert
    assertTrue("onClick event was not triggered", tested);
  }

  @Test
  public void click_ComplexPanel() {
    // Arrange
    tested = false;
    final Anchor a = new Anchor();
    final ComplexPanel panel = new StackPanel() {

      @Override
      public void onBrowserEvent(com.google.gwt.user.client.Event event) {
        super.onBrowserEvent(event);

        if (DOM.eventGetType(event) == Event.ONCLICK) {
          tested = !tested;
          assertNull(event.getRelatedEventTarget());
        }

        assertEquals(a.getElement(), event.getEventTarget());
      };
    };

    panel.add(a);

    // Act
    Browser.click(panel, 0);

    // Assert
    assertTrue("onClick event was not triggered", tested);
  }

  @Test
  public void click_Grid() {
    // Arrange
    final Grid g = new Grid(2, 2);
    final Anchor a = new Anchor();
    g.setWidget(1, 1, a);

    g.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        clickedCell = g.getCellForEvent(event);

        assertEquals(a.getElement(), event.getNativeEvent().getEventTarget());
        assertNull(event.getNativeEvent().getRelatedEventTarget());
      }
    });

    // Act
    Browser.click(g, 1, 1);

    // Assert
    assertEquals(1, clickedCell.getRowIndex());
    assertEquals(1, clickedCell.getCellIndex());
  }

  @Test
  public void click_SuggestBox() {
    // Arrange
    MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
    oracle.add("suggestion 1");
    oracle.add("suggestion 2");
    SuggestBox box = new SuggestBox(oracle);

    // Act
    Browser.fillText(box, "sug");
    Browser.click(box, 1);

    // Assert
    assertEquals("suggestion 2", box.getText());
  }

  @Test
  public void click_WithPosition() {
    // Arrange
    tested = false;
    b.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        tested = !tested;

        // Assert
        assertEquals(b.getElement(), event.getNativeEvent().getEventTarget());
        assertNull(event.getNativeEvent().getRelatedEventTarget());

        // check positions
        assertEquals(1, event.getNativeEvent().getClientX());
        assertEquals(2, event.getNativeEvent().getClientY());
        assertEquals(3, event.getNativeEvent().getScreenX());
        assertEquals(4, event.getNativeEvent().getScreenY());
      }

    });

    Event clickEvent = EventBuilder.create(Event.ONCLICK).setMouseX(1).setMouseY(
        2).setMouseScreenX(3).setMouseScreenY(4).build();

    // Act
    Browser.dispatchEvent(b, clickEvent);

    // Assert
    assertTrue("onClick event was not triggered", tested);
  }

  @Test
  public void emptyText_LongPressFalse() {
    // Arrange
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
        fail("no keyPress event should be triggered when pressing backspace button");
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyUpCount++;
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyDownCount++;
      }
    });

    // Act
    Browser.emptyText(tb, false);

    // Assert
    // the textbox value should be updated
    assertEquals("", tb.getText());
    assertEquals(4, keyDownCount);
    assertEquals(4, keyUpCount);
    assertTrue(onBlurTriggered);
    assertTrue(onChangeTriggered);
  }

  @Test
  public void emptyText_LongPressTrue() {
    // Arrange
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
        fail("no keyPress event should be triggered when pressing backspace button");
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyUpCount++;
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyDownCount++;
      }
    });

    // Act
    Browser.emptyText(tb, true);

    // Assert
    // the textbox value should be updated
    assertEquals("", tb.getText());
    assertEquals(4, keyDownCount);
    assertEquals(1, keyUpCount);
    assertTrue(onBlurTriggered);
    assertTrue(onChangeTriggered);
  }

  @Test
  public void emptyText_LongPressTrue_Does_Not_Update_When_KeyDown_PreventDefault() {
    // Arrange
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
        fail("no keyPress event should be triggered when pressing backspace button");
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyUpCount++;
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyDownCount++;

        event.preventDefault();
      }
    });

    // Act
    Browser.emptyText(tb, true);

    // Assert
    // the textbox value should not be updated
    assertEquals("1234", tb.getText());
    assertEquals(4, keyDownCount);
    assertEquals(1, keyUpCount);
    assertTrue(onBlurTriggered);
    assertFalse(onChangeTriggered);
  }

  @Test
  public void fillText() {
    // Arrange
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
        assertEquals(keyPressChars.size(), keyUpChars.size());
        assertEquals(keyPressChars.size() + 1, keyDownChars.size());

        keyPressChars.add(event.getCharCode());
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        // Assert that onKeyUp is triggered after onKeyDown and onKeyPress
        assertEquals(keyUpChars.size() + 1, keyDownChars.size());
        assertEquals(keyUpChars.size() + 1, keyPressChars.size());

        keyUpChars.add((char) event.getNativeKeyCode());
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        // Assert that onKeyDown is triggered before onKeyPress and onKeyUp
        assertEquals(keyDownChars.size(), keyPressChars.size());
        assertEquals(keyDownChars.size(), keyUpChars.size());

        keyDownChars.add((char) event.getNativeKeyCode());
      }
    });

    // Act
    Browser.fillText(tb, textToFill);

    // Assert
    assertEquals(textToFill, tb.getText());
    assertEquals(textToFill, tb.getValue());
    assertTextFilledCorrectly(textToFill, keyDownChars);
    assertTextFilledCorrectly(textToFill, keyPressChars);
    assertTextFilledCorrectly(textToFill, keyUpChars);
    assertTrue(onBlurTriggered);
    assertTrue(onChangeTriggered);
  }

  @Test
  public void fillText_Does_Not_Update_When_KeyDown_PreventDefault() {
    // Arrange
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
        assertEquals(keyPressChars.size(), keyUpChars.size());
        assertEquals(keyPressChars.size() + 1, keyDownChars.size());

        keyPressChars.add(event.getCharCode());
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        // Assert that onKeyUp is triggered after onKeyDown and onKeyPress
        assertEquals(keyUpChars.size() + 1, keyDownChars.size());
        assertEquals(keyUpChars.size() + 1, keyPressChars.size());

        keyUpChars.add((char) event.getNativeKeyCode());
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        // Assert that onKeyDown is triggered before onKeyPress and onKeyUp
        assertEquals(keyDownChars.size(), keyPressChars.size());
        assertEquals(keyDownChars.size(), keyUpChars.size());

        keyDownChars.add((char) event.getNativeKeyCode());

        // prevent the keydown event : the textbox value should not be updated
        event.preventDefault();
      }
    });

    // Act
    Browser.fillText(tb, textToFill);

    // Assert
    // the textbox value should not be updated
    assertEquals(initialText, tb.getText());
    assertEquals(initialText, tb.getValue());
    assertFalse(onChangeTriggered);

    assertTextFilledCorrectly(textToFill, keyDownChars);
    assertTextFilledCorrectly(textToFill, keyPressChars);
    assertTextFilledCorrectly(textToFill, keyUpChars);
    assertTrue(onBlurTriggered);
  }

  @Test
  public void fillText_Does_Not_Update_When_KeyPress_PreventDefault() {
    // Arrange
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
        assertEquals(keyPressChars.size(), keyUpChars.size());
        assertEquals(keyPressChars.size() + 1, keyDownChars.size());

        keyPressChars.add(event.getCharCode());

        // prevent the keyPress event : the textbox value should not be updated
        event.preventDefault();
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        // Assert that onKeyUp is triggered after onKeyDown and onKeyPress
        assertEquals(keyUpChars.size() + 1, keyDownChars.size());
        assertEquals(keyUpChars.size() + 1, keyPressChars.size());

        keyUpChars.add((char) event.getNativeKeyCode());
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        // Assert that onKeyDown is triggered before onKeyPress and onKeyUp
        assertEquals(keyDownChars.size(), keyPressChars.size());
        assertEquals(keyDownChars.size(), keyUpChars.size());

        keyDownChars.add((char) event.getNativeKeyCode());
      }
    });

    // Act
    Browser.fillText(tb, textToFill);

    // Assert
    // the textbox value should not be updated
    assertEquals(initialText, tb.getText());
    assertEquals(initialText, tb.getValue());
    assertFalse(onChangeTriggered);

    assertTextFilledCorrectly(textToFill, keyDownChars);
    assertTextFilledCorrectly(textToFill, keyPressChars);
    assertTextFilledCorrectly(textToFill, keyUpChars);
    assertTrue(onBlurTriggered);
  }

  @Test
  public void fillText_EmptyShouldThrowsAnError() {
    // Arrange
    final TextBox tb = new TextBox();
    tb.setText("test");

    // Act
    try {
      Browser.fillText(tb, "");
      fail();
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
      assertEquals(
          "Cannot fill a null or empty text. If you intent to remove some text, use 'Browser.emptyText(..)' instead",
          e.getMessage());
    }
  }

  @Test
  public void fillText_NullShouldThrowsAnError() {
    // Arrange
    final TextBox tb = new TextBox();
    tb.setText("test");

    // Act
    try {
      Browser.fillText(tb, null);
      fail();
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
      assertEquals(
          "Cannot fill a null or empty text. If you intent to remove some text, use 'Browser.emptyText(..)' instead",
          e.getMessage());
    }
  }

  @Test
  public void fillText_Still_Update_When_KeyUp_PreventDefault() {
    // Arrange
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
        assertEquals(keyPressChars.size(), keyUpChars.size());
        assertEquals(keyPressChars.size() + 1, keyDownChars.size());

        keyPressChars.add(event.getCharCode());
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        // Assert that onKeyUp is triggered after onKeyDown and onKeyPress
        assertEquals(keyUpChars.size() + 1, keyDownChars.size());
        assertEquals(keyUpChars.size() + 1, keyPressChars.size());

        keyUpChars.add((char) event.getNativeKeyCode());

        // prevent the keyUp event : the textbox value should be updated
        event.preventDefault();
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        // Assert that onKeyDown is triggered before onKeyPress and onKeyUp
        assertEquals(keyDownChars.size(), keyPressChars.size());
        assertEquals(keyDownChars.size(), keyUpChars.size());

        keyDownChars.add((char) event.getNativeKeyCode());
      }
    });

    // Act
    Browser.fillText(tb, textToFill);

    // Assert
    // the textbox value should be updated
    assertEquals(textToFill, tb.getText());
    assertEquals(textToFill, tb.getValue());
    assertTrue(onChangeTriggered);

    assertTextFilledCorrectly(textToFill, keyDownChars);
    assertTextFilledCorrectly(textToFill, keyPressChars);
    assertTextFilledCorrectly(textToFill, keyUpChars);
    assertTrue(onBlurTriggered);
  }

  @Test
  public void focus() {
    // Arrange
    tested = false;;
    b.addFocusHandler(new FocusHandler() {

      public void onFocus(FocusEvent event) {
        tested = !tested;

        assertEquals(b.getElement(), event.getNativeEvent().getEventTarget());
        assertNull(event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // Act
    Browser.focus(b);

    // Assert
    assertTrue("onFocus event was not triggered", tested);
  }

  @Test
  public void keyDown() {
    // Arrange
    tested = false;;
    b.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          tested = !tested;
        }

        assertEquals(b.getElement(), event.getNativeEvent().getEventTarget());
        assertNull(event.getNativeEvent().getRelatedEventTarget());
      }
    });

    // Act 1
    Browser.keyDown(b, KeyCodes.KEY_ESCAPE);
    // Assert 1
    assertFalse("onKeyDown event should not be triggered", tested);

    // Act 2
    Browser.keyDown(b, KeyCodes.KEY_ENTER);
    // Assert 2
    assertTrue("onKeyDown event was not triggered", tested);
  }

  @Test
  public void keyPress() {
    // Arrange
    tested = false;;
    b.addKeyPressHandler(new KeyPressHandler() {

      public void onKeyPress(KeyPressEvent event) {
        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
          tested = !tested;
        }

        assertEquals(b.getElement(), event.getNativeEvent().getEventTarget());
        assertNull(event.getNativeEvent().getRelatedEventTarget());
      }
    });

    // Act 1
    Browser.keyDown(b, KeyCodes.KEY_ESCAPE);
    // Assert 1
    assertFalse("onKeyPress event should not be triggered", tested);

    // Act 2
    Browser.keyPress(b, KeyCodes.KEY_ENTER);
    // Assert 2
    assertTrue("onKeyPress event was not triggered", tested);
  }

  @Test
  public void keyUp() {
    // Arrange
    tested = false;;
    b.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          tested = !tested;
        }

        assertEquals(b.getElement(), event.getNativeEvent().getEventTarget());
        assertNull(event.getNativeEvent().getRelatedEventTarget());
      }
    });

    // Act 1
    Browser.keyDown(b, KeyCodes.KEY_ESCAPE);
    // Assert 1
    assertFalse("onKeyUp event should not be triggered", tested);

    // Act 2
    Browser.keyUp(b, KeyCodes.KEY_ENTER);
    // Assert 2
    assertTrue("onKeyUp event was not triggered", tested);
  }

  @Test
  public void mouseDown() {
    // Arrange
    tested = false;;
    b.addMouseDownHandler(new MouseDownHandler() {

      public void onMouseDown(MouseDownEvent event) {
        tested = !tested;

        assertEquals(b.getElement(), event.getNativeEvent().getEventTarget());
        assertNull(event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // Act
    Browser.mouseDown(b);

    // Assert
    assertTrue("onMouseDown event was not triggered", tested);
  }

  @Test
  public void mouseMove() {
    // Arrange
    tested = false;;
    b.addMouseMoveHandler(new MouseMoveHandler() {

      public void onMouseMove(MouseMoveEvent event) {
        tested = !tested;

        assertEquals(b.getElement(), event.getNativeEvent().getEventTarget());
        assertNull(event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // Act
    Browser.mouseMove(b);

    // Assert
    assertTrue("onMouseMove event was not triggered", tested);
  }

  @Test
  public void mouseOut() {
    // Arrange
    tested = false;

    b.addMouseOutHandler(new MouseOutHandler() {

      public void onMouseOut(MouseOutEvent event) {
        tested = !tested;

        assertEquals(b.getElement(), event.getNativeEvent().getEventTarget());
        assertEquals(b.getParent().getElement(),
            event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // Act
    Browser.mouseOut(b);

    // Assert
    assertTrue("onMouseOut event was not triggered", tested);
  }

  @Test
  public void mouseOver() {
    // Arrange
    tested = false;;
    b.addMouseOverHandler(new MouseOverHandler() {

      public void onMouseOver(MouseOverEvent event) {
        tested = !tested;

        assertEquals(b.getElement(), event.getNativeEvent().getEventTarget());
        assertEquals(b.getParent().getElement(),
            event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // Act
    Browser.mouseOver(b);

    // Assert
    assertTrue("onMouseOver event was not triggered", tested);
  }

  @Test
  public void mouseUp() {
    // Arrange
    tested = false;;
    b.addMouseUpHandler(new MouseUpHandler() {

      public void onMouseUp(MouseUpEvent event) {
        tested = !tested;

        assertEquals(b.getElement(), event.getNativeEvent().getEventTarget());
        assertNull(event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // Act
    Browser.mouseUp(b);

    // Assert
    assertTrue("onMouseUp event was not triggered", tested);
  }

  @Test
  public void mouseWheel() {
    // Arrange
    tested = false;;
    b.addMouseWheelHandler(new MouseWheelHandler() {

      public void onMouseWheel(MouseWheelEvent event) {
        tested = !tested;

        assertEquals(b.getElement(), event.getNativeEvent().getEventTarget());
        assertNull(event.getNativeEvent().getRelatedEventTarget());
      }

    });

    // Act
    Browser.mouseWheel(b);

    // Assert
    assertTrue("onMouseWheel event was not triggered", tested);
  }

  @Test
  public void removeText() {
    // Arrange
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
        fail("no keyPress event should be triggered when pressing backspace button");
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyUpCount++;
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyDownCount++;
      }
    });

    // Act
    Browser.removeText(tb, 2);

    // Assert
    // the textbox value should be updated
    assertEquals("12", tb.getText());
    assertEquals(2, keyDownCount);
    assertEquals(2, keyUpCount);
    assertTrue(onBlurTriggered);
    assertTrue(onChangeTriggered);
  }

  @Test
  public void removeText_Does_Not_Update_When_KeyDown_PreventDefault() {
    // Arrange
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
        fail("no keyPress event should be triggered when pressing backspace button");
      }
    });

    tb.addKeyUpHandler(new KeyUpHandler() {

      public void onKeyUp(KeyUpEvent event) {
        assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyUpCount++;
      }
    });

    tb.addKeyDownHandler(new KeyDownHandler() {

      public void onKeyDown(KeyDownEvent event) {
        assertEquals(KeyCodes.KEY_BACKSPACE, event.getNativeKeyCode());
        keyDownCount++;

        event.preventDefault();
      }
    });

    // Act
    Browser.removeText(tb, 2);

    // Assert
    // the textbox value should be updated
    assertEquals("1234", tb.getText());
    assertEquals(2, keyDownCount);
    assertEquals(2, keyUpCount);
    assertTrue(onBlurTriggered);
    assertFalse(onChangeTriggered);
  }

  private void assertTextFilledCorrectly(String filledText,
      List<Character> typedChars) {

    assertEquals(filledText.length(), typedChars.size());

    for (int i = 0; i < filledText.length(); i++) {
      assertEquals((Object) filledText.charAt(i), typedChars.get(i));
    }

  }
}
