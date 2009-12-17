package com.octo.gwt.test17.internal.dom;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.impl.DOMImpl;

public class UserDomImpl extends DOMImpl {

	@Override
	public Element eventGetFromElement(Event evt) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Element eventGetToElement(Event evt) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Element getChild(Element elem, int index) {
		UserElement e = UserElement.overrideCast(elem);
		return index >= e.getOverrideList().size() ? null : e.getOverrideList().get(index);
	}

	@Override
	public int getChildCount(Element elem) {
		UserElement e = UserElement.overrideCast(elem);
		return e.getOverrideList().size();
	}

	@Override
	public int getChildIndex(Element parent, Element child) {
		UserElement p = UserElement.overrideCast(parent);
		UserElement c = UserElement.overrideCast(child);
		return p.getOverrideList().indexOf(c);
	}

	@Override
	protected void initEventSystem() {
		// nothing to do
	}

	@Override
	public void sinkEvents(Element elem, int eventBits) {
		// nothing to do
	}

	@Override
	public void insertChild(Element parent, Element child, int index) {
		UserElement p = UserElement.overrideCast(parent);
		UserElement c = UserElement.overrideCast(child);
		if (index > p.getOverrideList().size()) {
			index = 0;
		}
		p.getOverrideList().add(index, c);
		c.setParent(p);
	}

	@Override
	public void releaseCapture(Element elem) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCapture(Element elem) {
		throw new UnsupportedOperationException();
	}

}
