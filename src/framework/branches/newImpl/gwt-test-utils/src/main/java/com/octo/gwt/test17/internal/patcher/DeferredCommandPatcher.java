package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.IncrementalCommand;

public class DeferredCommandPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (matchWithArgs(m, "addCommand", Command.class)) {
			return callMethod("addCommand", "$1");
		} else if (matchWithArgs(m, "addCommand", IncrementalCommand.class)) {
			return callMethod("addIncrementalCommand", "$1");
		}

		return null;
	}

	public static void addCommand(Command command) {
		command.execute();
	}

	public static void addIncrementalCommand(IncrementalCommand command) {
		command.execute();
	}

}
