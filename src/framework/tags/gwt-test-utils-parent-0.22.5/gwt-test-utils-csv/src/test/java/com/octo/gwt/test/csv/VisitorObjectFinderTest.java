package com.octo.gwt.test.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.octo.gwt.test.csv.tools.VisitorObjectFinder;
import com.octo.gwt.test.csv.tools.VisitorObjectFinder.WidgetRepository;
import com.octo.gwt.test.csv.tools.WidgetVisitor;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class VisitorObjectFinderTest extends GwtTestWithEasyMock {

  private MyBeautifulApp app;

  @Mock
  private CsvRunner csvRunner;

  private VisitorObjectFinder finder;

  @Before
  public void beforeVisitorObjectFinder() {
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
        repository.addAlias(hasTextWidget.getText(), hasTextWidget);
      }

      public void visitWidget(Widget widget, WidgetRepository repository) {
        repository.addAlias(widget.getElement().getId(), widget);

      }
    };

    finder = new VisitorObjectFinder(myVisitor);
  }

  @Test
  public void find() {
    // Arrange
    Button expectedB1 = GwtReflectionUtils.getPrivateFieldValue(app, "b1");
    Button expectedB2 = GwtReflectionUtils.getPrivateFieldValue(app, "b2");
    MyComposite myComposite = GwtReflectionUtils.getPrivateFieldValue(app,
        "myComposite");
    Label expectedCompositeLabel = GwtReflectionUtils.getPrivateFieldValue(
        myComposite, "compositeLabel");

    replay();

    // Act
    Widget b1 = (Widget) finder.find(csvRunner, "Button1's HTML");
    Widget b2 = (Widget) finder.find(csvRunner, "Button2's HTML");
    Label compositeLabel = (Label) finder.find(csvRunner, "myComposite Label");

    // Assert
    verify();
    assertNotNull(b1);
    assertEquals(expectedB1, b1);
    assertNotNull(b2);
    assertEquals(expectedB2, b2);
    assertNotNull(compositeLabel);
    assertEquals(expectedCompositeLabel, compositeLabel);
  }

  @Override
  public String getModuleName() {
    return "com.octo.gwt.test.csv.GwtCsvTest";
  }

}
