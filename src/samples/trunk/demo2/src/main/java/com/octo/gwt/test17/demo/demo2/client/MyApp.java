package com.octo.gwt.test17.demo.demo2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class MyApp implements EntryPoint {
	
	private SimpleComposite c1;
	private SimpleComposite2 c2;
	private RPCComposite c3;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		c1 = new SimpleComposite();
		RootPanel.get().add(c1);
		c2 = new SimpleComposite2();
		RootPanel.get().add(c2);
		c3 = new RPCComposite();
		RootPanel.get().add(c3);
	}
}
