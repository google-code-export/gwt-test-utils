package com.octo.gwt.test.internal.utils.i18n;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.i18n.client.LocalizableResource;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.i18n.client.impl.CldrImpl;
import com.google.gwt.i18n.client.impl.cldr.DateTimeFormatInfoImpl;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.exceptions.GwtTestI18NException;
import com.octo.gwt.test.internal.GwtConfig;

public class LocalizableCreateHandler implements GwtCreateHandler {

  private static class LocalizableResourceProxyFactory {

    private static Map<String, LocalizableResourceProxyFactory> factoryMap = new HashMap<String, LocalizableResourceProxyFactory>();

    public static <T extends LocalizableResource> LocalizableResourceProxyFactory getFactory(
        Class<T> clazz) {
      LocalizableResourceProxyFactory factory = factoryMap.get(clazz.getName());
      if (factory == null) {
        factory = new LocalizableResourceProxyFactory(clazz);
        factoryMap.put(clazz.getName(), factory);
      }

      return factory;
    }

    private final Class<? extends LocalizableResource> proxiedClass;

    private LocalizableResourceProxyFactory(
        Class<? extends LocalizableResource> proxiedClass) {
      this.proxiedClass = proxiedClass;
    }

    @SuppressWarnings("unchecked")
    public <T extends LocalizableResource> T createProxy() {
      InvocationHandler ih = createInvocationHandler(proxiedClass);
      return (T) Proxy.newProxyInstance(proxiedClass.getClassLoader(),
          new Class<?>[]{proxiedClass}, ih);
    }

    private InvocationHandler createInvocationHandler(
        Class<? extends LocalizableResource> clazz) {
      if (ConstantsWithLookup.class.isAssignableFrom(clazz)) {
        return new ConstantsWithLookupInvocationHandler(clazz);
      }
      if (Constants.class.isAssignableFrom(clazz)) {
        return new ConstantsInvocationHandler(clazz);
      } else if (Messages.class.isAssignableFrom(clazz)) {
        return new MessagesInvocationHandler(clazz);
      } else {
        throw new GwtTestI18NException(
            "Not managed GWT i18n interface for testing : "
                + clazz.getSimpleName());
      }
    }
  }

  @SuppressWarnings("unchecked")
  public Object create(Class<?> classLiteral) throws Exception {
    if (LocalizableResource.class.isAssignableFrom(classLiteral)) {
      return LocalizableResourceProxyFactory.getFactory(
          (Class<? extends LocalizableResource>) classLiteral).createProxy();
    } else if (CldrImpl.class == classLiteral) {
      return getLocalizedClassImpl(CldrImpl.class, CldrImpl.class);
    } else if (DateTimeFormatInfoImpl.class == classLiteral) {
      return getLocalizedClassImpl(DateTimeFormatInfoImpl.class,
          DefaultDateTimeFormatInfo.class);
    }

    return null;

  }

  private Object getLocalizedClassImpl(Class<?> localizedClass,
      Class<?> defaultImpl) throws Exception {
    Locale locale = GwtConfig.get().getLocale();
    if (locale == null) {
      return defaultImpl.newInstance();
    }

    Class<?> implementationClass;
    try {
      implementationClass = Class.forName(localizedClass.getName() + "_"
          + locale.getLanguage());
    } catch (ClassNotFoundException e) {
      try {
        implementationClass = Class.forName(localizedClass.getName() + "_"
            + locale.getCountry());
      } catch (ClassNotFoundException e2) {
        implementationClass = null;
      }
    }

    if (implementationClass == null) {
      implementationClass = defaultImpl;
    }

    return implementationClass.newInstance();
  }

}
