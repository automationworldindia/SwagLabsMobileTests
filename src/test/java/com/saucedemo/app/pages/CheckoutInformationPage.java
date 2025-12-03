package com.saucedemo.app.pages;

import com.saucedemo.app.objects.User;
import com.saucedemo.app.pages.android.CheckoutInformationPage_Android;
import com.saucedemo.app.pages.interfaces.ICheckoutInformationPage;
import com.saucedemo.app.pages.ios.CheckoutInformationPage_iOS;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class CheckoutInformationPage {
	private ICheckoutInformationPage checkoutInfoPage;
	private AppiumDriver driver;
	
	public CheckoutInformationPage(AppiumDriver driver) {
		this.driver = driver;
		if (this.driver instanceof AndroidDriver) {
			checkoutInfoPage = new CheckoutInformationPage_Android(driver);
		} else {
			checkoutInfoPage = new CheckoutInformationPage_iOS(driver);
		}
	}
	
	public void validateNavigationToCheckoutInfoPage() {
		checkoutInfoPage.validateNavigationToCheckoutInfoPage();
	}
	
	public void setCheckoutInfo(User user) {
		checkoutInfoPage.setCheckoutInfo(user);
	}
}
