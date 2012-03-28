package com.googlecode.gwt.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.CustomButton;
import com.google.gwt.user.client.ui.PushButton;
import com.googlecode.gwt.test.GwtTestWithMockito;

public class CompositeWithMockitoTest extends GwtTestWithMockito {

  public class MyComposite extends Composite {

    public MyComposite(CustomButton button) {
      initWidget(button);
    }
  }

  @Mock
  private Element element;

  @Mock
  private PushButton injectedButton;

  @Override
  public String getModuleName() {
    return "com.googlecode.gwt.test.GwtTestUtils";
  }

  @Test
  public void testComposite() {
    // Arrange
    Mockito.when(injectedButton.getElement()).thenReturn(element);

    // Act
    MyComposite composite = new MyComposite(injectedButton);

    // Assert
    assertEquals(element, composite.getElement());
  }
}
