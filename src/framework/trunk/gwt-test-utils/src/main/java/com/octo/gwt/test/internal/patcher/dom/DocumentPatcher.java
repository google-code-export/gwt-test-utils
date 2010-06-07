package com.octo.gwt.test.internal.patcher.dom;

import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.internal.patcher.tools.AutomaticSubclasser;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.patcher.tools.SubClassedHelper;

@PatchClass(Document.class)
public class DocumentPatcher extends AutomaticSubclasser {

	private static int ID = 0;
	
	public static final String BODY_PROPERTY = "Body";

	@PatchMethod
	public static String getCompatMode(Document document) {
		return "toto";
	}
	
	@PatchMethod
	public static Document get() {
		return NodeFactory.getDocument();
	}

	@PatchMethod
	public static Text createTextNode(Document document, String data) {
		return NodeFactory.createTextNode(data);
	}
	
	@PatchMethod
	public static  BodyElement getBody(Document document) {
		Element e = document.getDocumentElement();
		return SubClassedHelper.getProperty(e, NodeFactory.BODY_ELEMENT);
	}
	
	@PatchMethod
	public static String getDomain(Document document) {
		return null;
	}
	
	@PatchMethod
	public static String getReferrer(Document document) {
		return "";
	}

	@PatchMethod
	public static String createUniqueId(Document document) {
		ID++;
		return "elem_" + Long.toString(ID);
	}

}
