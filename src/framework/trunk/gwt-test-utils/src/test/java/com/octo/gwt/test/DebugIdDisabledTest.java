package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.ui.Button;

public class DebugIdDisabledTest extends GwtTestTest {

  @Test
  public void checkEnsureDebugId() {
    // Arrange
    Button b = new Button();

    // Act
    b.ensureDebugId("myDebugId");

    // Assert
    Assert.assertEquals("", b.getElement().getId());
  }

  @Override
  protected boolean ensureDebugId() {
    return false;
  }

}
