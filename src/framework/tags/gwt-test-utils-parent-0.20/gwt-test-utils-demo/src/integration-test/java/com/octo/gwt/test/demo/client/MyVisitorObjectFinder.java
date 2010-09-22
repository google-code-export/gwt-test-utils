package com.octo.gwt.test.demo.client;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.integ.tools.VisitorObjectFinder;

public class MyVisitorObjectFinder extends VisitorObjectFinder {

	public MyVisitorObjectFinder() {
		super(new WidgetVisitor() {

			public void visitHasText(HasText hasText, WidgetRepository repository) {
				if (hasText.getText() != null && hasText.getText().length() > 0) {
					repository.addAlias(hasText.getText(), hasText);
				}
			}

			public void visitHasHTML(HasHTML hasHTML, WidgetRepository repository) {
				if (hasHTML.getHTML() != null && hasHTML.getHTML().length() > 0) {
					repository.addAlias(hasHTML.getHTML(), hasHTML);
				}
			}

			public void visitHasName(HasName hasName, WidgetRepository repository) {
				if (hasName.getName() != null && hasName.getName().length() > 0) {
					repository.addAlias(hasName.getName(), hasName);
				}
			}

			public void visitWidget(Widget widget, WidgetRepository repository) {
				if (widget.getElement() == null) {
					return;
				}
				String id = widget.getElement().getId();
				if (id != null && id.length() > 0) {
					repository.addAlias(id, widget);
				}
			}
		});
	}

}
