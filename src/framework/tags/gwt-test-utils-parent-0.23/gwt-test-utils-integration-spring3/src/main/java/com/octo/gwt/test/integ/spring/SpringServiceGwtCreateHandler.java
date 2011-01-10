package com.octo.gwt.test.integ.spring;

import org.springframework.context.ApplicationContext;

import com.octo.gwt.test.integ.handler.RemoteServiceCreateHandler;

public abstract class SpringServiceGwtCreateHandler extends RemoteServiceCreateHandler {
	
	private ApplicationContext applicationContext;
	
	public SpringServiceGwtCreateHandler(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	protected Object findService(Class<?> remoteServiceClass, String remoteServiceRelativePath) {
		return findInSpringContext(applicationContext, remoteServiceClass, remoteServiceRelativePath);
	}
	
	protected abstract Object findInSpringContext(ApplicationContext applicationContext, Class<?> remoteServiceClass, String remoteServiceRelativePath);



}
