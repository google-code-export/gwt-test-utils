package com.octo.gwt.test17.integ.tools;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test17.GwtCreateHandler;
import com.octo.gwt.test17.PatchGWT;
import com.octo.gwt.test17.ReflectionUtils;
import com.octo.gwt.test17.WidgetUtils;
import com.octo.gwt.test17.integ.csvrunner.CsvRunner;
import com.octo.gwt.test17.integ.csvrunner.Node;
import com.octo.gwt.test17.internal.overrides.OverrideEvent;

public abstract class AbstractGwtIntegrationShell {

	private DirectoryTestReader reader;
	
	protected CsvRunner csvRunner;
	
	private MacroReader macroReader;
	
	@Before
	public void setUpAbstractGwtIntegrationShell() throws Exception {
		initPatchGwt();
		PatchGWT.setGwtCreateHandler(getGwtCreateHandler());
	}
	
	@After
	public void tearDownAbstractGwtIntegrationShell() throws Exception {
		resetPatchGwt();
	}
	
	protected void initPatchGwt() throws Exception {
		//initialisation du framework de mock GWT
		PatchGWT.init();
	}
	
	protected void resetPatchGwt() throws Exception {
		// reinit GWT
		PatchGWT.reset();
	}
	
	public void setReader(DirectoryTestReader reader) {
		this.reader = reader;
		this.csvRunner = new CsvRunner();
		macroReader = new MacroReader();
		for (String name : reader.getMacroFileList()) {
			macroReader.read(reader.getMacroFile(name));
		}
	}

	public void runMacro(String macroName) throws Exception {
		List<List<String>> macro = macroReader.getMacro(macroName);
		Assert.assertNotNull(csvRunner.getAssertionErrorMessagePrefix() + "Not existing macro " + macroName, macro);
		int i = 0;
		for (List<String> line : macro) {
			csvRunner.setExtendedLineInfo(macroName + " line " + (i + 1));
			csvRunner.executeRow(line, this);
			i++;
		}
		csvRunner.setExtendedLineInfo(null);
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
	
	protected GwtCreateHandler getGwtCreateHandler() {
		//this method can be overrided
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
		Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + "Wrong object type, not a " + clazz.getCanonicalName() + " : "
				+ o.getClass().getCanonicalName());
		return null;
	}

	public void assertExact(String value, String objectLocalization) {
		String s = getObject(String.class, objectLocalization);
		Assert.assertEquals(csvRunner.getAssertionErrorMessagePrefix() + "Wrong string", value, s);
	}

	public void assertNumberExact(String value, String objectLocalization) {
		Integer i = getObject(Integer.class, objectLocalization, false);
		if (i != null) {
			Assert.assertEquals(csvRunner.getAssertionErrorMessagePrefix() + "Wrong number", Integer.parseInt(value), i.intValue());
		} else {
			Long l = getObject(Long.class, objectLocalization);
			Assert.assertEquals(csvRunner.getAssertionErrorMessagePrefix() + "Wrong number", Long.parseLong(value), l.intValue());
		}
	}

