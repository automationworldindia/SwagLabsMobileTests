package com.saucedemo.app.tests;

import org.testng.annotations.Test;

import com.saucedemo.app.base.BaseTest;
import com.saucedemo.app.objects.User;
import com.saucedemo.app.pages.CartPage;
import com.saucedemo.app.pages.CheckoutInformationPage;
import com.saucedemo.app.pages.LoginPage;
import com.saucedemo.app.pages.ProductsPage;

public class CheckoutTests extends BaseTest {
	@Test(description="Verify user is able to checkout a single product.")
	public void checkoutSingleProduct() throws InterruptedException {
		try {
			//Login
			LoginPage loginPage = new LoginPage(driver);
			loginPage.login("standard_user", "secret_sauce");	
			//Add Products to Cart
			ProductsPage productPage = new ProductsPage(driver);
			productPage.addproductToCart("Sauce Labs Bike Light");
			productPage.clickCartButton();
			//Validate Cart Page
			CartPage cartPage = new CartPage(driver);
			cartPage.validateNavigationToCartPage();
			cartPage.validateProductDisplayed("Sauce Labs Bike Light");
			cartPage.clickCheckoutButton();
			//Enter Checkout Information
			User userObject = new User("Abhijeet", "Podder", "560023"); 
			CheckoutInformationPage checkoutInfo = new CheckoutInformationPage(driver);
			checkoutInfo.validateNavigationToCheckoutInfoPage();
			checkoutInfo.setCheckoutInfo(userObject);
			
			Thread.sleep(4000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Test(description="Verify user is able to checkout multiple products.")
	public void checkoutMultipleProducts() {
		try {
			//Login
			LoginPage loginPage = new LoginPage(driver);
			loginPage.login("standard_user", "secret_sauce");	
			//Add Products to Cart
			ProductsPage productPage = new ProductsPage(driver);
			productPage.addproductToCart("Sauce Labs Fleece Jacket");
			productPage.addproductToCart("Sauce Labs Bike Light");
			productPage.clickCartButton();
			//Validate Cart Page
			CartPage cartPage = new CartPage(driver);
			cartPage.validateNavigationToCartPage();
			cartPage.validateProductDisplayed("Sauce Labs Fleece Jacket");
			cartPage.validateProductDisplayed("Sauce Labs Bike Light");
			cartPage.clickCheckoutButton();
			//Enter Checkout Information
			User userObject = new User("Abhijeet", "Podder", "560023"); 
			CheckoutInformationPage checkoutInfo = new CheckoutInformationPage(driver);
			checkoutInfo.validateNavigationToCheckoutInfoPage();
			checkoutInfo.setCheckoutInfo(userObject);
			
			Thread.sleep(4000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
