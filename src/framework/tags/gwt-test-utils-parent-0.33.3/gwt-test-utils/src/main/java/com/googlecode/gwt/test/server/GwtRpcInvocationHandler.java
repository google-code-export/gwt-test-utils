package com.googlecode.gwt.test.server;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.googlecode.gwt.test.exceptions.GwtTestException;
import com.googlecode.gwt.test.exceptions.GwtTestRpcException;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

class GwtRpcInvocationHandler implements InvocationHandler {

  private static final Logger logger = LoggerFactory.getLogger(GwtRpcInvocationHandler.class);

  private final GwtRpcExceptionHandler exceptionHandler;
  private final HashMap<Method, Method> methodTable;
  private final GwtRpcSerializerHandler serializerHander;
  private final Object target;

  public GwtRpcInvocationHandler(Class<?> asyncClazz, Object target,
      GwtRpcExceptionHandler exceptionHandler,
      GwtRpcSerializerHandler serializerHandler) {
    this.target = target;
    this.exceptionHandler = exceptionHandler;
    this.serializerHander = serializerHandler;

    this.methodTable = new HashMap<Method, Method>();
    for (Method m : asyncClazz.getMethods()) {
      for (Method m2 : target.getClass().getMethods()) {
        if (m.getName().equals(m2.getName())
            && m.getParameterTypes().length == m2.getParameterTypes().length + 1) {
          methodTable.put(m, m2);
          GwtReflectionUtils.makeAccessible(m2);
        }
      }
    }
  }

  @SuppressWarnings("unchecked")
  public Object invoke(Object proxy, Method method, Object[] args) {
    Object[] subArgs = new Object[args.length - 1];
    for (int i = 0; i < args.length - 1; i++) {
      subArgs[i] = args[i];
    }
    AsyncCallback<Object> callback = (AsyncCallback<Object>) args[args.length - 1];
    Method m = methodTable.get(method);
    if (m == null) {
      logger.error("Method not found " + method);
      callback.onFailure(new StatusCodeException(500, "No method found"));
    }
    try {
      logger.debug("Invoking " + m + " on "
          + target.getClass().getCanonicalName());
      // Serialize objects
      Object[] serializedArgs = new Object[subArgs.length];
      for (int i = 0; i < subArgs.length; i++) {
        try {
          serializedArgs[i] = serializerHander.serializeUnserialize(subArgs[i]);
        } catch (Exception e) {
          throw new GwtTestRpcException("Error while serializing argument " + i
              + " of type " + subArgs[i].getClass().getName() + " in method "
              + method.getDeclaringClass().getSimpleName() + "."
              + method.getName() + "(..)", e);
        }
      }
      Object resultObject = m.invoke(target, serializedArgs);
      Object returnObject = null;

      try {
        returnObject = serializerHander.serializeUnserialize(resultObject);
      } catch (Exception e) {
        throw new GwtTestRpcException("Error while serializing object of type "
            + resultObject.getClass().getName()
            + " which was returned from RPC Service "
            + method.getDeclaringClass().getSimpleName() + "."
            + method.getName() + "(..)", e);
      }

      logger.debug("Result of " + m.getName() + " : " + returnObject);
      callback.onSuccess(returnObject);

    } catch (InvocationTargetException e) {
      if (GwtTestException.class.isInstance(e.getCause())) {
        // it can be a gwt-test-utils exception
        throw (GwtTestException) e.getCause();
      }

      logger.error("Exception when invoking service throw to handler : "
          + e.getCause());
      exceptionHandler.handle(e.getCause(), callback);
    } catch (IllegalAccessException e) {
      logger.error("GWT RPC invokation error : ", e);
      callback.onFailure(new StatusCodeException(500, e.toString()));
    } catch (IllegalArgumentException e) {
      logger.error("GWT RPC invokation error : ", e);
      callback.onFailure(new StatusCodeException(500, e.toString()));
    }

    // Async calls always return void
    return null;
  }

}
