package com.saucedemo.app.pages;

import com.saucedemo.app.pages.android.LoginPage_Android;
import com.saucedemo.app.pages.interfaces.ILoginPage;
import com.saucedemo.app.pages.ios.LoginPage_iOS;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class LoginPage {
	private ILoginPage loginPage;
	
	private AppiumDriver driver;
	
	public LoginPage(AppiumDriver driver) {
		this.driver = driver;
		if (this.driver instanceof AndroidDriver) {
			loginPage = new LoginPage_Android(driver);
		} else {
			loginPage = new LoginPage_iOS(driver);
		}
	}
	
	public void login(String userName, String password) {
		loginPage.login(userName, password);
	}

}
