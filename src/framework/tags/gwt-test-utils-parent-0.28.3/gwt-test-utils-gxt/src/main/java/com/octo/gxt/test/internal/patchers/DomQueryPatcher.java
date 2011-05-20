package com.octo.gxt.test.internal.patchers;

import java.util.ArrayList;
import java.util.Set;

import se.fishtank.css.selectors.NodeSelectorException;
import se.fishtank.css.selectors.dom.DOMNodeSelector;

import com.extjs.gxt.ui.client.core.DomQuery;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test.exceptions.GwtTestPatchException;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(DomQuery.class)
public class DomQueryPatcher extends AutomaticPatcher {

  @PatchMethod
  public static JavaScriptObject internalSelect(String selector) {
    Element body = Document.get().getBody().cast();
    return internalSelect(selector, body);
  }

  @PatchMethod
  public static JavaScriptObject internalSelect(String selector, Element root) {
    try {
      Set<Node> nodeSet = new DOMNodeSelector(root).querySelectorAll(selector);
      return JavaScriptObjects.newNodeList(new ArrayList<Node>(nodeSet));
    } catch (NodeSelectorException e) {
      throw new GwtTestPatchException(
          "Error while trying to find GWT nodes matching '" + selector + "'", e);
    }
  }

}
