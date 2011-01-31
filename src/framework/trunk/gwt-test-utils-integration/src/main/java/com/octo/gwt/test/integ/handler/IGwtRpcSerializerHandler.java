package com.octo.gwt.test.integ.handler;

public interface IGwtRpcSerializerHandler {

	public <T> T serializeUnserialize(T o) throws Exception;

}
