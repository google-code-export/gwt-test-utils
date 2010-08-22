package com.octo.gwt.test.internal.modifiers;

import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.JavaClass;

public class ClassSubstituer implements JavaClassModifier {

	private String substitutionClass;
	private String originalClass;

	public ClassSubstituer(String originalClass, String substitionClass) {
		this.originalClass = originalClass;
		this.substitutionClass = substitionClass;
	}

	public void modify(JavaClass classToModify) {
		if (classToModify.getClassName().equals(originalClass)) {
			return;
		}

		ConstantPool cp = classToModify.getConstantPool();
		String renamedClsString = substitutionClass.replaceAll("\\.", "\\/");
		String originalClsString = originalClass.replaceAll("\\.", "\\/");
		for (Constant c : cp.getConstantPool()) {
			if (c instanceof ConstantUtf8) {
				ConstantUtf8 stringConstant = (ConstantUtf8) c;

				if (stringConstant.getBytes().contains(originalClsString)) {
					stringConstant.setBytes(stringConstant.getBytes().replaceAll(originalClsString, renamedClsString));
				}
			}
		}
	}

}
