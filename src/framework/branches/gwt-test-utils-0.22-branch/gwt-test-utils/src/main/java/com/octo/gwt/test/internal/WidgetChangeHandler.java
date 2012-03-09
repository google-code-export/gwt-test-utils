package com.octo.gwt.test.internal;

import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public interface WidgetChangeHandler {

  boolean onAttach(Widget widget);

  boolean onDetach(Widget widget);

  boolean onSetId(UIObject o, String newId);

}
