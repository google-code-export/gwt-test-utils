package com.octo.gwt.test.csv;

import org.junit.Before;
import org.junit.runner.RunWith;

import com.octo.gwt.test.csv.data.MyBeautifulApp;
import com.octo.gwt.test.csv.data.MyRemoteService;
import com.octo.gwt.test.csv.data.MyService;
import com.octo.gwt.test.csv.data.MyStringStore;
import com.octo.gwt.test.csv.runner.CsvRunner;
import com.octo.gwt.test.csv.runner.Node;
import com.octo.gwt.test.csv.tools.NodeObjectFinder;
import com.octo.gwt.test.integration.RemoteServiceCreateHandler;

@RunWith(GwtCsvRunner.class)
public abstract class MyGwtShell extends GwtCsvTest {

  private MyBeautifulApp app;

  @CsvMethod
  public void append(String s) {
    MyStringStore.appender += s;
  }

  @Override
  public String getModuleName() {
    return "com.octo.gwt.test.csv.GwtCsvTest";
  }

  @CsvMethod
  public void initApp() {
    app = new MyBeautifulApp();
    app.onModuleLoad();
  }

  @Before
  public void setUp() throws Exception {
    RemoteServiceCreateHandler remoteServiceCreateHandler = new RemoteServiceCreateHandler() {

      @Override
      public Object findService(Class<?> remoteServiceClass,
          String remoteServiceRelativePath) {
        if (remoteServiceClass == MyRemoteService.class
            && "myService".equals(remoteServiceRelativePath)) {
          return new MyService();
        }
        return null;
      }

    };

    addGwtCreateHandler(remoteServiceCreateHandler);

    MyStringStore.appender = "";
  }

  @Override
  protected String getHostPagePath(String moduleFullQualifiedName) {
    return null;
  }

  @Override
  protected NodeObjectFinder getNodeObjectFinder(String prefix) {
    if ("app".equals(prefix)) {
      return new NodeObjectFinder() {

        public Object find(CsvRunner csvRunner, Node node) {
          return csvRunner.getNodeValue(app, node);
        }
      };
    } else if ("appender".equals(prefix)) {
      return new NodeObjectFinder() {

        public Object find(CsvRunner csvRunner, Node node) {
          return MyStringStore.appender;
        }
      };

    }
    return super.getNodeObjectFinder(prefix);
  }

}
