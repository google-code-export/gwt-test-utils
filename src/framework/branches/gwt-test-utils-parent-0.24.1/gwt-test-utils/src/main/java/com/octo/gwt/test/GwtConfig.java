package com.octo.gwt.test;

import java.util.Locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.UIObject.DebugIdImpl;
import com.octo.gwt.test.internal.GwtCreateHandlerManager;
import com.octo.gwt.test.internal.patchers.GwtPatcher;
import com.octo.gwt.test.internal.patchers.ImplPatcher;
import com.octo.gwt.test.internal.patchers.dom.NodeFactory;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class GwtConfig {

	private static Locale locale;
	private static boolean ensureDebugId;

	public static void setLocale(Locale locale) {
		GwtConfig.locale = locale;
	}

	public static Locale getLocale() {
		return locale;
	}

	public static boolean ensureDebugId() {
		return ensureDebugId;
	}

	public static void setEnsureDebugId(boolean ensureDebugId) {
		GwtConfig.ensureDebugId = ensureDebugId;
		// refresh the static instance in UIObject
		GwtReflectionUtils.setStaticField(UIObject.class, "debugIdImpl", GWT.create(DebugIdImpl.class));
	}

	public static void setLogHandler(GwtLogHandler gwtLogHandler) {
		GwtPatcher.gwtLogHandler = gwtLogHandler;
	}

	public static void setHostPagePath(String hostPagePath) {
		NodeFactory.hostPagePath = hostPagePath;
	}

	public static boolean addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		return GwtCreateHandlerManager.getInstance().addGwtCreateHandler(gwtCreateHandler);
	}

	/**
	 * Set a custom GwtCreateHandler to handle GWT.create(..) calls
	 * 
	 * @param gwtCreateHandler
	 *            A handler gwt-test-utils will try to delegate GWT.create(..)
	 *            calls
	 * @deprecated Use {@link GwtConfig#addGwtCreateHandler(GwtCreateHandler)
	 *             instead}
	 */
	@Deprecated
	public static void setGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		addGwtCreateHandler(gwtCreateHandler);
	}

	public static void setCurrentTestedModuleFile(String currentTestedModuleFile) {
		ImplPatcher.currentTestedModuleFile = currentTestedModuleFile;
	}

	public static void reset() {
		locale = null;
		ensureDebugId = false;
	}

}
