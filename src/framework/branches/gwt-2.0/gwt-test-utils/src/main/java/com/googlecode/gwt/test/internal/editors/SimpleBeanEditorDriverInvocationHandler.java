package com.googlecode.gwt.test.internal.editors;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;
import com.googlecode.gwt.test.utils.GwtReflectionUtils.FieldCallback;
import com.googlecode.gwt.test.utils.GwtReflectionUtils.FieldFilter;
import com.googlecode.gwt.test.utils.GwtReflectionUtils.MethodCallback;
import com.googlecode.gwt.test.utils.GwtReflectionUtils.MethodFilter;

/**
 * InvocationHandler for {@link SimpleBeanEditorDriver} proxies. <strong>For
 * internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class SimpleBeanEditorDriverInvocationHandler implements
    InvocationHandler {

  private static FieldFilter EDITOR_FIELD_FILTER = new FieldFilter() {

    public boolean matches(Field field) {
      return isEditorAssignable(field.getType())
          && !Modifier.isPrivate(field.getModifiers())
          && !field.isAnnotationPresent(Ignore.class);
    }
  };

  private static MethodFilter EDITOR_METHOD_FILTER = new MethodFilter() {

    public boolean matches(Method method) {
      return isEditorAssignable(method.getReturnType())
          && method.getParameterTypes().length == 0
          && !Modifier.isPrivate(method.getModifiers())
          && !method.isAnnotationPresent(Ignore.class);
    }
  };

  private static boolean isEditorAssignable(Class<?> candidate) {
    return Editor.class.isAssignableFrom(candidate)
        || IsEditor.class.isAssignableFrom(candidate);
  }

  private Object bean;
  private Editor<?> editor;

  private final Class<?> proxiedClass;

  public SimpleBeanEditorDriverInvocationHandler(Class<?> proxiedClass) {
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

  private void collectEditorFields(
      final Map<String, LeafValueEditor<?>> result,
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

            String editorPath = extractEditorPath(field, pathPrefix);

            if (childEditor instanceof LeafValueEditor) {
              result.put(editorPath, ((LeafValueEditor<?>) childEditor));
            } else {
              collectEditorLeafsRecursive(result, childEditor, editorPath);
            }

          }
        }, EDITOR_FIELD_FILTER);
  }

  private Map<String, LeafValueEditor<?>> collectEditorLeafs() {
    Map<String, LeafValueEditor<?>> result = new HashMap<String, LeafValueEditor<?>>();
    collectEditorLeafsRecursive(result, editor, "");

    return result;
  }

  private void collectEditorLeafsRecursive(Map<String, LeafValueEditor<?>> map,
      Editor<?> currentEditor, String pathPrefix) {
    collectEditorFields(map, currentEditor, pathPrefix);
    collectEditorMethods(map, currentEditor, pathPrefix);
  }

  private void collectEditorMethods(
      final Map<String, LeafValueEditor<?>> result,
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

            String editorPath = extractEditorPath(method, pathPrefix);

            if (childEditor instanceof LeafValueEditor) {
              result.put(editorPath, ((LeafValueEditor<?>) childEditor));
            } else {
              collectEditorLeafsRecursive(result, childEditor, editorPath);
            }

          }

        }, EDITOR_METHOD_FILTER);
  }

  @SuppressWarnings("unchecked")
  private void edit(Object bean) throws IllegalAccessException,
      InvocationTargetException, NoSuchMethodException {
    if (editor == null) {
      throw new IllegalStateException(proxiedClass.getName()
          + ".initialize(editor) method has not been called");
    }
    this.bean = bean;

    for (Map.Entry<String, LeafValueEditor<?>> entry : collectEditorLeafs().entrySet()) {
      // extract value from bean
      String propertyPath = entry.getKey();
      Object value = BeanUtils.getProperty(bean, propertyPath);
      // set the value in editor
      LeafValueEditor<Object> leafValueEditor = (LeafValueEditor<Object>) entry.getValue();
      leafValueEditor.setValue(value);
    }
  }

  private String extractEditorPath(Field field, String pathPrefix) {
    Path pathAnnotation = field.getAnnotation(Path.class);

    String path;
    if (pathAnnotation != null) {
      path = pathAnnotation.value();
    } else {
      path = field.getName();
      if (path.endsWith("Editor")) {
        path = path.substring(0, path.length() - 6);
      }
    }

    return (pathPrefix.length() > 0) ? pathPrefix + "." + path : path;
  }

  private String extractEditorPath(Method method, String pathPrefix) {
    Path pathAnnotation = method.getAnnotation(Path.class);

    String path;
    if (pathAnnotation != null) {
      path = pathAnnotation.value();
    } else {
      path = method.getName();
      if (path.endsWith("Editor")) {
        path = path.substring(0, path.length() - 6);
      }
    }

    return (pathPrefix.length() > 0) ? pathPrefix + "." + path : path;
  }

  private Object flush() throws IllegalAccessException,
      InvocationTargetException {
    if (this.bean == null) {
      throw new IllegalStateException(proxiedClass.getName()
          + ".edit(object) method has not been called");
    }

    Map<String, Object> properties = new HashMap<String, Object>();

    for (Map.Entry<String, LeafValueEditor<?>> entry : collectEditorLeafs().entrySet()) {
      String propertyPath = entry.getKey();
      Object propertyValue = entry.getValue().getValue();
      properties.put(propertyPath, propertyValue);
    }

    BeanUtils.populate(bean, properties);

    return bean;
  }

  private Editor<?> getEditor(Editor<?> currentEditor, Field field) {
    if (Editor.class.isAssignableFrom(field.getType())) {
      return GwtReflectionUtils.<Editor<?>> getPrivateFieldValue(currentEditor,
          field);
    } else {
      IsEditor<Editor<?>> isEditor = GwtReflectionUtils.<IsEditor<Editor<?>>> getPrivateFieldValue(
          currentEditor, field);
      return (isEditor != null) ? isEditor.asEditor() : null;
    }
  }

  private Editor<?> getEditor(Editor<?> currentEditor, Method method) {
    if (Editor.class.isAssignableFrom(method.getReturnType())) {
      return GwtReflectionUtils.<Editor<?>> callPrivateMethod(currentEditor,
          method);
    } else {
      IsEditor<Editor<?>> isEditor = GwtReflectionUtils.<IsEditor<Editor<?>>> callPrivateMethod(
          currentEditor, method);
      return (isEditor != null) ? isEditor.asEditor() : null;
    }
  }

  private void initialize(Editor<?> editor) {
    this.editor = editor;
  }

}
