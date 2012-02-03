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

	private final List<GwtCreateHandler> addedHandlers;
	private GwtCreateHandler mockCreateHandler;
	private final GwtCreateHandler localizableResourceHandler;
	private final GwtCreateHandler clientBundleCreateHander;
	private final GwtCreateHandler imageBundleCreateHandler;
	private final GwtCreateHandler defaultGwtCreateHandler;
	private final GwtCreateHandler abstractClassCreateHandler;
	private final GwtCreateHandler debugIdImplCreateHandler;
	private final GwtCreateHandler safeHtmlTemplatesCreateHandler;

	private GwtCreateHandlerManager() {
		addedHandlers = new ArrayList<GwtCreateHandler>();
		localizableResourceHandler = new LocalizableCreateHandler();
		clientBundleCreateHander = new ClientBundleCreateHandler();
		imageBundleCreateHandler = new ImageBundleCreateHandler();
		defaultGwtCreateHandler = new DefaultGwtCreateHandler();
		abstractClassCreateHandler = new AbstractClassCreateHandler();
		debugIdImplCreateHandler = new DebugIdImplCreateHandler();
		safeHtmlTemplatesCreateHandler = new SafeHtmlTemplatesCreateHandler();
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
		list.add(safeHtmlTemplatesCreateHandler);
		list.add(defaultGwtCreateHandler);
		list.add(abstractClassCreateHandler);

		return Collections.unmodifiableList(list);
	}

	public void reset() {
		addedHandlers.clear();
	}

	public void setMockCreateHandler(GwtCreateHandler mockCreateHandler) {
		this.mockCreateHandler = mockCreateHandler;
	}

}
