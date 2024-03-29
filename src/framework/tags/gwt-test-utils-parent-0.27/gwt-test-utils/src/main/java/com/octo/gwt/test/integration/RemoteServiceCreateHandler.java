package com.octo.gwt.test.integration;

import java.lang.reflect.Proxy;

import javassist.CtClass;
import javassist.bytecode.annotation.AnnotationImpl;
import javassist.bytecode.annotation.StringMemberValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.integration.internal.GwtRpcInvocationHandler;
import com.octo.gwt.test.internal.GwtClassPool;

/**
 * <p>
 * The {@link GwtCreateHandler} class used to create {@link RemoteService}
 * sub-interface instances. It returns an asynchrone proxy of the service and
 * handles the simulation of GWT-RPC serialization through a
 * {@link GwtRpcInvocationHandler} and the exception management through a
 * {@link GwtRpcExceptionHandler}.
 * </p>
 * 
 * <p>
 * Before delegating the remote service object instantiation to the abstract
 * {@link RemoteServiceCreateHandler#findService(Class, String)} method, this
 * class ensure that a corresponding async interface is available for the
 * RemoteService subinterface to create.
 * </p>
 * 
 * @author Bertrand Paquet
 * @author Gael Lazzari
 * 
 */
public abstract class RemoteServiceCreateHandler implements GwtCreateHandler {

  private static final Logger logger = LoggerFactory.getLogger(RemoteServiceCreateHandler.class);

  private GwtRpcExceptionHandler exceptionHandler;
  private GwtRpcSerializerHandler serializerHander;

  public RemoteServiceCreateHandler() {
    exceptionHandler = getExceptionHandler();
    serializerHander = getSerializerHandler();
  }

  @SuppressWarnings("unchecked")
  public Object create(Class<?> classLiteral) throws Exception {

    if (!RemoteService.class.isAssignableFrom(classLiteral)) {
      return null;
    }

    String className = classLiteral.getName();
    logger.debug("Try to create Remote service class " + className);

    String asyncName = className + "Async";
    String relativePath = getRemoveServiceRelativePath(classLiteral);
    Class<?> asyncClazz = Class.forName(asyncName);
    if (asyncClazz == null) {
      throw new Exception("Remote serivce Async class not found : " + asyncName);
    }
    logger.debug("Searching remote service implementing " + className);
    Object service = findService((Class<? extends RemoteService>) classLiteral,
        relativePath);
    if (service == null) {
      logger.error("Remote service not found " + className);
      throw new RuntimeException("Remote service not found " + className);
    }

    GwtRpcInvocationHandler handler = new GwtRpcInvocationHandler(asyncClazz,
        service, exceptionHandler, serializerHander);

    return Proxy.newProxyInstance(getClass().getClassLoader(),
        new Class[]{asyncClazz}, handler);
  }

  private String getRemoveServiceRelativePath(Class<?> clazz) {
    CtClass ctClass = GwtClassPool.getCtClass((clazz));
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
        + "' annotation on RemoteService interface '" + clazz.getName() + "'");
  }

  /**
   * Method which actually is responsible for getting the {@link RemoteService}
   * sub-interface implementation.
   * 
   * @param remoteServiceClass {@link RemoteService} sub-interface to get an
   *          implementation.
   * @param remoteServiceRelativePath the associated relative path, which is
   *          provided in {@link RemoteServiceRelativePath} annotation.
   * @return The corresponding remote service implementation.
   */
  protected abstract Object findService(Class<?> remoteServiceClass,
      String remoteServiceRelativePath);

  /**
   * Specify the handler to use to handle GWT-RPC errors.
   * 
   * @return The handler to use to handle GWT-RPC errors.
   */
  protected GwtRpcExceptionHandler getExceptionHandler() {
    return new DefaultGwtRpcExceptionHandler();
  }

  /**
   * Specify the handler to use to simulate the GWT-RPC serialization
   * 
   * @return The handler to use to simulate the GWT-RPC serialization.
   */
  protected GwtRpcSerializerHandler getSerializerHandler() {
    return new DefaultGwtRpcSerializerHandler();
  }

}
