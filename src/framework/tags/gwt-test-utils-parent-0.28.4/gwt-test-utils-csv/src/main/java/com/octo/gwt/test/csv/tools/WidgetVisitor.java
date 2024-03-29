package com.octo.gwt.test.csv.tools;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.csv.tools.VisitorObjectFinder.WidgetRepository;

public interface WidgetVisitor {

  void visitHasHTML(HasHTML hasHTML, WidgetRepository repository);

  void visitHasName(HasName hasName, WidgetRepository repository);

  void visitHasText(HasText hasText, WidgetRepository repository);

  void visitWidget(Widget widget, WidgetRepository repository);
}
