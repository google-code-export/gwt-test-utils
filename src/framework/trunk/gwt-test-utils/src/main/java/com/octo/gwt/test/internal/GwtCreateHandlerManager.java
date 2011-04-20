package com.octo.gwt.test.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.integration.internal.TestRemoteServiceCreateHandler;
import com.octo.gwt.test.internal.uibinder.UiBinderCreateHandler;
import com.octo.gwt.test.internal.utils.i18n.LocalizableCreateHandler;
import com.octo.gwt.test.internal.utils.resources.ClientBundleCreateHandler;
import com.octo.gwt.test.internal.utils.resources.ImageBundleCreateHandler;

public class GwtCreateHandlerManager {

  private static final GwtCreateHandlerManager INSTANCE = new GwtCreateHandlerManager();

  public static GwtCreateHandlerManager get() {
    return INSTANCE;
  }

  private final GwtCreateHandler abstractClassCreateHandler;
  private final List<GwtCreateHandler> addedHandlers;
  private final GwtCreateHandler clientBundleCreateHander;
  private final GwtCreateHandler debugIdImplCreateHandler;
  private final GwtCreateHandler defaultGwtCreateHandler;
  private final GwtCreateHandler imageBundleCreateHandler;
  private final GwtCreateHandler localizableResourceHandler;
  private GwtCreateHandler mockCreateHandler;
  private final TestRemoteServiceCreateHandler testRemoteServiceCreateHandler;
  private final GwtCreateHandler uiBinderCreateHandler;

  private GwtCreateHandlerManager() {
    // TODO : all createHandler should be singleton ?
    abstractClassCreateHandler = new AbstractClassCreateHandler();
    addedHandlers = new ArrayList<GwtCreateHandler>();
    clientBundleCreateHander = new ClientBundleCreateHandler();
    debugIdImplCreateHandler = new DebugIdImplCreateHandler();
    defaultGwtCreateHandler = new DefaultGwtCreateHandler();
    localizableResourceHandler = new LocalizableCreateHandler();
    imageBundleCreateHandler = new ImageBundleCreateHandler();
    uiBinderCreateHandler = new UiBinderCreateHandler();
    testRemoteServiceCreateHandler = TestRemoteServiceCreateHandler.get();
  }

  public boolean addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
    return addedHandlers.add(gwtCreateHandler);
  }

  public List<GwtCreateHandler> getGwtCreateHandlers() {
    List<GwtCreateHandler> list = new ArrayList<GwtCreateHandler>();

    // debug always configure first
    list.add(debugIdImplCreateHandler);

    // than, declared @Mock objects creation
    if (mockCreateHandler != null) {
      list.add(mockCreateHandler);
    }

    // then, add all user custom createHandlers
    list.addAll(addedHandlers);

    // finally, add all default gwt-test-utils createHandlers
    list.add(localizableResourceHandler);
    list.add(clientBundleCreateHander);
    list.add(imageBundleCreateHandler);
    list.add(uiBinderCreateHandler);
    list.add(testRemoteServiceCreateHandler);
    list.add(defaultGwtCreateHandler);
    list.add(abstractClassCreateHandler);

    return Collections.unmodifiableList(list);
  }

  public void reset() {
    addedHandlers.clear();
    testRemoteServiceCreateHandler.reset();
  }

  public void setMockCreateHandler(GwtCreateHandler mockCreateHandler) {
    this.mockCreateHandler = mockCreateHandler;
  }

}
