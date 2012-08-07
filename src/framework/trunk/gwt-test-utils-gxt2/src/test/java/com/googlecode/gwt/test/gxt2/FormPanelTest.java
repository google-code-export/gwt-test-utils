package com.googlecode.gwt.test.gxt2;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar.Resources;
import com.google.gwt.user.client.ui.RootPanel;

public class FormPanelTest extends GwtGxtTest {

   @Test
   public void add() {
      // Arrange
      Resources resources = GWT.create(Resources.class);
      FormPanel panel = new FormPanel();

      // Act
      panel.add(new Image(resources.menuBarSubMenuIcon()));

      // Assert
      assertThat(((Image) panel.getWidget(0)).getUrl()).contains("menuBarSubMenuIcon");
   }

   @Test
   public void formWithTextField() {
      // Arrange
      Window window = new Window();
      LayoutContainer formContainer = new LayoutContainer();
      FormLayout layout = new FormLayout();
      formContainer.setLayout(layout);
      TextField<String> field = new TextField<String>();
      field.setName("test");
      field.setFieldLabel("Test");
      formContainer.add(field);
      window.add(formContainer);
      window.render(RootPanel.getBodyElement());

      // Act
      window.show();

      // Assert
      assertThat(formContainer.getElement().getInnerHTML()).isEqualTo(
               "<div role=\"presentation\" class=\"x-form-item \" tabindex=\"-1\"><label for=\"x-auto-13-input\" class=\"x-form-item-label\">Test:</label><div role=\"presentation\" class=\"x-form-element x-form-el-x-auto-13\" id=\"x-form-el-x-auto-13\"><div role=\"presentation\" class=\"x-form-field-wrap x-component\" id=\"x-auto-13\" style=\"width: 200px; \"><input type=\"text\" class=\" x-form-field x-form-text\" id=\"x-auto-13-input\" name=\"test\" tabIndex=\"0\" style=\"width: 200px; \"></input></div></div><div class=\"x-form-clear-left\" role=\"presentation\"></div></div>");
   }
}
