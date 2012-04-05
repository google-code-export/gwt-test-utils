package com.googlecode.gwt.test.editors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.googlecode.gwt.test.GwtTestTest;
import com.googlecode.gwt.test.utils.events.Browser;

public class AddressEditorTest extends GwtTestTest {

  interface AddressDriver extends
      SimpleBeanEditorDriver<Address, AddressEditor> {
  }

  @Test
  public void editSimple() {
    // Arrange
    Address address = new Address();
    AddressEditor editor = new AddressEditor();
    AddressDriver driver = GWT.create(AddressDriver.class);
    driver.initialize(editor);
    // Start editing
    driver.edit(address);

    // Act : edit widget
    Browser.fillText(editor.city, "Paris");
    Browser.fillText(editor.street, "Avenue des Champs Elysées");
    Browser.fillText(editor.zip(), "75008");
    // TODO : API Browser for ValueListBox
    editor.state.setValue("France", true);
    driver.flush();

    // Assert
    assertEquals("Paris", address.getCity());
    assertEquals("Avenue des Champs Elysées", address.getStreet());
    assertEquals("75008", address.getZip());
    assertEquals("France", address.getState());
  }

}
