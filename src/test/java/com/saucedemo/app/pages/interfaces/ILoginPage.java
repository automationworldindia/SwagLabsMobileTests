package com.saucedemo.app.pages.interfaces;

public interface ILoginPage {
	public void login(String userName, String password);
	public void validateErrorMessage(String expectedMsg);
	public void validateNoErrorMessageDisplayed();
}
