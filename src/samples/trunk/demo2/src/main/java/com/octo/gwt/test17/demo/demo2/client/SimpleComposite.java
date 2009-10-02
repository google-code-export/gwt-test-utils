package com.octo.gwt.test17.demo.demo2.client;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SimpleComposite extends Composite {

	private Label label;
	private Image img;

	public SimpleComposite() {
		
		label = new Label();

		img = new Image("img/logo.PNG");
		img.getElement().setId("pc-template-img");

		// The wrapper panel
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setWidth("100%");
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		vPanel.add(img);
		vPanel.add(label);

		// All composites must call initWidget() in their constructors.
		initWidget(vPanel);

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.setWidth("100%");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

		// Add mouse move handler to the image
		img.addMouseMoveHandler(new MouseMoveHandler() {

			public void onMouseMove(MouseMoveEvent event) {
				label.setText("mouse moved on picture !");

			}
		});

	}

}
