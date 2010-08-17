package com.octo.gwt.test.internal.patcher;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.GwtLogHandler;
import com.octo.gwt.test.Mock;
import com.octo.gwt.test.internal.GwtCreateHandlerManager;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

@PatchClass(GWT.class)
public class GwtPatcher extends AutomaticPatcher {

	public static GwtLogHandler gwtLogHandler = null;

	public static void reset() {
		gwtLogHandler = null;
	}

	@PatchMethod
	public static void log(String message, Throwable t) {
		if (gwtLogHandler != null) {
			gwtLogHandler.log(message, t);
		}
	}

	@PatchMethod
	public static String getVersion() {
		return "GWT 2 by gwt-test-utils";
	}

	@PatchMethod
	public static boolean isClient() {
		return true;
	}

	@PatchMethod
	public static Object create(Class<?> classLiteral) {
		for (GwtCreateHandler gwtCreateHandler : GwtCreateHandlerManager.getInstance().getGwtCreateHandlers()) {
			try {
				Object o = gwtCreateHandler.create(classLiteral);
				if (o != null) {
					return o;
				}
			} catch (Exception e) {
				throw new RuntimeException("Error while creation instance of '" + classLiteral.getName() + "' through '"
						+ gwtCreateHandler.getClass().getName() + "' instance", e);
			}
		}

		throw new RuntimeException("No declared " + GwtCreateHandler.class.getSimpleName() + " has been able to create an instance of '"
				+ classLiteral.getName() + "'. You should add our own with 'addGwtCreateHandler(..)' method or declared your tested object with @"
				+ Mock.class.getSimpleName());
	}

}
