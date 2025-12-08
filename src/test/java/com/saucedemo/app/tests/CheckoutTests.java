package com.saucedemo.app.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.saucedemo.app.base.BaseTest;
import com.saucedemo.app.objects.User;
import com.saucedemo.app.pages.CartPage;
import com.saucedemo.app.pages.CheckoutInformationPage;
import com.saucedemo.app.pages.LoginPage;
import com.saucedemo.app.pages.ProductsPage;
import com.saucedemo.app.utils.TestDataUtils;

public class CheckoutTests extends BaseTest {
	@Test(description="[Checkout-001] Verify user is able to checkout a single product.")
	public void checkoutSingleProduct() throws InterruptedException {
		try {
			//Get test data
			Map<String, String> data = TestDataUtils.getData(
					"Regression_TestData.xlsx", "CheckoutTests", "Checkout-001");
			
			//Login
			LoginPage loginPage = new LoginPage(driver);
			loginPage.login(data.get("UserName"), data.get("Password"));	
			
			//Add Products to Cart
			ProductsPage productPage = new ProductsPage(driver);
			productPage.addproductToCart(data.get("ProductName"));
			productPage.clickCartButton();
			
			//Validate Cart Page
			CartPage cartPage = new CartPage(driver);
			cartPage.validateNavigationToCartPage();
			cartPage.validateProductDisplayed(data.get("ProductName"));
			cartPage.clickCheckoutButton();
			
			//Enter Checkout Information
			User userObject = new User(
					data.get("FirstName"), data.get("LastName"), data.get("ZipCode")); 
			CheckoutInformationPage checkoutInfo = new CheckoutInformationPage(driver);
			checkoutInfo.validateNavigationToCheckoutInfoPage();
			checkoutInfo.setCheckoutInfo(userObject);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error: " + e.getMessage());
		}		
	}
	
	@Test(description="[Checkout-002] Verify user is able to checkout multiple products.")
	public void checkoutMultipleProducts() {
		try {
			//Get test data
			Map<String, String> data = TestDataUtils.getData(
					"Regression_TestData.xlsx", "CheckoutTests", "Checkout-002");
			
			//Login
			LoginPage loginPage = new LoginPage(driver);
			loginPage.login(data.get("UserName"), data.get("Password"));	
			
			//Add Products to Cart
			ProductsPage productPage = new ProductsPage(driver);
			String[] productNames = data.get("ProductName").split("\\|");
			for (String product : productNames) {
				productPage.addproductToCart(product);
			}
			productPage.clickCartButton();
			
			//Validate Cart Page
			CartPage cartPage = new CartPage(driver);
			cartPage.validateNavigationToCartPage();
			for (String product : productNames) {
				cartPage.validateProductDisplayed(product);
			}
			cartPage.clickCheckoutButton();
			
			//Enter Checkout Information
			User userObject = new User(
					data.get("FirstName"), data.get("LastName"), data.get("ZipCode")); 
			CheckoutInformationPage checkoutInfo = new CheckoutInformationPage(driver);
			checkoutInfo.validateNavigationToCheckoutInfoPage();
			checkoutInfo.setCheckoutInfo(userObject);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error: " + e.getMessage());
		}	
	}
}
