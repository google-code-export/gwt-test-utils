package com.octo.gwt.test.internal;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.Translator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.gwt.test.Patcher;
import com.octo.gwt.test.internal.modifiers.JavaClassModifier;
import com.octo.gwt.test.internal.utils.GwtPatcherUtils;

public class GwtTranslator implements Translator {

  public static final Logger logger = LoggerFactory.getLogger(GwtTranslator.class);

  private static final Pattern TEST_PATTERN = Pattern.compile("^.*[T|t][E|e][S|s][T|t].*$");

  private Set<String> jsoSubTypes;
  private Map<String, Patcher> patchers;

  public GwtTranslator(Map<String, Patcher> patchers, Set<String> jsoSubTypes) {
    this.patchers = patchers;
    this.jsoSubTypes = jsoSubTypes;
  }

  public void onLoad(ClassPool pool, String className)
      throws NotFoundException, CannotCompileException {
    if (!jsoSubTypes.contains(className)) {
      patchClass(pool.get(className));
    }
  }

  public void start(ClassPool pool) throws NotFoundException,
      CannotCompileException {
    for (String jsoSubType : jsoSubTypes) {
      patchClass(pool.get(jsoSubType));
    }
  }

  private void applyJavaClassModifier(CtClass ctClass)
      throws CannotCompileException {
    for (JavaClassModifier modifier : ConfigurationLoader.get().getJavaClassModifierList()) {
      logger.debug("Apply '" + modifier.getClass().getName() + "' to class '"
          + ctClass.getName() + "'");
      try {
        modifier.modify(ctClass);
      } catch (Exception e) {
        if (CannotCompileException.class.isInstance(e)) {
          throw (CannotCompileException) e;
        } else if (RuntimeException.class.isInstance(e)) {
          throw (RuntimeException) e;
        } else {
          throw new CannotCompileException(e);
        }
      }
    }
  }

  private void applyPatcher(CtClass classToModify) {
    Patcher patcher = patchers.get(classToModify.getName());
    if (patcher != null) {
      logger.debug("Patching '" + classToModify.getName() + "' with patcher '"
          + patcher.getClass().getName() + "'");
      try {
        GwtPatcherUtils.patch(classToModify, patcher);
      } catch (Exception e) {
        throw new RuntimeException("Error while patching class '"
            + classToModify.getName() + "' with patcher '"
            + patcher.getClass().getName() + "'");
      }
    }
  }

  private void modifiyClass(CtClass classToModify)
      throws CannotCompileException {

    for (String exclusion : ModuleData.get().getClientExclusions()) {
      if (classToModify.getName().equals(exclusion)) {
        // don't modify this class
        return;
      }
    }

    for (String clientPackage : ModuleData.get().getClientPaths()) {
      if (classToModify.getName().startsWith(clientPackage)) {
        // modifiy this class
        applyJavaClassModifier(classToModify);
        return;
      }
    }

    if (TEST_PATTERN.matcher(classToModify.getName()).matches()) {
      applyJavaClassModifier(classToModify);
    }
  }

  private void patchClass(CtClass classToModify) throws NotFoundException,
      CannotCompileException {
    logger.debug("Load class '" + classToModify.getName() + "'");
    applyPatcher(classToModify);
    modifiyClass(classToModify);
    logger.debug("Class '" + classToModify.getName() + "' has been loaded");
  }

}
