package com.octo.gwt.test.internal.handlers;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.integration.RemoteServiceCreateHandler;
import com.octo.gwt.test.internal.ModuleData;
import com.octo.gwt.test.utils.GwtReflectionUtils;

class TestRemoteServiceCreateHandler extends RemoteServiceCreateHandler {

  private static final TestRemoteServiceCreateHandler INSTANCE = new TestRemoteServiceCreateHandler();

  public static TestRemoteServiceCreateHandler get() {
    return INSTANCE;
  }

  private final Map<String, Object> cachedServices = new HashMap<String, Object>();

  public void reset() {
    cachedServices.clear();
  }

  @SuppressWarnings("unchecked")
  private <T> T instanciateRemoteServiceInstance(Class<T> remoteServiceClass,
      String remoteServiceRelativePath) {

    Class<?> remoteServiceImplClass = ModuleData.get().getRemoteServiceImplClass(
        remoteServiceRelativePath);

    if (remoteServiceImplClass == null) {
      throw new GwtTestConfigurationException(
          "Cannot find a RemoteService implementation class for servlet path '"
              + remoteServiceRelativePath
              + "'. Please add a <servlet> element in your test GWT configuration file (.gwt.xml)");
    } else if (!remoteServiceClass.isAssignableFrom(remoteServiceImplClass)) {
      throw new GwtTestConfigurationException("The servlet class '"
          + remoteServiceImplClass.getName() + "' setup for path '"
          + remoteServiceRelativePath
          + "' does not implement RemoteService interface '"
          + remoteServiceClass.getName());
    } else {
      try {
        return (T) GwtReflectionUtils.instantiateClass(remoteServiceImplClass);
      } catch (Exception e) {
        throw new GwtTestConfigurationException(
            "Error during the instanciation of "
                + RemoteService.class.getSimpleName()
                + " implementation for servlet path '"
                + remoteServiceRelativePath + "'", e);
      }
    }

  }

  @Override
  protected Object findService(Class<?> remoteServiceClass,
      String remoteServiceRelativePath) {

    Object remoteServiceInstance = cachedServices.get(remoteServiceRelativePath);

    if (remoteServiceInstance == null) {
      remoteServiceInstance = instanciateRemoteServiceInstance(
          remoteServiceClass, remoteServiceRelativePath);
      cachedServices.put(remoteServiceRelativePath, remoteServiceInstance);
    }

    return remoteServiceInstance;
  }

}
