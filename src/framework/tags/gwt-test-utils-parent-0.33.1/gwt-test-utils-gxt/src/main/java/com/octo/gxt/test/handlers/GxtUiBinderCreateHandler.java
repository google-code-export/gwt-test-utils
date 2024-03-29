package com.octo.gxt.test.handlers;

import org.apache.commons.beanutils.Converter;

import com.extjs.gxt.ui.client.widget.Layout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.uibinder.UiBinderBeanUtils;
import com.octo.gwt.test.uibinder.UiBinderCreateHandler;

/**
 * A custom <code>GwtCreateHandler</code> to handle GXT component creations
 * through the third-party library <strong>gxt-uibinder</strong>
 * (http://code.google.com/p/gxt-uibinder/)
 * 
 * @author Gael Lazzari
 * 
 */
@SuppressWarnings("rawtypes")
public class GxtUiBinderCreateHandler implements GwtCreateHandler {

  static {
    UiBinderBeanUtils.registerConverter(new Converter() {

      public Object convert(Class type, Object value) {
        if ("FitLayout".equals(value)) {
          return new FitLayout();
        } else if ("BorderLayout".equals(value)) {
          return new BorderLayout();
        } else {
          throw new GwtTestUiBinderException("GXT Layout not managed yet : '"
              + value + "'");
        }
      }
    }, Layout.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.GwtCreateHandler#create(java.lang.Class)
   */
  public Object create(Class<?> classLiteral) throws Exception {
    return UiBinderCreateHandler.get().create(classLiteral);
  }

}
