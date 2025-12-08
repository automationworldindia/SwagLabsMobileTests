package com.saucedemo.app.base;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.saucedemo.app.controller.AppiumDriverManager;
import com.saucedemo.app.controller.AppiumServiceManager;
import com.saucedemo.app.utils.Constants;
import com.saucedemo.app.utils.JsonUtils;
import com.saucedemo.app.utils.PlatformType;
import com.saucedemo.app.utils.PropertyUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public abstract class BaseTest {    
    protected AppiumDriver driver = null;
	
	@BeforeTest(alwaysRun=true)
	@Parameters({"platform", "profile", "port"})
	public void suiteSetUp(@Optional String platform, @Optional String profile, @Optional String port) {
		try {
			System.out.println("******** Test Suite started at thread#: " + Thread.currentThread().getId() + " ********");
			loadTestConfiguration(platform, profile);
			AppiumServiceManager.startAppiumService(port);
		} catch (Exception e) {
			System.out.println(String.format("************** Thread %s: Excpetion occurred during driver initialization: %s **************", 
					Thread.currentThread().getId(), e.getMessage()));
		}
	}
	
	@BeforeMethod(alwaysRun=true)
	public void testSetup(Method method) {
		try {
			System.out.println(String.format("******** Thread %s: Initializing driver. ********", Thread.currentThread().getId()));
			driver = AppiumDriverManager.getDriver();
			System.out.println(String.format("******** Thread %s: Initialized driver. ********", Thread.currentThread().getId()));
		} catch (MalformedURLException e) {
			Assert.fail("************** Exception while initializing drivers: " + e.getMessage() + " *****************");
		}
	}
	
	@AfterMethod(alwaysRun=true)
	public void testTeardown() {
		try {
			terminateApp(driver);	
			AppiumDriverManager.killDriver();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@AfterTest(alwaysRun=true)
	public void suiteTearDown() {
		System.out.println(String.format("******** Thread id %s: Stopping Appium service for %s. ********", 
				Thread.currentThread().getId(), Constants.get().PLATFORM_TYPE));
		AppiumServiceManager.stopAppiumService();
	}
	
	private void terminateApp(AppiumDriver driver) {
		if (!Constants.get().ENABLE_PERFECTO) {
			if (driver instanceof AndroidDriver) {
				((AndroidDriver) driver).terminateApp(Constants.get().PKGNAME);
			} else {
				((IOSDriver) driver).terminateApp(Constants.get().BUNDLEID);
			}
		}
	}
	
	private void loadTestConfiguration(String platformFromXML, String profilefromXML) throws IOException {
		PropertyUtils.loadConfigProperties();
		if (platformFromXML != null) {
			Constants.get().PLATFORM_TYPE = PlatformType.valueOf(platformFromXML.trim().toUpperCase());
		}
		if (profilefromXML != null) {
			Constants.get().DEVICE_PROFILE = profilefromXML.trim();
		}
		System.out.println(String.format("********* Running at thread %s using %s on %s ***********", 
				Thread.currentThread().getId(), Constants.get().DEVICE_PROFILE, Constants.get().PLATFORM_TYPE));
		JsonUtils.loadDeviceProfile(Constants.get().DEVICE_PROFILE);
	}

}
