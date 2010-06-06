package com.octo.gwt.test.integ.tools.integ;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class MyBeautifulApp implements EntryPoint {

	private Button b1;

	private Button b2;

	private Button b3;

	private Button b4;
	
	private Button b5;

	private Label l;

	private TextBox t;

	private Label historyLabel;

	private TextBox invisibleTB;

	private ListBox lb;

	public void onModuleLoad() {
		FlowPanel panel = new FlowPanel();
		b1 = new Button();
		panel.add(b1);

		b2 = new Button();
		panel.add(b2);

		l = new Label();
		l.setText("init");
		panel.add(l);

		t = new TextBox();
		panel.add(t);

		historyLabel = new Label();
		panel.add(historyLabel);

		invisibleTB = new TextBox();
		invisibleTB.setVisible(false);
		panel.add(invisibleTB);

		lb = new ListBox();
		lb.addItem("lbText0");
		lb.addItem("lbText1");
		lb.addItem("lbText2");
		panel.add(lb);

		RootPanel.get().add(panel);

		b1.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				l.setText("click on b1");
			}

		});
		b2.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				l.setText("click on b2");
			}

		});
		b3 = new Button();
		b3.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				MyRemoteServiceAsync remoteServiceAsync = GWT.create(MyRemoteService.class);
				remoteServiceAsync.myMethod(l.getText(), new AsyncCallback<String>() {

					public void onSuccess(String arg0) {
						l.setText(arg0);
					}

					public void onFailure(Throwable arg0) {
						l.setText("error");
					}

				});
			}
		});

		b4 = new Button();
		panel.add(b4);
		
		b4.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				MyRemoteServiceAsync remoteServiceAsync = GWT.create(MyRemoteService.class);
				remoteServiceAsync.myMethod2(new MyCustomObject(), new AsyncCallback<MyCustomObject>() {

					public void onSuccess(MyCustomObject arg0) {
						l.setText(arg0.myField);
					}

					public void onFailure(Throwable arg0) {
						l.setText("error");
					}

				});
			}
			
		});
		
		b5 = new Button();
		panel.add(b5);
		
		b5.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				MyRemoteServiceAsync remoteServiceAsync = GWT.create(MyRemoteService.class);
				remoteServiceAsync.myMethod3(new AsyncCallback<Void>() {

					public void onSuccess(Void arg0) {
						l.setText("success");
					}

					public void onFailure(Throwable arg0) {
						l.setText("error");
					}

				});
			}
			
		});
		
		t.addChangeHandler(new ChangeHandler() {

			public void onChange(ChangeEvent event) {
				historyLabel.setText("t was filled with value \"" + t.getText() + "\"");
			}

		});

		invisibleTB.addChangeHandler(new ChangeHandler() {

			public void onChange(ChangeEvent event) {
				historyLabel.setText("invisibleTB was filled with value \"" + invisibleTB.getText() + "\"");
			}

		});

	}

}
