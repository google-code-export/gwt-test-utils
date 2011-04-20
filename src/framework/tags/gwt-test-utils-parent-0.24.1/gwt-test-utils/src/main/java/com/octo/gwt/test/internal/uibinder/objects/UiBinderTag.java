package com.octo.gwt.test.internal.uibinder.objects;


public interface UiBinderTag {

  public void addTag(UiBinderTag tag);

  public void appendText(String data);

  public Object complete();
}
