package com.octo.gwt.test;

/**
 * Interface for an object capable of handling GWT logging, which is delegated
 * by the patched version of {@link GWT#log(String, Throwable)}.
 * 
 */
public interface GwtLogHandler {

	/**
	 * Logs a message (calls to {@link GWT#log(String, Throwable)} are delagated
	 * to this method).
	 */
	void log(String message, Throwable t);

}
