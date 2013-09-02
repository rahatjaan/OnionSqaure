package com.onionsquare.web.service;

import com.onionsquare.web.model.ShoppingCart;

public interface ShoppingCartService {
	
	/**
	 * Return ShoppingCart object associated with current session.
	 * @return ShoppingCart object associated with current session.
	 */
	ShoppingCart getShoppingCart();		
	/**
	 * Removes an item from Shopping Cart. It removes the product as a whole and no instance of the item exists in ShoppingCart.
	 * @param productID
	 */	
	void removeItemFromCart(int productId);
	/**
	 * Adds a product item in the cart.
	 * @param productId: id of the product that is being added in the cart.
	 * @param quantity:  quantity of the product item i.e. number of items to be added in the cart.
	 */
	void addItemToCart(int productId,int quantity);
	/**
	 * Updates the items in shopping cart. It removes the specified number of items from a cart.
	 * @param productId : the id of the product existing in cart whose number is to be deducted.
	 * @param newQuantity: the number by which the product is to be deducted.
	 */
	void updateItemsInCartRemoveOneItem(int productId, int newQuantity);
	
	
	public void updateProductQuantityInCart(int productId, int newQuantity);
}
