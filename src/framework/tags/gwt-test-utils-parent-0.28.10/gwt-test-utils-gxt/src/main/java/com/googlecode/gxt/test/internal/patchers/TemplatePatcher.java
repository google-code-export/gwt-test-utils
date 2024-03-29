package com.googlecode.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.core.Template;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;

@PatchClass(Template.class)
class TemplatePatcher {

  static class TemplateJSO extends JavaScriptObject {

    private final String html;

    TemplateJSO(String html) {
      this.html = html;
    }
  }

  @PatchMethod
  static Element appendInternal(JavaScriptObject t, Element el,
      JavaScriptObject values) {
    TemplateJSO tjso = t.cast();
    el.setInnerHTML(tjso.html);

    return el;

  }

  @PatchMethod
  static String applyInternal(JavaScriptObject t, JavaScriptObject values) {
    TemplateJSO tjso = t.cast();
    return tjso.html;
  }

  @PatchMethod
  static void compile(Template tem) {
    //
  }

  @PatchMethod
  static JavaScriptObject create(String html) {
    return new TemplateJSO(html);
  }

  @PatchMethod
  static String getHtml(JavaScriptObject t) {
    TemplateJSO tjso = t.cast();
    return tjso.html;

  }

  @PatchMethod
  static Element insertInternal(String method, JavaScriptObject t, Element el,
      JavaScriptObject values) {
    TemplateJSO tjso = t.cast();
    el.setInnerHTML(tjso.html);

    return el;
  }

}
