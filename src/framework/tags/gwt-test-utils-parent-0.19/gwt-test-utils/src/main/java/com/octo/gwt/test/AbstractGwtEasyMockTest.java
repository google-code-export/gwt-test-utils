package com.octo.gwt.test;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;

import com.octo.gwt.test.internal.mock.MockCreateHandlerImpl;

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

	protected void replay() {
		PatchGwtConfig.getMockCreateHandler().replay();
	}

	protected void verify() {
		PatchGwtConfig.getMockCreateHandler().verify();
	}

	protected void reset() {
		PatchGwtConfig.getMockCreateHandler().reset();
	}

	protected void expectServiceAndCallbackOnFailure(final Throwable exception) {
		PatchGwtConfig.getMockCreateHandler().expectServiceAndCallbackOnFailure(exception);
	}

	protected <T> void expectServiceAndCallbackOnSuccess(final T object) {
		PatchGwtConfig.getMockCreateHandler().expectServiceAndCallbackOnSuccess(object);
	}

	public <T> T createMockAndKeepOneMethod(Class<T> clazz, String methodName) {
		return PatchGwtConfig.getMockCreateHandler().createMockAndKeepOneMethod(clazz, methodName);
	}

	protected <T> T createMockAndKeepMethods(Class<T> clazz, final boolean keepSetters, final Method... list) {
		return PatchGwtConfig.getMockCreateHandler().createMockAndKeepMethods(clazz, keepSetters, list);
	}

	protected Object addMockedObject(Class<?> createClass, Object mock) {
		return PatchGwtConfig.getMockCreateHandler().addMockedObject(createClass, mock);
	}
}
