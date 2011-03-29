package com.octo.gwt.test.csv.tools;

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
import com.octo.gwt.test.csv.data.MyBeautifulApp;
import com.octo.gwt.test.csv.data.MyComposite;
import com.octo.gwt.test.csv.runner.CsvRunner;
import com.octo.gwt.test.csv.tools.VisitorObjectFinder.WidgetRepository;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class VisitorObjectFinderTest extends GwtTestWithEasyMock {

  private MyBeautifulApp app;
  @Mock
  private CsvRunner csvRunner;

  private VisitorObjectFinder finder;

  @Test
  public void checkFind() {
    // Setup
    Button expectedB1 = GwtReflectionUtils.getPrivateFieldValue(app, "b1");
    Button expectedB2 = GwtReflectionUtils.getPrivateFieldValue(app, "b2");
    MyComposite myComposite = GwtReflectionUtils.getPrivateFieldValue(app,
        "myComposite");
    Label expectedCompositeLabel = GwtReflectionUtils.getPrivateFieldValue(
        myComposite, "compositeLabel");

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

  @Before
  public void setupVisitorObjectFinder() {
    app = new MyBeautifulApp();
    app.onModuleLoad();

    WidgetVisitor myVisitor = new WidgetVisitor() {

      public void visitHasHTML(HasHTML hasHTML, WidgetRepository repository) {
        repository.addAlias(hasHTML.getText(), hasHTML);
        repository.addAlias(hasHTML.getHTML(), hasHTML);
      }

      public void visitHasName(HasName hasName, WidgetRepository repository) {
        repository.addAlias(hasName.getName(), hasName);

      }

      public void visitHasText(HasText hasTextWidget,
          WidgetRepository repository) {
        repository.addAlias(hasTextWidget.getText(), (Widget) hasTextWidget);
      }

      public void visitWidget(Widget widget, WidgetRepository repository) {
        repository.addAlias(widget.getElement().getId(), widget);

      }
    };

    finder = new VisitorObjectFinder(myVisitor);
  }

}
