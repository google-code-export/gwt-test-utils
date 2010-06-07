package com.octo.gwt.test.internal.patcher.tools;

import javassist.CtClass;

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
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.dom.client.TitleElement;
import com.google.gwt.dom.client.UListElement;
import com.octo.gwt.test.ElementWrapper;

@PatchClass({
	AnchorElement.class,
	AreaElement.class,
	BaseElement.class,
	BodyElement.class,
	BRElement.class,
	ButtonElement.class,
	DivElement.class,
	DListElement.class,
	FieldSetElement.class,
	FrameElement.class,
	FrameSetElement.class,
	FormElement.class,
	HeadElement.class,
	HRElement.class,
	IFrameElement.class,
	ImageElement.class,
	LIElement.class,
	LabelElement.class,
	LegendElement.class,
	LinkElement.class,
	MapElement.class,
	MetaElement.class,
	ObjectElement.class,
	OptionElement.class,
	OListElement.class,
	OptGroupElement.class,
	ParagraphElement.class,
	ParamElement.class,
	PreElement.class,
	ScriptElement.class,
	StyleElement.class,
	SpanElement.class,
	TableCaptionElement.class,
	TableElement.class,
	TableRowElement.class,
	TextAreaElement.class,
	TitleElement.class,
	UListElement.class
})
public class AutomaticElementSubclasser extends AutomaticSubclasser {

	@PatchMethod(PatchType.NEW_CODE_AS_STRING)
	public static String as(CtClass clazz) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ if ($1 instanceof ");
		sb.append(ElementWrapper.class.getCanonicalName());
		sb.append(") { return (");
		sb.append(clazz.getName());
		sb.append(") ((");
		sb.append(ElementWrapper.class.getCanonicalName());
		sb.append(") $1).getWrappedElement(); } else { return (");
		sb.append(clazz.getName());
		sb.append(") $1; } }");

		return sb.toString();
	}
	
}
