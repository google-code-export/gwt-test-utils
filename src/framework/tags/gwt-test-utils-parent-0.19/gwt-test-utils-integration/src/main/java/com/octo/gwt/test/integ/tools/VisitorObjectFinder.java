package com.octo.gwt.test.integ.tools;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.integ.csvrunner.CsvRunner;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

public abstract class VisitorObjectFinder implements ObjectFinder {

	private Map<Object, WidgetRepository> repositories = new HashMap<Object, WidgetRepository>();
	private WidgetVisitor visitor;

	public VisitorObjectFinder(WidgetVisitor visitor) {
		this.visitor = visitor;
	}

	public boolean accept(String... params) {
		return params.length == 1;
	}

	public Object find(CsvRunner csvRunner, String... params) {
		Object displayedObject = getDisplayedObject(csvRunner);
		WidgetRepository repository = repositories.get(displayedObject);
		if (repository == null) {
			repository = new WidgetRepository();
			inspectChilds(displayedObject, repository, new HashSet<Object>());
			repositories.put(displayedObject, repository);
		}

		return repository.getAlias(params[0]);

	}

	private void inspectChilds(Object inspected, WidgetRepository repository, Set<Object> alreadyInspectedObjects) {
		if (alreadyInspectedObjects.contains(inspected)) {
			return;
		} else {
			alreadyInspectedObjects.add(inspected);
		}

		for (Field field : GwtTestReflectionUtils.getFields(inspected.getClass())) {
			if (field.getName().startsWith("$") || !Widget.class.isAssignableFrom(field.getType())
					|| inspected.getClass().getName().startsWith("com.google.gwt")) {
				continue;
			}

			Object fieldInstance = null;

			try {
				fieldInstance = field.get(inspected);
			} catch (Exception e) {
				throw new RuntimeException("Error while getting '" + inspected.getClass().getSimpleName() + "." + field.getName()
						+ " field value on the current visited object", e);
			}

			if (fieldInstance == null) {
				continue;
			}

			if (Composite.class.isInstance(fieldInstance)) {
				inspectChilds(fieldInstance, repository, alreadyInspectedObjects);
			}

			if (HasHTML.class.isInstance(fieldInstance)) {
				HasHTML hasHTMLWidget = (HasHTML) fieldInstance;
				visitor.visitHasHTML(hasHTMLWidget, field.getName(), inspected, repository);
			}

			if (HasText.class.isInstance(fieldInstance)) {
				HasText hasTextWidget = (HasText) fieldInstance;
				visitor.visitHasText(hasTextWidget, field.getName(), inspected, repository);
			}

			if (HasName.class.isInstance(fieldInstance)) {
				HasName hasNameWidget = (HasName) fieldInstance;
				visitor.visitHasName(hasNameWidget, field.getName(), inspected, repository);
			}

			if (Widget.class.isInstance(fieldInstance)) {
				Widget widget = (Widget) fieldInstance;
				visitor.visitWidget(widget, field.getName(), inspected, repository);
			}
		}
	}

	public abstract Object getDisplayedObject(CsvRunner csvRunner);

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
	}

	public static interface WidgetVisitor {

		void visitHasHTML(HasHTML hasHTML, String name, Object parent, WidgetRepository repository);

		void visitHasText(HasText hasText, String name, Object parent, WidgetRepository repository);

		void visitHasName(HasName hasName, String name, Object parent, WidgetRepository repository);

		void visitWidget(Widget widget, String name, Object parent, WidgetRepository repository);
	}

}
