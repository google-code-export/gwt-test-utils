package com.octo.gxt.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.widget.Window;

public class WindowTest extends GwtGxtTest {

  private boolean activate;
  private boolean deactivate;
  private boolean hide;
  private boolean maximize;
  private boolean minimize;
  private boolean restore;
  private boolean show;
  private Window window;

  @Test
  public void activate() {
    // Arrange
    window.addWindowListener(new WindowListener() {

      @Override
      public void windowActivate(WindowEvent we) {
        Assert.assertEquals(window, we.getWindow());
        activate = true;
      }
    });

    window.show();

    // Act
    window.setActive(true);

    // Assert
    Assert.assertTrue(activate);
  }

  @Test
  public void deactivate() {
    // Arrange
    window.addWindowListener(new WindowListener() {

      @Override
      public void windowDeactivate(WindowEvent we) {
        Assert.assertEquals(window, we.getWindow());
        deactivate = true;
      }
    });

    window.show();

    // Act
    window.setActive(false);

    // Assert
    Assert.assertTrue(deactivate);
  }

  @Test
  public void hide() {
    // Arrange
    window.addWindowListener(new WindowListener() {

      @Override
      public void windowHide(WindowEvent we) {
        Assert.assertEquals(window, we.getWindow());
        hide = true;
      }
    });

    window.show();

    // Act
    window.hide();

    // Assert
    Assert.assertTrue(hide);
  }

  @Test
  public void maximize() {
    // Arrange
    window.addWindowListener(new WindowListener() {

      @Override
      public void windowMaximize(WindowEvent we) {
        Assert.assertEquals(window, we.getWindow());
        maximize = true;
      }
    });

    window.show();

    // Act
    window.maximize();

    // Assert
    Assert.assertTrue(maximize);
  }

  @Test
  public void minimize() {
    // Arrange
    window.addWindowListener(new WindowListener() {

      @Override
      public void windowMinimize(WindowEvent we) {
        Assert.assertEquals(window, we.getWindow());
        minimize = true;
      }
    });

    window.show();

    // Act
    window.minimize();

    // Assert
    Assert.assertTrue(minimize);
  }

  @Test
  public void restore() {
    // Arrange
    window.addWindowListener(new WindowListener() {

      @Override
      public void windowRestore(WindowEvent we) {
        Assert.assertEquals(window, we.getWindow());
        restore = true;
      }
    });

    window.show();
    window.maximize();

    // Act
    window.restore();

    // Assert
    Assert.assertTrue(restore);
  }

  @Before
  public void setupWindowTest() {
    window = new Window();
    activate = false;
    deactivate = false;
    hide = false;
    maximize = false;
    minimize = false;
    restore = false;
    show = false;
  }

  @Test
  public void show() {
    // Arrange
    window.addWindowListener(new WindowListener() {

      @Override
      public void windowShow(WindowEvent we) {
        Assert.assertEquals(window, we.getWindow());
        show = true;
      }
    });

    // Act
    window.show();

    // Assert
    Assert.assertTrue(show);
  }

}
