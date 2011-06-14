package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(DeferredCommand.class)
public class DeferredCommandPatcher {

  @PatchMethod
  public static void addCommand(Command command) {
    command.execute();
  }

  @PatchMethod
  public static void addCommand(IncrementalCommand command) {
    command.execute();
  }

}
