package com.octo.gwt.test.integ.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.Descriptor;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.octo.gwt.test.internal.PatchGwtClassPool;
import com.octo.gwt.test.internal.modifiers.JavaClassModifier;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

public class ExternalizableModifier implements JavaClassModifier {

	public void modify(CtClass classToModify) throws Exception {
		boolean modified = false;

		if (classToModify.isInterface() || classToModify.isPrimitive() || classToModify.isEnum() || classToModify.isArray()
				|| classToModify.isAnnotation()) {
			return;
		}

		CtClass charSequenceCtClass = PatchGwtClassPool.getCtClass(CharSequence.class);

		if (classToModify.subtypeOf(charSequenceCtClass)) {
			return;
		}

		CtClass externalizableCtClass = PatchGwtClassPool.getCtClass(Externalizable.class);
		if (classToModify.subtypeOf(externalizableCtClass)) {
			// Externalizable object which is not serialized by GWT RPC
			return;
		}

		CtClass IsSerializableCtClass = PatchGwtClassPool.getCtClass(IsSerializable.class);
		if (classToModify.subtypeOf(IsSerializableCtClass)) {
			removeInterface(classToModify, IsSerializableCtClass);
			modified = true;
		}
		CtClass serializableCtClass = PatchGwtClassPool.getCtClass(Serializable.class);
		if (classToModify.subtypeOf(serializableCtClass)) {
			removeInterface(classToModify, serializableCtClass);
			modified = true;
		}

		if (modified) {
			classToModify.addInterface(externalizableCtClass);

			// add Externalizable.readExternal(..) method
			CtClass ObjectInputCtClass = PatchGwtClassPool.getCtClass(ObjectInput.class);
			CtMethod readExternalMethod = new CtMethod(CtClass.voidType, "readExternal", new CtClass[] { ObjectInputCtClass }, classToModify);
			readExternalMethod.setBody(getExternalizableMethodBody("readExternal"));
			readExternalMethod.setModifiers(Modifier.PUBLIC);
			classToModify.addMethod(readExternalMethod);

			// add Externalizable.writeExternal(..) method
			CtClass ObjectOutputCtClass = PatchGwtClassPool.getCtClass(ObjectOutput.class);
			CtMethod writeExternalMethod = new CtMethod(CtClass.voidType, "writeExternal", new CtClass[] { ObjectOutputCtClass }, classToModify);
			writeExternalMethod.setBody(getExternalizableMethodBody("writeExternal"));
			writeExternalMethod.setModifiers(Modifier.PUBLIC);
			classToModify.addMethod(writeExternalMethod);

			// ExternalizableInstance should have an empty public constructor
			CtConstructor defaultCons = null;
			try {
				defaultCons = classToModify.getConstructor(Descriptor.ofConstructor(new CtClass[0]));
				defaultCons.setModifiers(Modifier.PUBLIC);
			} catch (NotFoundException e) {
				// this class should never be serialized by GWT RPC
			}
		}
	}

	public static void writeExternal(Externalizable ex, ObjectOutput out) throws IOException {
		for (Field f : GwtTestReflectionUtils.getFields(ex.getClass())) {
			try {

				if (Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers())) {
					continue;
				}

				GwtTestReflectionUtils.makeAccessible(f);

				Object o = f.get(ex);

				Class<?> clazz = f.getType();

				if (Modifier.isTransient(f.getModifiers())) {
					continue;
				} else if (clazz.isPrimitive()) {
					if (Boolean.TYPE == clazz) {
						out.writeBoolean((Boolean) o);
					} else if (Character.TYPE == clazz) {
						out.writeChar((Character) o);
					} else if (Double.TYPE == clazz) {
						out.writeDouble((Double) o);
					} else if (Float.TYPE == clazz) {
						out.writeFloat((Float) o);
					} else if (Integer.TYPE == clazz) {
						out.writeInt((Integer) o);
					} else if (Long.TYPE == clazz) {
						out.writeLong((Long) o);
					} else if (Short.TYPE == clazz) {
						out.writeShort((Short) o);
					}
				} else if (clazz == String.class) {
					out.writeUTF((String) o);
				} else {
					out.writeObject(o);
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Error during serialization of object " + ex.getClass().getName(), e);
			}
		}
	}

	public static void readExternal(Externalizable ex, ObjectInput in) throws IOException {

		Object prototype = null;

		for (Field f : GwtTestReflectionUtils.getFields(ex.getClass())) {
			try {

				if (Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers())) {
					continue;
				}

				GwtTestReflectionUtils.makeAccessible(f);

				Class<?> clazz = f.getType();

				if (Modifier.isTransient(f.getModifiers())) {
					if (prototype == null) {
						prototype = ex.getClass().newInstance();
					}
					f.set(ex, f.get(prototype));

				} else if (clazz.isPrimitive()) {
					if (Boolean.TYPE == clazz) {
						f.set(ex, in.readBoolean());
					} else if (Character.TYPE == clazz) {
						f.set(ex, in.readChar());
					} else if (Double.TYPE == clazz) {
						f.set(ex, in.readDouble());
					} else if (Float.TYPE == clazz) {
						f.set(ex, in.readFloat());
					} else if (Integer.TYPE == clazz) {
						f.set(ex, in.readInt());
					} else if (Long.TYPE == clazz) {
						f.set(ex, in.readLong());
					} else if (Short.TYPE == clazz) {
						f.set(ex, in.readShort());
					}
				} else if (clazz == String.class) {
					f.set(ex, in.readUTF());
				} else {
					f.set(ex, in.readObject());
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Error during deserialization of object " + ex.getClass().getName(), e);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Error during deserialization of object " + ex.getClass().getName(), e);
			} catch (InstantiationException e) {
				throw new RuntimeException("Error during deserialization of object " + ex.getClass().getName(), e);
			}
		}
	}

	private void removeInterface(CtClass classToModify, CtClass ctInterface) throws NotFoundException {
		List<CtClass> interfaces = new ArrayList<CtClass>();
		for (CtClass ctClass : classToModify.getInterfaces()) {
			if (!ctClass.equals(ctInterface)) {
				interfaces.add(ctClass);
			}
		}
		classToModify.setInterfaces(interfaces.toArray(new CtClass[0]));
	}

	private String getExternalizableMethodBody(String methodName) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("{");
		buffer.append(this.getClass().getName() + "." + methodName);
		buffer.append("((").append(Externalizable.class.getName());
		buffer.append(") this, $1);}");

		return buffer.toString();
	}

}
