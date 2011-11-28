package com.octo.gwt.test.uibinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;
import com.octo.gwt.test.internal.GwtConfig;

/**
 * Default UiBinderWidgetFactory, which try to delegate {@link UiBinderWidget}
 * instanciation to UiBinderWidgetFactories added by users before using the
 * default ones declared in gwt-test-utils.
 * 
 * @author Gael Lazzari
 * 
 * @see GwtConfig#getUiBinderWidgetFactories()
 * 
 */
class DefaultUiBinderWidgetFactory implements UiBinderWidgetFactory {

  private static final DefaultUiBinderWidgetFactory INSTANCE = new DefaultUiBinderWidgetFactory();

  public static DefaultUiBinderWidgetFactory get() {
    return INSTANCE;
  }

  private final List<UiBinderWidgetFactory> gwtTestUtilsFactories;

  private DefaultUiBinderWidgetFactory() {
    gwtTestUtilsFactories = new ArrayList<UiBinderWidgetFactory>();

    gwtTestUtilsFactories.add(new UiBinderDockLayoutPanelFactory());
    gwtTestUtilsFactories.add(new UiBinderCellPanelFactory());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.octo.gwt.test.uibinder.UiBinderWidgetFactory#createUiBinderWidget(com
   * .google.gwt.user.client.ui.IsWidget, java.util.Map,
   * com.octo.gwt.test.uibinder.UiBinderTag, java.lang.Object,
   * com.octo.gwt.test.uibinder.UiResourceManager)
   */
  public UiBinderWidget<? extends IsWidget> createUiBinderWidget(
      IsWidget widget, Map<String, Object> attributes, UiBinderTag parentTag,
      Object owner, UiResourceManager resourceManager) {

    // try with user's custom UiBinderWidgetFactories
    UiBinderWidget<? extends IsWidget> result = tryInstanciate(widget,
        attributes, parentTag, owner, resourceManager,
        GwtConfig.get().getUiBinderWidgetFactories());

    if (result != null) {
      return result;
    }

    // try with gwt-test-utils custom UiBinderWidgetFactories
    result = tryInstanciate(widget, attributes, parentTag, owner,
        resourceManager, gwtTestUtilsFactories);
    if (result != null) {
      return result;
    }

    // default
    return new UiBinderWidget<IsWidget>(widget, attributes, parentTag, owner,
        resourceManager);
  }

  private UiBinderWidget<? extends IsWidget> tryInstanciate(IsWidget widget,
      Map<String, Object> attributes, UiBinderTag parentTag, Object owner,
      UiResourceManager resourceManager,
      List<UiBinderWidgetFactory> uiBinderWidgetFactories) {
    for (UiBinderWidgetFactory factory : uiBinderWidgetFactories) {
      UiBinderWidget<? extends IsWidget> result = factory.createUiBinderWidget(
          widget, attributes, parentTag, owner, resourceManager);
      if (result != null) {
        return result;
      }
    }

    return null;
  }

}
