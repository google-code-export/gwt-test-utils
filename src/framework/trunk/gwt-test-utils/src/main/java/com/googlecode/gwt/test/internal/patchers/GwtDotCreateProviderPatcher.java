package com.googlecode.gwt.test.internal.patchers;

import java.lang.reflect.Type;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.rebind.adapter.GwtDotCreateProvider;
import com.google.inject.Binding;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.binder.ScopedBindingBuilder;
import com.google.inject.internal.AbstractBindingBuilder;
import com.googlecode.gwt.test.gin.GwtTestGinException;
import com.googlecode.gwt.test.patchers.InitMethod;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

@PatchClass(target = "com.google.gwt.inject.rebind.adapter.GwtDotCreateProvider")
class GwtDotCreateProviderPatcher {

   private static final String BINDED_CLASS_FIELD = "gwtTestUtilsBindedClass";

   @PatchMethod
   static <T> ScopedBindingBuilder bind(LinkedBindingBuilder<T> builder) {

      if (!(builder instanceof AbstractBindingBuilder)) {
         throw new GwtTestGinException("Not managed " + LinkedBindingBuilder.class.getSimpleName()
                  + " implementation : " + builder.getClass().getName());
      }

      Binding<T> binding = GwtReflectionUtils.<Binding<T>> getPrivateFieldValue(builder, "binding");

      Type type = binding.getKey().getTypeLiteral().getType();

      if (!(type instanceof Class)) {
         throw new GwtTestGinException("Not managed binded type : " + type);
      }

      @SuppressWarnings("unchecked")
      GwtDotCreateProvider<T> gwtDotCreateProvider = GwtReflectionUtils.instantiateClass(GwtDotCreateProvider.class);

      GwtReflectionUtils.setPrivateFieldValue(gwtDotCreateProvider, BINDED_CLASS_FIELD, type);

      return builder.toProvider(gwtDotCreateProvider);
   }

   @PatchMethod
   static <T> T get(GwtDotCreateProvider<?> gwtDotCreateProvider) {
      Class<T> bindedType = GwtReflectionUtils.<Class<T>> getPrivateFieldValue(
               gwtDotCreateProvider, BINDED_CLASS_FIELD);

      return GWT.<T> create(bindedType);
   }

   @InitMethod
   static void initClass(CtClass ctClass) throws CannotCompileException {
      CtField field = CtField.make("private Class " + BINDED_CLASS_FIELD + ";", ctClass);
      ctClass.addField(field);
   }

}
