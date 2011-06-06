package com.octo.gwt.test.internal;

import java.util.HashMap;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.Translator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.gwt.test.IPatcher;
import com.octo.gwt.test.internal.modifiers.JavaClassModifier;
import com.octo.gwt.test.internal.utils.PatchGwtUtils;

public class GwtTranslator implements Translator {

  public static final Logger logger = LoggerFactory.getLogger(GwtTranslator.class);

  private Map<String, IPatcher> map = new HashMap<String, IPatcher>();

  public GwtTranslator(Map<String, IPatcher> map) {
    this.map = map;
  }

  public void onLoad(ClassPool pool, String className)
      throws NotFoundException, CannotCompileException {
    try {
      IPatcher patcher = map.get(className);
      if (patcher != null) {
        logger.debug("Load class " + className + ", use patcher "
            + patcher.getClass().getCanonicalName());
        CtClass clazz = pool.get(className);
        logger.debug("Patch class " + className);
        PatchGwtUtils.patch(clazz, patcher);
        logger.debug("Class loaded & patched " + className);
      } else {
        logger.debug("Load class " + className + ", no patch");
      }

      modifiyClass(className);
    } catch (Exception e) {
      throw new CannotCompileException(e);
    }
  }

  public void start(ClassPool pool) throws NotFoundException,
      CannotCompileException {

  }

  private void modifiyClass(String className) throws Exception {
    logger.debug("Modify class " + className
        + ", with modifier declared in 'META-INF/gwt-test-utils.properties'");
    CtClass classToModify = PatchGwtClassPool.get().get(className);
    for (JavaClassModifier modifier : ConfigurationLoader.getInstance().getJavaClassModifierList()) {
      modifier.modify(classToModify);
    }
  }

}
