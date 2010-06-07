package com.octo.gwt.test.internal.patcher.tools;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;

import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.QuoteElement;
import com.google.gwt.dom.client.ModElement;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableColElement;
import com.google.gwt.dom.client.TableSectionElement;
import com.octo.gwt.test.utils.TagAware;

@PatchClass({
	HeadingElement.class,
	ModElement.class,
	QuoteElement.class,
	TableCellElement.class,
	TableColElement.class,
	TableSectionElement.class
})
public class AutomaticTagSubclasser extends AutomaticElementSubclasser {

	private static final String OVERRIDE_TAG = "overrideTag";

	@Override
	public void createConstructor(ClassPool cp, CtClass subClazz, CtClass c) throws Exception {
		subClazz.addInterface(cp.get(TagAware.class.getCanonicalName()));
		
		CtField tagField = new CtField(cp.get(String.class.getCanonicalName()), OVERRIDE_TAG, subClazz);
		subClazz.addField(tagField);
		
		CtConstructor constructor = new CtConstructor(new CtClass[]{cp.get(String.class.getCanonicalName())}, subClazz);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("{");
		stringBuffer.append("super();");
		stringBuffer.append("this." + OVERRIDE_TAG + " = $1;");
		stringBuffer.append("}");
		constructor.setBody(stringBuffer.toString());
		subClazz.addConstructor(constructor);
		
		CtMethod getTag = new CtMethod(cp.get(String.class.getCanonicalName()), "getTag", new CtClass[]{}, subClazz);
		getTag.setBody("return " + OVERRIDE_TAG + ";");
		subClazz.addMethod(getTag);
	}
	
}
