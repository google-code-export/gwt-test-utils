package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.IncrementalCommand;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;

public class DeferredCommandPatcher extends AutomaticPatcher {

	@PatchMethod(args={Command.class})
	public static void addCommand(Command command) {
		command.execute();
	}

	@PatchMethod(args={IncrementalCommand.class})
	public static void addCommand(IncrementalCommand command) {
		command.execute();
	}

}