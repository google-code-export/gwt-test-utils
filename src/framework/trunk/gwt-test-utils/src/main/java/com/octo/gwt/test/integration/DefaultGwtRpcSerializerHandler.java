package com.octo.gwt.test.integration;

import java.io.Serializable;

import org.apache.commons.lang.SerializationUtils;

public class DefaultGwtRpcSerializerHandler implements IGwtRpcSerializerHandler {

	@SuppressWarnings("unchecked")
	public <T> T serializeUnserialize(T o) throws Exception {
		return (T) SerializationUtils.clone((Serializable) o);
	}

}
