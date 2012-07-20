package com.googlecode.gwt.test.internal.handlers;

import java.lang.reflect.Constructor;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.googlecode.gwt.test.GwtCreateHandler;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

/**
 * GwtCreateHandler for {@link DockLayoutPanel}.
 * 
 * @author Gael Lazzari
 * 
 */
class DockLayoutPanelHandler implements GwtCreateHandler {

   /*
    * (non-Javadoc)
    * 
    * @see com.googlecode.gwt.test.GwtCreateHandler#create(java.lang.Class)
    */
   public Object create(Class<?> classLiteral) throws Exception {
      if (!DockLayoutPanel.class.isAssignableFrom(classLiteral)) {
         return null;
      }

      Constructor<?> cons = classLiteral.getConstructor(Unit.class);
      return GwtReflectionUtils.instantiateClass(cons, Unit.EM);
   }

}
