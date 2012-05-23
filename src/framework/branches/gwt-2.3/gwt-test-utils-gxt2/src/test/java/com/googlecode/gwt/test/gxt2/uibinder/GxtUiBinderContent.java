/**
 * 
 */
package com.googlecode.gwt.test.gxt2.uibinder;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Composite;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;

public class GxtUiBinderContent extends Composite {
  static interface Binder extends UiBinder<Component, GxtUiBinderContent> {
  }

  private static Binder UIBINDER = GWT.create(Binder.class);

  public @UiField
  Button button;
  @UiField
  ContentPanel content;

  public GxtUiBinderContent() {
    initComponent(UIBINDER.createAndBindUi(this));

    button.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent ce) {
        content.collapse();
      }
    });
    content.collapse();
  }

  @Override
  public void hide() {
    content.collapse();
  }

  public boolean isShown() {
    return !content.isCollapsed();
  }

  @Override
  public void show() {
    content.expand();
  }
}