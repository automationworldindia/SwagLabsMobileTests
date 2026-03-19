package com.saucedemo.app.controller;

import com.saucedemo.app.utils.Constants;
import com.saucedemo.app.utils.DateUtils;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.net.MalformedURLException;

public class ReportManager implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotName = result.getMethod().getMethodName() + "_" +
                getStatusString(result.getStatus()) + "_" +
                DateUtils.getTimestamp("yyyy_MM_dd_HH_mm_ss_SSS");
        attachScreenshot(screenshotName);
        updatePerfectoStatus(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String screenshotName = result.getMethod().getMethodName() + "_" +
                getStatusString(result.getStatus()) + "_" +
                DateUtils.getTimestamp("yyyy_MM_dd_HH_mm_ss_SSS");
        attachScreenshot(screenshotName);
        updatePerfectoStatus(result);
    }

    private String getStatusString(int status) {
        switch (status) {
            case ITestResult.SUCCESS: return "PASS";
            case ITestResult.FAILURE: return "FAIL";
            case ITestResult.SKIP:    return "SKIP";
            default: return "UNKNOWN";
        }
    }

    public static synchronized void attachScreenshot(String screenshotName) {
        AppiumDriver driver = null;
        try {
            driver = AppiumDriverManager.getDriver();
            if (driver == null) {
                throw new RuntimeException("Driver instance is null. Cannot capture screenshot.");
            }
            attachScreenshot(driver, screenshotName);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Attachment(value = "{screenshotName}", type = "image/png")
    private static byte[] attachScreenshot(AppiumDriver driver, String screenshotName) {
        //byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        //return new ByteArrayInputStream(screenshotBytes);

        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    private void updatePerfectoStatus(ITestResult result) {
        try {
            if (Constants.get().ENABLE_PERFECTO) {
                if (result.isSuccess()) {
                    AppiumDriverManager.getDriver().executeScript("sauce:job-result=passed");
                } else {
                    AppiumDriverManager.getDriver().executeScript("sauce:job-result=failed");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
