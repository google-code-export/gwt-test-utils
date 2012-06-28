package com.googlecode.gwt.test.internal;

/**
 * Testing framework independent error listener. <strong>For internal use
 * only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class GwtTestErrorHolder {

  private static final GwtTestErrorHolder INSTANCE = new GwtTestErrorHolder();

  public static GwtTestErrorHolder get() {
    return INSTANCE;
  }

  private boolean currentTestFailed;

  public boolean isCurrentTestFailed() {
    return currentTestFailed;
  }

  public void setCurrentTestFailed(boolean currentTestFailed) {
    this.currentTestFailed = currentTestFailed;
  }

}
