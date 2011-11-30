package com.octo.gwt.test.internal.handlers;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.internal.utils.WebXmlUtils;
import com.octo.gwt.test.server.RemoteServiceCreateHandler;
import com.octo.gwt.test.utils.GwtReflectionUtils;

class WebXmlRemoteServiceCreateHandler extends RemoteServiceCreateHandler {

  // a map with servletUrl as key and serviceImpl instance as value
  private final Map<String, Object> servicesImplMap = new HashMap<String, Object>();

  @Override
  protected Object findService(Class<?> remoteServiceClass,
      String remoteServiceRelativePath) {

    String servletPath = "/" + GWT.getModuleName() + "/"
        + remoteServiceRelativePath;

    Object serviceImpl = servicesImplMap.get(servletPath);

    if (serviceImpl != null) {
      return serviceImpl;
    }

    String className = WebXmlUtils.get().getServletClass(servletPath);

    if (className == null) {
      return null;
    }

    try {
      serviceImpl = GwtReflectionUtils.instantiateClass(Class.forName(className));
    } catch (ClassNotFoundException e) {
      // should not happen..
      throw new GwtTestConfigurationException(e);
    }

    // cache the implementation
    servicesImplMap.put(servletPath, serviceImpl);

    return serviceImpl;
  }

}