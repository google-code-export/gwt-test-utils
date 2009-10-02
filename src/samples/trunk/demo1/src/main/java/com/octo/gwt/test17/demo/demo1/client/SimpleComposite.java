package com.octo.gwt.test17.demo.demo1.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SimpleComposite extends Composite {

	private Button button = new Button();
	private Label label = new Label();
	private ListBox listBox = new ListBox();
	private Image img = new Image("img/logo.PNG");

	public SimpleComposite() {

		button.setHTML("display something");

		// We can add style names
		button.addStyleName("pc-template-btn");

		// or we can set an id on a specific element for styling
		img.getElement().setId("pc-template-img");

		// Add some text in the listbox
		listBox.addItem("Foo");
		listBox.addItem("Bar");
		listBox.addItem("FooBar");

		// The wrapper panel
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setWidth("100%");
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		vPanel.add(img);
		vPanel.add(listBox);
		vPanel.add(button);
		vPanel.add(label);

		// All composites must call initWidget() in their constructors.
		initWidget(vPanel);

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.setWidth("100%");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

		// Add click handlers to the button
		button.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				label.setText("The button was clicked with value : " + listBox.getItemText(listBox.getSelectedIndex()));
			}
		});

		// Add mouse move handler to the image
		img.addMouseMoveHandler(new MouseMoveHandler() {

			public void onMouseMove(MouseMoveEvent event) {
				label.setText("mouse moved on picture !");

			}
		});

	}

}
