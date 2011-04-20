package com.octo.gwt.test;

import org.junit.Test;

import com.google.gwt.dom.client.StyleInjector;

public class StyleInjectorTest extends GwtTest {

	@Test
	public void checkInject() {
		StyleInjector.inject(".test{color:red;}");
		StyleInjector.inject(".testImmediate{color:green;}", true);
	}

	@Test
	public void checkInjectAtEnd() {
		StyleInjector.injectAtEnd(".test{color:red;}");
		StyleInjector.injectAtEnd(".testImmediate{color:green;}", true);
	}

	@Test
	public void checkInjectAtStart() {
		StyleInjector.injectAtStart(".test{color:red;}");
		StyleInjector.injectAtStart(".testImmediate{color:green;}", true);
	}

}
