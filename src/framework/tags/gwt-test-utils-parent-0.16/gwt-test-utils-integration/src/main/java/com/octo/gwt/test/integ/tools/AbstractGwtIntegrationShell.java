package com.octo.gwt.test.integ.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.GwtLogHandler;
import com.octo.gwt.test.PatchGwtConfig;
import com.octo.gwt.test.PatchGwtReset;
import com.octo.gwt.test.integ.CsvMethod;
import com.octo.gwt.test.integ.csvrunner.CsvRunner;
import com.octo.gwt.test.integ.csvrunner.Node;
import com.octo.gwt.test.utils.ArrayUtils;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;
import com.octo.gwt.test.utils.GwtTestStringUtils;
import com.octo.gwt.test.utils.WidgetUtils;

public abstract class AbstractGwtIntegrationShell {

	protected CsvRunner csvRunner;

	private DirectoryTestReader reader;
	private MacroReader macroReader;

	public String getCurrentTestedModuleFile() {
		//this method can be overrided
		return null;
	}

	protected GwtCreateHandler getGwtCreateHandler() throws Exception {
		//this method can be overrided
		return null;
	}

	protected Locale getLocale() {
		//this method can be overrided
		return null;
	}

	protected GwtLogHandler getLogHandler() {
		//this method can be overrided
		return null;
	}

	@Before
	public void setUpAbstractGwtIntegrationShell() throws Exception {
		PatchGwtConfig.setLocale(getLocale());
		PatchGwtConfig.setGwtCreateHandler(getGwtCreateHandler());
		PatchGwtConfig.setCurrentTestedModuleFile(getCurrentTestedModuleFile());
		PatchGwtConfig.setLogHandler(getLogHandler());
	}

	@After
	public void tearDownAbstractGwtIntegrationShell() throws Exception {
		resetPatchGwt();
	}

	protected void resetPatchGwt() throws Exception {
		// reinit GWT
		PatchGwtReset.reset();
	}

	public void setReader(DirectoryTestReader reader) {
		this.reader = reader;
		this.csvRunner = new CsvRunner();
		macroReader = new MacroReader();
		for (String name : reader.getMacroFileList()) {
			macroReader.read(reader.getMacroFile(name));
		}
	}

	public void launchTest(String testName) throws Exception {
		csvRunner.runSheet(reader.getTest(testName), this);
	}

	protected PrefixProcessor findPrefixProcessor(String prefix) {
		if ("root".equals(prefix)) {
			return new PrefixProcessor() {

				public Object process(CsvRunner csvRunner, Node next, boolean failOnError) {
					return csvRunner.getValue(failOnError, RootPanel.get(), next);
				}

			};
		}
		return null;
	}

	protected final <T> T getObject(Class<T> clazz, String objectLocalization) {
		return getObject(clazz, objectLocalization, true);
	}

