package com.octo.gwt.test.internal;

import java.lang.reflect.Modifier;
import java.util.Hashtable;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.NotFoundException;

import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.Event;
import com.octo.gwt.test.internal.dom.UserElement;
import com.octo.gwt.test.internal.overrides.OverrideEvent;
import com.octo.gwt.test.internal.overrides.OverrideIFrameElement;
import com.octo.gwt.test.internal.overrides.OverrideLabelElement;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.internal.overrides.OverrideOptionElement;
import com.octo.gwt.test.internal.overrides.OverrideSelectElement;

public class PatchDOMImpl {

	private static Hashtable<String, Class<?>> classCacheElement = new Hashtable<String, Class<?>>();

	public static void selectAdd(SelectElement select, OptionElement option, OptionElement before) {
		OverrideSelectElement s = OverrideSelectElement.overrideCast(select);
		OverrideOptionElement o = OverrideOptionElement.overrideCast(option);
		if (before == null) {
			s.getOverrideOptionList().add(o);
			return;
		}
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public static NodeList<OptionElement> selectGetOptions(SelectElement select) {
		OverrideSelectElement s = OverrideSelectElement.overrideCast(select);
		OverrideNodeList<OptionElement> l = new OverrideNodeList<OptionElement>();
		for (OverrideOptionElement e : s.getOverrideOptionList()) {
			l.getOverrideList().add(e);
		}
		return l;
	}

	public static String eventGetType(NativeEvent nativeEvent) {
		OverrideEvent event = OverrideEvent.overrideCast(nativeEvent);
		switch (event.getOverrideType()) {
		case Event.ONBLUR:
			return "blur";
		case Event.ONCHANGE:
			return "change";
		case Event.ONCLICK:
			return "click";
		case Event.ONDBLCLICK:
			return "dblclick";
		case Event.ONFOCUS:
			return "focus";
		case Event.ONKEYDOWN:
			return "keydown";
		case Event.ONKEYPRESS:
			return "keypress";
		case Event.ONKEYUP:
			return "keyup";
		case Event.ONLOAD:
			return "load";
		case Event.ONLOSECAPTURE:
			return "losecapture";
		case Event.ONMOUSEDOWN:
			return "mousedown";
		case Event.ONMOUSEMOVE:
			return "mousemove";
		case Event.ONMOUSEOUT:
			return "mouseout";
		case Event.ONMOUSEOVER:
			return "mouseover";
		case Event.ONMOUSEUP:
			return "mouseup";
		case Event.ONSCROLL:
			return "scroll";
		case Event.ONERROR:
			return "error";
		case Event.ONMOUSEWHEEL:
			return "mousewheel";
		case Event.ONCONTEXTMENU:
			return "contextmenu";
		default:
			throw new RuntimeException("Cannot get the String type of event with code [" + event.getOverrideType() + "]");
		}
	}

	public static boolean eventGetAltKey(NativeEvent nativeEvent) {
		OverrideEvent event = OverrideEvent.overrideCast(nativeEvent);

		return event.isOverrideAltKey();
	}

	public static int eventGetTypeInt(String type) {
		if (type.equals("blur")) {
			return Event.ONBLUR;
		} else if (type.equals("change")) {
			return Event.ONCHANGE;
		} else if (type.equals("click")) {
			return Event.ONCLICK;
		} else if (type.equals("dblclick")) {
			return Event.ONDBLCLICK;
		} else if (type.equals("focus")) {
			return Event.ONFOCUS;
		} else if (type.equals("keydown")) {
			return Event.ONKEYDOWN;
		} else if (type.equals("keypress")) {
			return Event.ONKEYPRESS;
		} else if (type.equals("keyup")) {
			return Event.ONKEYUP;
		} else if (type.equals("load")) {
			return Event.ONLOAD;
		} else if (type.equals("losecapture")) {
			return Event.ONLOSECAPTURE;
		} else if (type.equals("mousedown")) {
			return Event.ONMOUSEDOWN;
		} else if (type.equals("mousemove")) {
			return Event.ONMOUSEMOVE;
		} else if (type.equals("mouseout")) {
			return Event.ONMOUSEOUT;
		} else if (type.equals("mouseover")) {
			return Event.ONMOUSEOVER;
		} else if (type.equals("mouseup")) {
			return Event.ONMOUSEUP;
		} else if (type.equals("scroll")) {
			return Event.ONSCROLL;
		} else if (type.equals("error")) {
			return Event.ONERROR;
		} else if (type.equals("mousewheel")) {
			return Event.ONMOUSEWHEEL;
		} else if (type.equals("contextmenu")) {
			return Event.ONCONTEXTMENU;
		}

		throw new RuntimeException("Unable to convert DOM Event \"" + type + "\" to an integer");
	}

	public static ButtonElement createButtonElement(Document doc, String type) {
		ButtonElement result = (ButtonElement) createElement("button");
		return result;
	}

	public static boolean isOrHasChild(Node parent, Node child) {
		if (parent == child) {
			return true;
		}
		UserElement parentElem = UserElement.overrideCast(parent);
		UserElement childElem = UserElement.overrideCast(child);

		return parentElem.getOverrideList().contains(childElem);
	}

	public static Element createElement(String tag) {
		if ("option".equals(tag)) {
			return new OverrideOptionElement();
		}
		if ("select".equals(tag)) {
			return new OverrideSelectElement(new UserElement(null));
		}
		if ("iframe".equals(tag)) {
			return new OverrideIFrameElement(new UserElement(null));
		}
		if ("label".equals(tag)) {
			return new OverrideLabelElement(new UserElement(null));
		}
		//		if ("form".equals(tag)) {
		//			return new OverrideFormElement();
		//		}
		try {
			Class<?> clazz = classCacheElement.get(tag);
			if (clazz == null) {
				ClassPool cp = ClassPool.getDefault();
				CtClass superClazz = null;
				try {
					superClazz = cp.get("com.google.gwt.dom.client." + format(tag) + "Element");
				} catch (NotFoundException e) {
					if ("tbody".equals(tag)) {
						superClazz = cp.get("com.google.gwt.dom.client.TableSectionElement");
					} else if ("tr".equals(tag)) {
						superClazz = cp.get("com.google.gwt.dom.client.TableRowElement");
					} else if ("td".equals(tag)) {
						superClazz = cp.get("com.google.gwt.dom.client.TableCellElement");
					} else if ("colgroup".equals(tag)) {
						superClazz = cp.get("com.google.gwt.dom.client.TableColElement");
					} else if ("col".equals(tag)) {
						superClazz = cp.get("com.google.gwt.dom.client.TableColElement");
					} else if ("a".equals(tag)) {
						superClazz = cp.get("com.google.gwt.dom.client.AnchorElement");
					} else if ("textarea".equals(tag)) {
						superClazz = cp.get("com.google.gwt.dom.client.TextAreaElement");
					} else if (tag.matches("^h[123456]$")) {
						superClazz = cp.get("com.google.gwt.dom.client.HeadingElement");
					} else if (tag.matches("ul")) {
						superClazz = cp.get("com.google.gwt.dom.client.UListElement");
					} else if (tag.matches("li")) {
						superClazz = cp.get("com.google.gwt.dom.client.LIElement");
					}
				}
				if (superClazz == null) {
					throw new RuntimeException("Super class not found for tag " + tag);
				}
				CtClass c = cp.makeClass("tmp.classCache." + tag);
				c.setSuperclass(superClazz);
				CtField f = new CtField(cp.get(String.class.getCanonicalName()), "overrideTagName", c);
				f.setModifiers(Modifier.PUBLIC + Modifier.FINAL + Modifier.STATIC);
				c.addField(f, "\"" + tag + "\"");
				CtConstructor constructorz = new CtConstructor(new CtClass[] {}, c);
				constructorz.setBody(";");
				c.addConstructor(constructorz);
				clazz = c.toClass();
				classCacheElement.put(tag, clazz);
			}
			return (Element) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Unable to compile class for tag " + tag, e);
		}
	}

	private static String format(String s) {
		String res = s.substring(0, 1).toUpperCase();
		res += s.substring(1);
		return res;
	}

}
