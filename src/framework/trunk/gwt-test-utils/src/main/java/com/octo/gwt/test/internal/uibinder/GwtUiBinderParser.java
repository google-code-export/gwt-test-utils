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

  private UiXmlContentHandler createUiBnderParser(Object rootObject,
      Object owner) {
    return new UiXmlContentHandler(rootObject, owner);
  }

  /**
   * Parse the .ui.xml file to fill the corresponding objects.
   * 
   * @param rootObject the root Element or widget UiBinder has instanciated.
   * @param owner The owner of the UiBinder template, with {@link UiField}
   *          fields.
   */
  public void fillObjects(Object rootObject, Object owner) {
    InputStream uiXmlStream = getUiXmlFile(owner);
    if (uiXmlStream == null) {
      throw new GwtTestConfigurationException(
          "Cannot find the .ui.xml file corresponding to '"
              + owner.getClass().getName() + "'");
    }

    try {
      XMLReader saxReader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
      UiXmlContentHandler contentHandler = createUiBnderParser(rootObject,
          owner);
      saxReader.setContentHandler(contentHandler);
      saxReader.parse(new InputSource(getUiXmlFile(owner)));
    } catch (Exception e) {
      if (GwtTestException.class.isInstance(e)) {
        throw (GwtTestException) e;
      } else {
        throw new GwtTestPatchException(
            "Error while parsing UiBinder file for '"
                + owner.getClass().getName() + "'", e);
      }
    }
  }

  private InputStream getUiXmlFile(Object owner) {
    return owner.getClass().getResourceAsStream(
        owner.getClass().getSimpleName() + ".ui.xml");
  }

}
