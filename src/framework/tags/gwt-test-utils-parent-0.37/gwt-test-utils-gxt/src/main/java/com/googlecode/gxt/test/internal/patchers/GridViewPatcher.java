package com.googlecode.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.widget.grid.GridView;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.googlecode.gwt.test.internal.utils.JavaScriptObjects;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

@PatchClass(GridView.class)
class GridViewPatcher {

  // TODO : remove this when overlay type will be handled nicely
  @PatchMethod
  static NodeList<Element> getRows(GridView gridView) {
    boolean hasRows = (Boolean) GwtReflectionUtils.callPrivateMethod(gridView,
        "hasRows");
    if (!hasRows) {
      return JavaScriptObjects.newNodeList();
    }

    El mainBody = GwtReflectionUtils.getPrivateFieldValue(gridView, "mainBody");
    return mainBody.dom.getChildNodes().cast();
  }

}
