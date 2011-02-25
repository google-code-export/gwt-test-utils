package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FormPanelTest extends AbstractGwtTest {

	private FormPanel form;
	private boolean submitted;
	private boolean completeSubmitted;

	@Override
	protected String getCurrentTestedModuleFile() {
		return "com/octo/gwt/test/test-config.gwt.xml";
	}

	@Before
	public void setupFormPanel() {
		form = new FormPanel();
		Assert.assertFalse(form.isAttached());
		RootPanel.get().add(form);
		Assert.assertTrue(form.isAttached());
	}

	@Test
	public void checkAdd() {
		// Setup
		Button b1 = new Button();
		Button b2 = new Button();
		Assert.assertFalse(b1.isAttached());
		Assert.assertFalse(b2.isAttached());

		// Test 1
		form.add(b1);

		// Assert 1
		Assert.assertEquals(b1, form.getWidget());
		Assert.assertTrue(b1.isAttached());
		Assert.assertFalse(b2.isAttached());

		// Test 2
		try {
			form.add(b2);
			Assert.fail("Simple panel can only contain one child widget");
		} catch (Exception e) {
			// Assert 2
			Assert.assertTrue(e instanceof IllegalStateException);
		}
	}

	@Test
	public void checkSetWidget() {
		// Setup
		Button b1 = new Button();
		Button b2 = new Button();
		Assert.assertFalse(b1.isAttached());
		Assert.assertFalse(b2.isAttached());

		// Test 1
		form.setWidget(b1);

		// Assert 1
		Assert.assertEquals(b1, form.getWidget());
		Assert.assertTrue(b1.isAttached());
		Assert.assertFalse(b2.isAttached());

		// Test 2
		form.setWidget(b2);

		// Assert 2
		Assert.assertEquals(b2, form.getWidget());
		Assert.assertFalse(b1.isAttached());
		Assert.assertTrue(b2.isAttached());
	}

	@Test
	public void checkFormSetup() {
		// Test
		form.setAction("/myFormHandler");
		form.setEncoding(FormPanel.ENCODING_MULTIPART);

		form.setMethod(FormPanel.METHOD_POST);
		form.setTitle("formTitle");
		form.setStyleName("formStyleName");
		form.addStyleName("addition");

		// Assert
		Assert.assertEquals("/myFormHandler", form.getAction());
		Assert.assertEquals(FormPanel.ENCODING_MULTIPART, form.getEncoding());
		Assert.assertEquals(FormPanel.METHOD_POST, form.getMethod());
		Assert.assertEquals("formTitle", form.getTitle());
		Assert.assertEquals("formStyleName addition", form.getStyleName());
	}

	@Test
	public void checkFormDimension() {
		// Test 1
		form.setHeight("10");
		form.setWidth("20");
		// Assert 1
		Assert.assertEquals("10", form.getElement().getStyle().getHeight());
		Assert.assertEquals("20", form.getElement().getStyle().getWidth());

		// Test 2
		form.setSize("30", "40");
		// Assert 2
		Assert.assertEquals("40", form.getElement().getStyle().getHeight());
		Assert.assertEquals("30", form.getElement().getStyle().getWidth());

		// Test 3
		form.setPixelSize(30, 40);
		// Assert 3
		Assert.assertEquals("40px", form.getElement().getStyle().getHeight());
		Assert.assertEquals("30px", form.getElement().getStyle().getWidth());
	}

	@Test
	public void checkRemoveFromParent() {
		// Test
		form.removeFromParent();

		// Assert
		Assert.assertFalse(form.isAttached());
	}

	@Test
	public void testSubmit() {
		// Setup
		TextBox tb = new TextBox();
		setupFormForSubmitTest(tb);
		submitted = false;
		completeSubmitted = false;

		form.submit();

		Assert.assertTrue(submitted);
		Assert.assertFalse(completeSubmitted);
	}

	@Test
	public void testSubmitComplete() {
		// Setup
		TextBox tb = new TextBox();
		tb.setText("some text");
		setupFormForSubmitTest(tb);
		submitted = false;
		completeSubmitted = false;

		// Test
		form.submit();

		Assert.assertTrue(submitted);
		Assert.assertTrue(completeSubmitted);
	}

	private void setupFormForSubmitTest(final TextBox tb) {
		form.setAction("/myFormHandler");

		// Because we're going to add a FileUpload widget, we'll need to set the
		// form to use the POST method, and multipart MIME encoding.
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);

		// Create a panel to hold all of the form widgets.
		VerticalPanel panel = new VerticalPanel();
		form.setWidget(panel);

		// Create a TextBox, giving it a name so that it will be submitted.
		tb.setName("textBoxFormElement");
		panel.add(tb);

		// Create a ListBox, giving it a name and some values to be associated with
		// its options.
		ListBox lb = new ListBox();
		lb.setName("listBoxFormElement");
		lb.addItem("foo", "fooValue");
		lb.addItem("bar", "barValue");
		lb.addItem("baz", "bazValue");
		panel.add(lb);

		// Create a FileUpload widget.
		FileUpload upload = new FileUpload();
		upload.setName("uploadFormElement");
		panel.add(upload);

		// Add an event handler to the form.
		form.addSubmitHandler(new FormPanel.SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				// This event is fired just before the form is submitted. We can take
				// this opportunity to perform validation.
				if (tb.getText() == null || tb.getText().length() == 0) {
					event.cancel();
				}

				submitted = true;
			}
		});
		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
				// When the form submission is successfully completed, this event is
				// fired. Assuming the service returned a response of type text/html,
				// we can get the result text here (see the FormPanel documentation for
				// further explanation).
				completeSubmitted = true;
			}
		});

	}

}
