package com.octo.gwt.test.uibinder;

public class UiBinderWithWidgetsChild extends UiBinderWithWidgets {

  public UiBinderWithWidgetsChild(String... names) {
    super(names);
    pushButton.setText("override by child");
  }

}
