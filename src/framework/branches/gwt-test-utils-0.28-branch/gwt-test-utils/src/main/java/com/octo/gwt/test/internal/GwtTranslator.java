package com.octo.gwt.test.internal;

import java.util.Map;
import java.util.regex.Pattern;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.Translator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.gwt.test.Patcher;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.exceptions.GwtTestPatchException;
import com.octo.gwt.test.internal.utils.GwtPatcherUtils;
import com.octo.gwt.test.patchers.OverlayPatcher;

class GwtTranslator implements Translator {

  public static final Logger logger = LoggerFactory.getLogger(GwtTranslator.class);

  private static final Pattern TEST_PATTERN = Pattern.compile("^.*[T|t][E|e][S|s][T|t].*$");

  private final CtClass jsoClass;
  private final Patcher overlayPatcher = new OverlayPatcher();
  private final Map<String, Patcher> patchers;
  private final SerializableModifier serializableModifier = new SerializableModifier();

  public GwtTranslator(Map<String, Patcher> patchers) {
    this.patchers = patchers;
    this.jsoClass = GwtClassPool.getClass("com.google.gwt.core.client.JavaScriptObject");
  }

  public void onLoad(ClassPool pool, String className) throws NotFoundException {
    if (!className.equals(jsoClass.getName())) {
      patchClass(pool.get(className));
    }
  }

  public void start(ClassPool pool) throws NotFoundException {
    patchClass(jsoClass);
  }

  private void applyJavaClassModifier(CtClass ctClass) {
    try {
      logger.debug("Apply method-remover");
      ConfigurationLoader.get().getMethodRemover().modify(ctClass);

      logger.debug("Apply class-substituers");
      ConfigurationLoader.get().getClassSubstituer().modify(ctClass);

      logger.debug("Apply serializable modifier");
      serializableModifier.modify(ctClass);
    } catch (Exception e) {
      if (GwtTestException.class.isInstance(e)) {
        throw (GwtTestException) e;
      } else {
        throw new GwtTestPatchException(e);
      }
    }

  }

  private void applyPatcher(CtClass classToModify) {
    Patcher patcher = patchers.get(classToModify.getName());
    try {
      if (patcher == null && classToModify.subtypeOf(jsoClass)) {
        patcher = overlayPatcher;
      }
    } catch (NotFoundException e) {
      // should never happend
      throw new GwtTestPatchException(e);
    }

    if (patcher == null) {
      return;
    }
    logger.debug("Patching '" + classToModify.getName() + "' with patcher '"
        + patcher.getClass().getName() + "'");
    try {
      GwtPatcherUtils.patch(classToModify, patcher);
    } catch (Exception e) {
      if (GwtTestException.class.isInstance(e)) {
        throw (GwtTestException) e;
      } else {
        throw new GwtTestPatchException("Error while patching class '"
            + classToModify.getName() + "' with patcher '"
            + patcher.getClass().getName() + "'", e);
      }
    }
  }

  private void modifiyClass(CtClass classToModify) {

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

  private void patchClass(CtClass classToModify) {
    logger.debug("Load class '" + classToModify.getName() + "'");
    applyPatcher(classToModify);
    modifiyClass(classToModify);
    logger.debug("Class '" + classToModify.getName() + "' has been loaded");
  }
}
