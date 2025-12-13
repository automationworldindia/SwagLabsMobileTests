package com.saucedemo.app.pages;

import com.saucedemo.app.pages.android.ProductsPage_Android;
import com.saucedemo.app.pages.interfaces.IProductsPage;
import com.saucedemo.app.pages.ios.ProductsPage_iOS;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;

public class ProductsPage {
	private IProductsPage productsPage;
	private AppiumDriver driver;
	
	public ProductsPage(AppiumDriver driver) {
		this.driver = driver;
		if (this.driver instanceof AndroidDriver) {
			productsPage = new ProductsPage_Android(driver);
		} else {
			productsPage = new ProductsPage_iOS(driver);
		}
	}

	@Step("Validate user is navigated to Products page after login.")
	public void validateNavigationToProductsPage() {
		productsPage.validateNavigationToProductsPage();
	}

	@Step("Add product {0} to cart.")
	public void addproductToCart(String productName) {
		productsPage.addproductToCart(productName);
	}

	@Step("Click cart button.")
	public void clickCartButton() {
		productsPage.clickCartButton();
	}
}
