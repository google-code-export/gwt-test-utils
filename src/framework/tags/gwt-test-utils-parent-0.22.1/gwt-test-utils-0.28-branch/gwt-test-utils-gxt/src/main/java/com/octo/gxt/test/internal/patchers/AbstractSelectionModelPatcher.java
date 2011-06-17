package com.octo.gxt.test.internal.patchers;

import java.lang.reflect.Method;

import com.extjs.gxt.ui.client.event.ContainerEvent;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Container;
import com.extjs.gxt.ui.client.widget.selection.AbstractSelectionModel;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(AbstractSelectionModel.class)
public class AbstractSelectionModelPatcher {

  @SuppressWarnings("unchecked")
  @PatchMethod
  public static ContainerEvent<Container<Component>, Component> createContainerEvent(
      AbstractSelectionModel<Container<Component>, Component> absm,
      Container<Component> container) {

    for (Method m : Container.class.getDeclaredMethods()) {
      if ("createContainerEvent".equals(m.getName())) {
        return (ContainerEvent<Container<Component>, Component>) GwtReflectionUtils.callPrivateMethod(
            container, m, (Component) null);
      }
    }
    return null;
  }

}
