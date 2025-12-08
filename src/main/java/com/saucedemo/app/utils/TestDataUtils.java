package com.saucedemo.app.utils;

import java.util.HashMap;
import java.util.Map;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class TestDataUtils {
	public synchronized static Map<String, String> getData(String fileName, String sheetName, String testCaseId) {
		Map<String, String> data = new HashMap<>();
		Fillo fillo = new Fillo();
        Connection connection = null;
        Recordset recordset = null;
        
        try {
            connection = fillo.getConnection(System.getProperty("user.dir") + "/src/test/resources/data/" + fileName);
            String query = String.format("SELECT * FROM %s WHERE TestCaseID='%s'", sheetName, testCaseId);
            recordset = connection.executeQuery(query);

            if (recordset.next()) {
                for (String columnName : recordset.getFieldNames()) {
                    data.put(columnName.trim(), recordset.getField(columnName).trim());
                }
            } else {
                throw new RuntimeException("No row found for TestCaseID: " + testCaseId);
            }

        } catch (FilloException e) {
            throw new RuntimeException("Error reading Excel: " + e.getMessage(), e);
        } finally {
            if (recordset != null) recordset.close();
            if (connection != null) connection.close();
        }
        return data;
    }
}
