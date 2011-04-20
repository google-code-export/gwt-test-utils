package com.octo.gwt.test.i18n;

import java.util.Map;

import com.google.gwt.i18n.client.Constants;

public interface MyConstants extends Constants {

	@DefaultBooleanValue(true)
	public boolean functionBoolean();

	@DefaultDoubleValue(6.6)
	public double functionDouble();

	@DefaultFloatValue((float) 6.66)
	public float functionFloat();

	@DefaultIntValue(6)
	public int functionInt();

	public String goodbye();

	@DefaultStringValue("hello from @DefaultStringValue")
	public String hello();

	@DefaultStringMapValue({ "map1", "default map1 value", "map2", "default map2 value", "map3", "default map3 value" })
	public Map<String, Object> map();

	@DefaultStringArrayValue({ "default0", "default1" })
	public String[] stringArray();

}
