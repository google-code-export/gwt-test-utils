package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.json.client.JSONArray;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class JSONArrayTest extends AbstractGWTTest {

	@Test
	public void checkPatchEmptyConstructor() throws Exception {

		PatchGWT.patch(JSONArray.class, new Patch[] { new PatchConstructor(null, new Class<?>[] {}), });

		JSONArray jsonArray = new JSONArray();

		Assert.assertNotNull("JSONArray instance should not be null", jsonArray);
	}

	@Test
	public void checkPatchConstructorWithCode() throws Exception {

		PatchGWT.patch(JSONArray.class, new Patch[] { new PatchConstructor("jsonArray = null", new Class<?>[] {}), });

		JSONArray jsonArray = new JSONArray();

		Assert.assertNotNull("JSONArray instance should not be null", jsonArray);
	}

}
