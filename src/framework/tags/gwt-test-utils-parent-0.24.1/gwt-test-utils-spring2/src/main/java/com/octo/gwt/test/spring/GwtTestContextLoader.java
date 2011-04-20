package com.octo.gwt.test.spring;

import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.test.context.support.GenericXmlContextLoader;

import com.octo.gwt.test.GwtClassLoader;

public class GwtTestContextLoader extends GenericXmlContextLoader {

  @Override
  protected BeanDefinitionReader createBeanDefinitionReader(
      GenericApplicationContext context) {
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(
        context);
    beanDefinitionReader.setResourceLoader(new DefaultResourceLoader(
        GwtClassLoader.get()));
    return beanDefinitionReader;
  }

}
