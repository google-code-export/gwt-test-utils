package com.octo.gwt.test.integ.tools;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHTML;
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
			inspectChilds(displayedObject, repository);
			repositories.put(displayedObject, repository);
		}

		return repository.getAlias(params[0]);

	}

	private void inspectChilds(Object displayedObject, WidgetRepository repository) {
		for (Field field : GwtTestReflectionUtils.getFields(displayedObject.getClass())) {
			if (field.getName().startsWith("$") || !Widget.class.isAssignableFrom(field.getType())
					|| displayedObject.getClass().getName().startsWith("com.google.gwt.user.client")) {
				continue;
			}

			Object fieldInstance = null;

			try {
				fieldInstance = field.get(displayedObject);
			} catch (Exception e) {
				throw new RuntimeException("Error while getting '" + displayedObject.getClass().getSimpleName() + "." + field.getName()
						+ " field value on the current visited object", e);
			}

			if (fieldInstance == null) {
				continue;
			}

			if (Composite.class.isInstance(fieldInstance)) {
				inspectChilds(fieldInstance, repository);
			} else if (HasHTML.class.isInstance(fieldInstance)) {
				HasHTML hasHTMLWidget = (HasHTML) fieldInstance;
				visitor.visitHasHTML(hasHTMLWidget, field.getName(), displayedObject, repository);
			} else if (HasText.class.isInstance(fieldInstance)) {
				HasText hasTextWidget = (HasText) fieldInstance;
				visitor.visitHasText(hasTextWidget, field.getName(), displayedObject, repository);
			}
		}
	}

	public abstract Object getDisplayedObject(CsvRunner csvRunner);

	public static class WidgetRepository {

		private Map<String, Widget> map = new HashMap<String, Widget>();

		public Widget addAlias(String alias, Widget widget) {
			return map.put(alias, widget);
		}

		public Widget getAlias(String alias) {
			return map.get(alias);
		}

		public Widget removeAlias(String alias) {
			return map.remove(alias);
		}
	}

	public static interface WidgetVisitor {

		void visitHasHTML(HasHTML hasHTMLWidget, String name, Object parent, WidgetRepository repository);

		void visitHasText(HasText hasTextWidget, String name, Object parent, WidgetRepository repository);
	}

}
