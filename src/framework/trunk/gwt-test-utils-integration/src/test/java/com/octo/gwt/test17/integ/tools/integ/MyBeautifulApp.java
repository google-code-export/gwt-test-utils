package com.octo.gwt.test17.integ.tools.integ;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class MyBeautifulApp implements EntryPoint {

	private Button b1;
	
	private Button b2;
	
	private Button b3;
	
	private Label l;
	
	public void onModuleLoad() {
		FlowPanel panel = new FlowPanel();
		b1 = new Button();
		panel.add(b1);
		b2 = new Button();
		panel.add(b2);
		l = new Label();
		l.setText("init");
		panel.add(l);
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
	}

}
