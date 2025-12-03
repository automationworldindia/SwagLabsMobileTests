package com.saucedemo.app.pages.interfaces;

public interface IProductsPage {
	public void validateNavigationToProductsPage();
	public void addproductToCart(String productName);
	public void clickCartButton();
	public void scrollProductsPageToTop();
	public void scrollToProduct(String productName);
	public void clickAddToCartButton(String productName);
	public void validateRemoveBtnDisplayed(String productName);

}
