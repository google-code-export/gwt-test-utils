package com.octo.gwt.test.uibinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;
import com.octo.gwt.test.internal.GwtConfig;

/**
 * Default UiWidgetTagFactory, which try to delegate {@link UiWidgetTag}
 * instanciation to UiWidgetTagFactories added by users before using those
 * implemented in gwt-test-utils.
 * 
 * @author Gael Lazzari
 * 
 */
class DefaultUiWidgetTagFactory implements UiWidgetTagFactory<IsWidget> {

  private static final DefaultUiWidgetTagFactory INSTANCE = new DefaultUiWidgetTagFactory();

  public static DefaultUiWidgetTagFactory get() {
    return INSTANCE;
  }

  private final List<UiWidgetTagFactory<? extends IsWidget>> gwtTestUtilsFactories;

  private DefaultUiWidgetTagFactory() {
    gwtTestUtilsFactories = new ArrayList<UiWidgetTagFactory<? extends IsWidget>>();

    gwtTestUtilsFactories.add(new UiCellPanelTagFactory());
    gwtTestUtilsFactories.add(new UiDateLabelTagFactory());
    gwtTestUtilsFactories.add(new UiDockLayoutPanelTagFactory());
    gwtTestUtilsFactories.add(new UiHTMLPanelTagFactory());
    gwtTestUtilsFactories.add(new UiImageTagFactory());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.uibinder.UiBinderWidgetFactory#createUiWidgetTag
   * (java.lang.Class, java.util.Map)
   */
  public UiWidgetTag<IsWidget> createUiWidgetTag(
      Class<? extends IsWidget> widgetClass, Map<String, Object> attributes) {

    // try with user's custom UiBinderWidgetFactories
    UiWidgetTag<IsWidget> result = tryInstanciateUiBinderWidget(widgetClass,
        attributes, GwtConfig.get().getUiBinderWidgetFactories());

    if (result != null) {
      return result;
    }

    // try with gwt-test-utils custom UiBinderWidgetFactories
    result = tryInstanciateUiBinderWidget(widgetClass, attributes,
        gwtTestUtilsFactories);
    if (result != null) {
      return result;
    }

    // default
    return new UiWidgetTag<IsWidget>() {

      @Override
      protected void finalizeWidget(IsWidget widget) {
        // nothing to do
      }

      @Override
      protected void initializeWidget(IsWidget wrapped,
          Map<String, Object> attributes, Object owner) {
        // nothing to do

      }

    };
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private UiWidgetTag<IsWidget> tryInstanciateUiBinderWidget(
      Class<? extends IsWidget> widgetClass, Map<String, Object> attributes,
      List<UiWidgetTagFactory<? extends IsWidget>> uiBinderWidgetFactories) {
    for (UiWidgetTagFactory factory : uiBinderWidgetFactories) {

      UiWidgetTag<IsWidget> result = factory.createUiWidgetTag(widgetClass,
          attributes);
      if (result != null) {
        return result;
      }
    }

    return null;
  }

}
