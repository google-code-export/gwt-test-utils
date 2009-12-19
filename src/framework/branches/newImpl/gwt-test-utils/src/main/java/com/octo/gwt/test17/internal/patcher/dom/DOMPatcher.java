package com.octo.gwt.test17.internal.patcher.dom;

import javassist.CtMethod;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.patcher.AbstractPatcher;

public class DOMPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "createAnchor")) {
			return callMethod("createElement", "\"a\"");
		} else if (match(m, "createButton")) {
			return callMethod("createElement", "\"button\"");
		} else if (match(m, "createCaption")) {
			return callMethod("createElement", "\"caption\"");
		} else if (match(m, "createCol")) {
			return callMethod("createElement", "\"col\"");
		} else if (match(m, "createColGroup")) {
			return callMethod("createElement", "\"colgroup\"");
		} else if (match(m, "createDiv")) {
			return callMethod("createElement", "\"div\"");
		} else if (match(m, "createElement")) {
			return callMethod("createElement", "$1");
		} else if (match(m, "createFieldSet")) {
			return callMethod("createElement", "\"fieldset\"");
		} else if (match(m, "createForm")) {
			return callMethod("createElement", "\"form\"");
		} else if (match(m, "createIFrame")) {
			return callMethod("createElement", "\"iframe\"");
		} else if (match(m, "createImg")) {
			return callMethod("createElement", "\"img\"");
		} else if (match(m, "createInputCheck")) {
			return callMethod("createInputCheck");
		} else if (match(m, "createInputPassword")) {
			return callMethod("createInputPassword");
		} else if (match(m, "createInputRadio")) {
			return callMethod("createInputRadio", "$1");
		} else if (match(m, "createInputText")) {
			return callMethod("createInputText");
		} else if (match(m, "createLabel")) {
			return callMethod("createElement", "\"label\"");
		} else if (match(m, "createLegend")) {
			return callMethod("createElement", "\"legend\"");
		} else if (match(m, "createOption")) {
			return callMethod("createElement", "\"option\"");
		} else if (match(m, "createOptions")) {
			return callMethod("createElement", "\"options\"");
		} else if (matchWithArgs(m, "createSelect", new Class[0])) {
			return callMethod("createSelect");
		} else if (matchWithArgs(m, "createSelect", Boolean.TYPE)) {
			return callMethod("createSelect", "$1");
		} else if (match(m, "createSpan")) {
			return callMethod("createElement", "\"span\"");
		} else if (match(m, "createTable")) {
			return callMethod("createElement", "\"table\"");
		} else if (match(m, "createTBody")) {
			return callMethod("createElement", "\"tbody\"");
		} else if (match(m, "createTD")) {
			return callMethod("createElement", "\"td\"");
		} else if (match(m, "createTextArea")) {
			return callMethod("createElement", "\"textarea\"");
		} else if (match(m, "createTFoot")) {
			return callMethod("createElement", "\"tfoot\"");
		} else if (match(m, "createTH")) {
			return callMethod("createElement", "\"th\"");
		} else if (match(m, "createTHead")) {
			return callMethod("createElement", "\"thead\"");
		} else if (match(m, "createTR")) {
			return callMethod("createElement", "\"tr\"");
		} else if (match(m, "getParent")) {
			return callMethod("getParent", "$1");
		} else if (match(m, "getFirstChild")) {
			return callMethod("getFirstChild", "$1");
		}

		return null;
	}

	public static Element createElement(String tag) {
		com.google.gwt.dom.client.Element element = Document.get().createElement(tag);
		return ElementUtils.castToUserElement(element);
	}

	public static Element createInputCheck() {
		com.google.gwt.dom.client.Element element = Document.get().createCheckInputElement();
		return ElementUtils.castToUserElement(element);
	}

	public static Element createInputPassword() {
		com.google.gwt.dom.client.Element element = Document.get().createPasswordInputElement();
		return ElementUtils.castToUserElement(element);
	}

	public static Element createInputRadio(String name) {
		com.google.gwt.dom.client.Element element = Document.get().createRadioInputElement(name);
		return ElementUtils.castToUserElement(element);
	}

	public static Element createInputText() {
		com.google.gwt.dom.client.Element element = Document.get().createTextInputElement();
		return ElementUtils.castToUserElement(element);
	}

	public static Element createSelect() {
		com.google.gwt.dom.client.Element element = Document.get().createSelectElement();
		return ElementUtils.castToUserElement(element);
	}

	public static Element createSelect(boolean multiple) {
		com.google.gwt.dom.client.Element element = Document.get().createSelectElement(multiple);
		return ElementUtils.castToUserElement(element);
	}

	public static Element getParent(Element elem) {
		if (elem == null)
			return null;
		else
			return ElementUtils.castToUserElement(elem.getParentElement());
	}

	public static Element getFirstChild(Element elem) {
		return ElementUtils.castToUserElement(elem.getFirstChildElement());
	}

}
