package com.octo.gwt.test.csv.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.octo.gwt.test.csv.runner.CsvRunner;
import com.octo.gwt.test.csv.runner.Node;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.internal.AfterTestCallback;
import com.octo.gwt.test.internal.AfterTestCallbackManager;

public class ObjectFinderManager implements AfterTestCallback {

	private static final ObjectFinderManager INSTANCE = new ObjectFinderManager();

	public static ObjectFinderManager get() {
		return INSTANCE;
	}

	private CsvRunner csvRunner;

	private NodeObjectFinderFactory nodeObjectFinderFactory;

	// map which holds NodeObjectFinder indexed by prefix
	private final Map<String, NodeObjectFinder> nodeObjectFinders = new HashMap<String, NodeObjectFinder>();

	private final List<ObjectFinder> objectFinders = new ArrayList<ObjectFinder>();

	private ObjectFinderManager() {
		AfterTestCallbackManager.get().registerCallback(this);
	}

	public void add(ObjectFinder objectFinder) {
		objectFinders.add(objectFinder);
	}

	public void afterTest() {
		clearCache();
		nodeObjectFinderFactory = null;
		csvRunner = null;
	}

	public void clearCache() {
		for (ObjectFinder objectFinder : objectFinders) {
			objectFinder.clear();
		}

		nodeObjectFinders.clear();
	}

	public Object findObject(CsvRunner csvRunner, String... params) {
		int i = 0;
		Object result = null;
		boolean accepted = false;

		while (i < objectFinders.size() && !accepted) {
			ObjectFinder objectFinder = objectFinders.get(i++);
			accepted = objectFinder.accept(params);
			if (accepted) {
				result = objectFinder.find(csvRunner, params);
			}
		}

		return result;
	}

	public void setup(CsvRunner csvRunner,
			NodeObjectFinderFactory nodeObjectFinderFactory,
			WidgetVisitor widgetVisitor) {
		if (this.nodeObjectFinderFactory != null) {
			throw new GwtTestException(
					"Because of the single-threaded nature of the GWT environment, gwt-test-utils tests can not be run in multiple thread at the same time");
		}

		this.csvRunner = csvRunner;
		this.nodeObjectFinderFactory = nodeObjectFinderFactory;

		VisitorObjectFinder visitorObjectFinder = new VisitorObjectFinder(
				widgetVisitor);

		objectFinders.add(visitorObjectFinder);
		ObjectFinder defaultFinder = createDefaultFinder(visitorObjectFinder);
		objectFinders.add(defaultFinder);
	}

	private ObjectFinder createDefaultFinder(
			final VisitorObjectFinder visitorObjectFinder) {
		ObjectFinder finder = new ObjectFinder() {

			public boolean accept(String... params) {
				return params.length == 1 && params[0].matches("^/\\w+/.*$");
			}

			public void clear() {
				// nothing to do
			}

			public Object find(CsvRunner csvRunner, String... params) {
				Node node = Node.parse(params[0]);
				if (node == null) {
					return null;
				}

				String prefix = node.getLabel();

				NodeObjectFinder finder = nodeObjectFinders.get(prefix);

				if (finder == null) {
					finder = createNodeObjectFinder(prefix, visitorObjectFinder);
					nodeObjectFinders.put(prefix, finder);
				}

				return finder.find(csvRunner, node.getNext());
			}

		};

		return finder;
	}

	private NodeObjectFinder createNodeObjectFinder(String prefix,
			VisitorObjectFinder visitorObjectFinder) {
		NodeObjectFinder finder = nodeObjectFinders.get(prefix);

		if (finder == null) {
			finder = nodeObjectFinderFactory.createNodeObjectFinder(prefix);
		}
		if (finder == null) {
			final Object o = visitorObjectFinder.find(csvRunner, prefix);

			finder = new NodeObjectFinder() {

				public Object find(CsvRunner csvRunner, Node node) {
					return csvRunner.getNodeValue(o, node);
				}
			};
		}

		// FIXME : is this still usefull ?
		Assert.assertNotNull(
				csvRunner.getAssertionErrorMessagePrefix()
						+ "Unknown prefix '"
						+ prefix
						+ "', you should override getNodeObjectFinder(..) method to provide a specific "
						+ NodeObjectFinder.class.getSimpleName(), finder);

		return finder;
	}

}
