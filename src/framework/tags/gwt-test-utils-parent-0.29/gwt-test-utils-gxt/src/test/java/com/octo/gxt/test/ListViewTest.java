package com.octo.gxt.test;

import org.junit.Assert;
import org.junit.Test;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.ListView;

// TODO: complete tests..
public class ListViewTest extends GwtGxtTest {

  @Test
  public void checkSimpleTemplate() {
    // Setup
    ListView<ModelData> list = new ListView<ModelData>();

    // Test
    list.setSimpleTemplate("<ul><li>1</li><li>2</li></ul>");

    // Assert
    Assert.assertNotNull(list.getTemplate());
  }

}
