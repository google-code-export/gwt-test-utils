package com.octo.gwt.test;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;

import com.octo.gwt.test.internal.mock.MockCreateHandlerImpl;

/**
 * <p>
 * Base class for test classes which make use of the framework's mocking
 * features (which rely on {@link org.easymock.EasyMock EasyMock}).
 * </p>
 * 
 * <p>
 * Those classes can declare fields annotated with
 * {@link Mock @Mock}, which will result in the injection of
 * mock objects of the corresponding type.
 * </p>
 * 
 * <p>
 * Mock objects can then be manipulated using the standard EasyMock API, or with
 * the methods provided by AbstractGwtEasyMockTest.
 * </p>
 */
public abstract class AbstractGwtEasyMockTest extends AbstractGwtTest {

	public AbstractGwtEasyMockTest() {
		PatchGwtConfig.setMockCreateHandler(new MockCreateHandlerImpl(this));
	}

	@Before
	public void setupAbstractGwtEasyMockTest() {
		PatchGwtConfig.getMockCreateHandler().beforeTest();
	}

	@After
	public void teardownAbstractGwtEasyMockTest() {
		PatchGwtConfig.getMockCreateHandler().afterTest();
	}

	/**
	 * Set all declared mocks to replay state.
	 */
	protected void replay() {
		PatchGwtConfig.getMockCreateHandler().replay();
	}

	/**
	 * Verifies that all recorded behaviors for every declared mocks has
	 * actually been used.
	 */
	protected void verify() {
		PatchGwtConfig.getMockCreateHandler().verify();
	}

	/**
	 * Reset all declared mocks.
	 */
	protected void reset() {
		PatchGwtConfig.getMockCreateHandler().reset();
	}

	/**
	 * Records a call to an asynchronous service and simulates a failure by
	 * calling the onFailure() method of the corresponding AsyncCallback object.
	 */
	protected void expectServiceAndCallbackOnFailure(final Throwable exception) {
		PatchGwtConfig.getMockCreateHandler().expectServiceAndCallbackOnFailure(exception);
	}

	/**
	 * Records a call to an asynchronous service and simulates a success by
	 * calling the onSuccess() method of the corresponding AsyncCallback object.
	 */
	protected <T> void expectServiceAndCallbackOnSuccess(final T object) {
		PatchGwtConfig.getMockCreateHandler().expectServiceAndCallbackOnSuccess(object);
	}

	/**
	 * Creates a mock object for a given class, where all methods are mocked
	 * except the one with the name given as a parameter.
	 */
	public <T> T createMockAndKeepOneMethod(Class<T> clazz, String methodName) {
		return PatchGwtConfig.getMockCreateHandler().createMockAndKeepOneMethod(clazz, methodName);
	}

	/**
	 * Creates a mock object for a given class, where all methods are mocked
	 * except the one given as parameters.
	 * 
	 * @param clazz
	 *            The class for which a mock object will be created
	 * @param keepSetters
	 *            False if setters should be mocked, true otherwise
	 * @param list
	 *            List of methods that should not be mocked
	 */
	protected <T> T createMockAndKeepMethods(Class<T> clazz, final boolean keepSetters, final Method... list) {
		return PatchGwtConfig.getMockCreateHandler().createMockAndKeepMethods(clazz, keepSetters, list);
	}

	protected Object addMockedObject(Class<?> createClass, Object mock) {
		return PatchGwtConfig.getMockCreateHandler().addMockedObject(createClass, mock);
	}
}
