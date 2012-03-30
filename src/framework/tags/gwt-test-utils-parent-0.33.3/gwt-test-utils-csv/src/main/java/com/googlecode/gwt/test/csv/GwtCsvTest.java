package com.googlecode.gwt.test.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.test.FinallyCommandTrigger;
import com.googlecode.gwt.test.GwtTest;
import com.googlecode.gwt.test.csv.internal.DirectoryTestReader;
import com.googlecode.gwt.test.csv.runner.CsvRunner;
import com.googlecode.gwt.test.csv.tools.DefaultWidgetVisitor;
import com.googlecode.gwt.test.csv.tools.WidgetVisitor;
import com.googlecode.gwt.test.finder.GwtFinder;
import com.googlecode.gwt.test.finder.Node;
import com.googlecode.gwt.test.finder.NodeObjectFinder;
import com.googlecode.gwt.test.internal.GwtConfig;
import com.googlecode.gwt.test.internal.utils.ArrayUtils;
import com.googlecode.gwt.test.internal.utils.GwtStringUtils;
import com.googlecode.gwt.test.utils.WidgetUtils;
import com.googlecode.gwt.test.utils.events.Browser;
import com.googlecode.gwt.test.utils.events.Browser.BrowserErrorHandler;

@RunWith(GwtCsvRunner.class)
public abstract class GwtCsvTest extends GwtTest {

  private static class MacroReader {

    private final static Logger logger = LoggerFactory.getLogger(MacroReader.class);

    private final HashMap<String, List<List<String>>> macroList;

    public MacroReader() {
      macroList = new HashMap<String, List<List<String>>>();
    }

    public List<List<String>> getMacro(String macroName) {
      return macroList.get(macroName);
    }

    public void read(List<List<String>> sheet) {
      logger.info("Start reading macro");
      String currentMacroName = null;
      List<List<String>> currentMacroContent = null;
      for (List<String> row : sheet) {
        if (currentMacroName != null) {
          if (row.size() > 0 && "endmacro".equals(row.get(0))) {
            logger.info("End macro, length : " + currentMacroContent.size());
            macroList.put(currentMacroName, currentMacroContent);
            currentMacroName = null;
          } else {
            currentMacroContent.add(row);
          }
        } else {
          if (row.size() > 0 && "macro".equals(row.get(0))) {
            currentMacroName = row.size() > 1 ? row.get(1) : null;
            Assert.assertNotNull("You have to specified a macro name",
                currentMacroName);
            logger.info("Starting reading " + currentMacroName);
            currentMacroContent = new ArrayList<List<String>>();
          }
        }
      }
    }

  }

  // MODE INTERACTIF
  private static final Class<?>[] baseList = {
      String.class, Integer.class, int.class, Class.class};

  protected CsvRunner csvRunner;

  private MacroReader macroReader;

  private DirectoryTestReader reader;

  private final NodeObjectFinder rootObjectFinder = new NodeObjectFinder() {

    public Object find(Node node) {
      return csvRunner.getNodeValue(RootPanel.get(), node);
    }
  };

  public GwtCsvTest() {
    setCanDispatchDomEventOnDetachedWidget(false);
  }

