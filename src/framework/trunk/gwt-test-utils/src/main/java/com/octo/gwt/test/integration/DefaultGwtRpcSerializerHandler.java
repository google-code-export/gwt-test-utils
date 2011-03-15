package com.octo.gwt.test.integration;

import java.io.Externalizable;
import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.octo.gwt.test.GwtClassLoader;
import com.octo.gwt.test.integration.internal.DeepCopy;
import com.octo.gwt.test.integration.internal.SerializableModifier;

/**
 * <p>
 * The default GwtRpcSerializerHandler, which simulate a GWT-RPC serialization.
 * </p>
 * 
 * <p>
 * GWT-RPC serialization means translation from JavaScript to Java and to Java
 * back to JavaScript. It's not just like Java Serialization : transient fields
 * are not set to null after the GWT-RPC serialization, they are set to theirs
 * initial value, after the default constructor has been called.
 * </p>
 * 
 * <p>
 * To simulate this behaviour, a lot of work is done behind the scene. While it
 * is loading a class, the {@link GwtClassLoader} knows if the class is attempt
 * to be compiled in JavaScript. If it is and if it can be serialized (by
 * implementing {@link IsSerializable} or {@link Serializable} interface), it
 * does a lot of bytecode manipulation (through the internal
 * {@link SerializableModifier} class) :
 * <ul>
 * <li>it replace the potential {@link IsSerializable} reference to
 * {@link Serializable} to make the class Java-serializable.</li>
 * <li>if the class does not provide a default constructor (which is required by
 * GWT-RPC serializer : it means the class will never be RPC-Serialized. The
 * class is not modified.</li>
 * <li>if the class extends {@link Externalizable} instead of
 * {@link Serializable} : it means the class will never be RPC-Serialized. The
 * class is not modified.</li>
 * <li>Otherwise, it copies the default constructor instructions in a new member
 * method.</li>
 * <li>then, it override the private Object.readObject(ObjectInputStream), a
 * callback which is called during a Java-Serialization : it call the default
 * serialization method and then the new method, so transient field are set with
 * theirs initial value.</li>
 * </ul>
 * After those bytecode modification on a class, its Java-serialization will
 * have the same behavior as the GWT-RPC one.
 * </p>
 * 
 * <p>
 * So, this default implementation will only perform an java serialization with
 * a set of optimized object from the internal API. ({@link DeepCopy}).
 * </p>
 * 
 * @author Gael Lazzari
 * 
 */
public class DefaultGwtRpcSerializerHandler implements GwtRpcSerializerHandler {

	public <T> T serializeUnserialize(T o) throws Exception {
		return DeepCopy.copy(o);
	}

}
