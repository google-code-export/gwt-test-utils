package com.octo.gwt.test.uibinder;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Factory for {@link UiWidgetTag}.
 * 
 * @author Gael Lazzari
 * 
 * @param <T>
 * 
 */
public interface UiWidgetTagFactory<T extends IsWidget> {

  /**
   * Try to create a UiBinderWidget which would match the current UiBinder tag
   * declaration. The resulting UiBinderWidget would wrap a IsWidget instance in
   * order to append its child Widgets and elements according to its UiBinder
   * declaration.
   * 
   * @param widgetClass The wrapped widget class to instanciate.
   * @param attributes map of attributes of the wrapped widget, with attribute
   *          XML names as keys, corresponding objects as values (includes
   *          ui:with and ui:import resources).
   * @return The UiBinderWidget instance which wrapped the wrapped widget in
   *         order to enhance it, or null if the factory was not able to
   *         instanciate the right UiBinderWidget implementation.
   */
  UiWidgetTag<T> createUiWidgetTag(
      Class<? extends IsWidget> widgetClass, Map<String, Object> attributes);
}
