package com.octo.gwt.test.integ.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.integ.csvrunner.CsvRunner;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

public class VisitorObjectFinder implements ObjectFinder {

	private Map<Object, WidgetRepository> repositories = new HashMap<Object, WidgetRepository>();
	private WidgetVisitor visitor;

	public VisitorObjectFinder(WidgetVisitor visitor) {
		this.visitor = visitor;
	}

	public boolean accept(String... params) {
		return params.length == 1 && !params[0].trim().startsWith("/");
	}

	public Object find(CsvRunner csvRunner, String... params) {
		Object result;
		List<Panel> roots = getRootPanels();

		for (Panel root : roots) {
			WidgetRepository repository = repositories.get(root);

			if (repository == null) {
				repository = new WidgetRepository();
				inspectObject(root, repository, new HashSet<Object>());
				repositories.put(root, repository);
				result = repository.getAlias(params[0]);
			} else {
				result = repository.getAlias(params[0]);
				if (result == null) {
					// try another time since code could have instanciate new widget after the last inspection
					repository.clear();
					inspectObject(root, repository, new HashSet<Object>());
					result = repository.getAlias(params[0]);
				}
			}

			if (result != null) {
				return result;
			}
		}

		return null;

	}

	protected List<Panel> getRootPanels() {
		List<Panel> list = new ArrayList<Panel>();
		list.add(RootPanel.get());

		return list;
	}

	private void inspectObject(Object inspected, WidgetRepository repository, Set<Object> alreadyInspectedObjects) {
		if (inspected == null || alreadyInspectedObjects.contains(inspected)) {
			return;
		} else {
			alreadyInspectedObjects.add(inspected);
		}

		if (UIObject.class.isInstance(inspected) && !((UIObject) inspected).isVisible()) {
			if (Widget.class.isInstance(inspected)) {
				// add the not visible widget but don't inspect its child
				Widget widget = (Widget) inspected;
				visitor.visitWidget(widget, repository);
			}

			return;
		}

		if (HasWidgets.class.isInstance(inspected)) {
			Iterator<Widget> it = ((HasWidgets) inspected).iterator();
			while (it.hasNext()) {
				inspectObject(it.next(), repository, alreadyInspectedObjects);
			}
		} else if (Composite.class.isInstance(inspected)) {
			Widget widget = GwtTestReflectionUtils.callPrivateMethod(inspected, "getWidget");
			inspectObject(widget, repository, alreadyInspectedObjects);
		}

		if (HasHTML.class.isInstance(inspected)) {
			HasHTML hasHTMLWidget = (HasHTML) inspected;
			visitor.visitHasHTML(hasHTMLWidget, repository);
		}

		if (HasText.class.isInstance(inspected)) {
			HasText hasTextWidget = (HasText) inspected;
			visitor.visitHasText(hasTextWidget, repository);
		}

		if (HasName.class.isInstance(inspected)) {
			HasName hasNameWidget = (HasName) inspected;
			visitor.visitHasName(hasNameWidget, repository);
		}

		if (Widget.class.isInstance(inspected)) {
			// add the not visible widget but don't inspect its child
			Widget widget = (Widget) inspected;
			visitor.visitWidget(widget, repository);
		}
	}

	public static class WidgetRepository {

		private Map<String, Object> map = new HashMap<String, Object>();

		public Object addAlias(String alias, Object widget) {
			return map.put(alias, widget);
		}

		public Object getAlias(String alias) {
			return map.get(alias);
		}

		public Object removeAlias(String alias) {
			return map.remove(alias);
		}

		public void clear() {
			map.clear();
		}
	}

}
