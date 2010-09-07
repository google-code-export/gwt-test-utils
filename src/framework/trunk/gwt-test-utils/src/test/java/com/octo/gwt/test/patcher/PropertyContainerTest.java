package com.octo.gwt.test.patcher;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.octo.gwt.test.AbstractGwtTest;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.internal.utils.PropertyContainerHelper;

public class PropertyContainerTest extends AbstractGwtTest {

	private Element owner;
	private PropertyContainer pc;

	@Before
	public void setupPropertyContainer() {
		owner = Document.get().createDivElement();
		pc = PropertyContainerHelper.cast(owner).getProperties();
	}

	@Test
	public void checkOrderedIterator() {
		// Setup
		owner.setClassName("myClass");
		owner.getStyle().setColor("red");
		owner.setId("myId");
		owner.setInnerText("myText");
		owner.setInnerText(null);
		owner.setLang("myLang");
		owner.setLang("");
		owner.setClassName("myOverridenClass");

		// Test
		Iterator<String> it = pc.orderedIterator();

		// Assert
		Assert.assertEquals("myOverridenClass", pc.get(it.next()));
		Assert.assertEquals(owner.getStyle(), pc.get(it.next()));
		Assert.assertEquals("myId", pc.get(it.next()));
		Assert.assertFalse(it.hasNext());
	}

	@Test
	public void checkClear() {
		// Setup
		pc.put("a", "a value");
		pc.put("b", "b value");

		// Test
		pc.clear();

		// Assert
		Assert.assertFalse(pc.orderedIterator().hasNext());
	}

}
