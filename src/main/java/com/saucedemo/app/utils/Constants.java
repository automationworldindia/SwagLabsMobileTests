package com.saucedemo.app.utils;

import static com.saucedemo.app.utils.PropertyUtils.Config;

public interface Constants {
	//IOS App Details
	public static final String BUNDLEID = Config.getProperty("app.bundle.id").trim();
	public static final String APP_FILENAME = Config.getProperty("app.file.path").trim();
	//Android App Details
	public static final String PKGNAME = Config.getProperty("apk.app.package").trim();
	public static final String ACTIVITY = Config.getProperty("apk.app.activity").trim();
	public static final String APK_FILENAME = Config.getProperty("apk.file.path").trim();
	
	//Appium Server Details
	public static final String APPIUMJS_FILEPATH = Config.getProperty("appium.js.path").trim();
	public static final String NODE_FILEPATH = Config.getProperty("appium.node.path").trim();
	public static final String APPIUM_SERVER_ADDRESS = Config.getProperty("appium.ip.address").trim();
	public static final int APPIUM_SERVER_PORT = Integer.parseInt(Config.getProperty("appium.port").trim());
	public static final String APPIUM_LOG_FILEPATH =  Config.getProperty("appium.log.path").trim();
	
	//Perfect Details
	public static final boolean ENABLE_PERFECTO=Boolean.parseBoolean(Config.getProperty("perfecto.enable").trim()) ;
	
	//Test Configuration Details
	public static final PlatformType PLATFORM_TYPE = PlatformType.valueOf(Config.getProperty("platform.type").trim().toUpperCase());
	public static final String DEVICE_PROFILE = Config.getProperty("device.profile").trim();
	
	//Reading device profiles
	public static String getPlatformName() { return JsonUtils.getString("platform.name"); }
    public static String getPlatformVersion() { return JsonUtils.getString("platform.version"); }
    public static String getDeviceName() { return JsonUtils.getString("device.name"); }
    public static String getDeviceType() { return JsonUtils.getString("device.type"); }
    public static boolean getNoReset() { return Boolean.parseBoolean(JsonUtils.getString("no_reset")); }
    public static String getAutomationName() { return JsonUtils.getString("automation_name"); }
}
