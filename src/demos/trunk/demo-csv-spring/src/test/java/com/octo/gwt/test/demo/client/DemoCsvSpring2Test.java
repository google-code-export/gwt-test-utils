package com.octo.gwt.test.demo.client;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.csv.CsvDirectory;
import com.octo.gwt.test.csv.CsvMacros;
import com.octo.gwt.test.csv.CsvMethod;
import com.octo.gwt.test.csv.runner.CsvRunner;
import com.octo.gwt.test.csv.runner.Node;
import com.octo.gwt.test.csv.tools.NodeObjectFinder;
import com.octo.gwt.test.spring.GwtSpringCsvTest;
import com.octo.gwt.test.spring.GwtTestContextLoader;

@CsvDirectory(value = "functional-tests", extension = ".csv")
@CsvMacros(value = "functional-tests", pattern = "^macro.*\\.csv$")
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}, loader = GwtTestContextLoader.class)
public class DemoCsvSpring2Test extends GwtSpringCsvTest {

  private final Application application = new Application();

  @Override
  public String getModuleName() {
    return "com.octo.gwt.test.demo.Application";
  }

  @CsvMethod
  public void initApp() {
    application.onModuleLoad();
  }

  @Override
  protected Object findRpcServiceInSpringContext(
      ApplicationContext applicationContext, Class<?> remoteServiceClass,
      String remoteServiceRelativePath) {

    if ("rpc/myService".equals(remoteServiceRelativePath)) {
      return applicationContext.getBean("myService");
    }
    return null;
  }

  @Override
  protected NodeObjectFinder getNodeObjectFinder(String prefix) {
    if ("myApp".equals(prefix)) {
      return new NodeObjectFinder() {

        public Object find(CsvRunner csvRunner, Node node) {
          return csvRunner.getNodeValue(application, node);
        }

      };
    } else if ("simpleComposite".equals(prefix)) {
      return new NodeObjectFinder() {

        public Object find(CsvRunner csvRunner, Node node) {
          return csvRunner.getNodeValue(RootPanel.get("main").getWidget(0),
              node);
        }
      };
    } else if ("simpleComposite2".equals(prefix)) {
      return new NodeObjectFinder() {

        public Object find(CsvRunner csvRunner, Node node) {
          return csvRunner.getNodeValue(RootPanel.get("main").getWidget(1),
              node);
        }
      };
    } else if ("rpcComposite".equals(prefix)) {
      return new NodeObjectFinder() {

        public Object find(CsvRunner csvRunner, Node node) {
          return csvRunner.getNodeValue(RootPanel.get("main").getWidget(2),
              node);
        }
      };
    }

    return super.getNodeObjectFinder(prefix);
  }
}
