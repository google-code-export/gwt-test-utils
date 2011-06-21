package com.octo.gwt.test;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.junit.Test;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;

@SuppressWarnings("deprecation")
public class HistoryTest extends GwtTestWithEasyMock {

  private static class ValueChangeEventMatcher<T> implements IArgumentMatcher {

    public static <X> ValueChangeEvent<X> eq(X expectedValue) {
      EasyMock.reportMatcher(new ValueChangeEventMatcher<X>(expectedValue));
      return null;
    }

    private final T expectedValue;

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

  }

  @Mock
  private HistoryListener listener;

  @Mock
  private ValueChangeHandler<String> listener2;

  @Test
  public void checkHistory() {
    // Arrange

    listener2.onValueChange(ValueChangeEventMatcher.eq("init"));
    EasyMock.expectLastCall();

    listener2.onValueChange(ValueChangeEventMatcher.eq("myToken"));
    EasyMock.expectLastCall();

    replay();
    // Act
    History.addValueChangeHandler(listener2);

    History.newItem("init");
    History.newItem("myToken");

    // Assert
    verify();

    reset();

    // Arrange

    listener2.onValueChange(ValueChangeEventMatcher.eq("init"));
    EasyMock.expectLastCall();

    replay();
    // Act
    History.back();

    // Assert
    verify();
  }

  @Test
  public void checkHistoryOldSchool() {
    // Arrange

    listener.onHistoryChanged(EasyMock.eq("init"));
    EasyMock.expectLastCall();

    listener.onHistoryChanged(EasyMock.eq("myToken"));
    EasyMock.expectLastCall();

    replay();
    // Act
    History.addHistoryListener(listener);

    History.newItem("init");
    History.newItem("myToken");

    // Assert
    verify();

    reset();

    // Arrange

    listener.onHistoryChanged(EasyMock.eq("init"));
    EasyMock.expectLastCall();

    replay();
    // Act
    History.back();

    History.removeHistoryListener(listener);

    History.newItem("myToken2");

    // Assert
    verify();
  }

  @Override
  public String getModuleName() {
    return "com.octo.gwt.test.GwtTestUtils";
  }

  @Override
  protected String getHostPagePath(String moduleFullQualifiedName) {
    return "test.html";
  }

}
