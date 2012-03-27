package com.googlecode.gwt.test.internal.handlers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.MessageFormat;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.googlecode.gwt.test.GwtCreateHandler;
import com.googlecode.gwt.test.exceptions.GwtTestPatchException;

/**
 * GwtCreateHandler for {@link SafeHtmlTemplates}.
 * 
 * @author Gael Lazzari
 * 
 */
class SafeHtmlTemplatesCreateHandler implements GwtCreateHandler {

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.GwtCreateHandler#create(java.lang.Class)
   */
  public Object create(Class<?> classLiteral) throws Exception {

    if (!SafeHtmlTemplates.class.isAssignableFrom(classLiteral)) {
      return null;
    }

    InvocationHandler ih = new InvocationHandler() {

      public Object invoke(Object proxy, Method method, Object[] args)
          throws Throwable {

        Template template = method.getAnnotation(Template.class);
        if (template == null) {
          throw new GwtTestPatchException(
              "Cannot find @Template annotation on method to stub : "
                  + method.toGenericString());
        }

        // convert SafeHtml params to String
        Object[] newArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
          if (SafeHtml.class.isInstance(args[i])) {
            newArgs[i] = ((SafeHtml) args[i]).asString();
          } else {
            newArgs[i] = args[i];
          }
        }

        // escape simple quote
        String templateValue = MessageFormat.format(
            template.value().replaceAll("'", "''"), newArgs);

        SafeHtmlBuilder builder = new SafeHtmlBuilder().appendHtmlConstant(templateValue);

        return builder.toSafeHtml();
      }

    };
    return Proxy.newProxyInstance(classLiteral.getClassLoader(),
        new Class<?>[]{classLiteral}, ih);

  }

}
