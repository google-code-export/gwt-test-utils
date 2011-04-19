package com.octo.gwt.test;

import com.google.gwt.core.client.GWT;

/**
 * Interface for an object capable of handling object creation, which is
 * delegated by the patched version of {@link GWT#create(Class)}.
 * 
 */
public interface GwtCreateHandler {

	/**
	 * <p>Instantiates an object of the given class.</p>
	 * 
	 * <p>This handler may be able to instantiate objects of certain types only. If
	 * the class passed as parameter is not handled, the method should return
	 * null.</p>
	 * 
	 * @param classLiteral
	 *            the class to instantiate
	 * @return an object of this class if this GwtCreateHandler is capable of
	 *         handling it, null otherwise
	 * @throws Exception
	 *             if an error occurred when trying to create the object
	 */
	Object create(Class<?> classLiteral) throws Exception;

}
