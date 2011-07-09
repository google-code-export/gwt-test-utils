package com.octo.gwt.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.octo.gwt.test.internal.EventDispatcher;
import com.octo.gwt.test.internal.EventDispatcher.BrowserErrorHandler;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

public class CellListTest extends GwtTestTest {

  private static final List<String> DAYS = Arrays.asList("Sunday", "Monday",
      "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");

  private CellList<String> cellList;

  @Before
  public void beforeCellListTest() {
    // Create a cell to render each value.
    TextCell textCell = new TextCell();

    // Create the CellList that uses the cell.
    cellList = new CellList<String>(textCell);

    // Set the total row count. This isn't strictly necessary, but it affects
    // paging calculations, so its good habit to keep the row count up to date.
    cellList.setRowCount(DAYS.size(), true);

    // Push the data into the widget.
    cellList.setRowData(0, DAYS);

    cellList.setVisibleRange(0, 5);

    // Add it to the root panel.
    RootPanel.get().add(cellList);

    // Pre-Assert
    Assert.assertEquals(DAYS.size(), cellList.getRowCount());
    Assert.assertEquals(5, cellList.getVisibleItemCount());
    Assert.assertEquals("Thursday",
        cellList.getVisibleItem(cellList.getVisibleItemCount() - 1));
  }

  @Test
  public void selectWithClick() {
    // Arrange
    final StringBuilder sb = new StringBuilder();

    final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
    cellList.setSelectionModel(selectionModel);
    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
      public void onSelectionChange(SelectionChangeEvent event) {
        String selected = selectionModel.getSelectedObject();
        if (selected != null) {
          sb.append("selected : " + selected);
        }
      }
    });

    // Act 1
    Browser.click(cellList, "Wednesday");

    // Assert 1
    Assert.assertEquals("selected : Wednesday", sb.toString());
    Assert.assertTrue(cellList.getSelectionModel().isSelected("Wednesday"));

    // Act 2 : deselect

    Browser.click(cellList, "Wednesday");

    // Assert 2
    Assert.assertEquals("selected : Wednesday", sb.toString());
    Assert.assertFalse(cellList.getSelectionModel().isSelected("Wednesday"));
  }

  @Test
  public void selectWithClick_OutOfRange() {
    // Arrange
    final StringBuilder sb = new StringBuilder();

    // Set a new BrowserErrorHandler in Browser
    BrowserErrorHandler mock = new BrowserErrorHandler() {

      public void onError(String errorMessage) {
        sb.append(errorMessage);
      }
    };

    GwtReflectionUtils.setStaticField(Browser.class, "DISPATCHER",
        EventDispatcher.newInstance(mock));

    final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
    cellList.setSelectionModel(selectionModel);
    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
      public void onSelectionChange(SelectionChangeEvent event) {
        String selected = selectionModel.getSelectedObject();
        if (selected != null) {
          sb.append("selected : " + selected);
        }
      }
    });

    Browser.click(cellList, "Saturday");

    // Assert : no trigger because "Saturday" is not visible
    Assert.assertEquals(
        "the item to click is now visible in the targeted CellList instance",
        sb.toString());
    Assert.assertFalse(cellList.getSelectionModel().isSelected("Saturday"));
  }
}
