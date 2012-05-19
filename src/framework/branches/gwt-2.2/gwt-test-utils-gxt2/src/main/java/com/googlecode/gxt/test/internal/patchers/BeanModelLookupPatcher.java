package com.googlecode.gxt.test.internal.patchers;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.googlecode.gwt.test.exceptions.ReflectionException;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;

@PatchClass(BeanModelLookup.class)
class BeanModelLookupPatcher {

  private static class BeanModelFactoryImpl extends BeanModelFactory {

    @Override
    protected BeanModel newInstance() {
      return new BeanModelImpl();
    }

  }

  private static class BeanModelImpl extends BeanModel {

    private static final long serialVersionUID = -4320294118731275689L;

    @SuppressWarnings("unchecked")
    @Override
    public <X> X get(String property) {
      try {
        return (X) PropertyUtils.getProperty(getBean(), property);
      } catch (Exception e) {
        throw new ReflectionException("Error while reading property '"
            + property + "' for object of type '"
            + getBean().getClass().getName() + "'", e);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getProperties() {
      try {
        return PropertyUtils.describe(getBean());
      } catch (Exception e) {
        throw new ReflectionException(
            "Error while reading properties for object of type '"
                + getBean().getClass().getName() + "'", e);
      }
    }

    @Override
    public Collection<String> getPropertyNames() {
      Collection<String> names = new ArrayList<String>();
      for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(getBean().getClass())) {
        names.add(pd.getDisplayName());
      }

      return names;
    }

  }

  @PatchMethod
  static BeanModelFactory getFactory(BeanModelLookup beanModelLookup,
      Class<?> bean) {
    return new BeanModelFactoryImpl();
  }

}
