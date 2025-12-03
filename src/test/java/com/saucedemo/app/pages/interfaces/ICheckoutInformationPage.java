package com.saucedemo.app.pages.interfaces;

import com.saucedemo.app.objects.User;

public interface ICheckoutInformationPage {
	public void validateNavigationToCheckoutInfoPage();
	public void setCheckoutInfo(User user);
}
