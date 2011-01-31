package com.octo.gwt.test.integ.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

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

		// if not exists, add a private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException method
		CtClass[] paramTypes = new CtClass[] { PatchGwtClassPool.getCtClass(ObjectInputStream.class) };
		try {
			classToModify.getDeclaredMethod("readObject", paramTypes);
		} catch (NotFoundException e) {
			overrideReadObject(classToModify);
		}

		// Serializable Instance should have an empty public constructor
		CtConstructor defaultCons = null;
		try {
			defaultCons = classToModify.getConstructor(Descriptor.ofConstructor(new CtClass[0]));
			defaultCons.setModifiers(Modifier.PUBLIC);
		} catch (NotFoundException e) {
			// this class should never be serialized by GWT RPC
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
		// call the default read method
		ois.defaultReadObject();

		Object prototype = null;

		try {
			// handle transient fields
			for (Field f : GwtTestReflectionUtils.getFields(ex.getClass())) {
				if (Modifier.isTransient(f.getModifiers()) && !Modifier.isFinal(f.getModifiers()) && !Modifier.isStatic(f.getModifiers())) {

					GwtTestReflectionUtils.makeAccessible(f);

					if (prototype == null) {
						prototype = ex.getClass().newInstance();
					}

					f.set(ex, f.get(prototype));
				}
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error during deserialization of object " + ex.getClass().getName(), e);
		} catch (InstantiationException e) {
			throw new RuntimeException("Error during deserialization of object " + ex.getClass().getName(), e);
		}

	}

}
