package com.octo.gwt.test;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Window;

public class WindowTest extends GwtTestTest {

  private final WindowOperationsHandler mockedHandler = EasyMock.createStrictMock(WindowOperationsHandler.class);;

  @Test
  public void checkAlert() {
    // Arrange
    mockedHandler.alert(EasyMock.eq("this is an alert"));
    EasyMock.expectLastCall();
    EasyMock.replay(mockedHandler);

    // Act
    Window.alert("this is an alert");

    // Assert
    EasyMock.verify(mockedHandler);
  }

  @Test
  public void checkConfirm() {
    // Arrange
    mockedHandler.confirm(EasyMock.eq("this is a confirmation"));
    EasyMock.expectLastCall().andReturn(true);
    EasyMock.replay(mockedHandler);

    // Act
    boolean result = Window.confirm("this is a confirmation");

    // Assert
    EasyMock.verify(mockedHandler);
    Assert.assertTrue(result);
  }

  @Test
  public void checkMargin() {
    // Arrange
    Document.get().getBody().setAttribute("style", "");

    // Act
    Window.setMargin("13px");

    // Assert
    Assert.assertEquals("13px", Document.get().getBody().getStyle().getMargin());
    Assert.assertEquals("margin: 13px; ",
        Document.get().getBody().getAttribute("style"));
  }

  @Test
  public void checkOpen() {
    // Arrange
    mockedHandler.open(EasyMock.eq("url"), EasyMock.eq("name"),
        EasyMock.eq("features"));
    EasyMock.expectLastCall();
    EasyMock.replay(mockedHandler);

    // Act
    Window.open("url", "name", "features");

    // Assert
    EasyMock.verify(mockedHandler);
  }

  @Test
  public void checkPrint() {
    // Arrange
    mockedHandler.print();
    EasyMock.expectLastCall();
    EasyMock.replay(mockedHandler);

    // Act
    Window.print();

    // Assert
    EasyMock.verify(mockedHandler);
  }

  @Test
  public void checkPrompt() {
    // Arrange
    mockedHandler.prompt(EasyMock.eq("prompt message"),
        EasyMock.eq("initial value"));
    EasyMock.expectLastCall().andReturn("mocked message");
    EasyMock.replay(mockedHandler);

    // Act
    String result = Window.prompt("prompt message", "initial value");

    // Assert
    EasyMock.verify(mockedHandler);
    Assert.assertEquals("mocked message", result);
  }

  @Test
  public void checkTitle() {
    // Arrange
    Document.get().setTitle("arranged title");

    // Act
    Window.setTitle("my title");

    // Assert
    Assert.assertEquals("my title", Window.getTitle());
    Assert.assertEquals("my title", Document.get().getTitle());
  }

  @Override
  public WindowOperationsHandler getWindowOperationsHandler() {
    return mockedHandler;
  }

  @Before
  public void setupWindowTest() {
    EasyMock.reset(mockedHandler);
  }

}
