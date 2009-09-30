package com.octo.gwt.test17.integ.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gwt.user.client.rpc.RemoteService;
import com.octo.gwt.test17.GwtCreateHandler;

public abstract class GwtCreateHandlerImpl implements GwtCreateHandler {

	private static final Logger logger = Logger.getLogger(GwtCreateHandlerImpl.class);

	private Map<Method, IDeserializationCallback> callbacks;

	private IGwtRpcExceptionHandler exceptionHandler;
	
	public GwtCreateHandlerImpl() {
		this(new DefaultGwtRpcExceptionHandler());
	}

	public GwtCreateHandlerImpl(IGwtRpcExceptionHandler exceptionHandler) {
		this(exceptionHandler, new HashMap<Method, IDeserializationCallback>());
	}

	public GwtCreateHandlerImpl(IGwtRpcExceptionHandler exceptionHandler, Map<Method, IDeserializationCallback> callbacks) {
		this.callbacks = callbacks;
		this.exceptionHandler = exceptionHandler;
	}

	public abstract Object findService(String serviceName);
	
	public Object create(Class<?> classLiteral) {
		logger.debug("Try to create class " + classLiteral.getCanonicalName());
		try {
			if (RemoteService.class.isAssignableFrom(classLiteral)) {
				String asyncName = classLiteral.getCanonicalName() + "Async";
				Class<?> asyncClazz = Class.forName(asyncName);
				if (asyncClazz == null) {
					throw new Exception("Async class not found : " + asyncName);
				}
				String serviceName = classLiteral.getSimpleName();
				if (serviceName.startsWith("I")) {
					serviceName = serviceName.substring(1);
				}
				logger.debug("Searching service named : " + serviceName);
				Object service = findService(serviceName);
				if (service == null) {
					logger.error("Service not found " + serviceName);
					throw new RuntimeException("Service not found " + serviceName);
				}
				InvocationHandler handler = new GwtRpcInvocationHandler(asyncClazz, service, callbacks, exceptionHandler);
				return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { asyncClazz }, handler);
			}
			logger.debug("Creating class " + classLiteral.getCanonicalName());
			Object o = classLiteral.newInstance();
			return o;
		} catch (Exception e) {
			logger.error("Unable to create class", e);
			throw new RuntimeException("Unable to create class " + classLiteral.getCanonicalName(), e);
		}
	}

}
