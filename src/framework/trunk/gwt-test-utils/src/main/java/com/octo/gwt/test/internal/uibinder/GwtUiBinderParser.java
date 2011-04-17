package com.octo.gwt.test.internal.uibinder;

import java.io.InputStream;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.google.gwt.uibinder.client.UiField;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.exceptions.GwtTestPatchException;

/**
 * Class in charge of parsing the .ui.xml file and filling both root
 * element/widget and all {@link UiField} in the owner object.
 * 
 * @author Gael Lazzari
 * 
 */
public class GwtUiBinderParser {

  /**
   * Parse the .ui.xml file to fill the corresponding objects.
   * 
   * @param rootComponentClass the root component's class that UiBinder has to
   *          instanciated.
   * @param owner The owner of the UiBinder template, with {@link UiField}
   *          fields.
   */
  public <T> T createUiComponent(Class<T> rootComponentClass, Object owner) {
    InputStream uiXmlStream = getUiXmlFile(owner);
    if (uiXmlStream == null) {
      throw new GwtTestConfigurationException(
          "Cannot find the .ui.xml file corresponding to '"
              + owner.getClass().getName() + "'");
    }

    UiXmlContentHandler<T> contentHandler = new UiXmlContentHandler<T>(
        rootComponentClass, owner);

    try {
      XMLReader saxReader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
      saxReader.setContentHandler(contentHandler);
      saxReader.parse(new InputSource(getUiXmlFile(owner)));
    } catch (Exception e) {
      if (GwtTestException.class.isInstance(e)) {
        throw (GwtTestException) e;
      } else {
        throw new GwtTestPatchException("Error while parsing '"
            + owner.getClass().getSimpleName() + ".ui.xml'", e);
      }
    }

    return contentHandler.getRootComponent();
  }

  private InputStream getUiXmlFile(Object owner) {
    return owner.getClass().getResourceAsStream(
        owner.getClass().getSimpleName() + ".ui.xml");
  }

}
