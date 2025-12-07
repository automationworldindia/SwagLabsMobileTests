package com.saucedemo.app.controller;

import java.io.File;

import com.saucedemo.app.utils.Constants;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumServiceManager {
    // ThreadLocal to ensure each thread has its own Appium server
    private static final ThreadLocal<AppiumDriverLocalService> service =
            new ThreadLocal<>();

    private AppiumServiceManager() {}

    private static AppiumDriverLocalService buildService(String portFromXML) {
        if (portFromXML != null) {
            Constants.get().APPIUM_SERVER_PORT = Integer.parseInt(portFromXML);
        }
        return new AppiumServiceBuilder()
                .withAppiumJS(new File(Constants.get().APPIUMJS_FILEPATH))
                .usingDriverExecutable(new File(Constants.get().NODE_FILEPATH))
                .withIPAddress(Constants.get().APPIUM_SERVER_ADDRESS)
                .usingPort(Constants.get().APPIUM_SERVER_PORT)
                .withArgument(() -> "--log-no-color")
                .build();
    }

    public static void startAppiumService(String portFromXML) {
        if (Constants.get().ENABLE_PERFECTO)
            return;
        if (service.get() == null) {
            AppiumDriverLocalService newService = buildService(portFromXML);
            service.set(newService);
        }

        AppiumDriverLocalService server = service.get();

        if (!server.isRunning()) {
            server.start();
            if (!server.isRunning()) {
                throw new RuntimeException("Failed to start Appium service on port: "
                        + Constants.get().APPIUM_SERVER_PORT);
            }
            System.out.println("Appium server started on: "
                    + Constants.get().APPIUM_SERVER_ADDRESS + ":" + Constants.get().APPIUM_SERVER_PORT);
        } else {
            System.out.println("Appium service already running for this thread on port: "
                    + Constants.get().APPIUM_SERVER_PORT);
        }
    }

    public static void stopAppiumService() {
        if (Constants.get().ENABLE_PERFECTO)
            return;
        AppiumDriverLocalService server = service.get();
        if (server == null) {
            System.out.println("Appium service was not initialized for this thread.");
            return;
        }
        if (server.isRunning()) {
            server.stop();
            System.out.println("Appium service stopped on port: "
                    + Constants.get().APPIUM_SERVER_PORT);
        }
        // Remove from ThreadLocal after stopping
        service.remove();
    }
}
