package com.octo.gwt.test.internal.utils.i18n;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Properties;

import com.google.gwt.i18n.client.LocalizableResource;
import com.google.gwt.i18n.client.LocalizableResource.DefaultLocale;
import com.octo.gwt.test.exceptions.GwtTestI18NException;
import com.octo.gwt.test.internal.GwtConfig;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public abstract class LocalizableResourcesInvocationHandler implements
    InvocationHandler {

  private final Class<? extends LocalizableResource> proxiedClass;

  public LocalizableResourcesInvocationHandler(
      Class<? extends LocalizableResource> proxiedClass) {
    this.proxiedClass = proxiedClass;
  }

  public Class<? extends LocalizableResource> getProxiedClass() {
    return proxiedClass;
  }

  public Object invoke(Object proxy, Method method, Object[] args)
      throws Throwable {
    Locale locale = getResourceLocale(proxiedClass);
    Properties prop = GwtPropertiesHelper.get().getLocalizedProperties(
        proxiedClass.getCanonicalName().replaceAll("\\.", "/"), locale);

    Object result = null;
    if (prop != null) {
      result = extractFromProperties(prop, method, args, locale);
    }

    if (result != null) {
      return result;
    }

    result = extractDefaultValue(method, args, locale);

    if (result != null) {
      return result;
    }

    throw new GwtTestI18NException(
        "Unable to find a Locale specific resource file to bind with i18n interface '"
            + proxiedClass.getName()
            + "' and there is no @DefaultXXXValue annotation on '"
            + method.getName() + "' called method");
  }

  private Locale getResourceLocale(Class<?> clazz) {
    if (GwtConfig.get().getLocale() != null) {
      return GwtConfig.get().getLocale();
    }

    DefaultLocale annotation = GwtReflectionUtils.getAnnotation(clazz,
        DefaultLocale.class);
    if (annotation != null) {
      String[] localeCodes = annotation.value().split("_");
      switch (localeCodes.length) {
        case 1:
          return new Locale(localeCodes[0]);
        case 2:
          return new Locale(localeCodes[0], localeCodes[1]);
        default:
          throw new GwtTestI18NException(
              "Cannot parse Locale value in annoted class ["
                  + clazz.getSimpleName() + "] : @"
                  + DefaultLocale.class.getSimpleName() + "("
                  + annotation.value() + ")");
      }
    } else {
      return null;
    }
  }

  protected abstract Object extractDefaultValue(Method method, Object[] args,
      Locale locale) throws Throwable;

  protected abstract Object extractFromProperties(
      Properties localizedProperties, Method method, Object[] args,
      Locale locale) throws Throwable;

  protected String extractProperty(Properties properties, String key) {
    String result = properties.getProperty(key);
    if (result == null) {
      result = properties.getProperty(key.replaceAll("_", "."));
    }
    return result;
  }

}
