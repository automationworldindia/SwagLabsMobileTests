package com.saucedemo.app.pages.ios;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.saucedemo.app.base.BasePage;
import com.saucedemo.app.pages.interfaces.IProductsPage;
import com.saucedemo.app.utils.iOSGestures;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class ProductsPage_iOS extends BasePage implements iOSGestures, IProductsPage {
	private By productsBanner = AppiumBy.iOSNsPredicateString("type == \"XCUIElementTypeStaticText\" AND name == \"PRODUCTS\"");
	//private By cartBtn = AppiumBy.xpath("**/XCUIElementTypeOther[`name == \"test-Cart\"`]/**/XCUIElementTypeOther");
	private By cartBtn = AppiumBy.accessibilityId("test-Cart");
	
	private By scrollableCntr = AppiumBy.iOSClassChain("**/XCUIElementTypeScrollView");
	
	private String addToCartBtn = "//XCUIElementTypeStaticText[@label='%s']//parent::XCUIElementTypeOther//following-sibling::XCUIElementTypeOther//XCUIElementTypeOther[@name='ADD TO CART']";
	private String removeBtn = "//XCUIElementTypeStaticText[@label='%s']//parent::XCUIElementTypeOther//following-sibling::XCUIElementTypeOther//XCUIElementTypeOther[@name='REMOVE']";
	
	
	public ProductsPage_iOS(AppiumDriver driver) {
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
		tapByCoords(DRIVER, cartBtn, 30, 30);
	}
	
	@Override
	public void scrollProductsPageToTop() {
		scrollToBeginning(DRIVER, scrollableCntr);
	}
	
	@Override
	public void scrollToProduct(String productName) {
		By targetLocator = AppiumBy.xpath(String.format(addToCartBtn, productName));
		scrollToElement(DRIVER, scrollableCntr, targetLocator);
	}
	
	@Override
	public void clickAddToCartButton(String productName) {
		By locator = AppiumBy.xpath(String.format(addToCartBtn, productName));
		tapByCoords(DRIVER, locator);
	}
	
	@Override
	public void validateRemoveBtnDisplayed(String productName) {
		By locator = AppiumBy.xpath(String.format(removeBtn, productName));
		Assert.assertTrue(isElementDisplayed(locator), "Selected product not added to Cart.");
	}

}
