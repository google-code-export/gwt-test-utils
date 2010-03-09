package com.octo.gwt.test.internal;

import com.google.gwt.user.client.ui.Widget;

public class PatchWidgetCollection {

	/**
	 * Inserts a widget before the specified index.
	 * 
	 * @param w
	 *            the widget to be inserted
	 * @param beforeIndex
	 *            the index before which the widget will be inserted
	 * @throws IndexOutOfBoundsException
	 *             if <code>beforeIndex</code> is out of range
	 */
	public static void insert(Widget w, int beforeIndex, int size, Widget[] array) {
		if ((beforeIndex < 0) || (beforeIndex > size)) {
			throw new IndexOutOfBoundsException();
		}

		// Realloc array if necessary (doubling).
		if (size == array.length) {
			Widget[] newArray = new Widget[array.length * 2];
			for (int i = 0; i < array.length; ++i) {
				newArray[i] = array[i];
			}
			array = newArray;
		}

		++size;

		// Move all widgets after 'beforeIndex' back a slot.
		for (int i = size - 1; i > beforeIndex; --i) {
			array[i] = array[i - 1];
		}

		array[beforeIndex] = w;
	}

}
