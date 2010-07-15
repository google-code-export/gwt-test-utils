package com.octo.gwt.test.internal.patcher;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

@PatchClass(DeferredCommand.class)
public class DeferredCommandPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void addCommand(Command command) {
		command.execute();
	}

	@PatchMethod
	public static void addCommand(IncrementalCommand command) {
		command.execute();
	}

}
