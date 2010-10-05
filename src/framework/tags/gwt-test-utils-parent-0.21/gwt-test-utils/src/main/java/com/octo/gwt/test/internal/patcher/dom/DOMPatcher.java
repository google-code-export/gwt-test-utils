package com.octo.gwt.test.internal.patcher.dom;

import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;
import com.octo.gwt.test.patcher.PatchType;

@PatchClass(DOM.class)
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
	public static Element getFirstChild(Element elem) {
		Node firstChild = elem.getFirstChildElement();
		if (firstChild != null) {
			return firstChild.cast();
		} else {
			return null;
		}
	}
}
