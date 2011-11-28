package com.octo.gwt.test.uibinder;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Factory for {@link UiBinderWidget}.
 * 
 * @author Gael Lazzari
 * 
 */
public interface UiBinderWidgetFactory {

  /**
   * 
   * @param widget
   * @param attributes
   * @param parentTag
   * @param owner
   * @param resourceManager
   * @return
   */
  UiBinderWidget<? extends IsWidget> createUiBinderWidget(IsWidget widget,
      Map<String, Object> attributes, UiBinderTag parentTag, Object owner,
      UiResourceManager resourceManager);
}
