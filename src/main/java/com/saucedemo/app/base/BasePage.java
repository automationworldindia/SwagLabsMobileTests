package com.saucedemo.app.base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import io.appium.java_client.AppiumDriver;

public abstract class BasePage {
	protected final AppiumDriver DRIVER;
	protected final FluentWait<AppiumDriver> WAIT;
	
	protected BasePage(AppiumDriver driver) {
        this.DRIVER = driver;
        this.WAIT = new FluentWait<>(this.DRIVER)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2));
    }
	
	protected WebElement getElement(By locator) {
        return DRIVER.findElement(locator);
    }
	
	protected void click(By locator) {
		getElement(locator).click();
    }
	
	protected void enterText(By locator, String text) {
		WebElement element = getElement(locator);
		element.clear();
		element.click();
        addDelay(1);
        element.sendKeys(text);
    }
	
	protected boolean isElementDisplayed(By locator) {
		return isElementDisplayed(locator, 5);
    }
	
	protected boolean isElementDisplayed(By locator, int timeoutInSeconds) {
        try {
        	WAIT.withTimeout(Duration.ofSeconds(timeoutInSeconds))
            		.ignoring(NoSuchElementException.class)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
	
	protected boolean isElementClickable(By locator) {
		return isElementDisplayed(locator, 5);
    }
	
	protected boolean isElementClickable(By locator, int timeoutInSeconds) {
        try {
        	if (isElementDisplayed(locator, timeoutInSeconds)) {
        		WAIT.withTimeout(Duration.ofSeconds(timeoutInSeconds))
        		.ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.elementToBeClickable(locator));
        		return true;
        	} else {
        		return false;
        	}
        } catch (TimeoutException e) {
            return false;
        }
    }
	
	protected void addDelay(int timeInSecs) {
        try {
            Thread.sleep(timeInSecs * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
