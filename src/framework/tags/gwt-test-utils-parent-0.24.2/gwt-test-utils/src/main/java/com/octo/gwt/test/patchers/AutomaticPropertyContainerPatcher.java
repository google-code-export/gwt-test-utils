package com.octo.gwt.test.patchers;

import java.lang.reflect.Modifier;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.AreaElement;
import com.google.gwt.dom.client.BRElement;
import com.google.gwt.dom.client.BaseElement;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DListElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.FieldSetElement;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.dom.client.FrameElement;
import com.google.gwt.dom.client.FrameSetElement;
import com.google.gwt.dom.client.HRElement;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.dom.client.LegendElement;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.dom.client.MapElement;
import com.google.gwt.dom.client.MetaElement;
import com.google.gwt.dom.client.OListElement;
import com.google.gwt.dom.client.ObjectElement;
import com.google.gwt.dom.client.OptGroupElement;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.dom.client.ParamElement;
import com.google.gwt.dom.client.PreElement;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.dom.client.TableCaptionElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.Text;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.dom.client.TitleElement;
import com.google.gwt.dom.client.UListElement;
import com.octo.gwt.test.internal.PatchGwtClassPool;
import com.octo.gwt.test.internal.utils.GwtPatcherUtils;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.internal.utils.PropertyContainerAware;
import com.octo.gwt.test.internal.utils.PropertyContainerHelper;

@PatchClass({ Text.class, AnchorElement.class, AreaElement.class, BaseElement.class, BodyElement.class, BRElement.class, ButtonElement.class,
		DivElement.class, DListElement.class, FieldSetElement.class, FrameElement.class, FrameSetElement.class, FormElement.class, HeadElement.class,
		HRElement.class, IFrameElement.class, ImageElement.class, LIElement.class, LabelElement.class, LegendElement.class, LinkElement.class,
		MapElement.class, MetaElement.class, ObjectElement.class, OptionElement.class, OListElement.class, OptGroupElement.class,
		ParagraphElement.class, ParamElement.class, PreElement.class, ScriptElement.class, StyleElement.class, SpanElement.class,
		TableCaptionElement.class, TableElement.class, TextAreaElement.class, TitleElement.class, UListElement.class })
public class AutomaticPropertyContainerPatcher extends AutomaticPatcher {

	private static final String PROPERTIES = "__PROPERTIES__";

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);

		CtClass pcType = PatchGwtClassPool.get().get(PropertyContainer.class.getName());
		CtField field = new CtField(pcType, PROPERTIES, c);
		field.setModifiers(Modifier.PRIVATE);
		c.addField(field);

		c.addInterface(PatchGwtClassPool.get().get(PropertyContainerAware.class.getName()));

		CtMethod getProperties = new CtMethod(pcType, "getProperties", new CtClass[] {}, c);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("{");
		stringBuffer.append("if (" + PROPERTIES + " == null) {");
		stringBuffer.append(PROPERTIES + " = " + PropertyContainerHelper.getConstructionCode() + ";");
		stringBuffer.append("}");
		stringBuffer.append("return " + PROPERTIES + ";");
		stringBuffer.append("}");
		getProperties.setBody(stringBuffer.toString());
		c.addMethod(getProperties);

	}

	public String getNewBody(CtMethod m) throws Exception {
		String superNewBody = super.getNewBody(m);
		if (superNewBody != null) {
			return superNewBody;
		}
		if (Modifier.isNative(m.getModifiers())) {
			String fieldName = GwtPatcherUtils.getPropertyName(m);
			if (fieldName != null) {
				if (m.getName().startsWith("set")) {
					return PropertyContainerHelper.getCodeSetProperty("this", fieldName, "$1");
				} else {
					return "return " + PropertyContainerHelper.getCodeGetProperty("this", fieldName, m.getReturnType());
				}
			}
		}
		return null;
	}

}
