package com.octo.gwt.test.internal.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.internal.AfterTestCallback;
import com.octo.gwt.test.internal.AfterTestCallbackManager;
import com.octo.gwt.test.internal.uibinder.UiBinderCreateHandler;
import com.octo.gwt.test.internal.utils.i18n.LocalizableResourceCreateHandler;
import com.octo.gwt.test.internal.utils.resources.ClientBundleCreateHandler;
import com.octo.gwt.test.internal.utils.resources.ImageBundleCreateHandler;

public class GwtCreateHandlerManager implements AfterTestCallback {

  private static final GwtCreateHandlerManager INSTANCE = new GwtCreateHandlerManager();

  public static GwtCreateHandlerManager get() {
    return INSTANCE;
  }

  private final GwtCreateHandler abstractClassCreateHandler;
  private final List<GwtCreateHandler> addedHandlers;
  private final GwtCreateHandler clientBundleCreateHander;
  private final GwtCreateHandler defaultGwtCreateHandler;
  private final GwtCreateHandler deferredGenerateWithCreateHandler;
  private final GwtCreateHandler deferredReplaceWithCreateHandler;
  private final GwtCreateHandler dockLayoutPanelCreateHandler;
  private final GwtCreateHandler htmlPanelCreateHandler;
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
    defaultGwtCreateHandler = new DefaultGwtCreateHandler();
    deferredGenerateWithCreateHandler = new DeferredGenerateWithCreateHandler();
    deferredReplaceWithCreateHandler = new DeferredReplaceWithCreateHandler();
    dockLayoutPanelCreateHandler = new DefaultDockLayoutPanelHandler();
    htmlPanelCreateHandler = new HTMLPanelCreateHandler();
    imageBundleCreateHandler = new ImageBundleCreateHandler();
    localizableResourceCreateHandler = new LocalizableResourceCreateHandler();
    uiBinderCreateHandler = new UiBinderCreateHandler();
    testRemoteServiceCreateHandler = TestRemoteServiceCreateHandler.get();
    webXmlRemoteServiceCreateHandler = new WebXmlRemoteServiceCreateHandler();

    AfterTestCallbackManager.get().registerCallback(this);
  }

  public boolean addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
    return addedHandlers.add(gwtCreateHandler);
  }

  public void afterTest() throws Throwable {
    addedHandlers.clear();
    testRemoteServiceCreateHandler.reset();
  }

  public List<GwtCreateHandler> getGwtCreateHandlers() {
    List<GwtCreateHandler> list = new ArrayList<GwtCreateHandler>();

    // than, declared @Mock objects creation
    if (mockCreateHandler != null) {
      list.add(mockCreateHandler);
    }

    // than, add all user custom createHandlers
    list.addAll(addedHandlers);

    // than, add custom deferred bindings
    list.add(deferredReplaceWithCreateHandler);
    list.add(deferredGenerateWithCreateHandler);

    // finally, add all default gwt-test-utils createHandlers
    list.add(localizableResourceCreateHandler);
    list.add(clientBundleCreateHander);
    list.add(imageBundleCreateHandler);
    list.add(htmlPanelCreateHandler);
    list.add(dockLayoutPanelCreateHandler);
    list.add(uiBinderCreateHandler);
    list.add(testRemoteServiceCreateHandler);
    list.add(webXmlRemoteServiceCreateHandler);
    list.add(defaultGwtCreateHandler);
    list.add(abstractClassCreateHandler);

    return Collections.unmodifiableList(list);
  }

  public void setMockCreateHandler(GwtCreateHandler mockCreateHandler) {
    this.mockCreateHandler = mockCreateHandler;
  }

}
