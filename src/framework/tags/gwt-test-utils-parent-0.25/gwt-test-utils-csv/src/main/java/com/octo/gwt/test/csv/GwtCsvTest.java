package com.octo.gwt.test.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.GwtTest;
import com.octo.gwt.test.csv.runner.CsvRunner;
import com.octo.gwt.test.csv.runner.Node;
import com.octo.gwt.test.csv.tools.DefaultWidgetVisitor;
import com.octo.gwt.test.csv.tools.DirectoryTestReader;
import com.octo.gwt.test.csv.tools.MacroReader;
import com.octo.gwt.test.csv.tools.NodeObjectFinder;
import com.octo.gwt.test.csv.tools.ObjectFinder;
import com.octo.gwt.test.csv.tools.VisitorObjectFinder;
import com.octo.gwt.test.csv.tools.WidgetVisitor;
import com.octo.gwt.test.internal.utils.ArrayUtils;
import com.octo.gwt.test.internal.utils.GwtStringUtils;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.WidgetUtils;
import com.octo.gwt.test.utils.events.Browser;
import com.octo.gwt.test.utils.events.EventBuilder;
import com.octo.gwt.test.utils.events.EventUtils;

public abstract class GwtCsvTest extends GwtTest {

	protected CsvRunner csvRunner;

	private DirectoryTestReader reader;
	private MacroReader macroReader;

	public void setReader(DirectoryTestReader reader) {
		this.reader = reader;
		csvRunner = new CsvRunner();
		VisitorObjectFinder visitorObjectFinder = new VisitorObjectFinder(getWidgetVisitor());
		csvRunner.addObjectFinder(visitorObjectFinder);
		ObjectFinder defaultFinder = createDefaultFinder(visitorObjectFinder);
		csvRunner.addObjectFinder(defaultFinder);
		macroReader = new MacroReader();
		for (String name : reader.getMacroFileList()) {
			macroReader.read(reader.getMacroFile(name));
		}
	}

	protected WidgetVisitor getWidgetVisitor() {
		return new DefaultWidgetVisitor();
	}

	protected NodeObjectFinder getNodeObjectFinder(String prefix) {
		if ("root".equals(prefix)) {
			return new NodeObjectFinder() {

				public Object find(CsvRunner csvRunner, Node node) {
					return csvRunner.getNodeValue(RootPanel.get(), node);
				}
			};
		}

		return null;
	}

	private ObjectFinder createDefaultFinder(final ObjectFinder visitorObjectFinder) {
		ObjectFinder finder = new ObjectFinder() {

			public boolean accept(String... params) {
				return params.length == 1 && params[0].matches("^/\\w+/.*$");
			}

			public Object find(CsvRunner csvRunner, String... params) {
				Node node = Node.parse(params[0]);
				if (node == null) {
					return null;
				}

				String prefix = node.getLabel();

				NodeObjectFinder finder = getNodeObjectFinder(prefix);
				if (finder == null) {
					final Object o = visitorObjectFinder.find(csvRunner, prefix);

					finder = new NodeObjectFinder() {

						public Object find(CsvRunner csvRunner, Node node) {
							return csvRunner.getNodeValue(o, node);
						}
					};
				}

				Assert.assertNotNull(csvRunner.getAssertionErrorMessagePrefix() + "Unknown prefix '" + prefix
						+ "', you should override getNodeObjectFinder(..) method to provide a specific " + NodeObjectFinder.class.getSimpleName(),
						finder);

				return finder.find(csvRunner, node.getNext());
			}

		};

		return finder;
	}

	public void launchTest(String testName) throws Exception {
		csvRunner.runSheet(reader.getTest(testName), this);
	}

