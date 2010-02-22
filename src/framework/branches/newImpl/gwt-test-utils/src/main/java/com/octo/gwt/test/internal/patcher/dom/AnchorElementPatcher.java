package com.octo.gwt.test.internal.patcher.dom;

import com.google.gwt.dom.client.AnchorElement;
import com.octo.gwt.test.internal.patcher.tools.AutomaticElementSubclasser;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

public class AnchorElementPatcher extends AutomaticElementSubclasser {

	@PatchMethod
	public static void blur(AnchorElement anchorElement) {
		
	}
	
	@PatchMethod
	public static void focus(AnchorElement anchorElement) {
		
	}
	
}
