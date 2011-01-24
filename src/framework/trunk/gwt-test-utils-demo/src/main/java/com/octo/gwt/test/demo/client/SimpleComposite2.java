package com.octo.gwt.test.demo.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class SimpleComposite2 extends Composite {

	private Button button;
	private TextBox textBox;
	private Label label;
	private ListBox listBox;

	public SimpleComposite2() {

		button = new Button();
		button.setHTML("Go");
		button.addStyleName("pc-template-btn");

		textBox = new TextBox();
		label = new Label("this label will be updated");
		listBox = new ListBox();

		listBox.addItem("Hello");
		listBox.addItem("Good morning");
		listBox.addItem("Good bye");

		// The wrapper panel
		FlowPanel panel = new FlowPanel();
		panel.getElement().setClassName("composite");
		panel.add(listBox);
		panel.add(textBox);
		panel.add(button);
		panel.add(label);

		// All composites must call initWidget() in their constructors.
		initWidget(panel);

		// Add click handlers to the button
		button.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				label.setText(listBox.getItemText(listBox.getSelectedIndex()) + " " + textBox.getText());
			}
		});

	}

}
