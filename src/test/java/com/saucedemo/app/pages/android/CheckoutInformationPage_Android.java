package com.saucedemo.app.pages.android;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.saucedemo.app.base.BasePage;
import com.saucedemo.app.objects.User;
import com.saucedemo.app.pages.interfaces.ICheckoutInformationPage;
import com.saucedemo.app.utils.AndroidGestures;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class CheckoutInformationPage_Android extends BasePage implements AndroidGestures, ICheckoutInformationPage {
	private By checkoutInfoBanner = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.TextView\").text(\"CHECKOUT: INFORMATION\")");
	private By firstNameTextbox = AppiumBy.accessibilityId("test-First Name");
	private By lastNameTextbox = AppiumBy.accessibilityId("test-Last Name");
	private By zipcodeTextbox = AppiumBy.accessibilityId("test-Zip/Postal Code");
	private By continueBtn = AppiumBy.accessibilityId("test-CONTINUE");
	
	public CheckoutInformationPage_Android(AppiumDriver driver) {
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
		clickGestureById(DRIVER, this.continueBtn);
	}

}
