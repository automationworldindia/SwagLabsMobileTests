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

public interface AndroidGestures {
	default void clickGestureById(AppiumDriver driver, By locator) {
		WebElement element = driver.findElement(locator);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("elementId", ((RemoteWebElement)element).getId());
		driver.executeScript("mobile: clickGesture", params);
	}
	
	default void clickGestureByCoords(AppiumDriver driver, By locator) {
		WebElement element = driver.findElement(locator);
		int x = element.getLocation().getX();
		int y = element.getLocation().getY();
		int width = element.getSize().getWidth();
		int height = element.getSize().getHeight();
		int centerX = x + width/2;
		int centerY = y + height/2;
		
		HashMap<String, Object> params = new HashMap<>();
		params.put("x", centerX);
		params.put("y", centerY);
		driver.executeScript("mobile: clickGesture", params);
	}
	
	default void scrollGestureById(AppiumDriver driver, By scrollCntrLocator, By targetLocator, String direction) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("elementId", ((RemoteWebElement) driver.findElement(scrollCntrLocator)).getId());
		params.put("direction", direction);
		params.put("percent", 0.3);
		
		Boolean canScrollMore = true;
		while (!isElementDisplayed(driver, targetLocator) && (canScrollMore == true)) {
			canScrollMore = (Boolean) driver.executeScript("mobile: scrollGesture", params);
		}
	}
	
	default void scrollGestureByCoords(AppiumDriver driver, By locator, String direction) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("left", 700);
		params.put("top", 550);
		params.put("width", 300);
		params.put("height", 1400);
		params.put("direction", direction);
		params.put("percent", 0.3);
		
		Boolean canScrollMore = true;
		while (canScrollMore && !isElementDisplayed(driver, locator)) {
			canScrollMore = (Boolean) driver.executeScript("mobile: scrollGesture", params);
		}
	}
	
	default WebElement scrollGestureUsingUiAutomator(AppiumDriver driver, By locator) {
		WebElement element = driver.findElement(locator);
		return element;
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
