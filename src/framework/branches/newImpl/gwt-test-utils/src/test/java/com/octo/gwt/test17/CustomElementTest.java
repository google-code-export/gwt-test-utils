package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class CustomElementTest extends AbstractGWTTest {

	static final String [] taglist = { "a", "area", "base", "body", "br", "button", "div", "dl", "fieldset", "form", "frame", "frameset", "head", "h1", "h2", "h3", "h4", "h5", "h6",
		"hr", "iframe", "img", "input", "label", "legend", "li", "link", "map", "meta", "object", "ol", "optgroup", "option", "options",
		"p", "param", "pre", "q", "script", "select", "span", "style", "caption", "td", "th", "col", "colgroup", "table", "tr", "tbody",
		"tfoot", "thead", "textarea", "title", "ul" };
	
	@Test
	public void checkTags() {
		for(String tag : taglist) {
			Element element = DOM.createElement(tag);
			Assert.assertNotNull("Element null for tag " + tag, element);
			String className = ((ElementWrapper) element).getWrappedElement().getClassName();
			Assert.assertFalse(Element.class.getCanonicalName().equals(className));
		}
	}
	
}
