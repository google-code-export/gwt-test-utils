package com.octo.gwt.test.integ.handler;

import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.client.rpc.RemoteService;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.utils.DeserializationContext;
import com.octo.gwt.test.utils.ISerializeCallback;

public abstract class RemoteServiceCreateHandler implements GwtCreateHandler {

	private static final Logger logger = LoggerFactory.getLogger(RemoteServiceCreateHandler.class);

	private DeserializationContext backToGwtCallbacks;

	private DeserializationContext fromGwtCallbacks;

	private IGwtRpcExceptionHandler exceptionHandler;

	public RemoteServiceCreateHandler() {
		this.exceptionHandler = new DefaultGwtRpcExceptionHandler();
		this.backToGwtCallbacks = new DeserializationContext();
		this.fromGwtCallbacks = new DeserializationContext();
	}

	public void addBackToGwtCallbacks(Class<?> clazz, ISerializeCallback callback) {
		this.backToGwtCallbacks.put(clazz, callback);
	}

	public void addFromGwtCallbacks(Class<?> clazz, ISerializeCallback callback) {
		this.fromGwtCallbacks.put(clazz, callback);
	}

	public void setExceptionHandler(IGwtRpcExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	public abstract Object findService(Class<?> remoteServiceClazz);

	protected Object createObject(Class<?> classLiteral) throws Exception {
		return null;
	}

	public Object create(Class<?> classLiteral) throws Exception {
		logger.debug("Try to create class " + classLiteral.getCanonicalName());

		if (RemoteService.class.isAssignableFrom(classLiteral)) {
			String asyncName = classLiteral.getCanonicalName() + "Async";
			Class<?> asyncClazz = Class.forName(asyncName);
			if (asyncClazz == null) {
				throw new Exception("Async class not found : " + asyncName);
			}
			logger.debug("Searching service implementing " + classLiteral.getCanonicalName());
			Object service = findService(classLiteral);
			if (service == null) {
				logger.error("Service not found " + classLiteral.getCanonicalName());
				throw new RuntimeException("Service not found " + classLiteral.getCanonicalName());
			}
			GwtRpcInvocationHandler handler = new GwtRpcInvocationHandler(asyncClazz, service);
			handler.setExceptionHandler(exceptionHandler);
			handler.setBackToGwtCallbacks(backToGwtCallbacks);
			handler.setFromGwtCallbacks(fromGwtCallbacks);
			return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { asyncClazz }, handler);
		}
		logger.debug("Creating class " + classLiteral.getCanonicalName());
		Object o = createObject(classLiteral);
		return o;
	}

}
