package com.saucedemo.app.tests;

import java.util.Map;

import com.saucedemo.app.utils.RetryUtils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.saucedemo.app.base.BaseTest;
import com.saucedemo.app.objects.User;
import com.saucedemo.app.pages.CartPage;
import com.saucedemo.app.pages.CheckoutInformationPage;
import com.saucedemo.app.pages.LoginPage;
import com.saucedemo.app.pages.ProductsPage;
import com.saucedemo.app.utils.TestDataUtils;

import io.qameta.allure.Description;

@Epic("Login Tests")
@Feature("Validate login functionality of the mobile app")
public class LoginTests extends BaseTest {
	@Test(description = "[Login-001] Verify user is able to login using valid credentials.",
			groups = {"sanity", "regression"},
			retryAnalyzer = RetryUtils.class)
	@Description("Validate login functionality using valid credentials.")
	public void loginUsingValidCredentials() throws InterruptedException {
		try {
			//Get test data
			Map<String, String> data = TestDataUtils.getData(
					"Regression_TestData.xlsx", "LoginTests", "Login-001");
			
			//Login
			LoginPage loginPage = new LoginPage(driver);
			loginPage.login(data.get("UserName"), data.get("Password"));
			loginPage.validateNoErrorMessageDisplayed();
			
			//Validate login
			ProductsPage productPage = new ProductsPage(driver);
			productPage.validateNavigationToProductsPage();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error: " + e.getMessage());
		}		
	}

	@Test(description="[Login-002] Verify user is not able to login using locked out credentials.",
			groups = {"regression", "develop"},
			retryAnalyzer = RetryUtils.class)
	@Description("Validate login functionality using locked credentials.")
	public void loginUsingLockedoutCredentials() {
		try {
			//Get test data
			Map<String, String> data = TestDataUtils.getData(
					"Regression_TestData.xlsx", "LoginTests", "Login-002");
			
			//Login
			LoginPage loginPage = new LoginPage(driver);
			loginPage.login(data.get("UserName"), data.get("Password"));	
			
			//Validate login
			loginPage.validateErrorMessage("Sorry, this user has been locked out.");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error: " + e.getMessage());
		}	
	}
}
