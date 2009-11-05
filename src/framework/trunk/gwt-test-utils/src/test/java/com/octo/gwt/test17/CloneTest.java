package com.octo.gwt.test17;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Anchor;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class CloneTest extends AbstractGWTTest {
	
	@Test
	public void cloneTest() throws Exception {
		Element anchor = new Anchor().getElement();
		Assert.assertEquals("a", anchor.getPropertyString("tagName"));
		Element anchor2 = (Element) anchor.cloneNode(true);
		Assert.assertEquals("a", anchor2.getPropertyString("tagName"));
	}

}
