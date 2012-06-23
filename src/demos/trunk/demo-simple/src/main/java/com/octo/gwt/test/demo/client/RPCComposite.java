package com.octo.gwt.test.demo.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.octo.gwt.test.demo.beans.FooBean;

public class RPCComposite extends Composite {

  private final Button button;
  private final Label label;

  private final MyServiceAsync service = GWT.create(MyService.class);

  public RPCComposite() {
    button = new Button();
    button.setText("Create a FooBean");

    // We can add style names
    button.addStyleName("pc-template-btn");

    // the wrapper panel
    FlowPanel panel = new FlowPanel();
    panel.getElement().setClassName("composite");
    panel.add(button);

    label = new Label();
    label.getElement().setId("labelId");
    panel.add(label);

    button.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        // Create an asynchronous callback to handle the result.
        AsyncCallback<FooBean> callback = new AsyncCallback<FooBean>() {

          public void onFailure(Throwable caught) {
            // Show the RPC error message to the user
            label.setText("Failure : " + caught.getMessage());
          }

          public void onSuccess(FooBean result) {
            label.setText("Bean \"" + result.getName() + "\" has been created");
          }
        };

        // Make the call. Control flow will continue immediately and later
        // 'callback' will be invoked when the RPC completes.
        service.createBean("OCTO", callback);
      }
    });

    // All composites must call initWidget() in their constructors.
    initWidget(panel);
  }

}
