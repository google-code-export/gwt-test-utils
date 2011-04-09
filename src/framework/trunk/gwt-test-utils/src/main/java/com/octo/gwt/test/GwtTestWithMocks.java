package com.octo.gwt.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;

import com.octo.gwt.test.internal.GwtCreateHandlerManager;
import com.octo.gwt.test.internal.mock.MockCreateHandler;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * <p>
 * Base class for test classes which make use of a mocking framework, such as
 * {@link org.easymock.EasyMock EasyMock} or {@link org.mockito.Mockito Mockito}
 * .
 * </p>
 * 
 * <p>
 * This class provides methods to register mock objects into the context of a
 * test class. This is required so that application calls to
 * {@link com.google.gwt.core.client.GWT#create(Class)
 * GWT.Create(MyClassToMock)} will return the corresponding mock object of type
 * MyClassToMock.
 * </p>
 * 
 * @author Eric Therond
 */
public abstract class GwtTestWithMocks extends GwtTest {

  protected List<Class<?>> mockedClasses = new ArrayList<Class<?>>();
  protected Set<Field> mockFields;
  protected Map<Class<?>, Object> mockObjects = new HashMap<Class<?>, Object>();

  public GwtTestWithMocks() {
    GwtCreateHandlerManager.get().setMockCreateHandler(
        new MockCreateHandler(mockObjects));
    mockFields = getMockFields();
    for (Field f : mockFields) {
      mockedClasses.add(f.getType());
    }
  }

  /**
   * Adds a mock object to the list of mocks used in the context of this test
   * class.
   * 
   * @param clazz The class for which a mock object is being defined
   * @param mock the mock instance
   */
  public Object addMockedObject(Class<?> createClass, Object mock) {
    return mockObjects.put(createClass, mock);
  }

  @After
  public void teardownGwtTestWithMocks() {
    mockObjects.clear();
  }

  protected Set<Field> getMockFields() {
    return GwtReflectionUtils.getAnnotatedField(this.getClass(), Mock.class);
  }

}
