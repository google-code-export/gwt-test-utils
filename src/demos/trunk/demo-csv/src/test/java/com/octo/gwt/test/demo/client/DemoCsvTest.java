package com.octo.gwt.test.demo.client;

import org.junit.Before;
import org.junit.runner.RunWith;

import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwt.test.GwtCreateHandler;
import com.googlecode.gwt.test.csv.CsvDirectory;
import com.googlecode.gwt.test.csv.CsvMacros;
import com.googlecode.gwt.test.csv.CsvMethod;
import com.googlecode.gwt.test.csv.GwtCsvRunner;
import com.googlecode.gwt.test.csv.GwtCsvTest;
import com.googlecode.gwt.test.finder.GwtFinder;
import com.googlecode.gwt.test.finder.Node;
import com.googlecode.gwt.test.finder.NodeObjectFinder;
import com.googlecode.gwt.test.rpc.RemoteServiceCreateHandler;
import com.octo.gwt.test.demo.server.MyServiceImpl;

@CsvDirectory(value = "functional-tests", extension = ".csv")
@CsvMacros(value = "functional-tests", pattern = "^macro.*\\.csv$")
@RunWith(GwtCsvRunner.class)
public class DemoCsvTest extends GwtCsvTest {

  private final Application application = new Application();

  @Before
  public void before() throws Exception {
    // add a GwtCreateHandler for our RemoteService
    addGwtCreateHandler(createRemoteServiceCreateHandler());

    // register all custom NodeObjectFinder
    registerCustomNodeFinders();
  }

  @Override
  public String getModuleName() {
    return "com.octo.gwt.test.demo.Application";
  }

  @CsvMethod
  public void initApp() {
    application.onModuleLoad();
  }

  private GwtCreateHandler createRemoteServiceCreateHandler() {
    return new RemoteServiceCreateHandler() {

      @Override
      public Object findService(Class<?> remoteServiceClass,
          String remoteServiceRelativePath) {
        if (remoteServiceClass == MyService.class
            && "myService".equals(remoteServiceRelativePath)) {
          return new MyServiceImpl();
        }
        return null;
      }
    };
  }

  private void registerCustomNodeFinders() {

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
