package com.octo.gwt.test.integ.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.octo.gwt.test.integ.handler.IGwtRpcExceptionHandler;
import com.octo.gwt.test.integ.handler.IGwtRpcSerializerHandler;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

public class GwtRpcInvocationHandler implements InvocationHandler {

	private static final Logger logger = LoggerFactory.getLogger(GwtRpcInvocationHandler.class);

	private Object target;
	private IGwtRpcExceptionHandler exceptionHandler;
	private IGwtRpcSerializerHandler serializerHander;
	private HashMap<Method, Method> methodTable;

	public GwtRpcInvocationHandler(Class<?> asyncClazz, Object target, IGwtRpcExceptionHandler exceptionHandler,
			IGwtRpcSerializerHandler serializerHandler) {
		this.target = target;
		this.exceptionHandler = exceptionHandler;
		this.serializerHander = serializerHandler;

		this.methodTable = new HashMap<Method, Method>();
		for (Method m : asyncClazz.getMethods()) {
			for (Method m2 : target.getClass().getMethods()) {
				if (m.getName().equals(m2.getName()) && m.getParameterTypes().length == m2.getParameterTypes().length + 1) {
					methodTable.put(m, m2);
					GwtTestReflectionUtils.makeAccessible(m2);
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
				try {
					serializedArgs[i] = serializerHander.serializeUnserialize(subArgs[i]);
				} catch (Exception e) {
					throw new RuntimeException("Error while serializing argument " + i + " of type " + subArgs[i].getClass().getName()
							+ " in method " + method.getDeclaringClass().getSimpleName() + "." + method.getName() + "(..)", e);
				}
			}
			Object resultObject = m.invoke(target, serializedArgs);
			Object returnObject = null;

			try {
				returnObject = serializerHander.serializeUnserialize(resultObject);
			} catch (Exception e) {
				throw new RuntimeException("Error while serializing object of type " + resultObject.getClass().getName()
						+ " which was returned from RPC Service " + method.getDeclaringClass().getSimpleName() + "." + method.getName() + "(..)", e);
			}

			logger.debug("Result of " + m.getName() + " : " + returnObject);
			callback.onSuccess(returnObject);

		} catch (InvocationTargetException e) {
			logger.info("Exception when invoking service throw to handler : " + e.getMessage());
			exceptionHandler.handle(e.getCause(), callback);
			return null;
		} catch (IllegalAccessException e) {
			logger.error("GWT RPC invokation error : ", e);
			callback.onFailure(new StatusCodeException(500, e.toString()));
		} catch (IllegalArgumentException e) {
			logger.error("GWT RPC invokation error : ", e);
			callback.onFailure(new StatusCodeException(500, e.toString()));
		}
		return null;
	}

}
