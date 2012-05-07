package com.googlecode.gwt.test.internal.handlers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.googlecode.gwt.test.GwtCreateHandler;
import com.googlecode.gwt.test.internal.ModuleData;
import com.googlecode.gwt.test.internal.ModuleData.ReplaceWithData;
import com.googlecode.gwt.test.utils.events.Browser;

public class DeferredReplaceWithCreateHandler implements GwtCreateHandler {

  public Object create(Class<?> classLiteral) throws Exception {

    List<ReplaceWithData> replaceWithList = ModuleData.get().getReplaceWithListMap().get(
        classLiteral.getName());

    // not handled by a <replace-with> element in any .gwt.xml file
    if (replaceWithList == null) {
      return null;
    }

    String replaceWith = getReplaceWithClass(replaceWithList,
        Browser.getProperties());

    return (replaceWith != null && !replaceWith.equals(classLiteral))
        ? GWT.create(Class.forName(replaceWith)) : null;

  }

  private String getReplaceWithClass(List<ReplaceWithData> replaceWithList,
      Map<String, String> browserProperties) {

    // the default <replace-with>, with no filter
    ReplaceWithData defaultReplaceWith = null;

    for (ReplaceWithData replaceWithData : replaceWithList) {

      if ((browserProperties == null || browserProperties.size() == 0)) {

        if (isDefault(replaceWithData)) {
          // default case : nothing is specified
          return replaceWithData.getReplaceWith();
        } else {
          // not a <replace-with> element to use
          continue;
        }
      } else if (isDefault(replaceWithData)) {
        // save the default <replace-with>, to use
        defaultReplaceWith = replaceWithData;
        continue;

      }

      // validate every when-property-is match

      if (replaceWithData.hasWhenPropertyIs()) {
        boolean validateProperties = true;
        Iterator<Entry<String, String>> it = browserProperties.entrySet().iterator();
        while (it.hasNext() && validateProperties) {
          Map.Entry<String, String> entry = it.next();
          String browserPropertyName = entry.getKey();
          String browserPropertyValue = entry.getValue();
          validateProperties = replaceWithData.whenPropertyIsMatch(
              browserPropertyName, browserPropertyValue);
        }
        if (!validateProperties) {
          continue;
        }
      }

      // validate at least one any/when-property-is matches
      boolean validateAnyProperty = !replaceWithData.hasAnyWhenPropertyIs();
      Iterator<Entry<String, String>> it = browserProperties.entrySet().iterator();
      while (it.hasNext() && !validateAnyProperty) {
        Map.Entry<String, String> entry = it.next();
        String browserPropertyName = entry.getKey();
        String browserPropertyValue = entry.getValue();

        validateAnyProperty = replaceWithData.anyMatch(browserPropertyName,
            browserPropertyValue);
      }

      if (validateAnyProperty) {
        return replaceWithData.getReplaceWith();
      }
    }

    return (defaultReplaceWith != null) ? defaultReplaceWith.getReplaceWith()
        : null;
  }

  private boolean isDefault(ReplaceWithData replaceWithData) {
    return !replaceWithData.hasAnyWhenPropertyIs()
        && !replaceWithData.hasWhenPropertyIs();
  }
}
