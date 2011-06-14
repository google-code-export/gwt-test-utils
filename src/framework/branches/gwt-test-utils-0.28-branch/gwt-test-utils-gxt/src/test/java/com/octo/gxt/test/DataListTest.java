package com.octo.gxt.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.extjs.gxt.ui.client.widget.DataList;
import com.extjs.gxt.ui.client.widget.DataListItem;

// TODO: complete tests..
@SuppressWarnings("deprecation")
public class DataListTest extends GwtGxtTest {

  private DataList dataList;

  @Test
  public void checkSelectedItem() {
    // Arrange
    DataListItem item0 = new DataListItem("item 0");
    dataList.add(item0);
    DataListItem item1 = new DataListItem("item 1");
    dataList.add(item1);

    // Act
    dataList.setSelectedItem(item1);

    // Assert
    Assert.assertEquals(item1, dataList.getSelectedItem());

  }

  @Before
  public void setupDataList() {
    dataList = new DataList();
  }

}
