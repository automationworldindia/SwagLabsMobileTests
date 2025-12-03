package com.saucedemo.app.pages.ios;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.saucedemo.app.base.BasePage;
import com.saucedemo.app.pages.interfaces.ILoginPage;
import com.saucedemo.app.utils.iOSGestures;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class LoginPage_iOS extends BasePage implements ILoginPage, iOSGestures {
	private By userName = AppiumBy.accessibilityId("test-Username");
	private By password = AppiumBy.accessibilityId("test-Password");
	private By loginBtn = AppiumBy.accessibilityId("test-LOGIN");
	

	public LoginPage_iOS(AppiumDriver driver) {
		super(driver);
	}
	
	@Override
	public void login(String userName, String password) {
		Assert.assertTrue(isElementDisplayed(this.userName), "Username field is not displayed.");
		enterText(this.userName, userName); 
		Assert.assertTrue(isElementDisplayed(this.password), "Password field is not displayed.");
		enterText(this.password, password); 
		Assert.assertTrue(isElementClickable(this.loginBtn), "Login Button is not displayed.");
		tapByCoords(DRIVER, this.loginBtn);
	}
	
	

}
