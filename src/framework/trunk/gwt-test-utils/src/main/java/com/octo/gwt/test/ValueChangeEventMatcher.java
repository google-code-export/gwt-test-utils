package com.octo.gwt.test;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;

import com.google.gwt.event.logical.shared.ValueChangeEvent;

/**
 * 
 * @author Bertrand Paquet
 * 
 * @param <T>
 */
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

	public static <X> ValueChangeEvent<X> eq(X expectedValue) {
		EasyMock.reportMatcher(new ValueChangeEventMatcher<X>(expectedValue));
		return null;
	}

}