  @CsvMethod
  public void assertBiggerThan(String value, String... params) {
    Integer i = getObject(Integer.class, false, params);
    if (i != null) {
      int currentValue = i.intValue();
      int intValue = Integer.parseInt(value);
      Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix()
          + "Current value <" + currentValue + "> is not bigger than <" + value
          + ">", currentValue > intValue);
    } else {
      Long l = getObject(Long.class, params);
      long currentValue = l.longValue();
      long longValue = Long.parseLong(value);
      Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix()
          + "Current value <" + currentValue + "> is not bigger than <" + value
          + ">", currentValue > longValue);
    }
  }

  /**
   * 
   * @param containedValue
   * @param params
   */
  @CsvMethod
  public void assertContains(String containedValue, String... params) {
    containedValue = GwtStringUtils.resolveBackSlash(containedValue);
    String actual = getString(params);
    Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix()
        + " not containing string '" + containedValue + "'",
        actual.contains(containedValue));
  }

  @CsvMethod
  public void assertEmpty(String... params) {
    assertExact("", params);
  }

  /**
   * 
   * @param value
   * @param objectLocalization
   */
  @CsvMethod
  public void assertExact(String value, String... params) {
    value = GwtStringUtils.resolveBackSlash(value);

    String actualValue = getString(params);

    if (value == null) {
      Assert.assertNull(csvRunner.getAssertionErrorMessagePrefix()
          + "Null was expected, but '" + actualValue + "' has been find",
          actualValue);
    } else {
      Assert.assertEquals(csvRunner.getAssertionErrorMessagePrefix()
          + "Wrong string", value, actualValue);
    }

  }

  @CsvMethod
  public void assertFalse(String... params) {
    Boolean b = getObject(Boolean.class, false, params);
    if (b == null) {
      Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + "null Boolean");
    } else {
      Assert.assertFalse(csvRunner.getAssertionErrorMessagePrefix()
          + "FALSE expected", b.booleanValue());
    }
  }

  @CsvMethod
  public void assertInstanceOf(String className, String objectLocalisation) {
    try {
      Class<?> clazz = Class.forName(className);
      Object o = getObject(Object.class, objectLocalisation);
      Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix()
          + "Target object is not an instance of [" + className
          + "] but an instance of [" + o.getClass().getCanonicalName() + "]",
          clazz.isAssignableFrom(o.getClass()));
    } catch (ClassNotFoundException e) {
      Assert.fail("Cannot assert instance of [" + className
          + "] because the class cannot be found");
    }
  }

  @CsvMethod
  public void assertListBoxDataEquals(String commaSeparatedContent,
      String... params) {
    ListBox listBox = getObject(ListBox.class, params);
    String[] content = commaSeparatedContent.split("\\s*,\\s*");
    if (!WidgetUtils.assertListBoxDataMatch(listBox, content)) {
      String lbContent = WidgetUtils.getListBoxContentToString(listBox);
      Assert.fail(csvRunner.getAssertionErrorMessagePrefix()
          + "Content is not equal to listBox content : " + lbContent);
    }
  }

  @CsvMethod
  public void assertListBoxSelectedValueIs(String value, String... params) {
    ListBox listBox = getObject(ListBox.class, params);
    Assert.assertEquals(csvRunner.getAssertionErrorMessagePrefix()
        + "Wrong ListBox selected value", value,
        listBox.getItemText(listBox.getSelectedIndex()));
  }

  @CsvMethod
  public void assertNotExist(String... params) {
    Object o = getObject(Object.class, false, params);
    Assert.assertNull(csvRunner.getAssertionErrorMessagePrefix()
        + "Object exist", o);
  }

  /**
   * 
   * @param value
   * @param objectLocalization
   */
  @CsvMethod
  public void assertNumberExact(String value, String... params) {
    Integer i = getObject(Integer.class, false, params);
    if (i != null) {
      Assert.assertEquals(csvRunner.getAssertionErrorMessagePrefix()
          + "Wrong number", Integer.parseInt(value), i.intValue());
    } else {
      Long l = getObject(Long.class, params);
      Assert.assertEquals(csvRunner.getAssertionErrorMessagePrefix()
          + "Wrong number", Long.parseLong(value), l.intValue());
    }
  }

  @CsvMethod
  public void assertSmallerThan(String value, String... params) {
    Integer i = getObject(Integer.class, false, params);
    if (i != null) {
      int currentValue = i.intValue();
      int intValue = Integer.parseInt(value);
      Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix()
          + "Current value <" + currentValue + "> is not smaller than <"
          + value + ">", currentValue < intValue);
    } else {
      Long l = getObject(Long.class, params);
      long currentValue = l.longValue();
      long longValue = Long.parseLong(value);
      Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix()
          + "Current value <" + currentValue + "> is not smaller than <"
          + value + ">", currentValue < longValue);
    }
  }

  @CsvMethod
  public void assertTrue(String... params) {
    Boolean b = getObject(Boolean.class, false, params);
    if (b == null) {
      Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + "null Boolean");
    } else {
      Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix()
          + "TRUE expected", b.booleanValue());
    }
  }

  @CsvMethod
  public void blur(String... params) {
    Widget target = getObject(Widget.class, params);
    Browser.blur(target);
  }

  @CsvMethod
  public void callMethod(String... params) {
    getObject(Object.class, false, params);
  }

  @CsvMethod
  public void change(String... params) {
    Widget target = getObject(Widget.class, params);
    Browser.change(target);
  }

  @CsvMethod
  public void click(String... params) {
    Widget target = getObject(Widget.class, params);
    Browser.click(target);
  }

  @CsvMethod
  public void clickComplexPanel(String index, String... params) {
    ComplexPanel panel = getObject(ComplexPanel.class, params);
    Browser.click(panel, Integer.parseInt(index));
  }

  @CsvMethod
  public void clickGridCell(String rowIndex, String columnIndex,
      String... params) {
    Grid grid = getObject(Grid.class, params);
    int row = Integer.parseInt(rowIndex);
    int column = Integer.parseInt(columnIndex);
    Browser.click(grid, row, column);
  }

  @CsvMethod
  public void clickMenuItem(String index, String... params) {
    MenuBar menuBar = getObject(MenuBar.class, params);
    Browser.click(menuBar, Integer.parseInt(index));
  }

  @CsvMethod
  public void emptyTextBox(String... params) {
    TextBox textBox = getObject(TextBox.class, params);
    Browser.emptyText(textBox);
  }

  @CsvMethod
  public void fillAndSelectInSuggestBoxByIndex(String content, String index,
      String... params) {
    SuggestBox suggestBox = getObject(SuggestBox.class, params);

    Browser.fillText(suggestBox, content);
    Browser.click(suggestBox, Integer.parseInt(index));
  }

  @CsvMethod
  public void fillAndSelectInSuggestBoxByText(String content, String selected,
      String... params) {
    SuggestBox suggestBox = getObject(SuggestBox.class, params);
    Browser.fillText(suggestBox, content);

    List<MenuItem> menuItems = WidgetUtils.getMenuItems(suggestBox);
    int i = 0;
    int index = -1;

    while (i < menuItems.size() && index == -1) {
      MenuItem item = menuItems.get(i);
      if (selected.equals(item.getHTML()) || selected.equals(item.getText())) {
        index = i;
        Browser.click(suggestBox, item);
      }
      i++;
    }

    Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix()
        + "Cannot find '" + selected + "' in suggested choices", index > -1);
  }

  @CsvMethod
  public void fillInvisibleTextBox(String value, String... params) {
    TextBox textBox = getObject(TextBox.class, params);
    Browser.fillText(textBox, false, value);
  }

  @CsvMethod
  public void fillTextBox(String value, String... params) {
    TextBox textBox = getObject(TextBox.class, params);
    Browser.fillText(textBox, value);
  }

  @CsvMethod
  public void focus(String... params) {
    Widget target = getObject(Widget.class, params);
    Browser.focus(target);
  }

  @Override
  public BrowserErrorHandler getBrowserErrorHandler() {
    return new BrowserErrorHandler() {

      public void onError(String errorMessage) {
        // remove pending tasks, no need to execute
        FinallyCommandTrigger.clearPendingCommands();
        Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + errorMessage);
      }
    };
  }

  @CsvMethod
  public void hasStyle(String style, String... params) {
    UIObject object = getObject(UIObject.class, params);
    Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix()
        + "Style not found : " + style, object.getStyleName().contains(style));
  }

  @CsvMethod
  public void interactive() {
    try {
      PrintStream os = System.out;
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      os.println("Welcome in interactive mode !");
      while (true) {
        os.print("> ");
        os.flush();
        String line = br.readLine();
        if ("quit".equals(line) || "q".equals(line)) {
          os.println("Bye bye !");
          return;
        }
        int index = line.indexOf(" ");
        if (index == -1) {
          os.println("Parse error");
        } else {
          String cmd = line.substring(0, index);
          String param = line.substring(index + 1);
          os.println("Command <" + cmd + ">, params : " + param);
          if ("go".equals(cmd) || "getObject".equals(cmd)) {
            try {
              getObject(param, os);
            } catch (Throwable e) {
              os.println("Not found : " + e.getMessage());
            }
          } else if ("lc".equals(cmd) || "listContent".equals(cmd)) {
            try {
              Object o = getObject(param, os);
              printGetter(o, o.getClass(), os);
            } catch (Throwable e) {
              os.println("Not found : " + e.getMessage());
            }
          } else if ("click".equals(cmd)) {
            try {
              click(param);
              os.println("Click successful");
            } catch (Throwable e) {
              os.println("Unable to click : " + e.getMessage());
            }
          } else {
            os.println("Unknown command : " + cmd);
          }
        }
      }
    } catch (IOException e) {
      Assert.fail("IO error " + e.toString());
    }
  }

  @CsvMethod
  public void isChecked(String... params) {
    CheckBox checkBox = getObject(CheckBox.class, params);
    Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix()
        + "Checkbox not checked", checkBox.getValue());
  }

  @CsvMethod
  public void isEnabled(String... params) {
    FocusWidget target = getFocusWidget(params);
    Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "targeted "
        + target.getClass().getSimpleName() + " is not enabled",
        target.isEnabled());
  }

  @CsvMethod
  public void isNotChecked(String... params) {
    CheckBox checkBox = getObject(CheckBox.class, params);
    Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix()
        + "Checkbox checked", !checkBox.getValue());
  }

  @CsvMethod
  public void isNotEnabled(String... params) {
    FocusWidget target = getFocusWidget(params);
    Assert.assertFalse(csvRunner.getAssertionErrorMessagePrefix() + "targeted "
        + target.getClass().getSimpleName() + " is enabled", target.isEnabled());
  }

  @CsvMethod
  public void isNotVisible(String... params) {
    UIObject object = getObject(UIObject.class, false, params);

    if (object != null) {
      boolean visible = false;
      visible = WidgetUtils.isWidgetVisible(object);
      if (visible && object instanceof Widget) {
        Widget w = (Widget) object;
        visible = w.isAttached();
      }
      Assert.assertFalse(csvRunner.getAssertionErrorMessagePrefix()
          + "targeted " + object.getClass().getSimpleName() + " is visible",
          visible);
    }
  }

  @CsvMethod
  public void isVisible(String... params) {
    UIObject object = getObject(UIObject.class, params);

    Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "targeted "
        + object.getClass().getSimpleName() + " is not visible",
        WidgetUtils.isWidgetVisible(object));

    if (!GwtConfig.get().canDispatchDomEventOnDetachedWidget()
        && Widget.class.isInstance(object)) {
      Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix()
          + "targeted " + object.getClass().getSimpleName()
          + " is not attached to the DOM", ((Widget) object).isAttached());
    }
  }

  public void launchTest(String testName) throws Exception {
    csvRunner.runSheet(reader.getTest(testName), this);
  }

  @CsvMethod
  public void mouseDown(String... params) {
    Widget target = getObject(Widget.class, params);
    Browser.mouseDown(target);
  }

  @CsvMethod
  public void mouseMove(String... params) {
    Widget target = getObject(Widget.class, params);
    Browser.mouseMove(target);
  }

  @CsvMethod
  public void mouseOut(String... params) {
    Widget target = getObject(Widget.class, params);
    Browser.mouseOut(target);
  }

  @CsvMethod
  public void mouseOver(String... params) {
    Widget target = getObject(Widget.class, params);
    Browser.mouseOver(target);
  }

  @CsvMethod
  public void mouseUp(String... params) {
    Widget target = getObject(Widget.class, params);
    Browser.mouseUp(target);
  }

  @CsvMethod
  public void mouseWheel(String... params) {
    Widget target = getObject(Widget.class, params);
    Browser.mouseWheel(target);
  }

  @CsvMethod
  public void runmacro(String macroName, String... params) throws Exception {
    List<List<String>> macro = macroReader.getMacro(macroName);
    Assert.assertNotNull(csvRunner.getAssertionErrorMessagePrefix()
        + "CsvMacro '" + macroName + "' has not been found", macro);
    int i = 0;
    for (List<String> line : macro) {
      List<String> l = new ArrayList<String>();
      for (String s : line) {
        String replaced = s;
        for (int z = 0; z < params.length; z++) {
          String param = params[z];
          if (param == null)
            param = "*null*";
          else if ("".equals(param))
            param = "*empty*";

          replaced = replaced.replaceAll("\\{" + z + "\\}", param);
        }
        l.add(replaced);
      }
      csvRunner.setExtendedLineInfo(macroName + " line " + (i + 1));
      csvRunner.executeRow(l, this);
      i++;
    }
    csvRunner.setExtendedLineInfo(null);
  }

  @CsvMethod
  public void selectInListBox(String value, String... params) {
    ListBox listBox = getObject(ListBox.class, params);
    selectInListBox(listBox, value, params);
  }

  @CsvMethod
  public void selectInListBoxByIndex(String index, String... params) {
    selectInListBox(index, params);
  }

  @CsvMethod
  public void selectInListBoxByText(String regex, String... params) {
    selectInListBox(regex, params);
  }

  public void setReader(DirectoryTestReader reader) {
    this.reader = reader;
    csvRunner = new CsvRunner();
    macroReader = new MacroReader();
    for (String name : reader.getMacroFileList()) {
      macroReader.read(reader.getMacroFile(name));
    }

    GwtFinder.registerNodeFinder("root", rootObjectFinder);
  }

  protected FocusWidget getFocusWidget(String... params) {
    return getObject(FocusWidget.class, params);
  }

  protected <T> T getObject(Class<T> clazz, boolean failOnError,
      String... params) {
    return csvRunner.getObject(clazz, failOnError, params);
  }

  protected <T> T getObject(Class<T> clazz, String... params) {
    return csvRunner.getObject(clazz, params);
  }

  protected String getString(Object o) {
    String actualValue;
    if (o == null) {
      return null;
    } else if (HasHTML.class.isInstance(o)) {
      HasHTML hasHTML = (HasHTML) o;
      String html = hasHTML.getHTML();
      actualValue = (html != null && html.length() > 0) ? html
          : hasHTML.getText();
    } else if (HasText.class.isInstance(o)) {
      actualValue = ((HasText) o).getText();
    } else {
      actualValue = "" + o;
    }

    return actualValue;
  }

  protected String getString(String... params) {
    Object o = getObject(Object.class, false, params);
    return getString(o);
  }

  protected WidgetVisitor getWidgetVisitor() {
    return new DefaultWidgetVisitor();
  }

  protected void selectInListBox(ListBox listBox, String regex,
      String... params) {
    int selectedIndex;
    String errorMessage;
    if (regex.matches("^\\d*$")) {
      selectedIndex = Integer.parseInt(regex);
      errorMessage = "Cannot select negative index in ListBox <" + regex
          + "> in ListBox with values : ";
    } else {
      selectedIndex = WidgetUtils.getIndexInListBox(listBox, regex);
      errorMessage = "Regex <" + regex
          + "> has not been matched in ListBox values : ";
    }

    if (selectedIndex > -1) {
      listBox.setSelectedIndex(selectedIndex);
      Browser.click(listBox);
      Browser.change(listBox);
    } else {
      errorMessage += WidgetUtils.getListBoxContentToString(listBox);
      Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + errorMessage);
    }
  }

  private Object getObject(String param, PrintStream os) {
    Object o = getObject(Object.class, param);
    os.println("Object found, class " + o.getClass().getCanonicalName());
    if (ArrayUtils.contains(baseList, o.getClass())) {
      os.println("Value : " + o.toString());
    }
    return o;
  }

  private void printGetter(Object o, Class<?> clazz, PrintStream os) {
    for (Method m : clazz.getDeclaredMethods()) {
      if (m.getName().startsWith("get")) {
        os.print("Getter [" + clazz.getSimpleName() + "] " + m.getName());
        if (ArrayUtils.contains(baseList, m.getReturnType())
            && m.getParameterTypes().length == 0) {
          try {
            Object res = m.invoke(o);
            os.print(", value " + res);
          } catch (Throwable e) {
          }
        }
        os.println();
      }
    }
    for (Field f : clazz.getDeclaredFields()) {
      if (!Modifier.isStatic(f.getModifiers())
          && !Modifier.isFinal(f.getModifiers())) {
        os.print("Field [" + clazz.getSimpleName() + "] ["
            + f.getClass().getSimpleName() + "] " + f.getName());
        if (ArrayUtils.contains(baseList, f.getType())) {
          try {
            Object res = f.get(o);
            os.print(", value " + res);
          } catch (Throwable e) {
          }
        }
        os.println();
      }
    }
    if (clazz.getSuperclass() != null) {
      printGetter(o, clazz.getSuperclass(), os);
    }
  }

}
