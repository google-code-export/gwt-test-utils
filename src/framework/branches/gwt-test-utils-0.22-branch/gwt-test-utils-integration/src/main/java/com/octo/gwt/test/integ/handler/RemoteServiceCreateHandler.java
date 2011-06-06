package com.octo.gwt.test.integ.handler;

import java.lang.reflect.Proxy;

import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.annotation.AnnotationImpl;
import javassist.bytecode.annotation.StringMemberValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.integ.internal.DeserializationContext;
import com.octo.gwt.test.integ.tools.ISerializeCallback;
import com.octo.gwt.test.internal.PatchGwtClassPool;

public abstract class RemoteServiceCreateHandler implements GwtCreateHandler {

  private static final Logger logger = LoggerFactory.getLogger(RemoteServiceCreateHandler.class);

  private DeserializationContext backToGwtCallbacks;

  private DeserializationContext fromGwtCallbacks;

  private IGwtRpcExceptionHandler exceptionHandler;

  public RemoteServiceCreateHandler() {
    this.exceptionHandler = new DefaultGwtRpcExceptionHandler();
    this.backToGwtCallbacks = new DeserializationContext();
    this.fromGwtCallbacks = new DeserializationContext();
  }

  public void addBackToGwtCallbacks(Class<?> clazz, ISerializeCallback callback) {
    this.backToGwtCallbacks.put(clazz, callback);
  }

  public void addFromGwtCallbacks(Class<?> clazz, ISerializeCallback callback) {
    this.fromGwtCallbacks.put(clazz, callback);
  }

  public void setExceptionHandler(IGwtRpcExceptionHandler exceptionHandler) {
    this.exceptionHandler = exceptionHandler;
  }

  protected abstract Object findService(Class<?> remoteServiceClass,
      String remoteServiceRelativePath);

  protected Object createObject(Class<?> classLiteral) throws Exception {
    return null;
  }

  public Object create(Class<?> classLiteral) throws Exception {
    String className = classLiteral.getName();
    logger.debug("Try to create class " + className);

    if (RemoteService.class.isAssignableFrom(classLiteral)) {
      String asyncName = className + "Async";
      String relativePath = getRemoveServiceRelativePath(className);
      Class<?> asyncClazz = Class.forName(asyncName);
      if (asyncClazz == null) {
        throw new Exception("Async class not found : " + asyncName);
      }
      logger.debug("Searching service implementing " + className);
      Object service = findService(classLiteral, relativePath);
      if (service == null) {
        logger.error("Service not found " + className);
        throw new RuntimeException("Service not found " + className);
      }
      GwtRpcInvocationHandler handler = new GwtRpcInvocationHandler(asyncClazz,
          service);
      handler.setExceptionHandler(exceptionHandler);
      handler.setBackToGwtCallbacks(backToGwtCallbacks);
      handler.setFromGwtCallbacks(fromGwtCallbacks);
      return Proxy.newProxyInstance(getClass().getClassLoader(),
          new Class[]{asyncClazz}, handler);
    }
    logger.debug("Creating class " + className);
    Object o = createObject(classLiteral);
    return o;
  }

  private String getRemoveServiceRelativePath(String className)
      throws NotFoundException, ClassNotFoundException {
    CtClass ctClass = PatchGwtClassPool.get().get(className);
    Object[] annotations = ctClass.getAvailableAnnotations();
    for (Object o : annotations) {
      if (Proxy.isProxyClass(o.getClass())) {
        AnnotationImpl annotation = (AnnotationImpl) Proxy.getInvocationHandler(o);
        if (annotation.getTypeName().equals(
            RemoteServiceRelativePath.class.getName())) {
          return ((StringMemberValue) annotation.getAnnotation().getMemberValue(
              "value")).getValue();
        }
      }
    }
    throw new RuntimeException("Cannot find the '@"
        + RemoteServiceRelativePath.class.getSimpleName()
        + "' annotation on RemoteService interface '" + className + "'");
  }

}
