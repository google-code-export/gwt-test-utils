package com.googlecode.gwt.test.internal.handlers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.googlecode.gwt.test.GwtCreateHandler;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;
import com.googlecode.gwt.test.utils.GwtReflectionUtils.FieldCallback;
import com.googlecode.gwt.test.utils.GwtReflectionUtils.FieldFilter;
import com.googlecode.gwt.test.utils.GwtReflectionUtils.MethodCallback;
import com.googlecode.gwt.test.utils.GwtReflectionUtils.MethodFilter;

class SimpleBeanEditorDriverCreateHandler implements GwtCreateHandler {

  private static class SimpleBeanEditorDriverInvocationHandler implements
      InvocationHandler {

    private static FieldFilter EDITOR_FIELD_FILTER = new FieldFilter() {

      public boolean matches(Field field) {
        return !Modifier.isPrivate(field.getModifiers())
            && !field.isAnnotationPresent(Ignore.class);
      }
    };

    private static MethodFilter EDITOR_METHOD_FILTER = new MethodFilter() {

      public boolean matches(Method method) {
        return !Modifier.isPrivate(method.getModifiers())
            && !method.isAnnotationPresent(Ignore.class);
      }
    };

    private Object bean;
    private Editor<?> editor;
    private final Class<?> proxiedClass;

    private SimpleBeanEditorDriverInvocationHandler(Class<?> proxiedClass) {
      this.proxiedClass = proxiedClass;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable {
      if ("edit".equals(method.getName())) {
        edit(args[0]);
        return null;
      } else if ("initialize".equals(method.getName())) {
        initialize((Editor<?>) args[0]);
        return null;
      } else if ("flush".equals(method.getName())) {
        return flush();
      } else {
        throw new UnsupportedOperationException(method.toGenericString()
            + " is not implemented in gwt-test-utils");
      }
    }

    private Map<String, Object> collectBeanProperties() {
      Map<String, Object> result = new HashMap<String, Object>();

      collectEditorFields(result, editor, "");
      collectEditorMethods(result, editor, "");

      return result;
    }

    private void collectEditorFields(final Map<String, Object> result,
        final Editor<?> currentEditor, final String pathPrefix) {

      GwtReflectionUtils.doWithFields(currentEditor.getClass(),
          new FieldCallback() {

            public void doWith(Field field) throws IllegalArgumentException,
                IllegalAccessException {

              Editor<?> childEditor = getEditor(currentEditor, field);

              // ignore null editors
              if (childEditor == null) {
                return;
              }

              String editorPath = extractEditorPath(field);

              if (childEditor instanceof LeafValueEditor) {
                result.put(editorPath,
                    ((LeafValueEditor<?>) childEditor).getValue());
              } else {
                collectEditorFields(result, childEditor, pathPrefix
                    + editorPath + ".");
              }

            }
          }, EDITOR_FIELD_FILTER);
    }

    private void collectEditorMethods(final Map<String, Object> result,
        final Editor<?> currentEditor, final String pathPrefix) {

      GwtReflectionUtils.doWithMethods(currentEditor.getClass(),
          new MethodCallback() {

            public void doWith(Method method) throws IllegalArgumentException,
                IllegalAccessException {

              Editor<?> childEditor = getEditor(currentEditor, method);

              // ignore null editors
              if (childEditor == null) {
                return;
              }

              String editorPath = extractEditorPath(method);

              if (childEditor instanceof LeafValueEditor) {
                result.put(editorPath,
                    ((LeafValueEditor<?>) childEditor).getValue());
              } else {
                collectEditorFields(result, childEditor, pathPrefix
                    + editorPath + ".");
              }

            }

          }, EDITOR_METHOD_FILTER);
    }

    private void edit(Object bean) {
      if (editor == null) {
        throw new IllegalStateException(proxiedClass.getName()
            + ".initialize(editor) method has not been called");
      }
      this.bean = bean;
    }

    private String extractEditorPath(Field field) {
      Path pathAnnotation = field.getAnnotation(Path.class);
      if (pathAnnotation != null) {
        return pathAnnotation.value();
      }

      String path = field.getName();
      if (path.endsWith("Editor")) {
        path = path.substring(0, path.length() - 6);
      }

      return path;
    }

    private String extractEditorPath(Method method) {
      Path pathAnnotation = method.getAnnotation(Path.class);
      if (pathAnnotation != null) {
        return pathAnnotation.value();
      }

      String path = method.getName();
      if (path.endsWith("Editor")) {
        path = path.substring(0, path.length() - 6);
      }

      return path;
    }

    private Object flush() throws IllegalAccessException,
        InvocationTargetException {
      if (this.bean == null) {
        throw new IllegalStateException(proxiedClass.getName()
            + ".edit(object) method has not been called");
      }

      Map<String, Object> properties = collectBeanProperties();

      BeanUtils.populate(bean, properties);

      return bean;
    }

    private Editor<?> getEditor(Editor<?> currentEditor, Field field) {
      if (Editor.class.isAssignableFrom(field.getType())) {
        return GwtReflectionUtils.<Editor<?>> getPrivateFieldValue(
            currentEditor, field);
      } else if (IsEditor.class.isAssignableFrom(field.getType())) {
        IsEditor<Editor<?>> isEditor = GwtReflectionUtils.<IsEditor<Editor<?>>> getPrivateFieldValue(
            currentEditor, field);
        return isEditor.asEditor();
      } else {
        // just ignore
        return null;
      }
    }

    private Editor<?> getEditor(Editor<?> currentEditor, Method method) {
      if (Editor.class.isAssignableFrom(method.getReturnType())) {
        return GwtReflectionUtils.<Editor<?>> callPrivateMethod(currentEditor,
            method);
      } else if (IsEditor.class.isAssignableFrom(method.getReturnType())) {
        IsEditor<Editor<?>> isEditor = GwtReflectionUtils.<IsEditor<Editor<?>>> callPrivateMethod(
            currentEditor, method);
        return isEditor.asEditor();
      } else {
        // just ignore
        return null;
      }
    }

    private void initialize(Editor<?> editor) {
      this.editor = editor;
    }

  }

  public Object create(Class<?> classLiteral) throws Exception {
    if (!SimpleBeanEditorDriver.class.isAssignableFrom(classLiteral)) {
      return null;
    }

    return Proxy.newProxyInstance(classLiteral.getClassLoader(),
        new Class[]{classLiteral}, new SimpleBeanEditorDriverInvocationHandler(
            classLiteral));
  }

}
