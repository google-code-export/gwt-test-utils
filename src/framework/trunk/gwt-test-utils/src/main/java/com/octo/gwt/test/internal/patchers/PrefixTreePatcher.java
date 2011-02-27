package com.octo.gwt.test.internal.patchers;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import com.octo.gwt.test.internal.utils.PropertyContainerHelper;
import com.octo.gwt.test.patchers.AutomaticPropertyContainerPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

@PatchClass(classes = { "com.google.gwt.user.client.ui.PrefixTree" })
public class PrefixTreePatcher extends AutomaticPropertyContainerPatcher {

	private static final String PREFIXES_SET = "PREFIXES_SET";

	@PatchMethod
	public static boolean add(Object prefixTree, String s) {
		Set<String> set = PropertyContainerHelper.getProperty(prefixTree, PREFIXES_SET);
		if (set == null) {
			set = new TreeSet<String>();
			PropertyContainerHelper.setProperty(prefixTree, PREFIXES_SET, set);
		}

		return set.add(s);
	}

	@PatchMethod
	public static void suggestImpl(Object prefixTree, String search, String prefix, Collection<String> output, int limit) {

		Set<String> set = PropertyContainerHelper.getProperty(prefixTree, PREFIXES_SET);
		if (set == null)
			return;

		for (String record : set) {
			if (search == null || record.contains(search.trim().toLowerCase())) {
				output.add(record);
			}
		}
	}

	@PatchMethod
	public static void clear(Object prefixTree) {
		GwtTestReflectionUtils.setPrivateFieldValue(prefixTree, "size", 0);
	}

}
