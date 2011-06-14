package com.octo.gwt.test.internal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.octo.gwt.test.GwtTestTest;
import com.octo.gwt.test.internal.MyClassToPatch.MyInnerClass;

public class AutomaticPatcherTest extends GwtTestTest {

  private MyClassToPatch instance;

  @Test
  public void checkPatchWithInnerClassAndMultiplePatchers() throws Exception {
    // Arrange
    MyInnerClass innerObject = new MyInnerClass("innerOjbectForUnitTest");

    // Act
    String result = instance.myStringMethod(innerObject);

    // Assert
    Assert.assertEquals(
        "myStringMethod has been patched by override patcher : patched by MyInnerClassOverridePatcher : new field added in overrided init",
        result);
  }

  @Before
  public void setupAutomaticPatcherTest() {
    instance = new MyClassToPatch();
  }

}
