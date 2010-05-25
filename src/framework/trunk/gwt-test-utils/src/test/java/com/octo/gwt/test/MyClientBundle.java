package com.octo.gwt.test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.TextResource;

public interface MyClientBundle extends ClientBundle {
	
	public static final MyClientBundle INSTANCE = GWT.create(MyClientBundle.class);
	
	
	@Source("cssResource.css")
	public CssResource cssResource();
	
	@Source("textResourceXml.xml")
	public TextResource textResourceXml();
	
	public TextResource textResourceTxt();
	

}
