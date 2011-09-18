package com.octo.gwt.test.internal.uibinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.utils.GwtReflectionUtils;

class UiBinderInvocationHandler implements InvocationHandler {

  private static final Map<Class<?>, GwtEvent<?>> EVENT_PROTOTYPES = new HashMap<Class<?>, GwtEvent<?>>();

  private final Class<?> proxiedClass;

  UiBinderInvocationHandler(Class<?> proxiedClass) {
    this.proxiedClass = proxiedClass;
  }

  public Object invoke(Object proxy, Method method, Object[] args)
      throws Throwable {
    if (method.getName().equals("createAndBindUi")) {
      return createAndBindUi(args[0]);
    } else {
      throw new GwtTestUiBinderException("Not managed method for UiBinder : "
          + method.getName());
    }
  }

  @SuppressWarnings("unchecked")
  private <H extends EventHandler> void addHandlers(Object owner) {
    Map<Method, UiHandler> uiHandlerMethods = GwtReflectionUtils.getAnnotatedMethod(
        owner.getClass(), UiHandler.class);

    for (Map.Entry<Method, UiHandler> entry : uiHandlerMethods.entrySet()) {
      for (String uiFieldName : entry.getValue().value()) {
        Widget uiField = GwtReflectionUtils.getPrivateFieldValue(owner,
            uiFieldName);
        GwtEvent.Type<H> eventType = (GwtEvent.Type<H>) getEventType(entry.getKey());

        H handler = (H) createHandler(uiField, entry.getKey(), owner);
        uiField.addHandler(handler, eventType);
      }

    }
  }

  /**
   * This method is in charge of the instanciation of DOM object / GWT widget
   * and their binding with @UiField in the owner
   * 
   * @param owner The owner UiBinder subclass instance.
   * @return The root component, initially returned by {@link Link
   *         UiBinder#createAndBindUi(Object)}.
   */
  private Object createAndBindUi(Object owner) {
    Class<?> rootComponentClass = getRootElementClass();
    GwtUiBinderParser parser = new GwtUiBinderParser();

    // parse .ui.xml file and inject @UiField
    Object rootComponent = parser.createUiComponent(rootComponentClass, owner);
    // handle @UiHandlers
    addHandlers(owner);

    return rootComponent;
  }

  private InvocationHandler createEventHandlerInvocationHandler(
      final Method uiHandlerMethod, final Object uiHandlerOwner) {

    return new InvocationHandler() {

      public Object invoke(Object proxy, Method method, Object[] args)
          throws Throwable {
        return GwtReflectionUtils.callPrivateMethod(uiHandlerOwner,
            uiHandlerMethod, args);
      }
    };
  }

  private EventHandler createHandler(Widget uiField, Method uiHandlerMethod,
      Object uiHandlerOwner) {
    Class<EventHandler> eventHandlerClass = findHandlerClass(uiHandlerMethod);
    InvocationHandler eventHandlerInvocationHandler = createEventHandlerInvocationHandler(
        uiHandlerMethod, uiHandlerOwner);

    return (EventHandler) Proxy.newProxyInstance(
        this.getClass().getClassLoader(), new Class<?>[]{eventHandlerClass},
        eventHandlerInvocationHandler);
  }

  @SuppressWarnings("unchecked")
  private Class<EventHandler> findHandlerClass(Method uiHandlerMethod) {
    Class<?> eventTypeClass = uiHandlerMethod.getParameterTypes()[0];
    String eventHandlerClassName = eventTypeClass.getName().substring(0,
        eventTypeClass.getName().lastIndexOf("Event"))
        + "Handler";
    try {
      return (Class<EventHandler>) Class.forName(eventHandlerClassName);
    } catch (ClassNotFoundException e) {
      // should never happen
      throw new GwtTestUiBinderException(
          "Cannot find handler class for event type '"
              + eventTypeClass.getName() + "'. By convention, it should be '"
              + eventHandlerClassName + "'");
    }

  }

  private GwtEvent.Type<?> getEventType(Method method) {
    Class<?> eventTypeClass = method.getParameterTypes()[0];

    GwtEvent<?> eventPrototype = EVENT_PROTOTYPES.get(eventTypeClass);
    if (eventPrototype == null) {
      eventPrototype = (GwtEvent<?>) GwtReflectionUtils.instantiateClass(eventTypeClass);
      EVENT_PROTOTYPES.put(eventTypeClass, eventPrototype);
    }

    return eventPrototype.getAssociatedType();
  }

  /**
   * Get the actual class of the <U> parameter.
   * 
   * @return The class of the root element or widget generated from UiBinder.
   */
  private Class<?> getRootElementClass() {
    for (Type type : proxiedClass.getGenericInterfaces()) {

      if (type instanceof ParameterizedType) {
        ParameterizedType pType = (ParameterizedType) type;

        return (Class<?>) pType.getActualTypeArguments()[0];
      }
    }

    throw new GwtTestUiBinderException("The UiBinder subinterface '"
        + proxiedClass.getName()
        + "' is not parameterized. Please add its generic types.");
  }

}
