package com.octo.gwt.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.SimplePanel;
import com.octo.gwt.test.utils.WidgetUtils;

public class WidgetUtilsTest extends GwtTestTest {

  @Test
  public void checkAssertListBoxDataDoNotMatchDifferentElement() {
    // Arrange
    ListBox lb = new ListBox();
    lb.addItem("item0");
    lb.addItem("item1");
    lb.addItem("iTem2");

    String[] content = new String[]{"item0", "item1", "item2"};

    // Act & Assert
    Assert.assertFalse(WidgetUtils.assertListBoxDataMatch(lb, content));
  }

  @Test
  public void checkAssertListBoxDataDoNotMatchMissingElement() {
    // Arrange
    ListBox lb = new ListBox();
    lb.addItem("item0");
    lb.addItem("item1");

    String[] content = new String[]{"item0", "item1", "item2"};

    // Act & Assert
    Assert.assertFalse(WidgetUtils.assertListBoxDataMatch(lb, content));
  }

  @Test
  public void checkAssertListBoxDataMatch() {
    // Arrange
    ListBox lb = new ListBox();
    lb.addItem("item0");
    lb.addItem("item1");
    lb.addItem("item2");

    String[] content = new String[]{"item0", "item1", "item2"};

    // Act & Assert
    Assert.assertTrue(WidgetUtils.assertListBoxDataMatch(lb, content));
  }

  @Test
  public void checkGetListBoxContentToString() {
    // Arrange
    ListBox lb = new ListBox();
    lb.addItem("item0");
    lb.addItem("item1");
    lb.addItem("item2");

    String expected = "item0 | item1 | item2 |";

    // Act
    String actual = WidgetUtils.getListBoxContentToString(lb);

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void checkListBoxIndex() {
    // Arrange
    ListBox lb = new ListBox();
    lb.addItem("item0");
    lb.addItem("item1");
    lb.addItem("item2");

    // Act & Assert
    Assert.assertEquals(0, WidgetUtils.getIndexInListBox(lb, "item0"));
    Assert.assertEquals(1, WidgetUtils.getIndexInListBox(lb, "item1"));
    Assert.assertEquals(2, WidgetUtils.getIndexInListBox(lb, "item2"));
    Assert.assertEquals(-1, WidgetUtils.getIndexInListBox(lb, "item3"));
  }

  @Test
  public void checkMenuBarItems() {
    // Arrange
    MenuBar bar = new MenuBar();

    Command cmd = new Command() {
      public void execute() {
      }

    };

    MenuItem item0 = bar.addItem("item0", cmd);
    MenuItem item1 = bar.addItem("item1", cmd);

    // Act
    List<MenuItem> items = WidgetUtils.getMenuItems(bar);

    // Assert
    Assert.assertEquals(2, items.size());
    Assert.assertEquals(item0, items.get(0));
    Assert.assertEquals(item1, items.get(1));
  }

  @Test
  public void checkNewWidgetIsNotVisibleWhenParentIsNotVisible() {
    // Arrange
    SimplePanel sp = new SimplePanel();
    sp.setVisible(false);
    Button b = new Button();
    sp.add(b);

    // Act
    Boolean isVisible = WidgetUtils.isWidgetVisible(b);

    // Assert
    Assert.assertFalse(isVisible);
  }

  @Test
  public void checkNewWidgetIsVisible() {
    // Arrange
    Button b = new Button();

    // Act
    Boolean isVisible = WidgetUtils.isWidgetVisible(b);

    // Assert
    Assert.assertTrue(isVisible);
  }

  @Test
  public void checNewWidgetIsNotVisibleWhenParentIsNotVisible() {
    MenuBar bar = new MenuBar();
    bar.setVisible(false);
    MenuItem item0 = bar.addItem("test0", (Command) null);
    item0.setVisible(true);

    // Act
    Boolean isVisible = WidgetUtils.isWidgetVisible(item0);

    // Assert
    Assert.assertFalse(isVisible);
  }

}
