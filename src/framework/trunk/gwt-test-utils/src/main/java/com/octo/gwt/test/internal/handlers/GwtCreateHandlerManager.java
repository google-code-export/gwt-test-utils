package com.octo.gwt.test.internal.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.octo.gwt.test.GwtCreateHandler;
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
  private final GwtCreateHandler dockLayoutPanelCreateHandler;
  private final GwtCreateHandler imageBundleCreateHandler;
  private final GwtCreateHandler localizableResourceCreateHandler;
  private GwtCreateHandler mockCreateHandler;
  private final TestRemoteServiceCreateHandler testRemoteServiceCreateHandler;
  private final GwtCreateHandler uiBinderCreateHandler;
  private final WebXmlRemoteServiceCreateHandler webXmlRemoteServiceCreateHandler;

  private GwtCreateHandlerManager() {
    // TODO : all createHandler should be singleton ?
    abstractClassCreateHandler = new AbstractClassCreateHandler();
    addedHandlers = new ArrayList<GwtCreateHandler>();
    clientBundleCreateHander = new ClientBundleCreateHandler();
    debugIdImplCreateHandler = new DebugIdImplCreateHandler();
    defaultGwtCreateHandler = new DefaultGwtCreateHandler();
    dockLayoutPanelCreateHandler = new DefaultDockLayoutPanelHandler();
    imageBundleCreateHandler = new ImageBundleCreateHandler();
    localizableResourceCreateHandler = new LocalizableCreateHandler();
    uiBinderCreateHandler = new UiBinderCreateHandler();
    testRemoteServiceCreateHandler = TestRemoteServiceCreateHandler.get();
    webXmlRemoteServiceCreateHandler = new WebXmlRemoteServiceCreateHandler();
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
    list.add(localizableResourceCreateHandler);
    list.add(clientBundleCreateHander);
    list.add(imageBundleCreateHandler);
    list.add(uiBinderCreateHandler);
    list.add(testRemoteServiceCreateHandler);
    list.add(webXmlRemoteServiceCreateHandler);
    list.add(defaultGwtCreateHandler);
    list.add(abstractClassCreateHandler);
    list.add(dockLayoutPanelCreateHandler);

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
