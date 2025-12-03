package com.saucedemo.app.pages.android;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.saucedemo.app.base.BasePage;
import com.saucedemo.app.pages.interfaces.ICartPage;
import com.saucedemo.app.utils.AndroidGestures;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class CartPage_Android extends BasePage implements AndroidGestures, ICartPage {
	private By yourCartBanner = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.TextView\").text(\"YOUR CART\")");
	private By scrollableCntr = AppiumBy.accessibilityId("test-Cart Content");
	private String productCard = "//android.widget.TextView[@text='%s']//ancestor::android.view.ViewGroup[@content-desc='test-Item']";
	
	private By checkoutBtn = AppiumBy.accessibilityId("test-CHECKOUT");
	
	public CartPage_Android(AppiumDriver driver) {
		super(driver);
	}
	
	@Override
	public void validateNavigationToCartPage() {
		Assert.assertTrue(isElementDisplayed(yourCartBanner), "User did not get navigated to Your Cart page.");
	}
	
	@Override
	public void validateProductDisplayed(String productName) {
		By locator = AppiumBy.xpath(String.format(productCard, productName));
		scrollGestureById(DRIVER, scrollableCntr, locator, "down");
		Assert.assertTrue(isElementDisplayed(locator), String.format("Product %s is not present in the Cart page.", productName));
	}
	
	@Override
	public void clickCheckoutButton() {
		scrollGestureById(DRIVER, scrollableCntr, checkoutBtn, "down");
		Assert.assertTrue(isElementClickable(checkoutBtn), "Checkout Button is not clickable.");
		clickGestureById(DRIVER, checkoutBtn);
	}

}
