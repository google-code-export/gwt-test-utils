package com.octo.gwt.test17;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class FocusImplTest extends AbstractGWTTest {
	
	@Test
	public void checkMethods() {
		FocusImpl impl = FocusImpl.getFocusImplForWidget();
		
		Button b = new Button();
		impl.focus(b.getElement());
		impl.blur(b.getElement());
		impl.setTabIndex(b.getElement(), 3);
		
		Assert.assertEquals(3, impl.getTabIndex(b.getElement()));
	}
}
