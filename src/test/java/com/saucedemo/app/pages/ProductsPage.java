package com.saucedemo.app.pages;

import com.saucedemo.app.pages.android.ProductsPage_Android;
import com.saucedemo.app.pages.interfaces.IProductsPage;
import com.saucedemo.app.pages.ios.ProductsPage_iOS;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

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
	
	public void validateNavigationToProductsPage() {
		productsPage.validateNavigationToProductsPage();
	}
	
	public void addproductToCart(String productName) {
		productsPage.addproductToCart(productName);
	}
	
	public void clickCartButton() {
		productsPage.clickCartButton();
	}

}
