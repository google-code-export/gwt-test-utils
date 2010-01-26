package com.octo.gwt.test17.internal.patcher.dom;

import com.google.gwt.dom.client.AnchorElement;
import com.octo.gwt.test17.ng.AutomaticElementSubclasser;
import com.octo.gwt.test17.ng.PatchMethod;

public class AnchorElementPatcher extends AutomaticElementSubclasser {

	@PatchMethod
	public static void blur(AnchorElement anchorElement) {
		
	}
	
	@PatchMethod
	public static void focus(AnchorElement anchorElement) {
		
	}
	
}
