package com.octo.gxt.test.internal.patchers;

import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(classes = { "com.extjs.gxt.desktop.client.StartButton" })
public class StartButtonPatcher extends AutomaticPatcher {

	@PatchMethod
	public static String getButtonTemplate(Object startButton) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"x-btn-wrap\">");
		sb.append("<tbody><tr>");
		sb.append("<td class=\"ux-startbutton-left\"><i>&#160;</i></td>");
		sb.append("<td class=\"ux-startbutton-center\"><em unselectable=\"on\"><button class=\"x-btn-text\" type=\"{1}\" style=\"height:30px;\">{0}</button></em></td>");
		sb.append("<td class=\"ux-startbutton-right\"><i>&#160;</i></td>");
		sb.append("</tr></tbody></table>");
		return sb.toString();
	}
}
