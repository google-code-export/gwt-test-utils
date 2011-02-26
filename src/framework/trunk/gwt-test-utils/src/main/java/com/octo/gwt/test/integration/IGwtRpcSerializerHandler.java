package com.octo.gwt.test.integration;

public interface IGwtRpcSerializerHandler {

	public <T> T serializeUnserialize(T o) throws Exception;

}
