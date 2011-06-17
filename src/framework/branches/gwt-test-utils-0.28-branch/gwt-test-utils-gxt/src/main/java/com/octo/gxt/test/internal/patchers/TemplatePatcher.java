package com.octo.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.core.Template;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Template.class)
public class TemplatePatcher {

  public static class TemplateJSO extends JavaScriptObject {

    private final String html;

    public TemplateJSO(String html) {
      this.html = html;
    }
  }

  @PatchMethod
  public static Element appendInternal(JavaScriptObject t, Element el,
      JavaScriptObject values) {
    TemplateJSO tjso = t.cast();
    el.setInnerHTML(tjso.html);

    return el;

  }

  @PatchMethod
  public static String applyInternal(JavaScriptObject t, JavaScriptObject values) {
    TemplateJSO tjso = t.cast();
    return tjso.html;
  }

  @PatchMethod
  public static void compile(Template tem) {
    //
  }

  @PatchMethod
  public static JavaScriptObject create(String html) {
    return new TemplateJSO(html);
  }

  @PatchMethod
  public static String getHtml(JavaScriptObject t) {
    TemplateJSO tjso = t.cast();
    return tjso.html;

  }

  @PatchMethod
  public static Element insertInternal(String method, JavaScriptObject t,
      Element el, JavaScriptObject values) {
    TemplateJSO tjso = t.cast();
    el.setInnerHTML(tjso.html);

    return el;
  }

}
