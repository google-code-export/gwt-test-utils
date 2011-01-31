package com.octo.gwt.test.integ.handler;

import java.lang.reflect.Proxy;

import javassist.CtClass;
import javassist.bytecode.annotation.AnnotationImpl;
import javassist.bytecode.annotation.StringMemberValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.integ.internal.GwtRpcInvocationHandler;
import com.octo.gwt.test.internal.PatchGwtClassPool;

public abstract class RemoteServiceCreateHandler implements GwtCreateHandler {

	private static final Logger logger = LoggerFactory.getLogger(RemoteServiceCreateHandler.class);

	private IGwtRpcExceptionHandler exceptionHandler;
	private IGwtRpcSerializerHandler serializerHander;

	public RemoteServiceCreateHandler() {
		exceptionHandler = getExceptionHandler();
		serializerHander = getSerializerHandler();
	}

	public IGwtRpcSerializerHandler getSerializerHandler() {
		return new DefaultGwtRpcSerializerHandler();
	}

	public IGwtRpcExceptionHandler getExceptionHandler() {
		return new DefaultGwtRpcExceptionHandler();
	}

	protected abstract Object findService(Class<?> remoteServiceClass, String remoteServiceRelativePath);

	public Object create(Class<?> classLiteral) throws Exception {

		if (!RemoteService.class.isAssignableFrom(classLiteral)) {
			return null;
		}

		String className = classLiteral.getName();
		logger.debug("Try to create Remote service class " + className);

		String asyncName = className + "Async";
		String relativePath = getRemoveServiceRelativePath(classLiteral);
		Class<?> asyncClazz = Class.forName(asyncName);
		if (asyncClazz == null) {
			throw new Exception("Remote serivce Async class not found : " + asyncName);
		}
		logger.debug("Searching remote service implementing " + className);
		Object service = findService(classLiteral, relativePath);
		if (service == null) {
			logger.error("Remote service not found " + className);
			throw new RuntimeException("Remote service not found " + className);
		}

		GwtRpcInvocationHandler handler = new GwtRpcInvocationHandler(asyncClazz, service, exceptionHandler, serializerHander);

		return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { asyncClazz }, handler);
	}

	private String getRemoveServiceRelativePath(Class<?> clazz) {
		CtClass ctClass = PatchGwtClassPool.getCtClass((clazz));
		Object[] annotations = ctClass.getAvailableAnnotations();
		for (Object o : annotations) {
			if (Proxy.isProxyClass(o.getClass())) {
				AnnotationImpl annotation = (AnnotationImpl) Proxy.getInvocationHandler(o);
				if (annotation.getTypeName().equals(RemoteServiceRelativePath.class.getName())) {
					return ((StringMemberValue) annotation.getAnnotation().getMemberValue("value")).getValue();
				}
			}
		}
		throw new RuntimeException("Cannot find the '@" + RemoteServiceRelativePath.class.getSimpleName()
				+ "' annotation on RemoteService interface '" + clazz.getName() + "'");
	}

}