	@CsvMethod
	public void runmacro(String macroName, String... params) throws Exception {
		List<List<String>> macro = macroReader.getMacro(macroName);
		Assert.assertNotNull(csvRunner.getAssertionErrorMessagePrefix() + "CsvMacro '" + macroName + "' has not been found", macro);
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
	public void assertExact(String value, String... params) {
		value = GwtStringUtils.resolveBackSlash(value);

		String actualValue = getString(params);

		if (value == null) {
			Assert.assertNull(csvRunner.getAssertionErrorMessagePrefix() + "Null was expected, but '" + actualValue + "' has been find", actualValue);
		} else {
			Assert.assertEquals(csvRunner.getAssertionErrorMessagePrefix() + "Wrong string", value, actualValue);
		}

	}

	@CsvMethod
	public void assertEmpty(String... params) {
		assertExact("", params);
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
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + " not containing string " + containedValue, actual.contains(containedValue));
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
			Assert.assertEquals(csvRunner.getAssertionErrorMessagePrefix() + "Wrong number", Integer.parseInt(value), i.intValue());
		} else {
			Long l = getObject(Long.class, params);
			Assert.assertEquals(csvRunner.getAssertionErrorMessagePrefix() + "Wrong number", Long.parseLong(value), l.intValue());
		}
	}

	@CsvMethod
	public void assertTrue(String... params) {
		Boolean b = getObject(Boolean.class, false, params);
		if (b == null) {
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + "null Boolean");
		} else {
			Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "TRUE expected", b.booleanValue());
		}
	}

	@CsvMethod
	public void assertFalse(String... params) {
		Boolean b = getObject(Boolean.class, false, params);
		if (b == null) {
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + "null Boolean");
		} else {
			Assert.assertFalse(csvRunner.getAssertionErrorMessagePrefix() + "FALSE expected", b.booleanValue());
		}
	}

	@CsvMethod
	public void blur(String... params) {
		Widget widget = getObject(Widget.class, params);
		dispatchEvent(widget, Event.ONBLUR);
	}

	@CsvMethod
	public void change(String... params) {
		Widget widget = getObject(Widget.class, params);
		dispatchEvent(widget, Event.ONCHANGE);
	}

	@CsvMethod
	public void click(String... params) {
		Widget target = getObject(Widget.class, params);

		Event onMouseOver = EventBuilder.create(Event.ONMOUSEOVER).setTarget(target.getElement()).build();
		Event onMouseDown = EventBuilder.create(Event.ONMOUSEDOWN).setButton(Event.BUTTON_LEFT).build();
		Event onMouseUp = EventBuilder.create(Event.ONMOUSEUP).setButton(Event.BUTTON_LEFT).build();
		Event onClick = EventBuilder.create(Event.ONCLICK).build();

		dispatchEvent(target, onMouseOver, onMouseDown, onMouseUp, onClick);
	}

	@CsvMethod
	public void clickComplexPanel(String index, String... params) {
		ComplexPanel panel = getObject(ComplexPanel.class, params);
		Widget target = panel.getWidget(Integer.parseInt(index));

		Event onMouseOver = EventBuilder.create(Event.ONMOUSEOVER).setTarget(target.getElement()).build();
		Event onMouseDown = EventBuilder.create(Event.ONMOUSEDOWN).setTarget(target.getElement()).setButton(Event.BUTTON_LEFT).build();
		Event onMouseUp = EventBuilder.create(Event.ONMOUSEUP).setTarget(target.getElement()).setButton(Event.BUTTON_LEFT).build();
		Event onClick = EventBuilder.create(Event.ONCLICK).setTarget(target.getElement()).build();

		assertCanApplyEvent(target, onMouseOver, onMouseDown, onMouseUp, onClick);
		dispatchNotCheckedEvent(panel, onMouseOver, onMouseDown, onMouseUp, onClick);
	}

	@CsvMethod
	public void clickSimplePanel(String... params) {
		SimplePanel panel = getObject(SimplePanel.class, params);

		Event onMouseOver = EventBuilder.create(Event.ONMOUSEOVER).setTarget(panel.getElement()).build();
		Event onMouseDown = EventBuilder.create(Event.ONMOUSEDOWN).setButton(Event.BUTTON_LEFT).build();
		Event onMouseUp = EventBuilder.create(Event.ONMOUSEUP).setButton(Event.BUTTON_LEFT).build();
		Event onClick = EventBuilder.create(Event.ONCLICK).build();

		assertCanApplyEvent(panel.getWidget(), onMouseOver, onMouseDown, onMouseUp, onClick);
		dispatchNotCheckedEvent(panel, onMouseOver, onMouseDown, onMouseUp, onClick);
	}

	@CsvMethod
	public void clickMenuItem(String index, String identifier) {
		MenuBar menuBar = getObject(MenuBar.class, identifier);
		List<MenuItem> menuItems = GwtReflectionUtils.getPrivateFieldValue(menuBar, "items");
		MenuItem itemToClick = menuItems.get(Integer.parseInt(index));

		Event onMouseOver = EventBuilder.create(Event.ONMOUSEOVER).setTarget(itemToClick.getElement()).build();
		Event onMouseDown = EventBuilder.create(Event.ONMOUSEDOWN).setTarget(itemToClick.getElement()).setButton(Event.BUTTON_LEFT).build();
		Event onMouseUp = EventBuilder.create(Event.ONMOUSEUP).setTarget(itemToClick.getElement()).setButton(Event.BUTTON_LEFT).build();
		Event onClick = EventBuilder.create(Event.ONCLICK).setTarget(itemToClick.getElement()).build();

		assertCanApplyEvent(itemToClick, onMouseOver, onMouseDown, onMouseUp, onClick);
		dispatchNotCheckedEvent(menuBar, onMouseOver, onMouseDown, onMouseUp, onClick);
	}

	@CsvMethod
	public void clickGridCell(String rowIndex, String column, String identifier) {
		Grid grid = getObject(Grid.class, identifier);
		Widget target = grid.getWidget(Integer.parseInt(rowIndex), Integer.parseInt(column));

		Event onMouseOver = EventBuilder.create(Event.ONMOUSEOVER).setTarget(target.getElement()).build();
		Event onMouseDown = EventBuilder.create(Event.ONMOUSEDOWN).setTarget(target.getElement()).setButton(Event.BUTTON_LEFT).build();
		Event onMouseUp = EventBuilder.create(Event.ONMOUSEUP).setTarget(target.getElement()).setButton(Event.BUTTON_LEFT).build();
		Event onClick = EventBuilder.create(Event.ONCLICK).setTarget(target.getElement()).build();

		assertCanApplyEvent(target, onMouseOver, onMouseDown, onMouseUp, onClick);
		dispatchNotCheckedEvent(grid, onMouseOver, onMouseDown, onMouseUp, onClick);
	}

	@CsvMethod
	public void focus(String identifier) {
		Widget widget = getObject(Widget.class, identifier);
		dispatchEvent(widget, EventBuilder.create(Event.ONFOCUS).build());
	}

	@CsvMethod
	public void mouseDown(String identifier) {
		Widget widget = getObject(Widget.class, identifier);
		dispatchEvent(widget, EventBuilder.create(Event.ONMOUSEDOWN).build());
	}

	@CsvMethod
	public void mouseMove(String identifier) {
		Widget widget = getObject(Widget.class, identifier);
		dispatchEvent(widget, EventBuilder.create(Event.ONMOUSEMOVE).build());
	}

	@CsvMethod
	public void mouseUp(String identifier) {
		Widget widget = getObject(Widget.class, identifier);
		dispatchEvent(widget, EventBuilder.create(Event.ONMOUSEUP).build());
	}

	@CsvMethod
	public void mouseWheel(String identifier) {
		Widget widget = getObject(Widget.class, identifier);
		dispatchEvent(widget, EventBuilder.create(Event.ONMOUSEWHEEL).build());
	}

	@CsvMethod
	public void mouseOut(String identifier) {
		Widget widget = getObject(Widget.class, identifier);
		dispatchEvent(widget, EventBuilder.create(Event.ONMOUSEOUT).build());
	}

	@CsvMethod
	public void mouseOver(String identifier) {
		Widget widget = getObject(Widget.class, identifier);
		dispatchEvent(widget, EventBuilder.create(Event.ONMOUSEOVER).build());
	}

	@CsvMethod
	public void hasStyle(String style, String identifier) {
		UIObject object = getObject(UIObject.class, identifier);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Style not found : " + style, object.getStyleName().contains(style));
	}

	@CsvMethod
	public void assertNotExist(String... params) {
		Object o = getObject(Object.class, false, params);
		Assert.assertNull(csvRunner.getAssertionErrorMessagePrefix() + "Object exist", o);
	}

	@CsvMethod
	public void callMethod(String... params) {
		getObject(Object.class, false, params);
	}

	@CsvMethod
	public void isChecked(String... params) {
		CheckBox checkBox = getObject(CheckBox.class, params);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Checkbox not checked", checkBox.getValue());
	}

	@CsvMethod
	public void isNotChecked(String... params) {
		CheckBox checkBox = getObject(CheckBox.class, params);
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
	public void assertBiggerThan(String value, String... params) {
		Integer i = getObject(Integer.class, false, params);
		if (i != null) {
			int currentValue = i.intValue();
			int intValue = Integer.parseInt(value);
			Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Current value <" + currentValue + "> is not bigger than <" + value + ">",
					currentValue > intValue);
		} else {
			Long l = getObject(Long.class, params);
			long currentValue = l.longValue();
			long longValue = Long.parseLong(value);
			Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Current value <" + currentValue + "> is not bigger than <" + value + ">",
					currentValue > longValue);
		}
	}

	@CsvMethod
	public void assertSmallerThan(String value, String... params) {
		Integer i = getObject(Integer.class, false, params);
		if (i != null) {
			int currentValue = i.intValue();
			int intValue = Integer.parseInt(value);
			Assert.assertTrue(
					csvRunner.getAssertionErrorMessagePrefix() + "Current value <" + currentValue + "> is not smaller than <" + value + ">",
					currentValue < intValue);
		} else {
			Long l = getObject(Long.class, params);
			long currentValue = l.longValue();
			long longValue = Long.parseLong(value);
			Assert.assertTrue(
					csvRunner.getAssertionErrorMessagePrefix() + "Current value <" + currentValue + "> is not smaller than <" + value + ">",
					currentValue < longValue);
		}
	}

	@CsvMethod
	public void assertListBoxSelectedValueIs(String value, String... params) {
		ListBox listBox = getObject(ListBox.class, params);
		Assert.assertEquals(csvRunner.getAssertionErrorMessagePrefix() + "Wrong ListBox selected value", value,
				listBox.getItemText(listBox.getSelectedIndex()));
	}

	@CsvMethod
	public void fillTextBox(String value, String... params) {
		TextBox textBox = getObject(TextBox.class, params);
		Browser.fillText(textBox, value);
	}

	@CsvMethod
	public void fillInvisibleTextBox(String value, String... params) {
		TextBox textBox = getObject(TextBox.class, params);
		textBox.setText(value);

		dispatchNotCheckedEvent(textBox, EventBuilder.create(Event.ONKEYUP).setKeyCode(KeyCodes.KEY_ENTER).build());
		dispatchNotCheckedEvent(textBox, EventBuilder.create(Event.ONCHANGE).build());
	}

	@CsvMethod
	public void assertListBoxDataEquals(String commaSeparatedContent, String... params) {
		ListBox listBox = getObject(ListBox.class, params);
		String[] content = commaSeparatedContent.split("\\s*,\\s*");
		if (!WidgetUtils.assertListBoxDataMatch(listBox, content)) {
			String lbContent = WidgetUtils.getListBoxContentToString(listBox);
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + "Content is not equal to listBox content : " + lbContent);
		}
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

	@CsvMethod
	public void fillAndSelectInSuggestBoxByIndex(String content, String index, String... params) {
		SuggestBox suggestBox = getObject(SuggestBox.class, params);

		Browser.fillText(suggestBox, content);
		executeSuggestCommandByIndex(WidgetUtils.getMenuItems(suggestBox), Integer.parseInt(index));
	}

	@CsvMethod
	public void fillAndSelectInSuggestBoxByText(String content, String selected, String... params) {
		SuggestBox suggestBox = getObject(SuggestBox.class, params);

		Browser.fillText(suggestBox, content);

		List<MenuItem> menuItems = WidgetUtils.getMenuItems(suggestBox);
		int i = 0;
		int index = -1;

		while (i < menuItems.size() && index == -1) {
			MenuItem item = menuItems.get(i);
			if (selected.equals(item.getHTML()) || selected.equals(item.getText())) {
				index = i;
			}
			i++;
		}

		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Cannot find '" + selected + "' in suggested choices", index > -1);

		executeSuggestCommandByIndex(menuItems, index);
	}

	@CsvMethod
	public void isVisible(String identifier) {
		UIObject object = getObject(UIObject.class, identifier);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "targeted " + object.getClass().getSimpleName() + " is not visible",
				WidgetUtils.isWidgetVisible(object));
	}

	@CsvMethod
	public void isNotVisible(String identifier) {
		UIObject object = getObject(UIObject.class, false, identifier);
		if (object != null) {
			Assert.assertFalse(csvRunner.getAssertionErrorMessagePrefix() + "targeted " + object.getClass().getSimpleName() + " is visible",
					WidgetUtils.isWidgetVisible(object));
		}
	}

	@CsvMethod
	public void isEnabled(String identifier) {
		FocusWidget button = getFocusWidget(identifier);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "targeted " + button.getClass().getSimpleName() + " is not enabled",
				button.isEnabled());
	}

	@CsvMethod
	public void isNotEnabled(String identifier) {
		FocusWidget button = getFocusWidget(identifier);
		Assert.assertFalse(csvRunner.getAssertionErrorMessagePrefix() + "targeted " + button.getClass().getSimpleName() + " is enabled",
				button.isEnabled());
	}

	protected void assertCanApplyEvent(UIObject widget, int eventType) {

		if (!WidgetUtils.isWidgetVisible(widget)) {
			String failureMessage = createFailureMessage(widget, eventType, "visible");
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + failureMessage);
		}

		if (widget instanceof FocusWidget && (!((FocusWidget) widget).isEnabled())) {
			String failureMessage = createFailureMessage(widget, eventType, "enabled");
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + failureMessage);
		}
	}

	protected void assertCanApplyEvent(UIObject widget, Event... events) {
		for (Event event : events) {
			assertCanApplyEvent(widget, event.getTypeInt());
		}
	}

	protected void dispatchEvent(Widget target, int eventTypeInt) {
		Event event = EventBuilder.create(eventTypeInt).build();
		dispatchEvent(target, event);
	}

	protected void dispatchEvent(Widget target, Event... events) {
		assertCanApplyEvent(target, events);

		dispatchNotCheckedEvent(target, events);
	}

	protected FocusWidget getFocusWidget(String identifier) {
		return getObject(FocusWidget.class, identifier);
	}

	protected <T> T getObject(Class<T> clazz, String... params) {
		return csvRunner.getObject(clazz, params);
	}

	protected <T> T getObject(Class<T> clazz, boolean failOnError, String... params) {
		return csvRunner.getObject(clazz, failOnError, params);
	}

	protected void selectInListBox(ListBox listBox, String regex, String... params) {
		int selectedIndex;
		String errorMessage;
		if (regex.matches("^\\d*$")) {
			selectedIndex = Integer.parseInt(regex);
			errorMessage = "Cannot select negative index in ListBox <" + regex + "> in ListBox with values : ";
		} else {
			selectedIndex = WidgetUtils.getIndexInListBox(listBox, regex);
			errorMessage = "Regex <" + regex + "> has not been matched in ListBox values : ";
		}

		if (selectedIndex > -1) {
			listBox.setSelectedIndex(selectedIndex);
			dispatchEvent(listBox, Event.ONCLICK);
			dispatchEvent(listBox, Event.ONCHANGE);
		} else {
			errorMessage += WidgetUtils.getListBoxContentToString(listBox);
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + errorMessage);
		}
	}

	protected void dispatchNotCheckedEvent(Widget target, Event... events) {
		for (Event event : events) {
			if (CheckBox.class.isInstance(target) && event.getTypeInt() == Event.ONCLICK) {
				CheckBox checkBox = (CheckBox) target;
				if (RadioButton.class.isInstance(target)) {
					checkBox.setValue(true);
				} else {
					checkBox.setValue(!checkBox.getValue());
				}
			}
			target.onBrowserEvent(event);
		}
	}

	protected String getString(String... params) {
		Object o = getObject(Object.class, false, params);
		return getString(o);
	}

	protected String getString(Object o) {
		String actualValue;
		if (o == null) {
			return null;
		} else if (HasHTML.class.isInstance(o)) {
			HasHTML hasHTML = (HasHTML) o;
			String html = hasHTML.getHTML();
			actualValue = (html != null && html.length() > 0) ? html : hasHTML.getText();
		} else if (HasText.class.isInstance(o)) {
			actualValue = ((HasText) o).getText();
		} else {
			actualValue = "" + o;
		}

		return actualValue;
	}

	private String createFailureMessage(UIObject target, int eventTypeInt, String attribut) {
		String className = target.getClass().isAnonymousClass() ? target.getClass().getName() : target.getClass().getSimpleName();
		StringBuilder sb = new StringBuilder();
		String event = EventUtils.getEventTypeString(eventTypeInt);
		sb.append("The targeted widget (").append(className);
		sb.append(") has to be ").append(attribut);
		sb.append(" to apply a browser '").append(event).append("\' event");

		return sb.toString();
	}

	private void executeSuggestCommandByIndex(List<MenuItem> menuItems, int index) {
		MenuItem item = menuItems.get(index);
		assertCanApplyEvent(item, Event.ONCLICK);
		item.getCommand().execute();
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
