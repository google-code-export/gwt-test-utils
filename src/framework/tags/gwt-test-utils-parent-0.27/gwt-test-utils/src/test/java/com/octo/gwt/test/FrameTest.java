package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.ui.Frame;

public class FrameTest extends GwtTest {

  @Test
  public void checkTitle() {
    Frame f = new Frame();
    f.setTitle("title");

    Assert.assertEquals("title", f.getTitle());
  }

  @Test
  public void checkUrl() {
    Frame f = new Frame("url");

    Assert.assertEquals("url", f.getUrl());

    f.setUrl("newURL");

    Assert.assertEquals("newURL", f.getUrl());
  }

}
