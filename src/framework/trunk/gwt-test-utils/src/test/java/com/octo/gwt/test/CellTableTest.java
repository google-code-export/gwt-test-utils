package com.octo.gwt.test;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.octo.gwt.test.utils.events.Browser;

public class CellTableTest extends GwtTestTest {

  // A simple data type that represents a contact.
  private static class Contact {
    private final String address;
    private final String name;

    public Contact(String name, String address) {
      this.name = name;
      this.address = address;
    }
  }

  // The list of data to display.
  private static List<Contact> CONTACTS = Arrays.asList(new Contact("Gael",
      "666 Evil"), new Contact("John", "123 Fourth Road"), new Contact("Mary",
      "222 Lancer Lane"));

  private CellTable<Contact> table;

  @Before
  public void beforeCellTableTest() {
    // Create a CellTable.
    table = new CellTable<Contact>();

    // Create name column.
    TextColumn<Contact> nameColumn = new TextColumn<Contact>() {
      @Override
      public String getValue(Contact contact) {
        return contact.name;
      }
    };

    // Create address column.
    TextColumn<Contact> addressColumn = new TextColumn<Contact>() {
      @Override
      public String getValue(Contact contact) {
        return contact.address;
      }
    };

    // Add the columns.
    table.addColumn(nameColumn, "Name");
    table.addColumn(addressColumn, "Address");

    // Set the total row count. This isn't strictly necessary, but it affects
    // paging calculations, so its good habit to keep the row count up to
    // date.
    table.setRowCount(CONTACTS.size(), true);

    // Push the data into the widget.
    table.setRowData(0, CONTACTS);

    table.setVisibleRange(0, 2);

    // Pre-Assert
    Assert.assertEquals(3, table.getRowCount());
    Assert.assertEquals(2, table.getVisibleItemCount());
  }

  @Test
  public void checkClick() {
    // Arrange
    final StringBuilder sb = new StringBuilder();

    // Add a selection model to handle user selection.
    final SingleSelectionModel<Contact> selectionModel = new SingleSelectionModel<Contact>();
    table.setSelectionModel(selectionModel);
    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
      public void onSelectionChange(SelectionChangeEvent event) {
        Contact selected = selectionModel.getSelectedObject();
        if (selected != null) {
          sb.append(selected.name).append(" : ").append(selected.address);
        }
      }
    });

    FinallyCommandTrigger.triggerCommands();

    // Act 1
    Browser.click(table, CONTACTS.get(0));

    // Assert 1
    Assert.assertEquals("Gael : 666 Evil", sb.toString());
    Assert.assertTrue(table.getSelectionModel().isSelected(CONTACTS.get(0)));

    // Act 2 : deselect
    Browser.click(table, CONTACTS.get(0));

    // Assert 2
    Assert.assertEquals("Gael : 666 Evil", sb.toString());
    Assert.assertFalse(table.getSelectionModel().isSelected(CONTACTS.get(0)));

  }
}
