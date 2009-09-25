package com.octo.gwt.test17.test;

import org.easymock.IArgumentMatcher;
import org.easymock.classextension.EasyMock;

import com.google.gwt.event.logical.shared.ValueChangeEvent;

public class ValueChangeEventMatcher<T> implements IArgumentMatcher {

	private T expectedValue;
	
	private ValueChangeEventMatcher(T expectedValue) {
		this.expectedValue = expectedValue;
	}
	
	public void appendTo(StringBuffer buffer) {
		buffer.append(expectedValue.toString());
	}

	public boolean matches(Object argument) {
		if (argument instanceof ValueChangeEvent<?>) {
			ValueChangeEvent<?> valueChangeEvent = (ValueChangeEvent<?>) argument;
			return expectedValue.equals(valueChangeEvent.getValue());
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static <X> ValueChangeEvent<X> eq(X expectedValue) {
		EasyMock.reportMatcher(new ValueChangeEventMatcher(expectedValue));
		return null;
	}
	
}
