package com.octo.gwt.test.internal.patchers;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.ui.UIObject;
import com.octo.gwt.test.internal.patchers.dom.ElementPatcher;
import com.octo.gwt.test.internal.utils.PropertyContainerHelper;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(UIObject.class)
public class UIObjectPatcher extends AutomaticPatcher {

	@PatchMethod
	public static double extractLengthValue(UIObject uiObject, String s) {
		if ("auto".equals(s) || "inherit".equals(s) || "".equals(s)) {
			return 0;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9') {
				buffer.append(c);
			}
		}
		return Double.parseDouble(buffer.toString());
	}

	@PatchMethod
	public static boolean isVisible(Element elem) {
		String display = elem.getStyle().getProperty("display");

		return !display.equals("none");
	}

	@PatchMethod
	public static void setVisible(Element elem, boolean visible) {
		String display = visible ? "" : "none";
		elem.getStyle().setProperty("display", display);
	}

	@PatchMethod
	public static void updatePrimaryAndDependentStyleNames(Element elem, String newPrimaryStyle) {

		String[] classes = getStyleName(elem).split(" ");

		if (classes.length < 1) {
			setStyleName(elem, newPrimaryStyle);
		} else {
			String oldPrimaryStyle = classes[0];
			int oldPrimaryStyleLen = oldPrimaryStyle.length();

			classes[0] = newPrimaryStyle;
			for (int i = 1; i < classes.length; i++) {
				String name = classes[i];
				if (name.length() > oldPrimaryStyleLen && name.charAt(oldPrimaryStyleLen) == '-' && name.indexOf(oldPrimaryStyle) == 0) {
					classes[i] = newPrimaryStyle + name.substring(oldPrimaryStyleLen);
				}
			}

			StringBuilder sb = new StringBuilder();
			for (String name : classes) {
				sb.append(name + " ");
			}

			setStyleName(elem, sb.toString().trim());
		}
	}

	@PatchMethod
	public static void replaceElement(UIObject uiObject, Element elem) {
		Element element = GwtReflectionUtils.getPrivateFieldValue(uiObject, "element");
		if (element != null) {
			// replace this.element in its parent with elem.
			replaceNode(uiObject, element, elem);
		}

		GwtReflectionUtils.setPrivateFieldValue(uiObject, "element", elem);
	}

	@PatchMethod
	public static void replaceNode(UIObject uiObject, Element node, Element newNode) {
		Node parent = node.getParentNode();

		if (parent != null) {
			parent.insertBefore(newNode, node);
			parent.removeChild(node);
		}
	}

	@PatchMethod
	public static String getStyleName(Element elem) {
		return PropertyContainerHelper.getProperty(elem, ElementPatcher.CLASSNAME_FIELD);
	}

	@PatchMethod
	public static void setStyleName(Element elem, String styleName) {
		PropertyContainerHelper.setProperty(elem, ElementPatcher.CLASSNAME_FIELD, styleName);
	}

}
