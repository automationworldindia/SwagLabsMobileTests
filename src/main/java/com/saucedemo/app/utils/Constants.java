package com.saucedemo.app.utils;

import static com.saucedemo.app.utils.PropertyUtils.Config;

public abstract class Constants {

    private static final ThreadLocal<Constants> threadLocal = ThreadLocal.withInitial(() -> new Constants() {});

    public static Constants get() {
        return threadLocal.get();
    }

    // iOS App Details
    public String BUNDLEID;
    public String APP_FILENAME;

    // Android App Details
    public String PKGNAME;
    public String ACTIVITY;
    public String APK_FILENAME;

    // Appium Server Details
    public String APPIUMJS_FILEPATH;
    public String NODE_FILEPATH;
    public String APPIUM_SERVER_ADDRESS;
    public int APPIUM_SERVER_PORT;
    public String APPIUM_LOG_FILEPATH;

    // Perfecto
    public boolean ENABLE_PERFECTO;
    public String PERFECTO_USERNAME;
    public String PERFECTO_ACCESSKEY;

    // Test Configuration
    public PlatformType PLATFORM_TYPE;
    public String DEVICE_PROFILE;

    // loads defaults from Config.properties)
    private Constants() {
        // iOS
        this.BUNDLEID              = Config.getProperty("app.bundle.id").trim();
        this.APP_FILENAME          = Config.getProperty("app.file.path").trim();
        // Android
        this.PKGNAME               = Config.getProperty("apk.app.package").trim();
        this.ACTIVITY              = Config.getProperty("apk.app.activity").trim();
        this.APK_FILENAME          = Config.getProperty("apk.file.path").trim();
        // Appium Server
        this.APPIUMJS_FILEPATH     = Config.getProperty("appium.js.path").trim();
        this.NODE_FILEPATH         = Config.getProperty("appium.node.path").trim();
        this.APPIUM_SERVER_ADDRESS = Config.getProperty("appium.ip.address").trim();
        this.APPIUM_SERVER_PORT    = Integer.parseInt(Config.getProperty("appium.port").trim());
        this.APPIUM_LOG_FILEPATH   = Config.getProperty("appium.log.path").trim();
        // Perfecto
        this.ENABLE_PERFECTO       = Boolean.parseBoolean(Config.getProperty("perfecto.enable").trim());
        this.PERFECTO_USERNAME     = getSystemOrConfigProperty("perfecto.username");
        this.PERFECTO_ACCESSKEY    = getSystemOrConfigProperty("perfecto.accessKey");
        		
        // Test Configuration
        this.PLATFORM_TYPE         = PlatformType.valueOf(getSystemOrConfigProperty("platform.type").toUpperCase());
        this.DEVICE_PROFILE        = getSystemOrConfigProperty("device.profile");
    }

    // -------- JSON Device Profile Reads (static is fine; thread-safe) --------
    public static String getPlatformName()      { return JsonUtils.getString("platform.name"); }
    public static String getPlatformVersion()   { return JsonUtils.getString("platform.version"); }
    public static String getDeviceName()        { return JsonUtils.getString("device.name"); }
    public static String getDeviceType()        { return JsonUtils.getString("device.type"); }
    public static boolean getNoReset()          { return Boolean.parseBoolean(JsonUtils.getString("no_reset")); }
    public static String getAutomationName()    { return JsonUtils.getString("automation_name"); }

    public String getSystemOrConfigProperty(String key) {
        String value = System.getProperty(key);
        return (value == null) ? Config.getProperty(key).trim() : value.trim();
    }
}
