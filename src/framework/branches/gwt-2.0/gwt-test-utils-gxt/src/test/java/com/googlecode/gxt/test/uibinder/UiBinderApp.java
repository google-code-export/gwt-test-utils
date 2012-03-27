package com.googlecode.gxt.test.uibinder;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class UiBinderApp implements EntryPoint {

  GxtUiBinderContent content;
  Button openButton;

  public void onModuleLoad() {
    Viewport viewport = new Viewport();
    viewport.setLayout(new RowLayout(Orientation.VERTICAL));

    content = new GxtUiBinderContent();

    // create some header
    ContentPanel header = new ContentPanel();
    openButton = new Button("Click Me", new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent ce) {
        content.show();
      }
    });
    header.add(openButton);

    viewport.add(header, new RowData(1, 100, new Margins(10)));
    viewport.add(content, new RowData(1, 1, new Margins(10)));

    RootPanel.get().add(viewport);
  }

}
