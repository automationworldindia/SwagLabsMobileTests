package com.saucedemo.app.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.MutableCapabilities;
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
            DRIVER.get().quit();
            DRIVER.remove();
        } else {
        	throw new Exception("Driver does not exist.");
        }
    }
	
	private static AppiumDriver getDriver(PlatformType platformType) throws MalformedURLException {
		AppiumDriver driver = null;
		String url = "http://" + Constants.APPIUM_SERVER_ADDRESS + ":" + Constants.APPIUM_SERVER_PORT;
		
		if (Constants.PLATFORM_TYPE == PlatformType.ANDROID) {
			driver = Constants.ENABLE_PERFECTO ? getPerfectoAndroidDriver() : getAndroidDriver(url);
		} else {
			driver = getIOSDriver(url);
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;    
    }
	
	private static AppiumDriver getAndroidDriver(String url) throws MalformedURLException {
		UiAutomator2Options options = new UiAutomator2Options();
		System.out.println(Constants.getDeviceName());
        options.setAvd(Constants.getDeviceName());
        options.setApp(System.getProperty("user.dir") + "/src/test/resources/" + Constants.APK_FILENAME);  
        options.setAppPackage(Constants.PKGNAME);
        options.setAppActivity(Constants.ACTIVITY);
        options.setNoReset(Constants.getNoReset()); //Every time you run the test, clear all saved data — like login info, settings, cache — and start the app brand new.    
        options.setAppWaitActivity("com.swaglabsmobileapp.*"); //Wait until an activity (screen) from the app appears.
        options.setAppWaitDuration(Duration.ofSeconds(10)); //Wait up to 10 seconds for the app to open before giving up.
        AppiumDriver driver = new AndroidDriver(new URL(url), options);
        return driver;
    }
	
	private static AppiumDriver getIOSDriver(String url) throws MalformedURLException {
		XCUITestOptions options = new XCUITestOptions();
        options.setDeviceName(Constants.getDeviceName());
        System.out.println(Constants.getPlatformVersion());        
        options.setPlatformVersion(Constants.getPlatformVersion());
        options.setApp(System.getProperty("user.dir") + "/src/test/resources/" + Constants.APP_FILENAME);
        options.setBundleId(Constants.BUNDLEID);
        options.setNoReset(Constants.getNoReset());
        AppiumDriver driver = new IOSDriver(new URL(url), options);
        return driver;
    }
	
	private static AppiumDriver getPerfectoAndroidDriver() throws MalformedURLException {
		//Device Capabilities
		MutableCapabilities capabilities = new MutableCapabilities();
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("appium:platformVersion", "16.0");
		capabilities.setCapability("appium:deviceName", "Google Pixel 6 Emulator");
		capabilities.setCapability("appium:automationName", "UiAutomator2");
		capabilities.setCapability("appium:app", "storage:filename=Android.SauceLabs.Mobile.Sample.app.2.7.1.apk"); 
		capabilities.setCapability("appium:noReset", false); //Every time you run the test, clear all saved data — like login info, settings, cache — and start the app brand new.
        capabilities.setCapability("appium:appWaitActivity", "com.swaglabsmobileapp.*"); //Wait until an activity (screen) from the app appears.
        capabilities.setCapability("appium:appWaitDuration", 10000); //Wait up to 10 seconds for the app to open before giving up.
		// Sauce options
        HashMap<String, Object> sauceOptions = new HashMap<String, Object>();
        sauceOptions.put("username", "oauth-automationworldindia-7132b");
        sauceOptions.put("accessKey", "eec77b1b-7148-445a-a875-7eb14c8fda07");
        sauceOptions.put("build", "appium-build-Real-Devices");
        sauceOptions.put("name", "Android Demo Test");
        //sauceOptions.put("appiumVersion", "latest");
        
        capabilities.setCapability("sauce:options", sauceOptions);
		
        AppiumDriver driver = new AndroidDriver(new URL("https://ondemand.eu-central-1.saucelabs.com:443/wd/hub"), capabilities);
        return driver;
	}

}
