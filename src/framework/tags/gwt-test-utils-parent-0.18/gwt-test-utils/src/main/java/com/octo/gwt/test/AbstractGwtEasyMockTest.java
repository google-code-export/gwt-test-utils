package com.octo.gwt.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easymock.IAnswer;
import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Before;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.utils.ArrayUtils;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;
import com.octo.gwt.test.utils.PatchGwtUtils;
import com.octo.gwt.test.utils.GwtTestReflectionUtils.MethodCallback;

public abstract class AbstractGwtEasyMockTest extends AbstractGwtTest {

	private List<Class<?>> mockList = new ArrayList<Class<?>>();

	private Set<Field> annotatedFieldToInject;

	private Map<Class<?>, Object> mockedObject = new HashMap<Class<?>, Object>();

	public AbstractGwtEasyMockTest() {
		annotatedFieldToInject = GwtTestReflectionUtils.getAnnotatedField(this.getClass(), Mock.class);
		for (Field f : annotatedFieldToInject) {
			mockList.add(f.getType());
		}
	}

	@Before
	public void setUpEasyMock() throws Exception {
		for (Class<?> clazz : mockList) {
			Object mock = EasyMock.createMock(clazz);
			addMockedObject(clazz, mock);
		}
		for (Field f : annotatedFieldToInject) {
			Object mock = mockedObject.get(f.getType());
			f.setAccessible(true);
			f.set(this, mock);
		}

		addGwtCreateHandler(new GwtCreateMockedObjectHandler());
	}

	@After
	public void tearDownEasyMock() {
		mockedObject.clear();
	}

	private class GwtCreateMockedObjectHandler implements GwtCreateHandler {

		public Object create(Class<?> classLiteral) throws Exception {
			if (RemoteService.class.isAssignableFrom(classLiteral)) {
				String asyncName = classLiteral.getCanonicalName() + "Async";
				classLiteral = Class.forName(asyncName);
			}
			return mockedObject.get(classLiteral);
		}

	}

	protected void addMockedObject(Class<?> createClass, Object mock) {
		mockedObject.put(createClass, mock);
	}

	public void replay() {
		for (Object o : mockedObject.values()) {
			EasyMock.replay(o);
		}
	}

	public void verify() {
		for (Object o : mockedObject.values()) {
			EasyMock.verify(o);
		}
	}

	public void reset() {
		for (Object o : mockedObject.values()) {
			EasyMock.reset(o);
		}
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

	public static void expectServiceAndCallbackOnFailure(final Throwable exception) {
		IAnswer<Object> answer = new FailureAnswer<Object>(exception);
		EasyMock.expectLastCall().andAnswer(answer);
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

	@SuppressWarnings("unchecked")
	public static <T> void expectServiceAndCallbackOnSuccess(final T object) {
		IAnswer<T> answer = new SuccessAnswer<T>(object);
		EasyMock.expectLastCall().andAnswer((IAnswer<Object>) answer);
	}

	protected void mockAddToParent(Widget w, Widget parent) throws Exception {
		w.removeFromParent();
		EasyMock.expectLastCall();

		w.getElement();
		EasyMock.expectLastCall().andReturn(DOM.createAnchor());

		Method m = Widget.class.getDeclaredMethod("setParent", Widget.class);
		m.setAccessible(true);
		m.invoke(w, EasyMock.eq(parent));
		EasyMock.expectLastCall();

		if (PatchGwtUtils.areAssertionEnabled()) {
			w.getParent();
			EasyMock.expectLastCall().andReturn(null);
		}
	}

	protected <T> T createMockAndKeepOneMethod(Class<T> clazz, String methodName) {
		return createMockAndKeepMethods(clazz, true, GwtTestReflectionUtils.findMethod(clazz, methodName, null));
	}

	@SuppressWarnings("unchecked")
	protected <T> T createMockAndKeepMethods(Class<T> clazz, final boolean keepSetters, final Method... list) {
		final List<Method> l = new ArrayList<Method>();
		GwtTestReflectionUtils.doWithMethods(clazz, new MethodCallback() {

			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				if (!ArrayUtils.contains(list, method)) {
					if (!keepSetters || !method.getName().startsWith("set") || method.getReturnType() != void.class) {
						l.add(method);
					}
				}
			}

		});
		Object o = EasyMock.createMock(clazz, l.toArray(new Method[] {}));
		addMockedObject(clazz, o);
		return (T) o;
	}
}
