package com.octo.gwt.test.csv.tools.integ;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.GwtTestWithEasyMock;
import com.octo.gwt.test.Mock;
import com.octo.gwt.test.csv.runner.CsvRunner;
import com.octo.gwt.test.csv.tools.VisitorObjectFinder;
import com.octo.gwt.test.csv.tools.VisitorObjectFinder.WidgetRepository;
import com.octo.gwt.test.csv.tools.WidgetVisitor;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class VisitorObjectFinderTest extends GwtTestWithEasyMock {

	private MyBeautifulApp app;
	private VisitorObjectFinder finder;

	@Mock
	private CsvRunner csvRunner;

	@Before
	public void setupVisitorObjectFinder() {
		app = new MyBeautifulApp();
		app.onModuleLoad();

		WidgetVisitor myVisitor = new WidgetVisitor() {

			public void visitHasText(HasText hasTextWidget, WidgetRepository repository) {
				repository.addAlias(hasTextWidget.getText(), (Widget) hasTextWidget);
			}

			public void visitHasHTML(HasHTML hasHTML, WidgetRepository repository) {
				repository.addAlias(hasHTML.getText(), hasHTML);
				repository.addAlias(hasHTML.getHTML(), hasHTML);
			}

			public void visitHasName(HasName hasName, WidgetRepository repository) {
				repository.addAlias(hasName.getName(), hasName);

			}

			public void visitWidget(Widget widget, WidgetRepository repository) {
				repository.addAlias(widget.getElement().getId(), widget);

			}
		};

		finder = new VisitorObjectFinder(myVisitor);
	}

	@Test
	public void checkFind() {
		// Setup
		Button expectedB1 = GwtReflectionUtils.getPrivateFieldValue(app, "b1");
		Button expectedB2 = GwtReflectionUtils.getPrivateFieldValue(app, "b2");
		MyComposite myComposite = GwtReflectionUtils.getPrivateFieldValue(app, "myComposite");
		Label expectedCompositeLabel = GwtReflectionUtils.getPrivateFieldValue(myComposite, "compositeLabel");

		replay();

		// Test
		Widget b1 = (Widget) finder.find(csvRunner, "Button1's HTML");
		Widget b2 = (Widget) finder.find(csvRunner, "Button2's HTML");
		Label compositeLabel = (Label) finder.find(csvRunner, "myComposite Label");

		// Assert
		verify();
		Assert.assertNotNull(b1);
		Assert.assertEquals(expectedB1, b1);
		Assert.assertNotNull(b2);
		Assert.assertEquals(expectedB2, b2);
		Assert.assertNotNull(compositeLabel);
		Assert.assertEquals(expectedCompositeLabel, compositeLabel);
	}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return null;
	}

}
