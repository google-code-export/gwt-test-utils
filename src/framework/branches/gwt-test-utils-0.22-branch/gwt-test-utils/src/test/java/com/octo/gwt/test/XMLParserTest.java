package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.xml.client.XMLParser;

public class XMLParserTest extends AbstractGwtTest {

  @Test
  public void checkParseThrowsException() {
    try {
      XMLParser.parse("any content");
      Assert.fail("XMLParser.parse(..) is expected to throw an exception since it's not handle by gwt-test-utils");
    } catch (Exception e) {
      // Assert
      Assert.assertEquals(UnsupportedOperationException.class, e.getClass());
      Assert.assertEquals(
          "Abstract method 'XMLParserImplSubClass.parseImpl()' is not patched by any declared IPatcher instance",
          e.getMessage());
    }
  }

}
