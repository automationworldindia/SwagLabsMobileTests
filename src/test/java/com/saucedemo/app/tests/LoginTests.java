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

public class LoginTests extends BaseTest {
	@Test(description = "[Login-001] Verify user is able to login using valid credentials.",
			groups = {"sanity", "regression", "develop"})
	public void loginUsingValidCredentials() throws InterruptedException {
		try {
			//Get test data
			Map<String, String> data = TestDataUtils.getData(
					"Regression_TestData.xlsx", "LoginTests", "Login-001");
			
			//Login
			LoginPage loginPage = new LoginPage(driver);
			loginPage.login(data.get("UserName"), data.get("Password"));	
			
			//Validate login
			ProductsPage productPage = new ProductsPage(driver);
			productPage.validateNavigationToProductsPage();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error: " + e.getMessage());
		}		
	}
	
	@Test(description="[Login-002] Verify user is not able to login using locked out credentials.",
			groups = {"regression", "develop"})
	public void loginUsingLockedoutCredentials() {
		try {
			//Get test data
			Map<String, String> data = TestDataUtils.getData(
					"Regression_TestData.xlsx", "LoginTests", "Login-002");
			
			//Login
			LoginPage loginPage = new LoginPage(driver);
			loginPage.login(data.get("UserName"), data.get("Password"));	
			
			//Validate login
			ProductsPage productPage = new ProductsPage(driver);
			productPage.validateErrorMessage("Sorry, this user has been locked out.");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error: " + e.getMessage());
		}	
	}
}
