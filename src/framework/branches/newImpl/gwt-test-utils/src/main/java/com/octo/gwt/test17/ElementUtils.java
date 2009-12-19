package com.octo.gwt.test17;

import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.Element;

public class ElementUtils {

	public static Element castToUserElement(com.google.gwt.dom.client.Element element) {
		if (element == null) {
			return null;
		}
		if (element instanceof Element) {
			return (Element) element;
		} else {
			return new ElementWrapper(element);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Node> T castToDomElement(Node node) {
		if (node == null) {
			return null;
		}
		if (node instanceof ElementWrapper) {
			return (T) ((ElementWrapper) node).getWrappedElement();
		}

		return (T) node;
	}
}
