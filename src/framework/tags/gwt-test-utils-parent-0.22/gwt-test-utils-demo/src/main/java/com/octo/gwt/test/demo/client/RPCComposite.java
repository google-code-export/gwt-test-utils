package com.octo.gwt.test.demo.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.octo.gwt.test.demo.beans.FooBean;

public class RPCComposite extends Composite {

	private Button button;
	private Label label;

	private MyServiceAsync service = GWT.create(MyService.class);

	public RPCComposite() {
		button = new Button();
		button.setText("Create a FooBean");

		// We can add style names
		button.addStyleName("pc-template-btn");

		//the wrapper panel
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setWidth("100%");
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		vPanel.add(button);

		label = new Label();
		label.getElement().setId("labelId");
		vPanel.add(label);

		button.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// Create an asynchronous callback to handle the result.
				AsyncCallback<FooBean> callback = new AsyncCallback<FooBean>() {

					public void onSuccess(FooBean result) {
						label.setText("Bean \"" + result.getName() + "\" has been created");
					}

					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						label.setText("Failure : " + caught.getMessage());
					}
				};

				// Make the call. Control flow will continue immediately and later
				// 'callback' will be invoked when the RPC completes.
				service.createBean("OCTO", callback);
			}
		});

		// All composites must call initWidget() in their constructors.
		initWidget(vPanel);
	}

}
