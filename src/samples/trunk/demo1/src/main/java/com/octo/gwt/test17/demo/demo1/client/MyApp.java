package com.octo.gwt.test17.demo.demo1.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class MyApp implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		RootPanel.get().add(new SimpleComposite());
		RootPanel.get().add(new RPCComposite());
	}
}
