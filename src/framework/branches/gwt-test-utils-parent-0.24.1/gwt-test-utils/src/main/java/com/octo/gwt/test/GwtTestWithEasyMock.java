package com.octo.gwt.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.After;
import org.junit.Before;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.octo.gwt.test.internal.GwtCreateHandlerManager;
import com.octo.gwt.test.internal.mock.MockCreateHandler;
import com.octo.gwt.test.internal.utils.ArrayUtils;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.GwtReflectionUtils.MethodCallback;

/**
 * <p>
 * Base class for test classes which make use of the framework's mocking
 * features (which rely on {@link org.easymock.EasyMock EasyMock}).
 * </p>
 * 
 * <p>
 * Those classes can declare fields annotated with {@link Mock @Mock}, which
 * will result in the injection of mock objects of the corresponding type.
 * </p>
 * 
 * <p>
 * Mock objects can then be manipulated using the standard EasyMock API, or with
 * the methods provided by AbstractGwtEasyMockTest.
 * </p>
 */
public abstract class GwtTestWithEasyMock extends GwtTest {

	private Map<Class<?>, Object> mockObjects = new HashMap<Class<?>, Object>();
	private List<Class<?>> mockedClasses = new ArrayList<Class<?>>();
	private Set<Field> annotatedFieldToInject;

	public GwtTestWithEasyMock() {
		GwtCreateHandlerManager.getInstance().setMockCreateHandler(new MockCreateHandler(mockObjects));
		annotatedFieldToInject = GwtReflectionUtils.getAnnotatedField(this.getClass(), Mock.class).keySet();
		for (Field f : annotatedFieldToInject) {
			mockedClasses.add(f.getType());
		}
	}

	@Before
	public void setupAbstractGwtEasyMockTest() {
		for (Class<?> clazz : mockedClasses) {
			Object mock = EasyMock.createMock(clazz);
			addMockedObject(clazz, mock);
		}
		try {
			for (Field f : annotatedFieldToInject) {
				Object mock = mockObjects.get(f.getType());
				GwtReflectionUtils.makeAccessible(f);
				f.set(this, mock);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error during gwt-test-utils @Mock creation", e);
		}
	}

	@After
	public void teardownAbstractGwtEasyMockTest() {
		mockObjects.clear();
	}

	/**
	 * Set all declared mocks to replay state.
	 */
	public void replay() {
		for (Object o : mockObjects.values()) {
			EasyMock.replay(o);
		}
	}

	/**
	 * Verifies that all recorded behaviors for every declared mock has actually
	 * been used.
	 */
	public void verify() {
		for (Object o : mockObjects.values()) {
			EasyMock.verify(o);
		}
	}

	/**
	 * Reset all declared mocks.
	 */
	public void reset() {
		for (Object o : mockObjects.values()) {
			EasyMock.reset(o);
		}
	}

	/**
	 * Records a call to an asynchronous service and simulates a failure by
	 * calling the onFailure() method of the corresponding AsyncCallback object.
	 */
	public void expectServiceAndCallbackOnFailure(final Throwable exception) {
		IAnswer<Object> answer = new FailureAnswer<Object>(exception);
		EasyMock.expectLastCall().andAnswer(answer);
	}

	/**
	 * Records a call to an asynchronous service and simulates a success by
	 * calling the onSuccess() method of the corresponding AsyncCallback object.
	 */
	@SuppressWarnings("unchecked")
	public <T> void expectServiceAndCallbackOnSuccess(final T object) {
		IAnswer<T> answer = new SuccessAnswer<T>(object);
		EasyMock.expectLastCall().andAnswer((IAnswer<Object>) answer);
	}

	/**
	 * Creates a mock object for a given class, where all methods are mocked
	 * except the one with the name given as a parameter.
	 */
	public <T> T createMockAndKeepOneMethod(Class<T> clazz, String methodName) {
		return createMockAndKeepMethods(clazz, true, GwtReflectionUtils.findMethod(clazz, methodName));
	}

	/**
	 * Creates a mock object for a given class, where all methods are mocked
	 * except the ones given as parameters.
	 * 
	 * @param clazz
	 *            The class for which a mock object will be created
	 * @param keepSetters
	 *            False if setters should be mocked, true otherwise
	 * @param list
	 *            List of methods that should not be mocked
	 */
	public <T> T createMockAndKeepMethods(Class<T> clazz, final boolean keepSetters, final Method... list) {
		final List<Method> l = new ArrayList<Method>();
		GwtReflectionUtils.doWithMethods(clazz, new MethodCallback() {

			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				if (!ArrayUtils.contains(list, method)) {
					if (!keepSetters || !method.getName().startsWith("set") || method.getReturnType() != void.class) {
						l.add(method);
					}
				}
			}

		});
		T o = EasyMock.createMockBuilder(clazz).addMockedMethods(l.toArray(new Method[] {})).createMock();
		addMockedObject(clazz, o);
		return o;
	}

	/**
	 * Adds a mock object to the list of mocks used in the context of this test
	 * class.
	 * 
	 * You can use this method if you want to use AbstractGwtEasyMockTest's mock
	 * handling methods ({@link GwtTestWithEasyMock#replay()},
	 * {@link GwtTestWithEasyMock#verify()}, {@link GwtTestWithEasyMock#reset()}
	 * ...) for a mock that you created without using the framework's
	 * {@link Mock @Mock} annotation.
	 * 
	 * @param clazz
	 *            The class for which a mock object is being defined
	 * @param mock
	 *            the mock instance
	 */
	public Object addMockedObject(Class<?> createClass, Object mock) {
		return mockObjects.put(createClass, mock);
	}

	private static class FailureAnswer<Z> implements IAnswer<Z> {

		private Throwable result;

		public FailureAnswer(Throwable result) {
			this.result = result;
		}

		@SuppressWarnings("unchecked")
		public Z answer() throws Throwable {
			final Object[] arguments = EasyMock.getCurrentArguments();
			AsyncCallback<Z> callback = (AsyncCallback<Z>) arguments[arguments.length - 1];
			callback.onFailure(result);
			return null;
		}

	}

	private static class SuccessAnswer<Z> implements IAnswer<Z> {

		private Z result;

		public SuccessAnswer(Z result) {
			this.result = result;
		}

		@SuppressWarnings("unchecked")
		public Z answer() throws Throwable {
			final Object[] arguments = EasyMock.getCurrentArguments();
			AsyncCallback<Z> callback = (AsyncCallback<Z>) arguments[arguments.length - 1];
			callback.onSuccess(result);
			return null;
		}

	}

}
