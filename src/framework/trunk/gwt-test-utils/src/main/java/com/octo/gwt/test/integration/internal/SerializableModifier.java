package com.octo.gwt.test.integration.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.Descriptor;

import com.octo.gwt.test.internal.PatchGwtClassPool;
import com.octo.gwt.test.internal.modifiers.JavaClassModifier;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

public class SerializableModifier implements JavaClassModifier {

	private static final String DEFAULT_CONS_METHOD_NAME = "DEFAULT_CONS_METHOD";

	public void modify(CtClass classToModify) throws Exception {

		if (classToModify.isInterface() || classToModify.isPrimitive() || classToModify.isEnum() || classToModify.isArray()
				|| classToModify.isAnnotation() || Modifier.isAbstract(classToModify.getModifiers())) {
			return;
		}

		CtClass charSequenceCtClass = PatchGwtClassPool.getCtClass(CharSequence.class);
		if (classToModify.subtypeOf(charSequenceCtClass)) {
			return;
		}

		// Externalizable object which is not serialized by GWT RPC
		CtClass externalizableCtClass = PatchGwtClassPool.getCtClass(Externalizable.class);
		if (classToModify.subtypeOf(externalizableCtClass)) {
			return;
		}

		CtClass serializableCtClass = PatchGwtClassPool.getCtClass(Serializable.class);
		if (!classToModify.subtypeOf(serializableCtClass)) {
			return;
		}

		if (getReadObjectMethod(classToModify) != null) {
			// this class should never be serialized by GWT RPC
			return;
		}

		// GWT RPC Serializable objects should have an empty constructor
		CtConstructor defaultCons = getDefaultConstructor(classToModify);
		if (defaultCons == null) {
			return;
		}

		CtMethod defaultConstMethod = defaultCons.toMethod(DEFAULT_CONS_METHOD_NAME, classToModify);
		defaultConstMethod.setModifiers(Modifier.PUBLIC);
		classToModify.addMethod(defaultConstMethod);

		overrideReadObject(classToModify);
	}

	private CtConstructor getDefaultConstructor(CtClass ctClass) {
		try {
			return ctClass.getConstructor(Descriptor.ofConstructor(new CtClass[0]));
		} catch (NotFoundException e) {
			return null;
		}
	}

	private CtMethod getReadObjectMethod(CtClass ctClass) {
		CtClass[] paramTypes = new CtClass[] { PatchGwtClassPool.getCtClass(ObjectInputStream.class) };
		try {
			return ctClass.getDeclaredMethod("readObject", paramTypes);
		} catch (NotFoundException e) {
			return null;
		}

	}

	private void overrideReadObject(CtClass classToModify) throws NotFoundException, CannotCompileException {
		CtClass[] paramTypes = new CtClass[] { PatchGwtClassPool.getCtClass(ObjectInputStream.class) };
		CtMethod readObjectMethod = new CtMethod(CtClass.voidType, "readObject", paramTypes, classToModify);
		readObjectMethod.setModifiers(Modifier.PRIVATE);

		// add exception types
		CtClass classNotFoundException = PatchGwtClassPool.getCtClass(ClassNotFoundException.class);
		CtClass ioException = PatchGwtClassPool.getCtClass(IOException.class);
		readObjectMethod.setExceptionTypes(new CtClass[] { classNotFoundException, ioException });

		// set body (call static readObject(Serializable, ObjectInputStream)
		readObjectMethod.setBody(callStaticReadObject());

		classToModify.addMethod(readObjectMethod);
	}

	private String callStaticReadObject() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("{");
		buffer.append(this.getClass().getName() + ".readObject(");
		buffer.append("(").append(Serializable.class.getName()).append(")");
		buffer.append(" this, $1);}");

		return buffer.toString();
	}

	public static void readObject(Serializable ex, ObjectInputStream ois) throws ClassNotFoundException, IOException {
		try {
			// call the default read method
			ois.defaultReadObject();

			if (ex.getClass().getName().endsWith("DeliveryAddressBlockModel")) {
				System.out.println(ex.getClass());
			}

			// keep non transient/static/final value somhere
			Map<Field, Object> buffer = getFieldValues(ex);

			// call the exported default constructor to reinitialise triansient field values
			// which are not expected to be null
			GwtTestReflectionUtils.callPrivateMethod(ex, DEFAULT_CONS_METHOD_NAME);

			// set the kept field values
			for (Map.Entry<Field, Object> entry : buffer.entrySet()) {
				entry.getKey().set(ex, entry.getValue());
			}

		} catch (Exception e) {
			throw new RuntimeException("Error during deserialization of object " + ex.getClass().getName(), e);
		}

	}

	private static Map<Field, Object> getFieldValues(Serializable o) throws IllegalArgumentException, IllegalAccessException {
		Map<Field, Object> result = new HashMap<Field, Object>();

		for (Field field : GwtTestReflectionUtils.getFields(o.getClass())) {
			int fieldModifier = field.getModifiers();
			if (!Modifier.isFinal(fieldModifier) && !Modifier.isStatic(fieldModifier) && !Modifier.isTransient(fieldModifier)) {
				result.put(field, field.get(o));
			}
		}

		return result;
	}
}
