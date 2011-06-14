package com.octo.gwt.test.internal.patchers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.octo.gwt.test.patchers.InitMethod;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.JavassistUtils;

@PatchClass(Image.class)
class ImagePatcher {

  private static final Pattern PATTERN = Pattern.compile("^(\\d+).*$");

  @InitMethod
  static void addImageElement(CtClass c) throws CannotCompileException {

    List<CtConstructor> constructors = getConstructorsToModify(c);

    for (CtConstructor cons : constructors) {
      cons.insertBeforeBody("setElement(" + Document.class.getName()
          + ".get().createImageElement());");
    }
  }

  @PatchMethod
  static int getHeight(Image image) {
    return getDim(image, "height");
  }

  @PatchMethod
  static int getWidth(Image image) {
    return getDim(image, "width");
  }

  private static List<CtConstructor> getConstructorsToModify(CtClass c) {
    List<CtConstructor> result = new ArrayList<CtConstructor>();
    result.add(JavassistUtils.findConstructor(c));
    result.add(JavassistUtils.findConstructor(c, String.class));
    result.add(JavassistUtils.findConstructor(c, String.class, Integer.TYPE,
        Integer.TYPE, Integer.TYPE, Integer.TYPE));

    return result;
  }

  private static int getDim(Image image, String dim) {
    ImageElement elem = image.getElement().cast();
    String width = elem.getStyle().getProperty(dim);
    if (width == null)
      return 0;
    Matcher m = PATTERN.matcher(width);
    if (m.matches()) {
      return Integer.parseInt(m.group(1));
    }
    return 0;
  }

}
