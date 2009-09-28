package com.octo.gwt.test17;

import org.junit.Assert;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class SuggestBoxTest extends AbstractGWTTest {

	//@Test
	public void checkSuggestBoxLimit() {

		SuggestBox box = getSuggestBox();
		
		box.setLimit(2);
		box.setAutoSelectEnabled(true);

		Assert.assertEquals(2, box.getLimit());
		Assert.assertEquals(true, box.isAutoSelectEnabled());
	}
	
	private SuggestBox getSuggestBox() {
		MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();  
		oracle.add("Cat");
		oracle.add("Dog");
		oracle.add("Horse");
		oracle.add("Canary");
		
		return new SuggestBox(oracle);
	}

}
