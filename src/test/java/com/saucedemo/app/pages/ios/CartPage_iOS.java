package com.saucedemo.app.pages.ios;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.saucedemo.app.base.BasePage;
import com.saucedemo.app.pages.interfaces.ICartPage;
import com.saucedemo.app.utils.iOSGestures;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class CartPage_iOS extends BasePage implements iOSGestures, ICartPage {
	private By yourCartBanner = AppiumBy.iOSNsPredicateString("type == \"XCUIElementTypeStaticText\" AND name == \"YOUR CART\"");
	private By scrollableCntr = AppiumBy.iOSClassChain("**/XCUIElementTypeScrollView");
	private String productCard = "//XCUIElementTypeStaticText[@name='%s']//ancestor::XCUIElementTypeOther[@name='test-Item']";
	
	private By checkoutBtn = AppiumBy.iOSNsPredicateString("name == \"test-CHECKOUT\"");
	
	public CartPage_iOS(AppiumDriver driver) {
		super(driver);
	}
	
	@Override
	public void validateNavigationToCartPage() {
		Assert.assertTrue(isElementDisplayed(yourCartBanner), "User did not get navigated to Your Cart page.");
	}
	
	@Override
	public void validateProductDisplayed(String productName) {
		scrollToBeginning(DRIVER, scrollableCntr);
		
		By locator = AppiumBy.xpath(String.format(productCard, productName));
		scrollToElement(DRIVER, scrollableCntr, locator);
		Assert.assertTrue(isElementDisplayed(locator), String.format("Product %s is not present in the Cart page.", productName));
	}
	
	@Override
	public void clickCheckoutButton() {
		scrollToElement(DRIVER, scrollableCntr, checkoutBtn);
		Assert.assertTrue(isElementClickable(checkoutBtn), "Checkout Button is not clickable.");
		tapByCoords(DRIVER, checkoutBtn);
	}
}
