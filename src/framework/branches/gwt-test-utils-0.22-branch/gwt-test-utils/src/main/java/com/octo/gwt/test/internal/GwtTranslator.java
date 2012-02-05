package com.octo.gwt.test.internal;

import java.util.regex.Pattern;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.Translator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.exceptions.GwtTestPatchException;

class GwtTranslator implements Translator {

  private static final Logger LOGGER = LoggerFactory.getLogger(GwtTranslator.class);

  private static final Pattern TEST_PATTERN = Pattern.compile("^.*[T|t][E|e][S|s][T|t].*$");

  private final ConfigurationLoader configurationLoader;
  private final SerializableModifier serializableModifier;

  GwtTranslator(ConfigurationLoader configurationLoader) {
    this.serializableModifier = new SerializableModifier();
    this.configurationLoader = configurationLoader;
  }

  public void onLoad(ClassPool pool, String className) throws NotFoundException {
    patchClass(pool.get(className));
  }

  public void start(ClassPool pool) throws NotFoundException {
  }

  private void applyJavaClassModifier(CtClass ctClass) {
    try {
      // Apply remove-method
      configurationLoader.getMethodRemover().modify(ctClass);

      // Apply substitute-class
      configurationLoader.getClassSubstituer().modify(ctClass);

      // Apply serializable modifier
      serializableModifier.modify(ctClass);
    } catch (Exception e) {
      if (GwtTestException.class.isInstance(e)) {
        throw (GwtTestException) e;
      } else {
        throw new GwtTestPatchException(e);
      }
    }

  }

  private void applyPatcher(CtClass classToPatch) {
    Patcher patcher = configurationLoader.getPatcherFactory().createPatcher(
        classToPatch);

    if (patcher != null) {
      LOGGER.debug("Apply '" + patcher.getClass().getName() + "'");
      try {
        GwtPatcherUtils.patch(classToPatch, patcher);
      } catch (Exception e) {
        if (GwtTestException.class.isInstance(e)) {
          throw (GwtTestException) e;
        } else {
          throw new GwtTestPatchException("Error while patching class '"
              + classToPatch.getName() + "'", e);
        }
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

    for (String scanPackage : configurationLoader.getScanPackages()) {
      if (classToModify.getName().startsWith(scanPackage)) {
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
    LOGGER.debug("Load class '" + classToModify.getName() + "'");
    applyPatcher(classToModify);
    modifiyClass(classToModify);
    LOGGER.debug("Class '" + classToModify.getName() + "' has been loaded");
  }
}
