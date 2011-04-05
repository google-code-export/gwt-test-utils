package com.octo.gwt.test.internal.patchers;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import javassist.CtClass;
import javassist.CtField;

import com.octo.gwt.test.internal.GwtClassPool;
import com.octo.gwt.test.patchers.OverlayPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(classes = {"com.google.gwt.user.client.ui.PrefixTree"})
public class PrefixTreePatcher extends OverlayPatcher {

  private static final String PREFIXES_SET_PROPERTY = "PREFIXES_SET";

  @PatchMethod
  public static boolean add(Object prefixTree, String s) {
    return getPrefixSet(prefixTree).add(s);
  }

  @PatchMethod
  public static void clear(Object prefixTree) {
    GwtReflectionUtils.setPrivateFieldValue(prefixTree, "size", 0);
  }

  @PatchMethod
  public static void suggestImpl(Object prefixTree, String search,
      String prefix, Collection<String> output, int limit) {

    for (String record : getPrefixSet(prefixTree)) {
      if (search == null || record.contains(search.trim().toLowerCase())) {
        output.add(record);
      }
    }
  }

  private static Set<String> getPrefixSet(Object prefixTree) {
    Set<String> set = GwtReflectionUtils.getPrivateFieldValue(prefixTree,
        PREFIXES_SET_PROPERTY);
    if (set == null) {
      set = new TreeSet<String>();
      GwtReflectionUtils.setPrivateFieldValue(prefixTree,
          PREFIXES_SET_PROPERTY, set);
    }
    return set;
  }

  @Override
  public void initClass(CtClass c) throws Exception {
    super.initClass(c);

    // add field "private Set<?> PREFIXES_SET;"
    CtClass pcType = GwtClassPool.getCtClass(Set.class);
    CtField field = new CtField(pcType, PREFIXES_SET_PROPERTY, c);
    field.setModifiers(Modifier.PRIVATE);
    c.addField(field);
  }

}
