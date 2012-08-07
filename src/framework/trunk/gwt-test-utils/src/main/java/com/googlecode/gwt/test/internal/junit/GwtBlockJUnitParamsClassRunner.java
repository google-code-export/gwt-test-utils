package com.googlecode.gwt.test.internal.junit;

import java.util.List;

import junitparams.internal.ParameterisedTestClassRunner;
import junitparams.internal.TestMethod;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * gwt-test-utils {@link Runner} with support for JUnitParams <strong>For internal use
 * only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class GwtBlockJUnitParamsClassRunner extends GwtBlockJUnit4ClassRunner {

   private Description description;
   private ParameterisedTestClassRunner parameterisedTestClassRunner;

   public GwtBlockJUnitParamsClassRunner(Class<?> klass) throws InitializationError {
      super(klass);
   }

   @Override
   public Description getDescription() {
      if (description == null) {
         description = Description.createSuiteDescription(getName(),
                  getTestClass().getAnnotations());
         List<FrameworkMethod> resultMethods = getDelegate().returnListOfMethods();

         for (FrameworkMethod method : resultMethods)
            description.addChild(describeMethod(method));
      }

      return description;
   }

   @Override
   protected List<FrameworkMethod> computeTestMethods() {
      return getDelegate().computeFrameworkMethods();
   }

   @Override
   protected void runChild(FrameworkMethod method, RunNotifier notifier) {
      if (handleIgnored(method, notifier))
         return;

      TestMethod testMethod = getDelegate().testMethodFor(method);
      if (getDelegate().shouldRun(testMethod))
         getDelegate().runParameterisedTest(testMethod, methodBlock(method), notifier);
      else
         super.runChild(method, notifier);
   }

   private Description describeMethod(FrameworkMethod method) {
      Description child = getDelegate().describeParameterisedMethod(method);

      if (child == null)
         child = describeChild(method);

      return child;
   }

   private ParameterisedTestClassRunner getDelegate() {
      if (parameterisedTestClassRunner == null) {
         parameterisedTestClassRunner = new ParameterisedTestClassRunner(getTestClass());
      }

      return parameterisedTestClassRunner;
   }

   private boolean handleIgnored(FrameworkMethod method, RunNotifier notifier) {
      TestMethod testMethod = getDelegate().testMethodFor(method);
      if (testMethod.isIgnored())
         notifier.fireTestIgnored(describeMethod(method));

      return testMethod.isIgnored();
   }

}
