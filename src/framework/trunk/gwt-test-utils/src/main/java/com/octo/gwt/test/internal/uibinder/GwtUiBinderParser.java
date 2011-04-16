package com.octo.gwt.test.internal.uibinder;

import java.io.InputStream;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.google.gwt.uibinder.client.UiField;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.exceptions.GwtTestPatchException;
import com.octo.gwt.test.internal.uibinder.objects.UiBinderTagFactory;

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
  @SuppressWarnings("unchecked")
  public <T> T createUiComponenet(Class<T> rootComponentClass, Object owner) {
    InputStream uiXmlStream = getUiXmlFile(owner);
    if (uiXmlStream == null) {
      throw new GwtTestConfigurationException(
          "Cannot find the .ui.xml file corresponding to '"
              + owner.getClass().getName() + "'");
    }

    UiXmlContentHandler contentHandler = createUiBnderParser(owner);

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

    Object rootComponent = contentHandler.getRootComponent();

    if (!rootComponentClass.isInstance(rootComponent)) {
      throw new GwtTestConfigurationException(
          "Error in '"
              + owner.getClass().getSimpleName()
              + ".ui.xml' configuration : root component is expected to be an instance of '"
              + rootComponentClass.getName()
              + "' but is actually an instance of '"
              + rootComponent.getClass().getName() + "'");
    }

    return (T) rootComponent;

  }

  private UiXmlContentHandler createUiBnderParser(Object owner) {
    UiBinderTagFactory factory = new UiBinderTagFactory(owner);
    return new UiXmlContentHandler(factory);
  }

  private InputStream getUiXmlFile(Object owner) {
    return owner.getClass().getResourceAsStream(
        owner.getClass().getSimpleName() + ".ui.xml");
  }

}
