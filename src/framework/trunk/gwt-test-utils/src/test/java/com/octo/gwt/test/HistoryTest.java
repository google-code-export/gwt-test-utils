package com.octo.gwt.test;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.junit.Test;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;

@SuppressWarnings("deprecation")
public class HistoryTest extends GwtTestWithMocks {

	@Mock
	private HistoryListener listener;

	@Mock
	private ValueChangeHandler<String> listener2;

	@Test
	public void checkHistoryOldSchool() {
		// Setup

		listener.onHistoryChanged(EasyMock.eq("init"));
		EasyMock.expectLastCall();

		listener.onHistoryChanged(EasyMock.eq("myToken"));
		EasyMock.expectLastCall();

		replay();
		// Test
		History.addHistoryListener(listener);

		History.newItem("init");
		History.newItem("myToken");

		// Assert
		verify();

		reset();

		// Setup

		listener.onHistoryChanged(EasyMock.eq("init"));
		EasyMock.expectLastCall();

		replay();
		// Test
		History.back();

		History.removeHistoryListener(listener);

		History.newItem("myToken2");

		// Assert
		verify();
	}

	@Test
	public void checkHistory() {
		// Setup

		listener2.onValueChange(ValueChangeEventMatcher.eq("init"));
		EasyMock.expectLastCall();

		listener2.onValueChange(ValueChangeEventMatcher.eq("myToken"));
		EasyMock.expectLastCall();

		replay();
		// Test
		History.addValueChangeHandler(listener2);

		History.newItem("init");
		History.newItem("myToken");

		// Assert
		verify();

		reset();

		// Setup

		listener2.onValueChange(ValueChangeEventMatcher.eq("init"));
		EasyMock.expectLastCall();

		replay();
		// Test
		History.back();

		// Assert
		verify();
	}

	private static class ValueChangeEventMatcher<T> implements IArgumentMatcher {

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

}
