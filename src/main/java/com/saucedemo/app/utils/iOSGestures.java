package com.saucedemo.app.utils;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;

public interface iOSGestures {
	default void tapByCoords(AppiumDriver driver, By locator) {
        WebElement element = driver.findElement(locator);
        int startX = element.getLocation().getX();
        int startY = element.getLocation().getY();

        int centerX = startX + element.getSize().getWidth()/2;
        int centerY = startY + element.getSize().getHeight()/2;

        HashMap<String, Object> params = new HashMap<>();
        params.put("x", centerX);
        params.put("y", centerY);
        driver.executeScript("mobile: tap", params);
    }	
	
	default void tapByCoords(AppiumDriver driver, By locator, int relativeX, int relativeY) {
        WebElement element = driver.findElement(locator);

        HashMap<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement)driver.findElement(locator)).getId());
        params.put("x", relativeX);
        params.put("y", relativeY);
        driver.executeScript("mobile: tap", params);
    }
	
	default void tapByCoords(AppiumDriver driver, int x, int y) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        driver.executeScript("mobile: tap", params);
    }
    
	default void scrollToElement(AppiumDriver driver, By scrollableCntrLocator, By targetLocator) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement)driver.findElement(scrollableCntrLocator)).getId());
        params.put("direction", "down");

        while (!isElementDisplayed(driver, targetLocator)) {
            driver.executeScript("mobile: scroll", params);
        }
    }
    
	default void scrollUsingPredicateString(AppiumDriver driver, By scrollableCntrLocator, String targetPredicateString) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement)driver.findElement(scrollableCntrLocator)).getId());
        params.put("predicateString", targetPredicateString);

        driver.executeScript("mobile: scroll", params);
    }
    
	default void scrollUsingAccessibiltyId(AppiumDriver driver, By scrollableCntrLocator, String targetName) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement)driver.findElement(scrollableCntrLocator)).getId());
        params.put("name", targetName);

        driver.executeScript("mobile: scroll", params);
    }
	
	default void scrollToBeginning(AppiumDriver driver, By scrollableCntrLocator) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement)driver.findElement(scrollableCntrLocator)).getId());
        params.put("direction", "down");

        driver.executeScript("mobile: swipe", params);
    }
	
	private boolean isElementDisplayed(AppiumDriver driver, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

}
