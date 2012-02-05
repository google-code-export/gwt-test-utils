package com.octo.gwt.test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.google.gwt.user.client.ui.IsWidget;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.internal.AfterTestCallbackManager;
import com.octo.gwt.test.internal.GwtConfig;
import com.octo.gwt.test.uibinder.UiObjectTagFactory;

/**
 * <p>
 * Base class for test classes which need to manipulate (directly or indirectly)
 * GWT components.
 * </p>
 * 
 * <p>
 * AbstractGwtTest provides the mechanism which allows the instantiation of GWT
 * components in the Java Virtual Machine.
 * </p>
 * 
 * <p>
 * Class loading parameters used to instantiate classes referenced in tests can
 * be configured using the META-INF\gwt-test-utils.properties file of your
 * application.
 * </p>
 * 
 */
@RunWith(GwtTestRunner.class)
public abstract class GwtTest extends GwtModuleRunnerAdapter {

	@Override
	public void addUiObjectTagFactory(
			UiObjectTagFactory<? extends IsWidget> factory) {
		GwtConfig.get().getUiObjectTagFactories().add(factory);
	}

	@Override
	public abstract String getModuleName();

	@Override
	public void registerUiConstructor(Class<? extends IsWidget> clazz,
			String... argNames) {
		GwtConfig.get().registerUiConstructor(clazz, argNames);
	}

	@Before
	public void setUpGwtTest() throws Exception {
		GwtConfig.get().setup(this);
	}

	@After
	public final void tearDownGwtTest() throws Exception {

		List<Throwable> throwables = AfterTestCallbackManager.get()
				.triggerCallbacks();

		GwtReset.get().reset();

		if (throwables.size() > 0) {
			throw new GwtTestException(
					throwables.size()
							+ " exception(s) thrown during JUnit @After callback. First one is thrown :",
					throwables.get(0));
		}

	}

}
