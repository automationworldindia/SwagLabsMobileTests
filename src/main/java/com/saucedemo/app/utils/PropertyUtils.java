package com.saucedemo.app.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public interface PropertyUtils {
	public static final Properties Config = new Properties();
	
	public static void loadConfigProperties() throws IOException {
        try (FileInputStream fis = new FileInputStream("./Config.properties")) {
            Config.load(fis);
        } catch (IOException e) {
            throw e;
        }
    }
}
