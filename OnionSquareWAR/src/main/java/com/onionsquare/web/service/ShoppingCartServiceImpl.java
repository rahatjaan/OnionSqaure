package com.onionsquare.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.onionsquare.core.dao.ProductDao;
import com.onionsquare.core.model.Product;
import com.onionsquare.web.model.ShoppingCart;

@Service("service")
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	ProductDao productDao;

	@Autowired
	private ShoppingCart shoppingCart;

	public ShoppingCartServiceImpl() {
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	/**
	 * @TODO check for for product availability(#OfProduct <= quantity)
	 */
	public void addItemToCart(int productId,int quantity) {
		
		Product product =productDao.findById(productId);		
		if(product != null){
			shoppingCart.addItemToCart(product, quantity);
		}
	}
	public void removeItemFromCart(int productId) {
		shoppingCart.removeItemFromCart(productId);
	}

	public void updateItemsInCartRemoveOneItem(int productId, int newQuantity) {
		 	shoppingCart.updateItemsInCartRemoveOneItem(productId, newQuantity);
	}

	@Override
	public void updateProductQuantityInCart(int productId, int newQuantity) {
		 shoppingCart.updateItemsInCart(productId, newQuantity);
		
	} 
	
	
}
