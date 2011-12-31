package com.octo.gwt.test;

import com.google.gwt.user.client.Window;

/**
 * Handler where are redirect {@link Window} operations.
 * 
 * @author Gael Lazzari
 * 
 */
public interface WindowOperationsHandler {

  /**
   * Handler's method called during the invocation of
   * {@link Window#alert(String)}.
   * 
   * @param msg the message to be displayed.
   */
  void alert(String msg);

  /**
   * Handler's method called during the invocation of
   * {@link Window#confirm(String)}.
   * 
   * @param msg the message to be displayed.
   * @return <code>true</code> if 'OK' is clicked, <code>false</code> if
   *         'Cancel' is clicked.
   */
  boolean confirm(String msg);

  /**
   * Handler's method called during the invocation of
   * {@link Window#open(String, String, String)}.
   * 
   * @param url the URL that the new window will display
   * @param name the name of the window (e.g. "_blank")
   * @param features the features to be enabled/disabled on this window
   */
  void open(String url, String name, String features);

  /**
   * Handler's method called during the invocation of {@link Window#print()}.
   */
  void print();

  /**
   * Handler's method called during the invocation of
   * {@link Window#prompt(String, String)}.
   * 
   * @param msg the message to be displayed
   * @param initialValue the initial value in the dialog's text field
   * @return the value entered by the user if 'OK' was pressed, or
   *         <code>null</code> if 'Cancel' was pressed
   */
  String prompt(String msg, String initialValue);

}
