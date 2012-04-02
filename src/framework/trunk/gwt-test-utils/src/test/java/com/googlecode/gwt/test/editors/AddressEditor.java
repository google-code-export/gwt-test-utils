package com.googlecode.gwt.test.editors;

import java.util.Arrays;
import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;

public class AddressEditor extends Composite implements Editor<Address> {

  interface Binder extends UiBinder<Widget, AddressEditor> {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  @UiField
  TextBox city;
  @UiField(provided = true)
  ValueListBox<String> state;
  @UiField
  TextBox street;
  @UiField
  TextBox zip;

  public AddressEditor() {

    state = new ValueListBox<String>(new AbstractRenderer<String>() {

      public String render(String object) {
        return object;
      }

    });
    state.setAcceptableValues(buildCollection("Austria", "France", "Germany"));

    initWidget(BINDER.createAndBindUi(this));

  }

  private Collection<String> buildCollection(String... string) {
    return Arrays.asList(string);
  }
}
