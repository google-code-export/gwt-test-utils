package com.googlecode.gwt.test;

import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletConfig;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.gwt.test.uibinder.UiObjectTagFactory;
import com.googlecode.gwt.test.utils.events.Browser;
import com.googlecode.gwt.test.utils.events.Browser.BrowserErrorHandler;

/**
 * 
 * @author Gael Lazzari
 * 
 */
public interface GwtModuleRunner {

  /**
   * Add String key/value pairs to a GWT {@link Dictionary}.
   * 
   * @param dictionaryName The name of the {@link Dictionary} on which the
   *          entries should be added
   * @param entries The Dictionary's entries to add
   * 
   * @see {@link Dictionary#get(String)}
   */
  void addDictionaryEntries(String dictionaryName, Map<String, String> entries);

  /**
   * Declare a GwtCreateHandler to be a candidate for GWT deferred binding calls
   * ({@link GWT#create(Class)}).
   * 
   * @param gwtCreateHandler The deferred binding candidate.
   */
  void addGwtCreateHandler(GwtCreateHandler gwtCreateHandler);

  /**
   * Declare a UiWidgetTagFactory to be a candidate to handle some widget
   * declaration in a .ui.xml UiBinder file.
   * 
   * @param factory The UiBinder Widget factory candidate.
   */
  void addUiObjectTagFactory(UiObjectTagFactory<? extends IsWidget> factory);

  /**
   * Specifies if The {@link Browser} helper methods can target not attached
   * widgets or not.
   * 
   * @return True if {@link DomEvent} can be dispatched on detached widgets,
   *         false otherwise.
   * 
   * @see Widget#isAttached()
   */
  boolean canDispatchDomEventOnDetachedWidget();

  /**
   * Specifies if the module runner is allowed the setup of debug id.
   * 
   * @return true if setting debug id should be enabled, false otherwise.
   * 
   * @see UIObject#ensureDebugId(com.google.gwt.dom.client.Element, String)
   * @see UIObject#ensureDebugId(String)
   */
  boolean ensureDebugId();

  /**
   * Specifies the callback to use when a simulated {@link Browser} action
   * throws an error. New BrowserErrorHandler <strong>must</strong> call
   * {@link FinallyCommandTrigger#clearPendingCommands()}.
   * 
   * @return The custom browser error handler callback.
   */
  BrowserErrorHandler getBrowserErrorHandler();

  /**
   * Specifies the relative path in the project of the HTML file which is used
   * by the GWT module to run.
   * 
   * @return The relative path of the HTML file used.
   */
  String getHostPagePath();

  /**
   * Specifies the locale to use when running the GWT module.
   * 
   * @return The specific locale to use for this module execution.
   */
  Locale getLocale();

  /**
   * Specifies the callback to use to handle {@link GWT#log(String)} and
   * {@link GWT#log(String, Throwable)} calls.
   * 
   * @return The callback to use to handle GWT log method calls.
   */
  GwtLogHandler getLogHandler();

  /**
   * <p>
   * Specifies the module to run.
   * 
   * <p>
   * If the return module name is not present in the gwt-test-utils
   * configuration file (e.g. "META-INF/gwt-test-utils.properties"), an
   * exception will be thrown.
   * </p>
   * 
   * @return the fully qualified name of a module. <strong>It cannot be null or
   *         empty</strong>.
   * 
   * 
   */
  String getModuleName();

  /**
   * Specifies the ServletConfig to use in {@link RemoteServiceServlet}
   * instances.
   * 
   * @return The servletConfig to use in {@link RemoteServiceServlet} instances.
   */
  ServletConfig getServletConfig();

  /**
   * Specifies the callback to use to handle {@link Window} method calls.
   * 
   * @return The callback to use to handle {@link Window} method calls.
   */
  WindowOperationsHandler getWindowOperationsHandler();

  /**
   * Specifies if the GWT DOM should be simulated or mocked.
   * 
   * @return True if the DOM should be mocked, false if it should be simulated
   *         well.
   */
  boolean isDomMocked();

  /**
   * Declare a {@link UiConstructor} which might be use to handle some widget
   * creation according to its declaration in a .ui.xml UiBinder file.
   * 
   * @param clazz The widget class where the {@link UiConstructor} is declared.
   * @param argNames An ordered array of argument names
   */
  void registerUiConstructor(Class<? extends IsWidget> clazz,
      String... argNames);
}
