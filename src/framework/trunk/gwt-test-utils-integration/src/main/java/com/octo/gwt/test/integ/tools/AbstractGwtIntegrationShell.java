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

import org.junit.Assert;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.AbstractGwtConfigurableTest;
import com.octo.gwt.test.integ.CsvMethod;
import com.octo.gwt.test.integ.csvrunner.CsvRunner;
import com.octo.gwt.test.integ.csvrunner.Node;
import com.octo.gwt.test.utils.ArrayUtils;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;
import com.octo.gwt.test.utils.GwtTestStringUtils;
import com.octo.gwt.test.utils.WidgetUtils;
import com.octo.gwt.test.utils.events.EventBuilder;
import com.octo.gwt.test.utils.events.EventUtils;

public abstract class AbstractGwtIntegrationShell extends AbstractGwtConfigurableTest {

	protected CsvRunner csvRunner;

	private DirectoryTestReader reader;
	private MacroReader macroReader;

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

	@CsvMethod
	public void assertContains(String value, String objectLocalization) {
		value = GwtTestStringUtils.resolveBackSlash(value);
		String s = getObject(String.class, objectLocalization);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + " not containing string " + value, s.contains(value));
	}

	@CsvMethod
	public void blur(String identifier) {
		Widget widget = getObject(Widget.class, identifier);
		dispatchEvent(widget, Event.ONBLUR);
	}

	@CsvMethod
	public void change(String identifier) {
		Widget widget = getObject(Widget.class, identifier);
		dispatchEvent(widget, Event.ONCHANGE);
	}

	@CsvMethod
	public void click(String widgetIdentifier) {
		Widget widget = getObject(Widget.class, widgetIdentifier);
		dispatchEvent(widget, Event.ONCLICK);
	}

	@CsvMethod
	public void clickComplexPanel(String index, String objectLocalization) {
		ComplexPanel panel = getObject(ComplexPanel.class, objectLocalization);
		Widget target = panel.getWidget(Integer.parseInt(index));

		Event complexPanelClick = EventBuilder.create(Event.ONCLICK).setTarget(target.getElement()).build();
		assertCanApplyEvent(target, complexPanelClick.getTypeInt());
		dispatchEventInternal(panel, EventBuilder.create(Event.ONCLICK).setTarget(target.getElement()).build());
	}

	@CsvMethod
	public void clickSimplePanel(String objectLocalization) {
		SimplePanel panel = getObject(SimplePanel.class, objectLocalization);
		Event clickEvent = EventBuilder.create(Event.ONCLICK).build();
		assertCanApplyEvent(panel.getWidget(), clickEvent.getTypeInt());
		dispatchEventInternal(panel, clickEvent);
	}

	@CsvMethod
	public void clickMenuItem(String index, String identifier) {
		MenuBar menuBar = getObject(MenuBar.class, identifier);
		List<MenuItem> menuItems = GwtTestReflectionUtils.getPrivateFieldValue(menuBar, "items");
		MenuItem itemToClick = menuItems.get(Integer.parseInt(index));

		Event clickEvent = EventBuilder.create(Event.ONCLICK).setTarget(itemToClick.getElement()).build();
		assertCanApplyEvent(itemToClick, clickEvent.getTypeInt());
		dispatchEventInternal(menuBar, clickEvent);
	}

	@CsvMethod
	public void clickOnTableRow(String rowIndex, String identifier) {
		Grid grid = getObject(Grid.class, identifier);
		Widget target = grid.getWidget(Integer.parseInt(rowIndex), 0);
		Event clickEvent = EventBuilder.create(Event.ONCLICK).setTarget(target.getElement()).build();
		assertCanApplyEvent(target, clickEvent.getTypeInt());
		dispatchEventInternal(grid, clickEvent);
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

	@CsvMethod
	public void fillTextBox(String value, String identifier) {
		TextBox textBox = getObject(TextBox.class, identifier);
		textBox.setText(value);

		Event keyUpEvent = EventBuilder.create(Event.ONKEYUP).setKeyCode(KeyCodes.KEY_ENTER).build();
		Event changeEvent = EventBuilder.create(Event.ONCHANGE).build();
		dispatchEvent(textBox, keyUpEvent);
		dispatchEvent(textBox, changeEvent);
	}

	@CsvMethod
	public void fillInvisibleTextBox(String value, String objectLocalization) {
		TextBox textBox = getObject(TextBox.class, objectLocalization);
		textBox.setText(value);

		dispatchEventInternal(textBox, EventBuilder.create(Event.ONKEYUP).setKeyCode(KeyCodes.KEY_ENTER).build());
		dispatchEventInternal(textBox, EventBuilder.create(Event.ONCHANGE).build());
	}

	@CsvMethod
	public void assertListBoxDataEquals(String commaSeparatedContent, String objectLocalization) {
		ListBox listBox = getObject(ListBox.class, objectLocalization);
		String[] content = commaSeparatedContent.split("\\s*,\\s*");
		if (!WidgetUtils.assertListBoxDataMatch(listBox, content)) {
			String lbContent = WidgetUtils.getListBoxContentToString(listBox);
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + "Content is not equal to listBox content : " + lbContent);
		}
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

	@CsvMethod
	public void selectSuggestByIndex(String index, String objectLocalization) {
		SuggestBox suggestBox = getObject(SuggestBox.class, objectLocalization);

		List<MenuItem> menuItems = WidgetUtils.getMenuItems(suggestBox);
		MenuItem item = menuItems.get(Integer.parseInt(index));
		assertCanApplyEvent(item, Event.ONCLICK);
		item.getCommand().execute();
	}

	@CsvMethod
	public void selectSuggestByText(String content, String objectLocalization) {
		SuggestBox suggestBox = getObject(SuggestBox.class, objectLocalization);

		List<MenuItem> menuItems = WidgetUtils.getMenuItems(suggestBox);
		int i = 0;

		while (i < menuItems.size()) {
			MenuItem item = menuItems.get(i++);
			if (content.equals(item.getHTML()) || content.equals(item.getText())) {
				assertCanApplyEvent(item, Event.ONCLICK);
				item.getCommand().execute();
			}
		}
	}

	@CsvMethod
	public void fillSuggestBox(String value, String objectLocalization) {
		SuggestBox suggestBox = getObject(SuggestBox.class, objectLocalization);

		suggestBox.setText(value);

		Event keyUpEvent = EventBuilder.create(Event.ONKEYUP).setKeyCode(KeyCodes.KEY_ENTER).build();
		dispatchEvent(suggestBox, keyUpEvent);
	}

	@CsvMethod
	public void isVisible(String identifier) {
		UIObject object = getObject(UIObject.class, identifier);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "targeted " + object.getClass().getSimpleName() + " is not visible",
				WidgetUtils.isWidgetVisible(object));
	}

	@CsvMethod
	public void isNotVisible(String identifier) {
		UIObject object = getObject(UIObject.class, identifier);
		Assert.assertFalse(csvRunner.getAssertionErrorMessagePrefix() + "targeted " + object.getClass().getSimpleName() + " is visible", WidgetUtils
				.isWidgetVisible(object));
	}

	@CsvMethod
	public void isEnabled(String identifier) {
		FocusWidget button = getFocusWidget(identifier);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "targeted " + button.getClass().getSimpleName() + " is not enabled", button
				.isEnabled());
	}

	@CsvMethod
	public void isNotEnabled(String identifier) {
		FocusWidget button = getFocusWidget(identifier);
		Assert.assertFalse(csvRunner.getAssertionErrorMessagePrefix() + "targeted " + button.getClass().getSimpleName() + " is enabled", button
				.isEnabled());
	}

	protected void assertCanApplyEvent(UIObject widget, int eventTypeInt) {
		if (!WidgetUtils.isWidgetVisible(widget)) {
			String failureMessage = createFailureMessage(widget, eventTypeInt, "visible");
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + failureMessage);
		}

		if (widget instanceof FocusWidget && (!((FocusWidget) widget).isEnabled())) {
			String failureMessage = createFailureMessage(widget, eventTypeInt, "enabled");
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + failureMessage);
		}
	}

	protected void dispatchEvent(Widget target, int eventTypeInt) {
		Event event = EventBuilder.create(eventTypeInt).build();
		dispatchEvent(target, event);
	}

	protected void dispatchEvent(Widget target, Event event) {
		assertCanApplyEvent(target, event.getTypeInt());

		dispatchEventInternal(target, event);
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

	protected FocusWidget getFocusWidget(String identifier) {
		return getObject(FocusWidget.class, identifier);
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

	protected void selectInListBox(ListBox listBox, String regex, String objectLocalization) {
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
			dispatchEvent(listBox, Event.ONCLICK);
			dispatchEvent(listBox, Event.ONCHANGE);
		} else {
			errorMessage += WidgetUtils.getListBoxContentToString(listBox);
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + errorMessage);
		}
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

	private void dispatchEventInternal(Widget target, Event event) {
		if (target instanceof CheckBox && event.getTypeInt() == Event.ONCLICK) {
			CheckBox checkBox = (CheckBox) target;
			checkBox.setValue(!checkBox.getValue());
		}
		target.onBrowserEvent(event);
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
