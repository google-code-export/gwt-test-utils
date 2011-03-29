package com.octo.gwt.test.demo.client;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class SimpleComposite extends Composite {

  private Image img;
  private Label label;

  public SimpleComposite() {

    label = new Label();

    img = new Image("img/logo.PNG");
    img.getElement().setId("pc-template-img");

    // The wrapper panel
    FlowPanel panel = new FlowPanel();
    panel.getElement().setClassName("composite");
    panel.add(img);
    panel.add(label);

    // All composites must call initWidget() in their constructors.
    initWidget(panel);

    // Add mouse move handler to the image
    img.addMouseMoveHandler(new MouseMoveHandler() {

      public void onMouseMove(MouseMoveEvent event) {
        label.setText("mouse moved on picture !");

      }
    });

  }

}