	@SuppressWarnings("unchecked")
	protected final <T> T getObject(Class<T> clazz, String objectLocalization, boolean failOnError) {
		Node n = Node.parse(objectLocalization);
		Assert.assertNotNull(csvRunner.getAssertionErrorMessagePrefix() + "Unable to parse " + objectLocalization, n);
		Assert.assertNotNull(csvRunner.getAssertionErrorMessagePrefix() + "Localization must have two /", n.getNext());
		String prefix = n.getLabel();
		Node next = n.getNext();
		PrefixProcessor processor = findPrefixProcessor(prefix);
		Assert.assertNotNull(csvRunner.getAssertionErrorMessagePrefix() + "Unkown prefix : <" + prefix + ">", processor);
		Object o = processor.process(csvRunner, next, failOnError);
		if (clazz.isInstance(o)) {
			return (T) o;
		}
		if (!failOnError) {
			return null;
		}

		if (o == null) {
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + "Targeted object [" + objectLocalization + "] is null");
		}

		Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + "Wrong object type, not a " + clazz.getCanonicalName() + " : "
				+ o.getClass().getCanonicalName());
		return null;
	}

	@CsvMethod
	public void runmacro(String macroName, String... params) throws Exception {
		List<List<String>> macro = macroReader.getMacro(macroName);
		Assert.assertNotNull(csvRunner.getAssertionErrorMessagePrefix() + "Not existing macro " + macroName, macro);
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

	/**
	 * 
	 * @param value
	 * @param objectLocalization
	 */
	@CsvMethod
	public void assertExact(String value, String objectLocalization) {
		value = GwtTestStringUtils.resolveBackSlash(value);
		String s = getObject(String.class, objectLocalization, false);
		Assert.assertEquals(csvRunner.getAssertionErrorMessagePrefix() + "Wrong string", value, s);
	}

	/**
	 * 
	 * @param value
	 * @param objectLocalization
	 */
	@CsvMethod
	public void assertNumberExact(String value, String objectLocalization) {
		Integer i = getObject(Integer.class, objectLocalization, false);
		if (i != null) {
			Assert.assertEquals(csvRunner.getAssertionErrorMessagePrefix() + "Wrong number", Integer.parseInt(value), i.intValue());
		} else {
			Long l = getObject(Long.class, objectLocalization);
			Assert.assertEquals(csvRunner.getAssertionErrorMessagePrefix() + "Wrong number", Long.parseLong(value), l.intValue());
		}
	}

	@CsvMethod
	public void assertTrue(String objectLocalization) {
		Boolean b = getObject(Boolean.class, objectLocalization, false);
		if (b == null) {
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + "null Boolean");
		} else {
			Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "TRUE expected", b.booleanValue());
		}
	}

	@CsvMethod
	public void assertFalse(String objectLocalization) {
		Boolean b = getObject(Boolean.class, objectLocalization, false);
		if (b == null) {
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + "null Boolean");
		} else {
			Assert.assertFalse(csvRunner.getAssertionErrorMessagePrefix() + "FALSE expected", b.booleanValue());
		}
	}

	/**
	 * 
	 * @param value
	 * @param objectLocalization
	 */
	@CsvMethod
	public void assertContains(String value, String objectLocalization) {
		value = GwtTestStringUtils.resolveBackSlash(value);
		String s = getObject(String.class, objectLocalization);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + " not containing string " + value, s.contains(value));
	}

	/**
	 * Launch a JavaScript "onBlur" event on the target object.
	 * 
	 * @param objectLocalization
	 *            The targeted object localisation path.
	 */
	@CsvMethod
	public void blur(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.blur(widget);
	}

	/**
	 * Launch a JavaScript "onChange" event on a object
	 * 
	 * @param objectLocalization
	 *            The targeted object localisation path.
	 */
	@CsvMethod
	public void change(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.change(widget);
	}

	/**
	 * Launch a JavaScript "onClick" event on the target object.
	 * 
	 * @param objectLocalization
	 *            The targeted object localisation path.
	 */
	@CsvMethod
	public void click(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.click(widget, csvRunner.getAssertionErrorMessagePrefix(), objectLocalization);
	}

	@CsvMethod
	public void clickComplexPanel(String index, String objectLocalization) {
		ComplexPanel widget = getObject(ComplexPanel.class, objectLocalization);
		checkWidgetVisible(widget, objectLocalization);
		WidgetUtils.click(widget, Integer.valueOf(index));
	}

	@CsvMethod
	public void clickPanel(String objectLocalization) {
		FocusPanel widget = getObject(FocusPanel.class, objectLocalization);
		checkWidgetVisibleAndEnable(widget, objectLocalization);
		WidgetUtils.click(widget, csvRunner.getAssertionErrorMessagePrefix(), objectLocalization);
	}

	@CsvMethod
	public void clickMenuItem(String index, String objectLocalization) {
		MenuBar menuBar = getObject(MenuBar.class, objectLocalization);
		List<MenuItem> menuItems = GwtTestReflectionUtils.getPrivateFieldValue(menuBar, "items");
		MenuItem itemToClick = menuItems.get(Integer.parseInt(index));
		WidgetUtils.click(menuBar, itemToClick);
	}

	@CsvMethod
	public void clickOnTableRow(String rowIndex, String objectLocalization) {
		Grid grid = getObject(Grid.class, objectLocalization);
		checkWidgetVisible(grid, objectLocalization);
		WidgetUtils.click(grid, Integer.parseInt(rowIndex), 0);
	}

	@CsvMethod
	public void focus(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.focus(widget);
	}

	@CsvMethod
	public void mouseDown(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.mouseDown(widget);
	}

	@CsvMethod
	public void mouseMove(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.mouseMove(widget);
	}

	@CsvMethod
	public void mouseUp(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.mouseUp(widget);
	}

	@CsvMethod
	public void mouseWheel(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.mouseWheel(widget);
	}

	@CsvMethod
	public void mouseOut(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.mouseOut(widget);
	}

	@CsvMethod
	public void mouseOver(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.mouseOver(widget);
	}

	@CsvMethod
	public void hasStyle(String style, String objectLocalization) {
		Widget w = getObject(Widget.class, objectLocalization);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Style not found : " + style, w.getStyleName().contains(style));
	}

	@CsvMethod
	public void assertNotExist(String objectLocalization) {
		Object o = getObject(Object.class, objectLocalization, false);
		Assert.assertNull(csvRunner.getAssertionErrorMessagePrefix() + "Object exist", o);
	}

	@CsvMethod
	public void callMethod(String objectLocalization) {
		getObject(Object.class, objectLocalization, false);
	}

	@CsvMethod
	public void isChecked(String objectLocalization) {
		CheckBox checkBox = getObject(CheckBox.class, objectLocalization);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Checkbox not checked", checkBox.getValue());
	}

	@CsvMethod
	public void isNotChecked(String objectLocalization) {
		CheckBox checkBox = getObject(CheckBox.class, objectLocalization);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Checkbox checked", !checkBox.getValue());
	}

	@CsvMethod
	public void assertInstanceOf(String className, String objectLocalisation) {
		try {
			Class<?> clazz = Class.forName(className);
			Object o = getObject(Object.class, objectLocalisation);
			Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Target object is not an instance of [" + className
					+ "] but an instance of [" + o.getClass().getCanonicalName() + "]", clazz.isAssignableFrom(o.getClass()));
		} catch (ClassNotFoundException e) {
			Assert.fail("Cannot assert instance of [" + className + "] because the class cannot be found");
		}
	}

	@CsvMethod
	public void assertBiggerThan(String value, String objectLocalization) {
		Integer i = getObject(Integer.class, objectLocalization, false);
		if (i != null) {
			int currentValue = i.intValue();
			int intValue = Integer.parseInt(value);
			Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Current value <" + currentValue + "> is not bigger than <" + value + ">",
					currentValue > intValue);
		} else {
			Long l = getObject(Long.class, objectLocalization);
			long currentValue = l.longValue();
			long longValue = Long.parseLong(value);
			Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Current value <" + currentValue + "> is not bigger than <" + value + ">",
					currentValue > longValue);
		}
	}

	@CsvMethod
	public void assertSmallerThan(String value, String objectLocalization) {
		Integer i = getObject(Integer.class, objectLocalization, false);
		if (i != null) {
			int currentValue = i.intValue();
			int intValue = Integer.parseInt(value);
			Assert.assertTrue(
					csvRunner.getAssertionErrorMessagePrefix() + "Current value <" + currentValue + "> is not smaller than <" + value + ">",
					currentValue < intValue);
		} else {
			Long l = getObject(Long.class, objectLocalization);
			long currentValue = l.longValue();
			long longValue = Long.parseLong(value);
			Assert.assertTrue(
					csvRunner.getAssertionErrorMessagePrefix() + "Current value <" + currentValue + "> is not smaller than <" + value + ">",
					currentValue < longValue);
		}
	}

	@CsvMethod
	public void assertListBoxSelectedValueIs(String value, String objectLocalization) {
		ListBox listBox = getObject(ListBox.class, objectLocalization);
		Assert.assertEquals(csvRunner.getAssertionErrorMessagePrefix() + "Wrong ListBox selected value", value, listBox.getItemText(listBox
				.getSelectedIndex()));
	}

	// TODO: delete "ONCHANGE" events
	@CsvMethod
	public void fillTextBox(String value, String objectLocalization) {
		TextBox textBox = getObject(TextBox.class, objectLocalization);
		checkWidgetVisibleAndEnable(textBox, objectLocalization);
		textBox.setText(value);
		WidgetUtils.keyUp(textBox, KeyCodes.KEY_ENTER);
		WidgetUtils.change(textBox);
	}

	@CsvMethod
	public void fillInvisibleTextBox(String value, String objectLocalization) {
		TextBox textBox = getObject(TextBox.class, objectLocalization);
		textBox.setText(value);
		WidgetUtils.keyUp(textBox, KeyCodes.KEY_ENTER);
		WidgetUtils.change(textBox);
	}

	@CsvMethod
	public void selectInListBox(String value, String objectLocalization) {
		ListBox listBox = getObject(ListBox.class, objectLocalization);
		selectInListBox(listBox, value, objectLocalization);
	}

	@CsvMethod
	public void selectInListBoxByIndex(String index, String objectLocalization) {
		selectInListBox(index, objectLocalization);
	}

	@CsvMethod
	public void selectInListBoxByText(String regex, String objectLocalization) {
		selectInListBox(regex, objectLocalization);
	}

	protected void selectInListBox(ListBox listBox, String regex, String objectLocalization) {
		checkWidgetVisibleAndEnable(listBox, objectLocalization);

		int selectedIndex;
		String errorMessage;
		if (regex.matches("^\\d*$")) {
			selectedIndex = Integer.parseInt(regex);
			errorMessage = "Cannot select negative index in ListBox <" + regex + ">";
		} else {
			selectedIndex = WidgetUtils.getIndexInListBox(listBox, regex);
			errorMessage = "Regex <" + regex + "> has not been matched in ListBox values";
		}

		if (selectedIndex > -1) {
			listBox.setSelectedIndex(selectedIndex);
			WidgetUtils.click(listBox);
			WidgetUtils.change(listBox);
		} else {
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + errorMessage);
		}
	}

	@CsvMethod
	public void selectSuggest(String index, String objectLocalization) {
		SuggestBox suggestBox = getObject(SuggestBox.class, objectLocalization);
		checkWidgetVisible(suggestBox, objectLocalization);
		MenuItem item = getObject(MenuItem.class, objectLocalization + "/suggestionMenu/items[" + Integer.parseInt(index) + "]");
		for (Method m : SuggestBox.class.getDeclaredMethods()) {
			if ("setNewSelection".equals(m.getName())) {
				try {
					m.setAccessible(true);
					m.invoke(suggestBox, item);
					return;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		throw new RuntimeException("Method not found setNewSelection");
	}

	@CsvMethod
	public void fillSuggestBox(String value, String objectLocalization) {
		SuggestBox suggestBox = getObject(SuggestBox.class, objectLocalization);
		checkWidgetVisible(suggestBox, objectLocalization);
		suggestBox.setText(value);
		WidgetUtils.keyUp(suggestBox, KeyCodes.KEY_ENTER);
	}

	@CsvMethod
	public void isVisible(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		checkWidgetVisible(widget, objectLocalization);
	}

	@CsvMethod
	public void isNotVisible(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		Assert.assertFalse(csvRunner.getAssertionErrorMessagePrefix() + "Visible " + objectLocalization, widget.isVisible());
	}

	@CsvMethod
	public void isEnabled(String objectLocalization) {
		FocusWidget button = getFocusWidget(objectLocalization);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "is not Enabled " + objectLocalization, button.isEnabled());
	}

	@CsvMethod
	public void isNotEnabled(String objectLocalization) {
		FocusWidget button = getFocusWidget(objectLocalization);
		Assert.assertFalse(csvRunner.getAssertionErrorMessagePrefix() + "is Enabled " + objectLocalization, button.isEnabled());
	}

	protected FocusWidget getFocusWidget(String objectLocalization) {
		return getObject(FocusWidget.class, objectLocalization);
	}

	protected void checkWidgetVisibleAndEnable(FocusWidget widget, String objectLocalization) {
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Widget have to be enabled : " + objectLocalization, widget.isEnabled());
		checkWidgetVisible(widget, objectLocalization);
	}

	protected void checkWidgetVisibleAndEnable(FocusPanel widget, String objectLocalization) {
		checkWidgetVisible(widget, objectLocalization);
	}

	protected void checkWidgetVisible(Widget widget, String objectLocalization) {
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Widget have to be visible : " + objectLocalization + ", "
				+ widget.getClass().getCanonicalName(), WidgetUtils.isWidgetVisible(widget));
	}

	// MODE INTERACTIF
	private static final Class<?>[] baseList = { String.class, Integer.class, int.class, Class.class };

	private Object getObject(String param, PrintStream os) {
		Object o = getObject(Object.class, param);
		os.println("Object found, class " + o.getClass().getCanonicalName());
		if (ArrayUtils.contains(baseList, o.getClass())) {
			os.println("Value : " + o.toString());
		}
		return o;
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

	private void printGetter(Object o, Class<?> clazz, PrintStream os) {
		for (Method m : clazz.getDeclaredMethods()) {
			if (m.getName().startsWith("get")) {
				os.print("Getter [" + clazz.getSimpleName() + "] " + m.getName());
				if (ArrayUtils.contains(baseList, m.getReturnType()) && m.getParameterTypes().length == 0) {
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
			if (!Modifier.isStatic(f.getModifiers()) && !Modifier.isFinal(f.getModifiers())) {
				os.print("Field [" + clazz.getSimpleName() + "] [" + f.getClass().getSimpleName() + "] " + f.getName());
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
