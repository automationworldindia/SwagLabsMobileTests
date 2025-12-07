package com.saucedemo.app.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.MutableCapabilities;

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
            DRIVER.set(getDriver(Constants.get().PLATFORM_TYPE));
            System.out.println(String.format("******* Initialized driver for %s at thread id %s. ********", 
            		Constants.get().PLATFORM_TYPE, Thread.currentThread().getId()));
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
		String url = "http://" + Constants.get().APPIUM_SERVER_ADDRESS + ":" + Constants.get().APPIUM_SERVER_PORT;
		
		if (Constants.get().PLATFORM_TYPE == PlatformType.ANDROID) {
			driver = Constants.get().ENABLE_PERFECTO ? getPerfectoAndroidDriver() : getAndroidDriver(url);
		} else {
			driver = getIOSDriver(url);
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;    
    }
	
	private static AppiumDriver getAndroidDriver(String url) throws MalformedURLException {
		UiAutomator2Options options = new UiAutomator2Options();
        options.setAvd(Constants.getDeviceName());
        options.setApp(System.getProperty("user.dir") + "/src/test/resources/" + Constants.get().APK_FILENAME);  
        options.setAppPackage(Constants.get().PKGNAME);
        options.setAppActivity(Constants.get().ACTIVITY);
        options.setNoReset(Constants.getNoReset()); //Every time you run the test, clear all saved data — like login info, settings, cache — and start the app brand new.    
        options.setAppWaitActivity("com.swaglabsmobileapp.*"); //Wait until an activity (screen) from the app appears.
        options.setAppWaitDuration(Duration.ofSeconds(10)); //Wait up to 10 seconds for the app to open before giving up.
        System.out.println(String.format("****** Thread id %s: Connecting to url %s for %s. ******", 
        		Thread.currentThread().getId(), url, Constants.getDeviceName()));
        AppiumDriver driver = new AndroidDriver(new URL(url), options);
        return driver;
    }
	
	private static AppiumDriver getIOSDriver(String url) throws MalformedURLException {
		XCUITestOptions options = new XCUITestOptions();
        options.setDeviceName(Constants.getDeviceName());       
        options.setPlatformVersion(Constants.getPlatformVersion());
        options.setApp(System.getProperty("user.dir") + "/src/test/resources/" + Constants.get().APP_FILENAME);
        options.setBundleId(Constants.get().BUNDLEID);
        options.setNoReset(Constants.getNoReset());
        System.out.println(String.format("****** Thread id %s: Connecting to url %s for %s. ******", 
        		Thread.currentThread().getId(), url, Constants.getDeviceName()));
        AppiumDriver driver = new IOSDriver(new URL(url), options);
        return driver;
    }
	
	private static AppiumDriver getPerfectoAndroidDriver() throws MalformedURLException {
		//Device Capabilities
		MutableCapabilities capabilities = new MutableCapabilities();
		capabilities.setCapability("platformName", Constants.getPlatformName());
		capabilities.setCapability("appium:platformVersion", Constants.getPlatformVersion());
		capabilities.setCapability("appium:deviceName", Constants.getDeviceName());
		capabilities.setCapability("appium:automationName", Constants.getAutomationName());
		capabilities.setCapability("appium:app", "storage:filename=" + Constants.get().APK_FILENAME); 
		capabilities.setCapability("appium:noReset", Constants.getNoReset()); //Every time you run the test, clear all saved data — like login info, settings, cache — and start the app brand new.
        capabilities.setCapability("appium:appWaitActivity", "com.swaglabsmobileapp.*"); //Wait until an activity (screen) from the app appears.
        capabilities.setCapability("appium:appWaitDuration", 10000); //Wait up to 10 seconds for the app to open before giving up.
		// Sauce options
        HashMap<String, Object> sauceOptions = new HashMap<String, Object>();
        sauceOptions.put("username", Constants.get().PERFECTO_USERNAME);
        sauceOptions.put("accessKey", Constants.get().PERFECTO_ACCESSKEY);
        sauceOptions.put("build", "build-"+Constants.getPlatformName().toLowerCase()+"-"+Constants.getDeviceName().toLowerCase());
        sauceOptions.put("name", "Swag Labs Automated Tests");
        //sauceOptions.put("appiumVersion", "latest");
        
        capabilities.setCapability("sauce:options", sauceOptions);
		
        AppiumDriver driver = new AndroidDriver(new URL("https://ondemand.eu-central-1.saucelabs.com:443/wd/hub"), capabilities);
        return driver;
	}

}
