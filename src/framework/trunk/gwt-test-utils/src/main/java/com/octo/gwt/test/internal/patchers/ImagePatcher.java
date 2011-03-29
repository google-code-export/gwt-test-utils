package com.octo.gwt.test.internal.patchers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Image.class)
public class ImagePatcher extends AutomaticPatcher {

  private static final Pattern PATTERN = Pattern.compile("^(\\d+).*$");

  public static int getDim(Image image, String dim) {
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

  @PatchMethod
  public static int getHeight(Image image) {
    return getDim(image, "height");
  }

  @PatchMethod
  public static int getWidth(Image image) {
    return getDim(image, "width");
  }

  @Override
  public void initClass(CtClass c) throws Exception {
    super.initClass(c);

    List<CtConstructor> constructors = getConstructorsToModify(c);

    for (CtConstructor cons : constructors) {
      cons.insertBeforeBody("setElement(" + Document.class.getName()
          + ".get().createImageElement());");
    }
  }

  private List<CtConstructor> getConstructorsToModify(CtClass c)
      throws NotFoundException {
    List<CtConstructor> result = new ArrayList<CtConstructor>();
    result.add(findConstructor(c));
    result.add(findConstructor(c, String.class));
    result.add(findConstructor(c, String.class, Integer.TYPE, Integer.TYPE,
        Integer.TYPE, Integer.TYPE));

    return result;
  }

}
