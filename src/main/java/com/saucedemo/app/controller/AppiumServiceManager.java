package com.saucedemo.app.controller;

import java.io.File;

import com.saucedemo.app.utils.Constants;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumServiceManager {
	private static AppiumDriverLocalService service = null;
    
    private AppiumServiceManager() {}
    
    private static void initAppiumService() {
        File logFile = new File(System.getProperty("user.dir") + Constants.APPIUM_LOG_FILEPATH);
        service = new AppiumServiceBuilder()
                .withAppiumJS(new File(Constants.APPIUMJS_FILEPATH))
                .usingDriverExecutable(new File(Constants.NODE_FILEPATH))
                .withIPAddress(Constants.APPIUM_SERVER_ADDRESS)
                .usingPort(Constants.APPIUM_SERVER_PORT)
                .withArgument(() -> "--log-no-color")
                .withLogFile(logFile)
                .withLogOutput(null)
                .build();
    }
    
    public static void startAppiumService() {
        initAppiumService();
        if (service != null && !service.isRunning()) {
            service.start();
            if (!service.isRunning()) {
                throw new RuntimeException("Failed to start Appium service.");
            }
        } else if (service != null && service.isRunning()) {
            throw new RuntimeException("Appium service is already running.");
        } else {
            throw new RuntimeException("Appium service initialization failed.");
        }
    }
    
    public static void stopAppiumService() {
        if (service != null && service.isRunning()) {
            service.stop();
        } else if (service != null) {
            throw new RuntimeException("Appium service is not running or has already been stopped.");
        } else {
            throw new RuntimeException("Appium service was not initialized.");
        }
    }
}
