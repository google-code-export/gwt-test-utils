package com.octo.gwt.test.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

public interface MyClientBundle extends ClientBundle {

  public static final MyClientBundle INSTANCE = GWT.create(MyClientBundle.class);

  public ImageResource doubleShouldThrowException();

  @Source("css/testCssResource.css")
  public TestCssResource testCssResource();

  @Source("textResourceXml.xml")
  public DataResource testDataResource();

  public ImageResource testImageResource();

  public TextResource textResourceTxt();

  @Source("textResourceXml.xml")
  public TextResource textResourceXml();

}
