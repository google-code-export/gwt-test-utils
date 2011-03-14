package com.octo.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.js.JsArray;
import com.google.gwt.core.client.JavaScriptObject;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gxt.test.internal.overrides.JsArrayJSO;

@PatchClass(JsArray.class)
public class JsArrayPatcher extends AutomaticPatcher {

	@PatchMethod
	public static JavaScriptObject create(JsArray jsArray) {
		return new JsArrayJSO();
	}

	@PatchMethod
	public static void add(JsArray jsArray, boolean value) {
		getJsArrayJSO(jsArray).add(value);
	}

	@PatchMethod
	public static void add(JsArray jsArray, byte value) {
		getJsArrayJSO(jsArray).add(value);
	}

	@PatchMethod
	public static void add(JsArray jsArray, char value) {
		getJsArrayJSO(jsArray).add(value);
	}

	@PatchMethod
	public static void add(JsArray jsArray, double value) {
		getJsArrayJSO(jsArray).add(value);
	}

	@PatchMethod
	public static void add(JsArray jsArray, float value) {
		getJsArrayJSO(jsArray).add(value);
	}

	@PatchMethod
	public static void add(JsArray jsArray, int value) {
		getJsArrayJSO(jsArray).add(value);
	}

	@PatchMethod
	public static void add(JsArray jsArray, JavaScriptObject value) {
		getJsArrayJSO(jsArray).add(value);
	}

	@PatchMethod
	public static void add(JsArray jsArray, short value) {
		getJsArrayJSO(jsArray).add(value);
	}

	@PatchMethod
	public static void add(JsArray jsArray, String value) {
		getJsArrayJSO(jsArray).add(value);
	}

	@PatchMethod
	public static Object get(JsArray jsArray, int index) {
		return getJsArrayJSO(jsArray).get(index);
	}

	@PatchMethod
	public static boolean getBoolean(JsArray jsArray, int index) {
		return getJsArrayJSO(jsArray).getBoolean(index);
	}

	@PatchMethod
	public static byte getByte(JsArray jsArray, int index) {
		return getJsArrayJSO(jsArray).getByte(index);
	}

	@PatchMethod
	public static char getChar(JsArray jsArray, int index) {
		return getJsArrayJSO(jsArray).getChar(index);
	}

	@PatchMethod
	public static double getDouble(JsArray jsArray, int index) {
		return getJsArrayJSO(jsArray).getDouble(index);
	}

	@PatchMethod
	public static float getFloat(JsArray jsArray, int index) {
		return getJsArrayJSO(jsArray).getFloat(index);
	}

	@PatchMethod
	public static int getInt(JsArray jsArray, int index) {
		return getJsArrayJSO(jsArray).getInt(index);
	}

	@PatchMethod
	public static short getShort(JsArray jsArray, int index) {
		return getJsArrayJSO(jsArray).getShort(index);
	}

	@PatchMethod
	public static String getString(JsArray jsArray, int index) {
		return getJsArrayJSO(jsArray).getString(index);
	}

	@PatchMethod
	public static void addObjectInternal(JsArray jsArray, Object value) {
		getJsArrayJSO(jsArray).addObject(value);
	}

	private static JsArrayJSO getJsArrayJSO(JsArray jsArray) {
		return GwtReflectionUtils.getPrivateFieldValue(jsArray, "jsArray");
	}
}
