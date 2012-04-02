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

class SimpleBeanEditorDriverCreateHandler implements GwtCreateHandler {

  private static class SimpleBeanEditorDriverInvocationHandler implements
      InvocationHandler {

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
      collectEditorMethods(result);

      return result;
    }

    private void collectEditorFields(Map<String, Object> result,
        Editor<?> currentEditor, String pathPrefix) {
      for (Field field : GwtReflectionUtils.getFields(currentEditor.getClass())) {

        if (!Modifier.isPrivate(field.getModifiers())
            && !field.isAnnotationPresent(Ignore.class)) {

          Editor<?> childEditor = getEditor(currentEditor, field);

          if (childEditor == null) {
            continue;
          }

          String editorPath = extractEditorPath(field);

          if (childEditor instanceof LeafValueEditor) {
            result.put(editorPath,
                ((LeafValueEditor<?>) childEditor).getValue());
          } else {
            collectEditorFields(result, childEditor, pathPrefix + editorPath
                + ".");
          }

        }
      }
    }

    private void collectEditorMethods(Map<String, Object> result) {
      // TODO Auto-generated method stub

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
