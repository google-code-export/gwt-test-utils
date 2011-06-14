package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Image;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.events.Browser;
import com.octo.gwt.test.utils.events.EventBuilder;

@PatchClass(classes = {"com.google.gwt.user.client.ui.Image$State"})
public class ImageStatePatcher {

  @PatchMethod
  public static void fireSyntheticLoadEvent(Object state, Image image) {
    Event loadEvent = EventBuilder.create(Event.ONLOAD).build();
    Browser.dispatchEvent(image, loadEvent);
  }

}
