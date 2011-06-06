package com.octo.gwt.test.resources;

import com.google.gwt.resources.client.CssResource;

public interface TestCssResource extends CssResource {

  public String testConstant();

  public String testStyle();

  public String testStyleWithHover();

  public String testStyleOnSpecificElement();

  public String testStyleOnSpecificStyle();

  public String notAvailableStyle();

}
