package com.saucedemo.app.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.testng.Assert;

import com.saucedemo.app.utils.Constants;
import com.saucedemo.app.utils.PlatformType;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

public class AppiumDriverManager {
	
	private static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<>();
	
	private AppiumDriverManager() {}
	
	public static AppiumDriver getDriver() throws MalformedURLException {
        if (DRIVER.get() == null) {
            DRIVER.set(getDriver(Constants.PLATFORM_TYPE));
        }
        return DRIVER.get();
    }
	
	public static void killDriver() throws Exception {
        if (DRIVER.get() != null) {
            //DRIVER.get().quit();
            DRIVER.remove();
        } else {
        	throw new Exception("Driver does not exist.");
        }
    }
	
	private static AppiumDriver getDriver(PlatformType platformType) throws MalformedURLException {
		AppiumDriver driver = null;
		String url = "http://" + Constants.APPIUM_SERVER_ADDRESS + ":" + Constants.APPIUM_SERVER_PORT;
		
		if (Constants.PLATFORM_TYPE == PlatformType.ANDROID) {
			driver = getAndroidDriver(url);
		} else {
			driver = getIOSDriver(url);
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;    
    }
	
	private static AppiumDriver getAndroidDriver(String url) throws MalformedURLException {
		UiAutomator2Options options = new UiAutomator2Options();
        options.setAvd("Pixel_7");
        options.setApp(System.getProperty("user.dir") + "/src/test/resources/" + Constants.APK_FILENAME);  
        options.setAppPackage(Constants.PKGNAME);
        options.setAppActivity(Constants.ACTIVITY);
        AppiumDriver driver = new AndroidDriver(new URL(url), options);
        return driver;
    }
	
	private static AppiumDriver getIOSDriver(String url) throws MalformedURLException {
		XCUITestOptions options = new XCUITestOptions();
        options.setDeviceName("iPhone 15");
        options.setPlatformVersion("17.0");
        options.setApp(System.getProperty("user.dir") + "/src/test/resources/" + Constants.APP_FILENAME);
        options.setBundleId(Constants.BUNDLEID);
        AppiumDriver driver = new IOSDriver(new URL(url), options);
        return driver;
    }

}
