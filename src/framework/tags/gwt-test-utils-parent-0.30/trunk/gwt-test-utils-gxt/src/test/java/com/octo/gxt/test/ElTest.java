package com.octo.gxt.test;

import junit.framework.Assert;

import org.junit.Test;

import com.extjs.gxt.ui.client.core.El;

public class ElTest extends GwtGxtTest {

  @Test
  public void checkAddUnitsAuto() {
    // Act
    String result = El.addUnits("auto", "px");

    // Assert
    Assert.assertEquals("auto", result);
  }

  @Test
  public void checkAddUnitsComplete() {
    // Act
    String result = El.addUnits("350em", "%");

    // Assert
    Assert.assertEquals("350em", result);
  }

  @Test
  public void checkAddUnitsEmpty() {
    // Act
    String result = El.addUnits("", "px");

    // Assert
    Assert.assertEquals("", result);
  }

  @Test
  public void checkAddUnitsNoUnit() {
    // Act
    String result = El.addUnits("200", "em");

    // Assert
    Assert.assertEquals("200em", result);
  }

  @Test
  public void checkAddUnitsNoUnitAndEmptyDefault() {
    // Act
    String result = El.addUnits("250", "");

    // Assert
    Assert.assertEquals("250px", result);
  }

  @Test
  public void checkAddUnitsNoUnitAndNoDefault() {
    // Act
    String result = El.addUnits("250", null);

    // Assert
    Assert.assertEquals("250px", result);
  }

  @Test
  public void checkAddUnitsNull() {
    // Act
    String result = El.addUnits(null, "px");

    // Assert
    Assert.assertEquals("", result);
  }

  @Test
  public void checkAddUnitsUndefined() {
    // Act
    String result = El.addUnits("undefined", "px");

    // Assert
    Assert.assertEquals("", result);
  }

  @Test
  public void checkAddUnitsWithWhitespaces() {
    // Act
    String result = El.addUnits(" 350 em ", "pt");

    // Assert
    Assert.assertEquals("350em", result);
  }
}
