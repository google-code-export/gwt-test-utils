package com.octo.gwt.test;

import org.easymock.classextension.EasyMock;
import org.junit.Test;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.octo.gwt.test.AbstractGwtEasyMockTest;
import com.octo.gwt.test.Mock;
import com.octo.gwt.test.ValueChangeEventMatcher;

@SuppressWarnings("deprecation")
public class HistoryTest extends AbstractGwtEasyMockTest {

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

}
