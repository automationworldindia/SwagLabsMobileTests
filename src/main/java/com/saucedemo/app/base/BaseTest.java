package com.saucedemo.app.base;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.saucedemo.app.controller.AppiumDriverManager;
import com.saucedemo.app.controller.AppiumServiceManager;
import com.saucedemo.app.utils.Constants;
import com.saucedemo.app.utils.JsonUtils;
import com.saucedemo.app.utils.PropertyUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public abstract class BaseTest {    
    protected AppiumDriver driver = null;
	
	@BeforeSuite
	public void suiteSetUp() {
		try {
			PropertyUtils.loadConfigProperties();
			//System.out.println(Config.getProperty(""));
			JsonUtils.loadDeviceProfile(Constants.DEVICE_PROFILE);
			AppiumServiceManager.startAppiumService();
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@BeforeMethod
	public void testSetup(Method method) {
		try {
			driver = AppiumDriverManager.getDriver();
		} catch (MalformedURLException e) {
			Assert.fail("Exception while initializing drivers: " + e.getMessage());
		}
	}
	
	@AfterMethod
	public void testTeardown() {
		try {
			terminateApp(driver);
			AppiumDriverManager.killDriver();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@AfterSuite
	public void suiteTearDown() {
		AppiumServiceManager.stopAppiumService();
	}
	
	private void terminateApp(AppiumDriver driver) {
		if (driver instanceof AndroidDriver) {
			((AndroidDriver) driver).terminateApp(Constants.PKGNAME);
		} else {
			((IOSDriver) driver).terminateApp(Constants.BUNDLEID);
		}
	}

}
