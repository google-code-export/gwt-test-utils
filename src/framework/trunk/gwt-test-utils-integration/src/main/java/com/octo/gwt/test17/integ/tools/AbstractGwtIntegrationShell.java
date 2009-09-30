package com.octo.gwt.test17.integ.tools;

import java.util.List;

import org.junit.Assert;

import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test17.integ.csvrunner.CsvRunner;
import com.octo.gwt.test17.integ.csvrunner.Node;

public abstract class AbstractGwtIntegrationShell {

	private DirectoryTestReader reader;
	
	protected CsvRunner csvRunner;
	
	private MacroReader macroReader;

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
		
	public PrefixProcessor findPrefixProcessor(String prefix) {
		if ("root".equals(prefix)) {
			return new PrefixProcessor() {

				public Object process(CsvRunner csvRunner, Node next) {
					return csvRunner.getValue(RootPanel.get(), next);
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
		Object o = processor.process(csvRunner, next);
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

	/*
	public void assertNumberExact(CsvRunner runner, String value, String objectLocalization) {
		Integer i = getObject(runner, Integer.class, objectLocalization, false);
		if (i != null) {
			Assert.assertEquals(runner.getAssertionErrorMessagePrefix() + "Wrong number", Integer.parseInt(value), i.intValue());
		} else {
			Long l = getObject(runner, Long.class, objectLocalization);
			Assert.assertEquals(runner.getAssertionErrorMessagePrefix() + "Wrong number", Long.parseLong(value), l.intValue());
		}
	}

	public void assertContain(CsvRunner runner, String value, String objectLocalization) {
		String s = getObject(runner, String.class, objectLocalization);
		Assert.assertTrue(runner.getAssertionErrorMessagePrefix() + " not containing string " + value, s.contains(value));
	}

	public void getObject(CsvRunner runner, String objectLocalization) {
		Object o = getObject(runner, Object.class, objectLocalization);
		Assert.assertNotNull(runner.getAssertionErrorMessagePrefix() + " is null ", o);
	}

	private MacroReader macroReader;

	public void runMacro(CsvRunner runner, String macroName) throws Exception {
		if (macroReader == null) {
			macroReader = new MacroReader();
			for (Entry<String, List<List<String>>> entry : getMacroSheets().entrySet()) {
				macroReader.read(entry.getValue());
			}
		}
		List<List<String>> macro = macroReader.getMacro(macroName);
		Assert.assertNotNull(runner.getAssertionErrorMessagePrefix() + "Not existing macro " + macroName, macro);
		int i = 0;
		for (List<String> line : macro) {
			runner.setExtendedLineInfo(macroName + " line " + (i + 1));
			runner.executeRow(line, this);
			i++;
		}
		runner.setExtendedLineInfo(null);
	}

	public void selectListBox(CsvRunner runner, String value, String objectLocalization) {
		ListBox listBox = getObject(runner, ListBox.class, objectLocalization);
		checkWidgetVisibleAndEnable(runner, listBox, objectLocalization);
		listBox.setSelectedIndex(Integer.parseInt(value));
		listBox.onBrowserEvent(new OverrideEvent(Event.ONKEYUP));
	}
	
	private void checkWidgetVisible(CsvRunner runner, Widget widget, String objectLocalization) {
		if (!widget.isVisible()) {
			Assert.fail(runner.getAssertionErrorMessagePrefix() + "Widget have to be visible : " + objectLocalization + ", "
					+ widget.getClass().getCanonicalName());
		}
		if (widget.getParent() != null) {
			checkWidgetVisible(runner, widget.getParent(), objectLocalization);
		}
	}

	private void checkWidgetVisibleAndEnable(CsvRunner runner, FocusWidget widget, String objectLocalization) {
		if (!widget.isEnabled()) {
			Assert.fail(runner.getAssertionErrorMessagePrefix() + "Widget have to be enabled : " + objectLocalization);
		}
		checkWidgetVisible(runner, widget, objectLocalization);
	}

	private void checkWidgetVisibleAndEnable(CsvRunner runner, FocusPanel widget, String objectLocalization) {
		checkWidgetVisible(runner, widget, objectLocalization);
	}

	public void click(CsvRunner runner, String objectLocalization) {
		Widget widget = getObject(runner, Widget.class, objectLocalization);
		if (widget instanceof FocusWidget) {
			FocusWidget focusWidget = (FocusWidget) widget;
			checkWidgetVisibleAndEnable(runner, focusWidget, objectLocalization);
		}
		if (widget instanceof CheckBox) {
			CheckBox checkBox = (CheckBox) widget;
			checkBox.setChecked(!checkBox.isChecked());
		}
		widget.onBrowserEvent(new OverrideEvent(Event.ONCLICK));
	}

	public void clickPanel(CsvRunner runner, String objectLocalization) {
		FocusPanel widget = getObject(runner, FocusPanel.class, objectLocalization);
		checkWidgetVisibleAndEnable(runner, widget, objectLocalization);
		widget.onBrowserEvent(new OverrideEvent(Event.ONCLICK));
	}

	public void clickCompositeOption(CsvRunner runner, String objectLocalization) {
		SimplePanel widget = getObject(runner, SimplePanel.class, objectLocalization);
		checkWidgetVisible(runner, widget, objectLocalization);
		widget.onBrowserEvent(new OverrideEvent(Event.ONCLICK));
	}

	public void isVisible(CsvRunner runner, String objectLocalization) {
		Widget widget = getObject(runner, Widget.class, objectLocalization);
		checkWidgetVisible(runner, widget, objectLocalization);
	}

	public void isNotVisible(CsvRunner runner, String objectLocalization) {
		Widget widget = getObject(runner, Widget.class, objectLocalization);
		Assert.assertFalse(runner.getAssertionErrorMessagePrefix() + "Visible " + objectLocalization, widget.isVisible());
	}

	public void isEnabled(CsvRunner runner, String objectLocalization) {
		FocusWidget button = getFocusWidget(runner, objectLocalization);
		Assert.assertTrue(runner.getAssertionErrorMessagePrefix() + "is not Enabled " + objectLocalization, button.isEnabled());
	}

	public void isNotEnabled(CsvRunner runner, String objectLocalization) {
		FocusWidget button = getFocusWidget(runner, objectLocalization);
		Assert.assertFalse(runner.getAssertionErrorMessagePrefix() + "is Enabled " + objectLocalization, button.isEnabled());
	}

	protected FocusWidget getFocusWidget(CsvRunner runner, String objectLocalization) {
		FocusWidget widget = getObject(runner, Widget.class, objectLocalization);
		return widget;
	}

	// TODO: Supprimer les événements "ONCHANGE"
	public void fillTextBox(CsvRunner runner, String value, String objectLocalization) {
		TextBox textBox = getObject(runner, Widget.class, objectLocalization);
		checkWidgetVisibleAndEnable(runner, textBox, objectLocalization);
		textBox.setText(value);
		textBox.onBrowserEvent(new OverrideEvent(Event.ONKEYUP));
		textBox.onBrowserEvent(new OverrideEvent(Event.ONCHANGE));
	}

	public void fillInvisibleTextBox(CsvRunner runner, String value, String objectLocalization) {
		TextBox textBox = getObject(runner, TextBox.class, objectLocalization);
		textBox.setText(value);
		textBox.onBrowserEvent(new OverrideEvent(Event.ONKEYUP));
		textBox.onBrowserEvent(new OverrideEvent(Event.ONCHANGE));
	}

	public void fillListBox(CsvRunner runner, String value, String objectLocalization) {
		ListBox listBox = getObject(runner, ListBox.class, objectLocalization);
		checkWidgetVisibleAndEnable(runner, listBox, objectLocalization);
		listBox.setSelectedIndex(Integer.parseInt(value));
		listBox.onBrowserEvent(new OverrideEvent(Event.ONCLICK));
		listBox.onBrowserEvent(new OverrideEvent(Event.ONCHANGE));
	}

	public void fillSuggestBox(CsvRunner runner, String value, String objectLocalization) {
		SuggestBox suggestBox = getObject(runner, SuggestBox.class, objectLocalization);
		checkWidgetVisible(runner, suggestBox, objectLocalization);
		suggestBox.setText(value);
		suggestBox.onBrowserEvent(new OverrideEvent(Event.ONKEYUP));
	}

	public void selectSuggest(CsvRunner runner, String index, String objectLocalization) {
		SuggestBox suggestBox = getObject(runner, SuggestBox.class, objectLocalization);
		checkWidgetVisible(runner, suggestBox, objectLocalization);
		MenuItem item = getObject(runner, MenuItem.class, objectLocalization + "/suggestionMenu/items[" + Integer.parseInt(index) + "]");
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
	*/

}
