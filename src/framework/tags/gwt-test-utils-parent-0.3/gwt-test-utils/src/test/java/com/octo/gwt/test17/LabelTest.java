package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.ui.Label;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class LabelTest extends AbstractGWTTest {
	
	@Test
	public void checkText() {
		Label label = new Label("foo");
		Assert.assertEquals("foo", label.getText());
		label.setText("text");
		Assert.assertEquals("text", label.getText());
	}
	
	@Test
	public void checkTitle() {
		Label label = new Label();
		label.setTitle("title");
		Assert.assertEquals("title", label.getTitle());
	}
	
	@Test
	public void checkVisible() {
		Label label = new Label();
		Assert.assertEquals(true, label.isVisible());
		label.setVisible(false);
		Assert.assertEquals(false, label.isVisible());
	}
	
	@Test
	public void checkDirection() {
		Label label = new Label();
		label.setDirection(Direction.RTL);
		
		Assert.assertEquals(Direction.RTL, label.getDirection());
	}
	
	@Test
	public void checkWordWrap() {
		Label label = new Label();
		label.setWordWrap(true);
		
		Assert.assertEquals(true, label.getWordWrap());
	}

}
