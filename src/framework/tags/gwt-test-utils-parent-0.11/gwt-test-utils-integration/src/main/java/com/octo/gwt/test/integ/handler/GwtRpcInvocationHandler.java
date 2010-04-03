package com.octo.gwt.test.integ.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

public class GwtRpcInvocationHandler implements InvocationHandler {

	private static final Logger logger = Logger.getLogger(GwtRpcInvocationHandler.class);

	private Object target;

	private HashMap<Method, Method> methodTable;

	private Map<Method, IDeserializationCallback> callbacks;

	private IGwtRpcExceptionHandler exceptionHandler;

	public GwtRpcInvocationHandler(Class<?> asyncClazz, Object target, Map<Method, IDeserializationCallback> callbacks,
			IGwtRpcExceptionHandler exceptionHandler) {
		this.target = target;
		this.callbacks = callbacks;
		this.exceptionHandler = exceptionHandler;
		this.methodTable = new HashMap<Method, Method>();
		for (Method m : asyncClazz.getMethods()) {
			for (Method m2 : target.getClass().getMethods()) {
				if (m.getName().equals(m2.getName()) && m.getParameterTypes().length == m2.getParameterTypes().length + 1) {
					methodTable.put(m, m2);
					m2.setAccessible(true);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object[] subArgs = new Object[args.length - 1];
		for (int i = 0; i < args.length - 1; i++) {
			subArgs[i] = args[i];
		}
		AsyncCallback<Object> callback = (AsyncCallback<Object>) args[args.length - 1];
		Method m = methodTable.get(method);
		if (m == null) {
			logger.error("Method not found " + method);
			callback.onFailure(new StatusCodeException(500, "No method found"));
		}
		try {
			logger.debug("Invoking " + m + " on " + target.getClass().getCanonicalName());
			// Serialize objects
			Object[] serializedArgs = new Object[subArgs.length];
			for (int i = 0; i < subArgs.length; i++) {
				serializedArgs[i] = GwtTestReflectionUtils.serializeUnserialize(subArgs[i]);
			}
			Object returnValue = m.invoke(target, serializedArgs);
			Object o = GwtTestReflectionUtils.serializeUnserialize(returnValue);
			IDeserializationCallback deserializationCallback = callbacks.get(method);
			if (deserializationCallback != null) {
				o = deserializationCallback.callaback(o);
			}
			logger.debug("Result of " + m.getName() + " : " + o);
			callback.onSuccess(o);
		} catch (InvocationTargetException e) {
			logger.info("Exception when invoking service throw to handler " + e.getMessage());
			exceptionHandler.handle(e, callback);
			return null;
		} catch (IllegalAccessException e) {
			logger.error("Invokation exception : " + e.toString(), e);
			callback.onFailure(new StatusCodeException(500, e.toString()));
		} catch (IllegalArgumentException e) {
			logger.error("Invokation exception : " + e.toString(), e);
			callback.onFailure(new StatusCodeException(500, e.toString()));
		}
		return null;
	}

}
