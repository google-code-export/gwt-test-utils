package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(DeferredCommand.class)
class DeferredCommandPatcher {

  @PatchMethod
  static void addCommand(Command command) {
    command.execute();
  }

  @PatchMethod
  static void addCommand(IncrementalCommand command) {
    command.execute();
  }

}
