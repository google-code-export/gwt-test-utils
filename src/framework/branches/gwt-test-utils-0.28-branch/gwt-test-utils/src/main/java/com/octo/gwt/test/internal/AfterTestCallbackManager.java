package com.octo.gwt.test.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gwt.dev.util.collect.HashSet;

/**
 * Class responsible for triggering {@link AfterTestCallback#afterTest()}
 * callback methods from registered callbacks. <strong>For internal use
 * only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class AfterTestCallbackManager {

  private static final AfterTestCallbackManager INSTANCE = new AfterTestCallbackManager();

  public static AfterTestCallbackManager get() {
    return INSTANCE;
  }

  private final Set<AfterTestCallback> callbacks;

  private AfterTestCallbackManager() {
    callbacks = new HashSet<AfterTestCallback>();
  }

  /**
   * Register a callback to trigger after a test execution.
   * 
   * @param callback The callback to register.
   * @return <tt>true</tt> if the callback was not already registered.
   */
  public boolean registerCallback(AfterTestCallback callback) {
    return callbacks.add(callback);
  }

  /**
   * Trigger all the registered callbacks and collect all the exception that may
   * be thrown.
   * 
   * @return A list of exceptions that has been thrown when triggering the
   *         different callbacks.
   */
  public List<Throwable> triggerCallbacks() {
    List<Throwable> throwables = new ArrayList<Throwable>();

    for (AfterTestCallback callback : callbacks) {
      try {
        callback.afterTest();
      } catch (Throwable t) {
        throwables.add(t);
      }
    }

    return throwables;

  }

  /**
   * Unregister a callback so it will not be triggered anymore.
   * 
   * @param callback The callback to unregister.
   * @return <tt>true</tt> if this callback was registered.
   */
  public boolean unregisterCallback(AfterTestCallback callback) {
    return callbacks.remove(callback);
  }

}
