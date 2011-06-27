package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.cellview.client.CellTable;

public class CellTableTest extends GwtTestTest {

  @Test
  public void checkPageSize() {
    // Act
    CellTable<String> celltable = new CellTable<String>(4);

    // Assert
    Assert.assertEquals(4, celltable.getPageSize());
  }
}
