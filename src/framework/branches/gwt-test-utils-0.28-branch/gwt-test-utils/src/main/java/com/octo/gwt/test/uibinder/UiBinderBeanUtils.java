package com.octo.gwt.test.uibinder;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.IsWidget;
import com.octo.gwt.test.exceptions.ReflectionException;

/**
 * Utility class to populate Widgets declared in .ui.xml files. It relies on
 * <code>commons-beanutils</code> API.
 * 
 * @author Gael Lazzari
 * 
 */
public class UiBinderBeanUtils {

  private static final BeanUtilsBean UIBINDER_BEANUTILS = new BeanUtilsBean();

  static {
    registerConverter(new Converter() {

      @SuppressWarnings("rawtypes")
      public Object convert(Class type, Object value) {
        return UiBinderXmlUtils.parseHorizontalAlignment(value.toString());
      }
    }, HorizontalAlignmentConstant.class);

    registerConverter(new Converter() {

      @SuppressWarnings("rawtypes")
      public Object convert(Class type, Object value) {
        return UiBinderXmlUtils.parseVerticalAlignment(value.toString());
      }
    }, VerticalAlignmentConstant.class);
  }

  /**
   * 
   * @param w
   * @param properties
   * 
   * @see BeanUtilsBean#populate(Object, Map)
   */
  public static void populateWidget(IsWidget w, Map<String, Object> properties) {
    try {
      UIBINDER_BEANUTILS.populate(w, properties);
    } catch (Exception e) {
      throw new ReflectionException(
          "UiBinder error while setting properties for '"
              + w.getClass().getSimpleName() + "'", e);
    }

    // handle specifics
    String[] styles = (String[]) properties.get("addStyleNames");
    if (styles != null) {
      for (String style : styles) {
        w.asWidget().addStyleName(style);
      }
    }
  }

  /**
   * 
   * @param converter
   * @param clazz
   * 
   * @see ConvertUtils#register(Converter, Class)
   */
  public static void registerConverter(Converter converter, Class<?> clazz) {
    UIBINDER_BEANUTILS.getConvertUtils().register(converter, clazz);
  }

}
