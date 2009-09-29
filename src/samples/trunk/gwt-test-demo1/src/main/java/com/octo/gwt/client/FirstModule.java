package com.octo.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.octo.gwt.beans.FooBean;
import com.octo.gwt.client.i18n.Messages;

public class FirstModule implements EntryPoint {

	// Create the client proxy. Note that although you are creating the
	// service interface proper, you cast the result to the asynchronous
	// version of the interface. The cast is always safe because the 
	// generated proxy implements the asynchronous interface automatically.
	//
	private FirstServiceAsync service = 
		(FirstServiceAsync) GWT.create(FirstService.class);
	
	// Create the dialog elements
	private DialogBox dialogBox = new DialogBox();
	private Button closeButton = new Button();
	private Label label = new Label();
	private ListBox listBox = new ListBox();
	private Button createButton = new Button();
	private Image img;


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		//instanciate the i18n interface implemented by GWT i18nCreator
		final Messages messages = GWT.create(Messages.class);

		img = new Image("img/logo.PNG");
		createButton.setHTML(messages.create());
		closeButton.setHTML(messages.close());

		// We can add style names
		createButton.addStyleName("pc-template-btn");

		// or we can set an id on a specific element for styling
		img.getElement().setId("pc-template-img");
		
		//add some text in the listbox
		listBox.addItem("Foo");
		listBox.addItem("Bar");
		listBox.addItem("FooBar !");

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setWidth("100%");
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		vPanel.add(img);
		vPanel.add(createButton);
		vPanel.add(listBox);

		// Add image and button to the RootPanel
		RootPanel.get().add(vPanel);

		dialogBox.setAnimationEnabled(true);
	
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.setWidth("100%");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialogVPanel.add(closeButton);

		closeButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
			
		});

		// Set the contents of the Widget
		dialogBox.setWidget(dialogVPanel);

		createButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {

				// Create an asynchronous callback to handle the result.
				AsyncCallback<FooBean> callback = new AsyncCallback<FooBean>() {

					public void onSuccess(FooBean result) {
						dialogBox.setText(messages.welcome() + " " + result.getName());
					}

					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox.setText("Failure : " + caught.getMessage());
						dialogBox.center();
					}
				};

				// Make the call. Control flow will continue immediately and later
				// 'callback' will be invoked when the RPC completes.
				service.createBean(listBox.getItemText(listBox.getSelectedIndex()), callback);
				
				dialogBox.center();
				dialogBox.show();
			}
		});
		
	}


	public String getTextLabel() {
		return label.getText();
	}

	
}
