package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class LayoutPanelTest extends AbstractGwtTest {

	private LayoutPanel panel;

	private boolean onAnimationComplete;

	@Before
	public void setUpLayoutPanel() {
		onAnimationComplete = false;

		panel = new LayoutPanel();
		Assert.assertFalse(panel.isAttached());
		RootPanel.get().add(panel);
		Assert.assertTrue(panel.isAttached());
		Assert.assertEquals(0, panel.getWidgetCount());
	}

	@Test
	public void checkAdd() {
		// Setup
		Button b = new Button();
		Assert.assertFalse(b.isAttached());

		// Test
		panel.add(b);

		// Assert
		Assert.assertEquals(1, panel.getWidgetCount());
		Assert.assertEquals(b, panel.getWidget(0));
		Assert.assertTrue(b.isAttached());
	}

	@Test
	public void checkAnimateWithCallback() {
		AnimationCallback callback = new AnimationCallback() {

			public void onLayout(Layer layer, double progress) {
				// never called in gwt-test-utils
			}

			public void onAnimationComplete() {
				onAnimationComplete = true;
			}
		};

		panel.animate(4, callback);

		Assert.assertTrue(onAnimationComplete);
	}

	@Test
	public void check() {
		//TODO: test those methods
		//		panel.getWidgetContainerElement(child)
		//		panel.insert(widget, beforeIndex)
		//		panel.remove(w)
		//		panel.setWidgetBottomHeight(child, bottom, bottomUnit, height, heightUnit)
		//		panel.setWidgetHorizontalPosition(child, position)
		//		panel.setWidgetLeftRight(child, left, leftUnit, right, rightUnit)
		//		panel.setWidgetLeftWidth(child, left, leftUnit, width, widthUnit)
		//		panel.setWidgetTopBottom(child, top, topUnit, bottom, bottomUnit)
		//		panel.setWidgetTopHeight(child, top, topUnit, height, heightUnit)
		//		panel.setWidgetVerticalPosition(child, position)
	}

}
