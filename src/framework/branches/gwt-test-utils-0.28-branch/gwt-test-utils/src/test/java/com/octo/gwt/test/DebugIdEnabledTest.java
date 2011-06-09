package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.ui.Button;

public class DebugIdEnabledTest extends GwtTestTest {

  @Test
  public void checkEnsureDebugId() {
    // Setup
    Button b = new Button();

    // Test
    b.ensureDebugId("myDebugId");

    // Assert
    Assert.assertEquals("gwt-debug-myDebugId", b.getElement().getId());
  }

  @Override
  protected boolean ensureDebugId() {
    return true;
  }

}