package com.googlecode.gwt.test.internal.junit;

import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import com.googlecode.gwt.test.internal.AfterTestCallbackManager;
import com.googlecode.gwt.test.internal.GwtTestErrorHolder;

/**
 * gwt-test-utils custom {@link RunListener} to be used for every custom JUnit
 * {@link Runner}. It registers potential assertion errors and failures not to
 * throw {@link AfterTestCallbackManager#triggerCallbacks() errors in addition}.
 * <strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class GwtRunListener extends RunListener {

  @Override
  public void testAssumptionFailure(Failure failure) {
    GwtTestErrorHolder.get().setCurrentTestFailed(true);
  }

  @Override
  public void testFailure(Failure failure) throws Exception {
    GwtTestErrorHolder.get().setCurrentTestFailed(true);
  }

}
