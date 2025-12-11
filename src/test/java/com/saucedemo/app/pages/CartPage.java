package com.saucedemo.app.pages;

import com.saucedemo.app.pages.android.CartPage_Android;
import com.saucedemo.app.pages.interfaces.ICartPage;
import com.saucedemo.app.pages.ios.CartPage_iOS;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;

public class CartPage {
	private ICartPage cartPage;
	private AppiumDriver driver;
	
	public CartPage(AppiumDriver driver) {
		this.driver = driver;
		if (this.driver instanceof AndroidDriver) {
			cartPage = new CartPage_Android(driver);
		} else {
			cartPage = new CartPage_iOS(driver);
		}
	}

	@Step("Validate user is navigated to cart page.")
	public void validateNavigationToCartPage() {
		cartPage.validateNavigationToCartPage();
	}

	@Step("Validate product {0} is displayed.")
	public void validateProductDisplayed(String productName) {
		cartPage.validateProductDisplayed(productName);
	}

	@Step("Click checkout button.")
	public void clickCheckoutButton() {
		cartPage.clickCheckoutButton();
	}

}
