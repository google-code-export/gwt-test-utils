package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.UIObject;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.ReflectionUtils;

public class UIObjectPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (matchWithArgs(m, "setElement", Element.class)) {
			return "setElement(" + ElementUtils.class.getCanonicalName() + ".castToUserElement($1))";
		} else if (matchWithArgs(m, "isVisible", Element.class)) {
			return callMethod("isVisible", "$1");
		} else if (matchWithArgs(m, "setVisible", Element.class, Boolean.TYPE)) {
			return callMethod("setVisible", "$1, $2");
		} else if (match(m, "updatePrimaryAndDependentStyleNames")) {
			return callMethod("updatePrimaryAndDependentStyleNames", "$1, $2");
		} else if (matchWithArgs(m, "getStyleName", Element.class)) {
			return callMethod("getStyleName", "$1");
		} else if (match(m, "replaceElement")) {
			return callMethod("replaceElement", "this, $1");
		} else if (match(m, "replaceNode")) {
			return callMethod("replaceNode", "$1, $2");
		} else if (matchWithArgs(m, "setStyleName", Element.class, String.class)) {
			return callMethod("setStyleName", "$1, $2");
		}

		return null;
	}

	public static boolean isVisible(Element elem) {
		String display = elem.getStyle().getProperty("display");

		return !(display != null && display.equals("none"));
	}

	public static void setVisible(Element elem, boolean visible) {
		String display = (visible) ? "" : "none";
		elem.getStyle().setProperty("display", display);
	}

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

	public static void replaceElement(UIObject uio, Element elem) {
		elem = ElementUtils.castToUserElement(elem);
		com.google.gwt.user.client.Element element = uio.getElement();
		if (element != null) {
			// replace this.element in its parent with elem.
			replaceNode(element, elem);
		}

		ReflectionUtils.setPrivateField(uio, "element", elem);
	}

	public static void replaceNode(Element node, Element newNode) {
		Node parent = node.getParentNode();

		if (parent != null) {
			parent.insertBefore(newNode, node);
			parent.removeChild(node);
		}
	}

	public static String getStyleName(Element e) {
		String s = DOM.getElementProperty(e.<com.google.gwt.user.client.Element> cast(), "className");
		return s == null ? "" : s;
	}

	public static void setStyleName(Element elem, String styleName) {
		DOM.setElementProperty(ElementUtils.castToUserElement(elem), "className", styleName);
	}

}
