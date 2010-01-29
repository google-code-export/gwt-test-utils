package com.octo.gwt.test17.internal.patcher.dom;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test17.internal.patcher.tools.PatchType;

public class DOMPatcher extends AutomaticPatcher {

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createAnchor() {
		return "return createElement(\"a\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createButton() {
		return "return createElement(\"button\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createCaption() {
		return "return createElement(\"caption\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createCol() {
		return "return createElement(\"col\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createColGroup() {
		return "return createElement(\"colgroup\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createDiv() {
		return "return createElement(\"div\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createFieldSet() {
		return "return createElement(\"fieldset\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createForm() {
		return "return createElement(\"form\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createIFrame() {
		return "return createElement(\"iframe\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createImg() {
		return "return createElement(\"img\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createLabel() {
		return "return createElement(\"label\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createLegend() {
		return "return createElement(\"legend\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createOption() {
		return "return createElement(\"option\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createOptions() {
		return "return createElement(\"options\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createSpan() {
		return "return createElement(\"span\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createTable() {
		return "return createElement(\"table\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createTBody() {
		return "return createElement(\"tbody\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createTD() {
		return "return createElement(\"td\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createTextArea() {
		return "return createElement(\"textarea\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createTFoot() {
		return "return createElement(\"tfoot\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createTH() {
		return "return createElement(\"th\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createTHead() {
		return "return createElement(\"thead\");";
	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String createTR() {
		return "return createElement(\"tr\");";
	}

	@PatchMethod
	public static Element createElement(String tag) {
		com.google.gwt.dom.client.Element element = Document.get().createElement(tag);
		return ElementUtils.castToUserElement(element);
	}

	@PatchMethod
	public static Element createInputCheck() {
		com.google.gwt.dom.client.Element element = Document.get().createCheckInputElement();
		return ElementUtils.castToUserElement(element);
	}

	@PatchMethod
	public static Element createInputPassword() {
		com.google.gwt.dom.client.Element element = Document.get().createPasswordInputElement();
		return ElementUtils.castToUserElement(element);
	}

	@PatchMethod
	public static Element createInputRadio(String name) {
		com.google.gwt.dom.client.Element element = Document.get().createRadioInputElement(name);
		return ElementUtils.castToUserElement(element);
	}

	@PatchMethod
	public static Element createInputText() {
		com.google.gwt.dom.client.Element element = Document.get().createTextInputElement();
		return ElementUtils.castToUserElement(element);
	}

	@PatchMethod
	public static Element getParent(Element elem) {
		if (elem == null)
			return null;
		else
			return ElementUtils.castToUserElement(elem.getParentElement());
	}

	@PatchMethod
	public static Element getFirstChild(Element elem) {
		return ElementUtils.castToUserElement(elem.getFirstChildElement());
	}

	@PatchMethod(args = {})
	public static Element createSelect() {
		com.google.gwt.dom.client.Element element = Document.get().createSelectElement();
		return ElementUtils.castToUserElement(element);
	}

	@PatchMethod(args = { Boolean.class })
	public static Element createSelect(boolean multiple) {
		com.google.gwt.dom.client.Element element = Document.get().createSelectElement(multiple);
		return ElementUtils.castToUserElement(element);
	}

}
