package com.octo.gwt.test.internal;

import com.google.gwt.user.client.Command;

public class PatchDeferredCommand {

	public static void immediateCommand(Command cmd) {
		cmd.execute();
	}

}