	public void assertContains(String value, String objectLocalization) {
		String s = getObject(String.class, objectLocalization);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + " not containing string " + value, s.contains(value));
	}
	
	public void blur(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.blur(widget);
	}
	
	public void change(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.change(widget);
	}

	public void click(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.click(widget);
	}
	
	public void clickMenuItem(String menuBarLocalization, String menuItemIndex) {
		Widget menuBar = getObject(Widget.class, menuBarLocalization);
		List<MenuItem> menuItems = ReflectionUtils.getPrivateFieldValue(menuBar, "items");
		MenuItem itemToClick = menuItems.get(Integer.parseInt(menuItemIndex));
		menuBar.onBrowserEvent(new OverrideEvent(Event.ONCLICK, itemToClick.getElement()));
	}
	
	public void focus(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.focus(widget);
	}
	
	public void mouseDown(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.mouseDown(widget);
	}
	
	public void mouseMove(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.mouseMove(widget);
	}
	
	public void mouseUp(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.mouseUp(widget);
	}	
	
	public void mouseWheel(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		WidgetUtils.mouseWheel(widget);
	}

	public void hasStyle(String style, String objectLocalization) {
		Widget w = getObject(Widget.class, objectLocalization);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Style not found : " + style, w.getStyleName().contains(style));
	}
	
	public void assertNotExist(String objectLocalization) {
		Object o = getObject(Object.class, objectLocalization, false);
		Assert.assertNull(csvRunner.getAssertionErrorMessagePrefix() + "Object exist", o);
	}
	
	public void callMethod(String objectLocalization) {
		getObject(Object.class, objectLocalization, false);
	}
	
	public void isChecked(String objectLocalization) {
		CheckBox checkBox = getObject(CheckBox.class, objectLocalization);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Checkbox not checked", checkBox.getValue());
	}
	
	public void isNotChecked(String objectLocalization) {
		CheckBox checkBox = getObject(CheckBox.class, objectLocalization);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "Checkbox checked", !checkBox.getValue());
	}
	
	public void clickOnTableRow(String rowIndex, String objectLocalization) {
		Grid grid = getObject(Grid.class, objectLocalization);
		checkWidgetVisible(grid, objectLocalization);
		WidgetUtils.click(grid.getWidget(Integer.parseInt(rowIndex), 0));
	}
	
	public void selectListBox(String value, String objectLocalization) {
		ListBox listBox = getObject(ListBox.class, objectLocalization);
		checkWidgetVisibleAndEnable(listBox, objectLocalization);
		listBox.setSelectedIndex(Integer.parseInt(value));
		listBox.onBrowserEvent(new OverrideEvent(Event.ONCHANGE));
	}
	
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
	
	// TODO: delete "ONCHANGE" events
	public void fillTextBox(String value, String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		TextBox textBox = (TextBox) widget;
		checkWidgetVisibleAndEnable(textBox, objectLocalization);
		textBox.setText(value);
		textBox.onBrowserEvent(new OverrideEvent(Event.ONKEYUP));
		textBox.onBrowserEvent(new OverrideEvent(Event.ONCHANGE));
	}

	public void fillInvisibleTextBox(String value, String objectLocalization) {
		TextBox textBox = getObject(TextBox.class, objectLocalization);
		textBox.setText(value);
		textBox.onBrowserEvent(new OverrideEvent(Event.ONKEYUP));
		textBox.onBrowserEvent(new OverrideEvent(Event.ONCHANGE));
	}
	
	public void fillListBox(String value, String objectLocalization) {
		ListBox listBox = getObject(ListBox.class, objectLocalization);
		checkWidgetVisibleAndEnable(listBox, objectLocalization);
		listBox.setSelectedIndex(Integer.parseInt(value));
		listBox.onBrowserEvent(new OverrideEvent(Event.ONCLICK));
		listBox.onBrowserEvent(new OverrideEvent(Event.ONCHANGE));
	}

	public void selectInListBox(String value, String objectLocalization) {
		ListBox listBox = getObject(ListBox.class, objectLocalization);
		checkWidgetVisibleAndEnable(listBox, objectLocalization);
		listBox.setSelectedIndex(Integer.parseInt(value));
		listBox.onBrowserEvent(new OverrideEvent(Event.ONCLICK));
		listBox.onBrowserEvent(new OverrideEvent(Event.ONCHANGE));
	}

	public void fillSuggestBox(String value, String objectLocalization) {
		SuggestBox suggestBox = getObject(SuggestBox.class, objectLocalization);
		checkWidgetVisible(suggestBox, objectLocalization);
		suggestBox.setText(value);
		suggestBox.onBrowserEvent(new OverrideEvent(Event.ONKEYUP));
	}
	
	public void isVisible(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		checkWidgetVisible(widget, objectLocalization);
	}

	public void isNotVisible(String objectLocalization) {
		Widget widget = getObject(Widget.class, objectLocalization);
		Assert.assertFalse(csvRunner.getAssertionErrorMessagePrefix() + "Visible " + objectLocalization, widget.isVisible());
	}

	public void isEnabled(String objectLocalization) {
		FocusWidget button = getFocusWidget(objectLocalization);
		Assert.assertTrue(csvRunner.getAssertionErrorMessagePrefix() + "is not Enabled " + objectLocalization, button.isEnabled());
	}

	public void isNotEnabled(String objectLocalization) {
		FocusWidget button = getFocusWidget(objectLocalization);
		Assert.assertFalse(csvRunner.getAssertionErrorMessagePrefix() + "is Enabled " + objectLocalization, button.isEnabled());
	}
	
	protected FocusWidget getFocusWidget(String objectLocalization) {
		return getObject(FocusWidget.class, objectLocalization);
	}
	
	protected void checkWidgetVisibleAndEnable(FocusWidget widget, String objectLocalization) {
		if (!widget.isEnabled()) {
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + "Widget have to be enabled : " + objectLocalization);
		}
		checkWidgetVisible(widget, objectLocalization);
	}
	
	protected void checkWidgetVisible(Widget widget, String objectLocalization) {
		if (!widget.isVisible()) {
			Assert.fail(csvRunner.getAssertionErrorMessagePrefix() + "Widget have to be visible : " + objectLocalization + ", "
					+ widget.getClass().getCanonicalName());
		}
		if (widget.getParent() != null) {
			checkWidgetVisible(widget.getParent(), objectLocalization);
		}
	}

}
