package com.googlecode.gwt.test.uibinder;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class WidgetWithUiChild extends Composite {

   private final List<Widget> customWidgets;
   private final List<Label> labels;

   public WidgetWithUiChild() {
      initWidget(new FlowPanel());
      labels = new ArrayList<Label>();
      customWidgets = new ArrayList<Widget>();
   }

   public int customWidgetCount() {
      return customWidgets.size();
   }

   public Widget getCustomChild(int index) {
      return customWidgets.get(index);
   }

   public Label getLabel(int index) {
      FlowPanel flowPanel = (FlowPanel) this.getWidget();
      return (Label) flowPanel.getWidget(index);
   }

   public int labelCount() {
      return labels.size();
   }

   @UiChild
   void addMyUiLabel(Label l) {
      FlowPanel flowPanel = (FlowPanel) this.getWidget();
      flowPanel.add(l);
      labels.add(l);
   }

   @UiChild(tagname = "customChild", limit = 1)
   void uiCustomChild(Widget w) {
      FlowPanel flowPanel = (FlowPanel) this.getWidget();
      flowPanel.add(w);
      customWidgets.add(w);
   }

}
