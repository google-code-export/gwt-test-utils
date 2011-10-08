package com.octo.gwt.test;

import java.util.Locale;

import javax.servlet.ServletConfig;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.octo.gwt.test.utils.events.Browser;
import com.octo.gwt.test.utils.events.Browser.BrowserErrorHandler;

/**
 * 
 * @author Gael Lazzari
 * 
 */
public interface GwtModuleRunner {

  /**
   * Add a GWT Dictionnary to be a candidate for {@link Dictionary#get(String)}
   * lookup.
   * 
   * @param dictionnary The dictionnary to register
   * @return
   */
  void addDictionary(Dictionary dictionnary);

  /**
   * Declare a GwtCreateHandler to be a candidate for GWT deferred binding calls
   * ({@link GWT#create(Class)}).
   * 
   * @param gwtCreateHandler The deferred binding candidate.
   */
  void addGwtCreateHandler(GwtCreateHandler gwtCreateHandler);

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
}
