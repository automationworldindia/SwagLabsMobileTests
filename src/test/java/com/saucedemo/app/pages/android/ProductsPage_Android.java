package com.saucedemo.app.pages.android;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.saucedemo.app.base.BasePage;
import com.saucedemo.app.pages.interfaces.IProductsPage;
import com.saucedemo.app.utils.AndroidGestures;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class ProductsPage_Android extends BasePage implements AndroidGestures, IProductsPage {
	private By productsBanner = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.TextView\").text(\"PRODUCTS\")");
	private By cartBtn = AppiumBy.accessibilityId("test-Cart");
	
	private String addToCartBtn = "//android.widget.TextView[@text='%s']//following-sibling::android.view.ViewGroup[@content-desc='test-ADD TO CART']";
	private String removeBtn = "//android.widget.TextView[@text='%s']//following-sibling::android.view.ViewGroup[@content-desc='test-REMOVE']";
	
	
	public ProductsPage_Android(AppiumDriver driver) {
		super(driver);
	}
	
	@Override
	public void validateNavigationToProductsPage() {
		Assert.assertTrue(isElementDisplayed(productsBanner), "User did not get navigated to Products page.");
	}
	
	@Override
	public void addproductToCart(String productName) {
		scrollProductsPageToTop();
		addDelay(1);
		scrollToProduct(productName);
		clickAddToCartButton(productName);
		validateRemoveBtnDisplayed(productName);
	}
	
	@Override
	public void clickCartButton() {
		Assert.assertTrue(isElementClickable(cartBtn), "Cart button not displayed or clickable on Products page.");
		clickGestureById(DRIVER, cartBtn);
	}
	
	@Override
	public void scrollProductsPageToTop() {
		scrollGestureUsingUiAutomator(DRIVER, 
				AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().description(\"test-PRODUCTS\")).flingToBeginning(5)"));
	}
	
	@Override
	public void scrollToProduct(String productName) {
		By locator = AppiumBy.xpath(String.format(addToCartBtn, productName));
		scrollGestureByCoords(DRIVER, locator, "down");
	}
	
	@Override
	public void clickAddToCartButton(String productName) {
		By locator = AppiumBy.xpath(String.format(addToCartBtn, productName));
		clickGestureById(DRIVER, locator);
	}
	
	@Override
	public void validateRemoveBtnDisplayed(String productName) {
		By locator = AppiumBy.xpath(String.format(removeBtn, productName));
		Assert.assertTrue(isElementDisplayed(locator), "Selected product not added to Cart.");
	}

}
