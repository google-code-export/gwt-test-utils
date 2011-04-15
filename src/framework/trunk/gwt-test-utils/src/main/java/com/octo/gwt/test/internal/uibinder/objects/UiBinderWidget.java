package com.octo.gwt.test.internal.uibinder.objects;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class UiBinderWidget<T extends Widget> implements UiBinderTag {

  private final T wrapped;

  public UiBinderWidget(Class<T> clazz) {
    wrapped = instanciate(clazz);
  }

  public void addTag(UiBinderTag tag) {
    Object wrappedChild = tag.complete();
    if (Widget.class.isInstance(wrappedChild)) {
      addWidget((Widget) wrappedChild);
    } else {
      appendElement((Element) wrappedChild);
    }
  }

  public void appendText(String data) {
    if (HasText.class.isInstance(wrapped)) {
      ((HasText) wrapped).setText(data);
    } else {
      Text text = JavaScriptObjects.newText(data);
      wrapped.getElement().appendChild(text);
    }
  }

  public Object complete() {
    return wrapped;
  }

  protected void addWidget(Widget widget) {
    if (HasWidgets.class.isInstance(wrapped)) {
      ((HasWidgets) wrapped).add(widget);
    }
  }

  protected void appendElement(Element child) {
    wrapped.getElement().appendChild(child);
  }

  protected T instanciate(Class<T> clazz) {
    return GwtReflectionUtils.instantiateClass(clazz);
  }

}
