package com.saucedemo.app.pages.ios;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.saucedemo.app.base.BasePage;
import com.saucedemo.app.objects.User;
import com.saucedemo.app.pages.interfaces.ICheckoutInformationPage;
import com.saucedemo.app.utils.AndroidGestures;
import com.saucedemo.app.utils.iOSGestures;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class CheckoutInformationPage_iOS extends BasePage implements iOSGestures, ICheckoutInformationPage {
	private By checkoutInfoBanner = AppiumBy.iOSNsPredicateString("type == \"XCUIElementTypeStaticText\" AND name == \"CHECKOUT: INFORMATION\"");
	private By firstNameTextbox = AppiumBy.accessibilityId("test-First Name");
	private By lastNameTextbox = AppiumBy.accessibilityId("test-Last Name");
	private By zipcodeTextbox = AppiumBy.accessibilityId("test-Zip/Postal Code");
	private By continueBtn = AppiumBy.accessibilityId("test-CONTINUE");
	
	public CheckoutInformationPage_iOS(AppiumDriver driver) {
		super(driver);
	}
	
	@Override
	public void validateNavigationToCheckoutInfoPage() {
		Assert.assertTrue(isElementDisplayed(checkoutInfoBanner), "User did not get navigated to Checkout Information page.");
	}
	
	@Override
	public void setCheckoutInfo(User user) {
		Assert.assertTrue(isElementDisplayed(this.firstNameTextbox), "First Name field is not displayed.");
		enterText(this.firstNameTextbox, user.getFirstName()); 
		Assert.assertTrue(isElementDisplayed(this.lastNameTextbox), "Last Name field is not displayed.");
		enterText(this.lastNameTextbox, user.getLastName()); 
		Assert.assertTrue(isElementDisplayed(this.zipcodeTextbox), "Zip Code field is not displayed.");
		enterText(this.zipcodeTextbox, user.getZipCode());
		
		Assert.assertTrue(isElementClickable(this.continueBtn), "Continue button is not displayed.");
		tapByCoords(DRIVER, this.continueBtn);
	}

}
