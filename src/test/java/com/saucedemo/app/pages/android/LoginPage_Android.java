package com.saucedemo.app.pages.android;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.saucedemo.app.base.BasePage;
import com.saucedemo.app.pages.interfaces.ILoginPage;
import com.saucedemo.app.utils.AndroidGestures;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class LoginPage_Android extends BasePage implements ILoginPage, AndroidGestures {
	private By userName = AppiumBy.accessibilityId("test-Username");
	private By password = AppiumBy.accessibilityId("test-Password");
	private By loginBtn = AppiumBy.accessibilityId("test-LOGIN");
	private By errorMsg = AppiumBy.xpath("//android.view.ViewGroup[@content-desc='test-Error message']//android.widget.TextView");

	public LoginPage_Android(AppiumDriver driver) {
		super(driver);
	}
	
	@Override
	public void login(String userName, String password) {
		Assert.assertTrue(isElementDisplayed(this.userName), "Username field is not displayed.");
		enterText(this.userName, userName); 
		Assert.assertTrue(isElementDisplayed(this.password), "Password field is not displayed.");
		enterText(this.password, password); 
		Assert.assertTrue(isElementClickable(this.loginBtn), "Login Button is not displayed.");
		clickGestureById(DRIVER, this.loginBtn);
		validateNoErrorMessageDisplayed();
	}

	@Override
	public void validateErrorMessage(String expectedMsg) {
		Assert.assertTrue(isElementDisplayed(errorMsg, 5), "No error message found.");
		String actualMsg = getElementAttribute(errorMsg, "text");
		Assert.assertTrue(actualMsg.trim().equals(expectedMsg),
				String.format("Error message does not match. Expected: %s. Actual: %s.", expectedMsg, actualMsg));
	}

	@Override
	public void validateNoErrorMessageDisplayed() {
		Assert.assertFalse(isElementDisplayed(errorMsg, 2), "Error message found during login.");
	}
}
