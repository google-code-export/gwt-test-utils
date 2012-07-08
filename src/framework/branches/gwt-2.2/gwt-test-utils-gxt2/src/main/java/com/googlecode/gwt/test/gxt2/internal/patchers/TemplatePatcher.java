package com.googlecode.gwt.test.gxt2.internal.patchers;

import com.extjs.gxt.ui.client.core.Template;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;

@PatchClass(Template.class)
class TemplatePatcher {

  @PatchMethod
  static Element appendInternal(JavaScriptObject t, Element el,
      JavaScriptObject values) {
    Element template = t.cast();
    el.setInnerHTML(template.getInnerHTML());

    return el;

  }

  @PatchMethod
  static String applyInternal(JavaScriptObject t, JavaScriptObject values) {
    Element template = t.cast();
    return template.getInnerHTML();
  }

  @PatchMethod
  static void compile(Template tem) {
    //
  }

  @PatchMethod
  static JavaScriptObject create(String html) {
    Element template = Document.get().createDivElement().cast();
    template.setInnerHTML(html);

    return template;
  }

  @PatchMethod
  static String getHtml(JavaScriptObject t) {
    Element template = t.cast();
    return template.getInnerHTML();

  }

  @PatchMethod
  static Element insertInternal(String method, JavaScriptObject t, Element el,
      JavaScriptObject values) {
    Element template = t.cast();
    el.setInnerHTML(template.getInnerHTML());

    return el;
  }

}
