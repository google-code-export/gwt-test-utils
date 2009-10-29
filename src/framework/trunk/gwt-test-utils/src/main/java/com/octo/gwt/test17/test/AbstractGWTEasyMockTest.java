package com.octo.gwt.test17.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.easymock.IAnswer;
import org.easymock.classextension.EasyMock;
import org.junit.Before;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.octo.gwt.test17.Mock;
import com.octo.gwt.test17.PatchGWT;
import com.octo.gwt.test17.ReflectionUtils;

public abstract class AbstractGWTEasyMockTest extends AbstractGWTTest {

	private List<Class<?>> mockList = new ArrayList<Class<?>>();

	private List<Field> annotatedFieldToInject;

	protected Hashtable<Class<?>, Object> mockedObject = new Hashtable<Class<?>, Object>();
	
	public AbstractGWTEasyMockTest() {
		annotatedFieldToInject = ReflectionUtils.getAnnotatedField(this, Mock.class);
		for (Field f : annotatedFieldToInject) {
			mockList.add(f.getType());
		}
	}

	protected void addMockedObject(Class<?> createClass, Class<?> mockClass, Object mock) {
		PatchGWT.addCreateClass(createClass, mock);
		mockedObject.put(mockClass, mock);
	}

	@Before
	public void setUpEasyMock() throws Exception {
		for (Class<?> clazz : mockList) {
			if (clazz.getName().endsWith("Async")) {
				Class<?> clazz2 = Class.forName(clazz.getCanonicalName().substring(0, clazz.getCanonicalName().length() - "Async".length()));
				Object mock = EasyMock.createMock(clazz);
				addMockedObject(clazz2, clazz, mock);
			} else {
				Object mock = EasyMock.createMock(clazz);
				addMockedObject(clazz, clazz, mock);
			}
		}
		for (Field f : annotatedFieldToInject) {
			Object mock = mockedObject.get(f.getType());
			f.setAccessible(true);
			f.set(this, mock);
		}
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
	
}
