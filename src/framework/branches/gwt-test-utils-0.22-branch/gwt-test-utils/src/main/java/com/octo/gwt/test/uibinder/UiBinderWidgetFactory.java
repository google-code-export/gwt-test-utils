package com.octo.gwt.test.uibinder;

import org.xml.sax.Attributes;

import com.google.gwt.user.client.ui.Widget;

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
  UiBinderWidget<? extends Widget> createUiBinderWidget(Widget widget,
      Attributes attributes, UiBinderTag parentTag, Object owner,
      UiResourceManager resourceManager);
}
