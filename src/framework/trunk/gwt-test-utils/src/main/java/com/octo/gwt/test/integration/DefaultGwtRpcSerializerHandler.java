package com.octo.gwt.test.integration;

import com.octo.gwt.test.integration.internal.DeepCopy;

public class DefaultGwtRpcSerializerHandler implements IGwtRpcSerializerHandler {

	public <T> T serializeUnserialize(T o) throws Exception {
		return DeepCopy.copy(o);
	}

}
