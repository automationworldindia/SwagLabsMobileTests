package com.saucedemo.app.utils;

import static com.saucedemo.app.utils.PropertyUtils.Config;

public interface Constants {
	//IOS App Details
	public static final String BUNDLEID = Config.getProperty("app.bundle.id");
	public static final String APP_FILENAME = Config.getProperty("app.file.path");
	//Android App Details
	public static final String PKGNAME = Config.getProperty("apk.app.package");
	public static final String ACTIVITY = Config.getProperty("apk.app.activity");
	public static final String APK_FILENAME = Config.getProperty("apk.file.path");
	
	//Appium Server Details
	public static final String APPIUMJS_FILEPATH = Config.getProperty("appium.js.path");
	public static final String NODE_FILEPATH = Config.getProperty("appium.node.path");
	public static final String APPIUM_SERVER_ADDRESS = Config.getProperty("appium.ip.address");
	public static final int APPIUM_SERVER_PORT = Integer.parseInt(Config.getProperty("appium.port"));
	public static final String APPIUM_LOG_FILEPATH =  Config.getProperty("appium.log.path");
	
	//Test Configuration Details
	public static final PlatformType PLATFORM_TYPE = PlatformType.valueOf(
			Config.getProperty("platform.type").toUpperCase());
	public static final String DEVICE_PROFILE = Config.getProperty("device.profile");
	
	public static String getPlatformName() { return JsonUtils.getString("platform_name"); }
    public static String getPlatformVersion() { return JsonUtils.getString("platform_version"); }
    public static String getAVDName() { return JsonUtils.getString("avd_name"); }
    public static String getDeviceName() { return JsonUtils.getString("device_name"); }
    public static String getAutomationName() { return JsonUtils.getString("automation_name"); }

}
