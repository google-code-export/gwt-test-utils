package com.octo.gwt.test.demo.client;

import org.junit.Before;

import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwt.test.csv.CsvDirectory;
import com.googlecode.gwt.test.csv.CsvMacros;
import com.googlecode.gwt.test.csv.CsvMethod;
import com.googlecode.gwt.test.finder.GwtFinder;
import com.googlecode.gwt.test.finder.Node;
import com.googlecode.gwt.test.finder.NodeObjectFinder;
import com.googlecode.gwt.test.guice.GwtGuiceCsvTest;

@CsvDirectory(value = "functional-tests", extension = ".csv")
@CsvMacros(value = "functional-tests", pattern = "^macro.*\\.csv$")
public class DemoCsvGuiceTest extends GwtGuiceCsvTest {

  private final Application application = new Application();

  @Override
  public String getModuleName() {
    return "com.octo.gwt.test.demo.Application";
  }

  @CsvMethod
  public void initApp() {
    application.onModuleLoad();
  }

  @Before
  public void registerCustomNodeFinders() {

    GwtFinder.registerNodeFinder("main", new NodeObjectFinder() {

      public Object find(Node node) {
        return csvRunner.getNodeValue(RootPanel.get("main"), node);
      }

    });

    GwtFinder.registerNodeFinder("myApp", new NodeObjectFinder() {

      public Object find(Node node) {
        return csvRunner.getNodeValue(application, node);
      }

    });

    GwtFinder.registerNodeFinder("simpleComposite", new NodeObjectFinder() {

      public Object find(Node node) {
        return csvRunner.getNodeValue(RootPanel.get("main").getWidget(0), node);
      }

    });

    GwtFinder.registerNodeFinder("simpleComposite2", new NodeObjectFinder() {

      public Object find(Node node) {
        return csvRunner.getNodeValue(RootPanel.get("main").getWidget(1), node);
      }

    });

    GwtFinder.registerNodeFinder("rpcComposite", new NodeObjectFinder() {

      public Object find(Node node) {
        return csvRunner.getNodeValue(RootPanel.get("main").getWidget(2), node);
      }

    });
  }
}
