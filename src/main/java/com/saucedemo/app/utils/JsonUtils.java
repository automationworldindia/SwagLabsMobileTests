package com.saucedemo.app.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    // Each thread gets its own map
    private static final ThreadLocal<Map<String, Object>> jsonData =
            ThreadLocal.withInitial(HashMap::new);

    public static void loadDeviceProfile(String fileName) throws IOException {
        String filePath = "src/test/resources/device-profiles/" + fileName;
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("JSON file not found: " + filePath);
        }

        Map<String, Object> map = mapper.readValue(file, Map.class);

        jsonData.get().clear();
        jsonData.get().putAll(map);
    }

    public static String getString(String key) {
        Map<String, Object> currentData = jsonData.get();
        Object value;

        if (key.contains(".")) {
            String[] partialKeys = key.split("\\.");
            Map<String, Object> nestedJsonData =
                    (Map<String, Object>) currentData.get(partialKeys[0]);

            value = nestedJsonData != null ? nestedJsonData.get(partialKeys[1]) : null;
        } else {
            value = currentData.get(key);
        }

        return value != null ? value.toString() : null;
    }
}
