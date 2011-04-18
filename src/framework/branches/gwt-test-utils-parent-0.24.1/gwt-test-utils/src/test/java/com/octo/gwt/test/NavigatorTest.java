package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.Window.Navigator;

public class NavigatorTest extends AbstractGwtTest {

	@Test
	public void checkGetAppCodeName() {
		String appCodeName = Navigator.getAppCodeName();
		Assert.assertNotNull(appCodeName);
	}

	@Test
	public void checkGetAppName() {
		String appName = Navigator.getAppName();
		Assert.assertNotNull(appName);
	}

	@Test
	public void checkGetAppVersion() {
		String appVersion = Navigator.getAppVersion();
		Assert.assertNotNull(appVersion);
	}

	@Test
	public void checkGetPlatform() {
		String platform = Navigator.getPlatform();
		Assert.assertNotNull(platform);
	}

	@Test
	public void checkGetUserAgent() {
		// Test
		String userAgent = Navigator.getUserAgent();
		Assert.assertNotNull(userAgent);
	}

	@Test
	public void checkIsCookiesEnabled() {
		boolean isCookiesEnabled = Navigator.isCookieEnabled();
		Assert.assertTrue(isCookiesEnabled);
	}

	@Test
	public void checkIsJavaEnabled() {
		boolean isJavaEnabled = Navigator.isJavaEnabled();
		Assert.assertTrue(isJavaEnabled);
	}

}
