package com.octo.gwt.test.uibinder;

import org.xml.sax.Attributes;

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
      Attributes attributes, UiBinderTag parentTag, Object owner,
      UiResourceManager resourceManager);
}
