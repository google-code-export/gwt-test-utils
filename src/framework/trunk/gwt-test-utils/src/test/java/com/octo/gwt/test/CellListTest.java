package com.octo.gwt.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
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

    // Add it to the root panel.
    RootPanel.get().add(cellList);
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
          sb.append("selected: " + selected);
        }
      }
    });

    // Act
    Browser.click(cellList, "Wednesday");

    // Assert
    // Assert.assertEquals("selected : Wednesday", sb.toString());
  }

}
