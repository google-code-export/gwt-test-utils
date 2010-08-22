package com.octo.gwt.test.patcher;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;

import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.ModElement;
import com.google.gwt.dom.client.QuoteElement;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableColElement;
import com.google.gwt.dom.client.TableSectionElement;
import com.octo.gwt.test.internal.PatchGwtClassPool;
import com.octo.gwt.test.utils.TagAware;

@PatchClass({ HeadingElement.class, ModElement.class, QuoteElement.class, TableCellElement.class, TableColElement.class, TableSectionElement.class })
public class AutomaticTagSubclasser extends AutomaticPropertyContainerPatcher {

	private static final String OVERRIDE_TAG = "overrideTag";

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);

		ClassPool cp = PatchGwtClassPool.get();

		c.addInterface(cp.get(TagAware.class.getName()));

		CtField tagField = new CtField(cp.get(String.class.getName()), OVERRIDE_TAG, c);
		c.addField(tagField);

		CtConstructor constructor = new CtConstructor(new CtClass[] { cp.get(String.class.getName()) }, c);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("{");
		stringBuffer.append("super();");
		stringBuffer.append("this." + OVERRIDE_TAG + " = $1;");
		stringBuffer.append("}");
		constructor.setBody(stringBuffer.toString());
		c.addConstructor(constructor);

		CtMethod getTag = new CtMethod(cp.get(String.class.getName()), "getTag", new CtClass[] {}, c);
		getTag.setBody("return " + OVERRIDE_TAG + ";");
		c.addMethod(getTag);
	}

}
