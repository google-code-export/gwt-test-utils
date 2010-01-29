package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.ui.UIObject;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.ReflectionUtils;
import com.octo.gwt.test17.internal.patcher.dom.ElementPatcher;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;
import com.octo.gwt.test17.ng.PatchType;
import com.octo.gwt.test17.ng.SubClassedHelper;

public class UIObjectPatcher extends AutomaticPatcher {

	@PatchMethod(value=PatchType.NEW_CODE_AS_STRING, args={Element.class})
	public static String setElement() {
		return "setElement(" + ElementUtils.class.getCanonicalName() + ".castToUserElement($1))";
	}
	
	@PatchMethod
	public static double extractLengthValue(UIObject uiObject, String s) {
		if ("auto".equals(s) || "inherit".equals(s) || "".equals(s)) {
			return 0;
		}
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9') {
				buffer.append(c);
			}
		}
		return Double.parseDouble(buffer.toString());
	}

	@PatchMethod(args={Element.class})
	public static boolean isVisible(Element elem) {
		String display = elem.getStyle().getProperty("display");

		return !(display != null && display.equals("none"));
	}

	@PatchMethod(args={Element.class, Boolean.class})
	public static void setVisible(Element elem, boolean visible) {
		String display = (visible) ? "" : "none";
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
		elem = ElementUtils.castToUserElement(elem);
		com.google.gwt.user.client.Element element = ReflectionUtils.getPrivateFieldValue(uiObject, "element");
		if (element != null) {
			// replace this.element in its parent with elem.
			replaceNode(uiObject, element, elem);
		}

		ReflectionUtils.setPrivateField(uiObject, "element", elem);
	}

	@PatchMethod
	public static void replaceNode(UIObject uiObject, Element node, Element newNode) {
		Node parent = node.getParentNode();

		if (parent != null) {
			parent.insertBefore(newNode, node);
			parent.removeChild(node);
		}
	}

	@PatchMethod(args={Element.class})
	public static String getStyleName(Element elem) {
		return SubClassedHelper.getProperty(elem, ElementPatcher.CLASSNAME_FIELD);
	}

	@PatchMethod(args={Element.class, String.class})
	public static void setStyleName(Element elem, String styleName) {
		SubClassedHelper.setProperty(elem, ElementPatcher.CLASSNAME_FIELD, styleName);
	}

}
