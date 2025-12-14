package com.saucedemo.app.base;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import com.saucedemo.app.controller.LoggerManager;
import com.saucedemo.app.controller.ReportManager;
import org.testng.Assert;
import org.testng.annotations.*;

import com.saucedemo.app.controller.AppiumDriverManager;
import com.saucedemo.app.controller.AppiumServiceManager;
import com.saucedemo.app.utils.Constants;
import com.saucedemo.app.utils.JsonUtils;
import com.saucedemo.app.utils.PlatformType;
import com.saucedemo.app.utils.PropertyUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

@Listeners({io.qameta.allure.testng.AllureTestNg.class,
		    com.saucedemo.app.controller.ReportManager.class})
public abstract class BaseTest {    
    protected AppiumDriver driver = null;
	
	@BeforeTest(alwaysRun=true)
	@Parameters({"platform", "profile", "port"})
	public void suiteSetUp(@Optional String platform, @Optional String profile, @Optional String port) {
		try {
			LoggerManager.info("******** Test Suite started at thread#: " + Thread.currentThread().getId() + " ********");
			loadTestConfiguration(platform, profile);
			AppiumServiceManager.startAppiumService(port);
		} catch (Exception e) {
			LoggerManager.error(String.format("************** Thread %s: Excpetion occurred during driver initialization: %s **************",
					Thread.currentThread().getId(), e.getMessage()));
		}
	}
	
	@BeforeMethod(alwaysRun=true)
	public void testSetup(Method method) {
		try {
			LoggerManager.info(
					String.format("******** Thread %s: Initializing driver. ********", Thread.currentThread().getId()));
			driver = AppiumDriverManager.getDriver();
		} catch (MalformedURLException e) {
			LoggerManager.error("************** Exception while initializing drivers: " + e.getMessage() + " *****************");
			Assert.fail("************** Exception while initializing drivers: " + e.getMessage() + " *****************");
		}
	}
	
	@AfterMethod(alwaysRun=true)
	public void testTeardown() {
		try {
			terminateApp(driver);	
			AppiumDriverManager.killDriver();
		} catch (Exception e) {
			LoggerManager.error(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}
	
	@AfterTest(alwaysRun=true)
	public void suiteTearDown() {
		LoggerManager.error(String.format("******** Thread id %s: Stopping Appium service for %s. ********",
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
		LoggerManager.error(
				String.format("********* Running at thread %s using %s on %s ***********",
				Thread.currentThread().getId(), Constants.get().DEVICE_PROFILE, Constants.get().PLATFORM_TYPE));
		JsonUtils.loadDeviceProfile(Constants.get().DEVICE_PROFILE);
	}
}
