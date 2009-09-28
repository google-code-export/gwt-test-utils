package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class SuggestBoxTest extends AbstractGWTTest {
	
	//@Test
	public void checkSuggestBox() {
		
		   MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();  
		   oracle.add("Cat");
		   oracle.add("Dog");
		   oracle.add("Horse");
		   oracle.add("Canary");
		   
		   SuggestBox box = new SuggestBox(oracle);
		   
		   Assert.assertNotNull(box);
	}

}
