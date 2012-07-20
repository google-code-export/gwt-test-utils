package com.googlecode.gwt.test.internal.patchers;

import java.lang.reflect.Constructor;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.impl.FormPanelImpl;
import com.googlecode.gwt.test.exceptions.GwtTestException;
import com.googlecode.gwt.test.exceptions.GwtTestPatchException;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

@PatchClass(FormPanel.class)
class FormPanelPatcher {

   @PatchMethod
   static void submit(FormPanel formPanel) {
      FormPanel.SubmitEvent event = new FormPanel.SubmitEvent();
      formPanel.fireEvent(event);
      if (!event.isCanceled()) {
         FormPanelImpl impl = GwtReflectionUtils.getPrivateFieldValue(formPanel, "impl");
         Element synthesizedFrame = GwtReflectionUtils.getPrivateFieldValue(formPanel,
                  "synthesizedFrame");
         FormPanel.SubmitCompleteEvent completeEvent = createCompleteSubmitEvent(impl.getContents(synthesizedFrame));
         formPanel.fireEvent(completeEvent);
      }

   }

   private static SubmitCompleteEvent createCompleteSubmitEvent(String resultsHtml) {
      try {
         Constructor<SubmitCompleteEvent> ctor = SubmitCompleteEvent.class.getDeclaredConstructor(String.class);
         return GwtReflectionUtils.instantiateClass(ctor, resultsHtml);
      } catch (Exception e) {
         if (GwtTestException.class.isInstance(e)) {
            throw (GwtTestException) e;
         } else {
            throw new GwtTestPatchException("Error while trying to instanciate "
                     + SubmitCompleteEvent.class.getName() + " class", e);
         }
      }
   }

}
