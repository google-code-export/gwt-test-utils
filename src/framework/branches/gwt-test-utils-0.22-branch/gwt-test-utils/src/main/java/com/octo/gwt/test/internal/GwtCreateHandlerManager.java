package com.octo.gwt.test.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.internal.patcher.tools.i18n.LocalizableResourceCreateHandler;
import com.octo.gwt.test.internal.patcher.tools.resources.ClientBundleCreateHandler;
import com.octo.gwt.test.internal.patcher.tools.resources.ImageBundleCreateHandler;

public class GwtCreateHandlerManager {

  private static GwtCreateHandlerManager INSTANCE;

  public static GwtCreateHandlerManager getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new GwtCreateHandlerManager();
    }

    return INSTANCE;
  }

  private List<GwtCreateHandler> addedHandlers;
  private GwtCreateHandler mockCreateHandler;
  private GwtCreateHandler localizableResourceHandler;
  private GwtCreateHandler clientBundleCreateHander;
  private GwtCreateHandler imageBundleCreateHandler;
  private GwtCreateHandler defaultGwtCreateHandler;
  private GwtCreateHandler abstractClassCreateHandler;

  private GwtCreateHandlerManager() {
    addedHandlers = new ArrayList<GwtCreateHandler>();
    localizableResourceHandler = new LocalizableResourceCreateHandler();
    clientBundleCreateHander = new ClientBundleCreateHandler();
    imageBundleCreateHandler = new ImageBundleCreateHandler();
    defaultGwtCreateHandler = new DefaultGwtCreateHandler();
    abstractClassCreateHandler = new AbstractClassCreateHandler();
  }

  public void setMockCreateHandler(GwtCreateHandler mockCreateHandler) {
    this.mockCreateHandler = mockCreateHandler;
  }

  public List<GwtCreateHandler> getGwtCreateHandlers() {
    List<GwtCreateHandler> list = new ArrayList<GwtCreateHandler>();

    // declared @Mock objects are always created in the first place if possible
    if (mockCreateHandler != null) {
      list.add(mockCreateHandler);
    }

    // then, add all user custom createHandlers
    list.addAll(addedHandlers);

    // finally, add all default gwt-test-utils createHandlers
    list.add(localizableResourceHandler);
    list.add(clientBundleCreateHander);
    list.add(imageBundleCreateHandler);
    list.add(defaultGwtCreateHandler);
    list.add(abstractClassCreateHandler);

    return Collections.unmodifiableList(list);
  }

  public boolean addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
    return addedHandlers.add(gwtCreateHandler);
  }

  public void reset() {
    addedHandlers.clear();
  }

}
