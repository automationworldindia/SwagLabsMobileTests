package com.saucedemo.app.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface JsonUtils {
	static final Map<String, Object> jsonData = new ConcurrentHashMap<>();
	
	public static void loadDeviceProfile(String fileName) throws IOException {
        String filePath = "src/test/resources/device-profiles/" + fileName;
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("JSON file not found: " + filePath);
        }
        Map<String, Object> map = mapper.readValue(file, Map.class);
        jsonData.clear();
        jsonData.putAll(map);
    }
	
	/*public static String getString(String key) {
        Object value = jsonData.get(key);
        return value != null ? value.toString() : null;
    }*/
	
	public static String getString(String key) {
		Object value = null;
		
		if (key.contains(".")) {
			String[] partialKeys = key.split("\\.");
			Map<String, Object> nestedJsonData = (Map<String, Object>) jsonData.get(partialKeys[0]);
			value = nestedJsonData.get(partialKeys[1]);
		} else {
			value = jsonData.get(key);
		}
		
		return value != null ? value.toString() : null;
	}
}
