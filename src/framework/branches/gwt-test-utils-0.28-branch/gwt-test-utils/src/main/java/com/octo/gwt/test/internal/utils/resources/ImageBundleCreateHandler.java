package com.octo.gwt.test.internal.utils.resources;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.exceptions.GwtTestResourcesException;

/**
 * Class in charge of the instanciation of all {@link ImageBundle}
 * sub-interfaces through deferred binding. <strong>For internal use
 * only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
@SuppressWarnings("deprecation")
public class ImageBundleCreateHandler implements GwtCreateHandler {

  private static class OverrideImagePrototype extends AbstractImagePrototype {

    public void applyTo(Image arg0) {
    }

    public Image createImage() {
      Image image = new Image();
      return image;
    }

    public String getHTML() {
      return "<img/>";
    }

  }

  public Object create(Class<?> classLiteral) throws Exception {
    if (!ImageBundle.class.isAssignableFrom(classLiteral)) {
      return null;
    }

    return generateImageWrapper(classLiteral);
  }

  private Object generateImageWrapper(Class<?> clazz) {
    InvocationHandler ih = new InvocationHandler() {

      public Object invoke(Object proxy, Method method, Object[] args)
          throws Throwable {
        if (method.getReturnType() == AbstractImagePrototype.class) {
          return new OverrideImagePrototype();
        }
        throw new GwtTestResourcesException(
            "Not managed return type for image bundle : "
                + method.getReturnType().getSimpleName());
      }

    };
    return Proxy.newProxyInstance(clazz.getClassLoader(),
        new Class<?>[]{clazz}, ih);
  }

}
