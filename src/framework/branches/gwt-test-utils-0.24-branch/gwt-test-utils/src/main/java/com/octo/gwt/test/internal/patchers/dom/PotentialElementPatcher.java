package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.PotentialElement;
import com.google.gwt.user.client.ui.UIObject;
import com.octo.gwt.test.exceptions.GwtTestPatchException;
import com.octo.gwt.test.internal.utils.PropertyContainerHelper;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(PotentialElement.class)
public class PotentialElementPatcher {

	private static final String POTENTIALELEMENT_TAG = "POTENTIALELEMENT_TAG";
	private static final String POTENTIALELEMENT_UIOBJECT = "POTENTIALELEMENT_UIOBJECT";
	private static final String POTENTIALELEMENT_WRAPPED_ELEMENT = "POTENTIALELEMENT_WRAPPED_ELEMENT";

	@PatchMethod
	public static PotentialElement build(UIObject o, String tagName) {
		PotentialElement e;
		try {
			e = PotentialElement.class.newInstance();
		} catch (Exception ex) {
			throw new GwtTestPatchException(
					"Error while trying to instanciate "
							+ PotentialElement.class.getSimpleName() + " class",
					ex);
		}
		Element wrappedElement = NodeFactory.createElement(tagName);
		PropertyContainerHelper.setProperty(e, POTENTIALELEMENT_TAG, true);
		PropertyContainerHelper.setProperty(e,
				POTENTIALELEMENT_WRAPPED_ELEMENT, wrappedElement);
		PropertyContainerHelper.setProperty(e, POTENTIALELEMENT_UIOBJECT, o);

		return e;
	}

	@PatchMethod
	public static boolean isPotential(JavaScriptObject o) {
		return PropertyContainerHelper.getBoolean(o, POTENTIALELEMENT_TAG);
	}

	@PatchMethod
	public static Element resolve(Element maybePotential) {
		if (isPotential(maybePotential)) {
			UIObject o = PropertyContainerHelper.getObject(maybePotential,
					POTENTIALELEMENT_UIOBJECT);
			GwtReflectionUtils.callPrivateMethod(o, "resolvePotentialElement");

			return PropertyContainerHelper.getObject(maybePotential,
					POTENTIALELEMENT_WRAPPED_ELEMENT);

		} else {
			return maybePotential;
		}
	}

	@PatchMethod
	public static Element setResolver(PotentialElement pe, UIObject resolver) {
		PropertyContainerHelper.setProperty(pe, POTENTIALELEMENT_UIOBJECT,
				resolver);

		return pe;

	}

}
