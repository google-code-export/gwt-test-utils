package com.octo.gwt.test;

import java.lang.reflect.Constructor;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.RunNotifier;

public abstract class AbstractGwtTestRunner extends Runner implements Filterable, Sortable {

	private Runner runner;

	public AbstractGwtTestRunner(Class<?> clazz) throws Exception {
		Class<?> runnerClass = GwtTestClassLoader.get().loadClass(getClassRunnerClassName());

		if (!Runner.class.isAssignableFrom(runnerClass)) {
			throw new RuntimeException("Cannot create a valid JUnit " + Runner.class.getSimpleName() + " : class '" + getClassRunnerClassName()
					+ "' does not extend '" + Runner.class.getName() + "'");
		}

		Constructor<?> cons = runnerClass.getConstructor(Class.class);

		runner = (Runner) cons.newInstance(GwtTestClassLoader.get().loadClass(clazz.getCanonicalName()));
	}

	protected abstract String getClassRunnerClassName();

	@Override
	public Description getDescription() {
		return runner.getDescription();
	}

	@Override
	public void run(RunNotifier notifier) {
		runner.run(notifier);
	}

	@Override
	public int testCount() {
		return runner.testCount();
	}

	public void sort(Sorter sorter) {
		if (Sortable.class.isInstance(runner)) {
			((Sortable) runner).sort(sorter);
		}
	}

	public void filter(Filter filter) throws NoTestsRemainException {
		if (Filterable.class.isInstance(runner)) {
			((Filterable) runner).filter(filter);
		}
	}

}
