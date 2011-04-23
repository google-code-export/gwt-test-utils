package com.octo.gwt.test.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.internal.utils.i18n.LocalizableCreateHandler;
import com.octo.gwt.test.internal.utils.resources.ClientBundleCreateHandler;
import com.octo.gwt.test.internal.utils.resources.ImageBundleCreateHandler;

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
	private GwtCreateHandler debugIdImplCreateHandler;

	private GwtCreateHandlerManager() {
		addedHandlers = new ArrayList<GwtCreateHandler>();
		localizableResourceHandler = new LocalizableCreateHandler();
		clientBundleCreateHander = new ClientBundleCreateHandler();
		imageBundleCreateHandler = new ImageBundleCreateHandler();
		defaultGwtCreateHandler = new DefaultGwtCreateHandler();
		abstractClassCreateHandler = new AbstractClassCreateHandler();
		debugIdImplCreateHandler = new DebugIdImplCreateHandler();
	}

	public void setMockCreateHandler(GwtCreateHandler mockCreateHandler) {
		this.mockCreateHandler = mockCreateHandler;
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