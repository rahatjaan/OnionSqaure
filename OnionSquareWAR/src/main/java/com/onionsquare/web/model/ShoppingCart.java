package com.onionsquare.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.onionsquare.core.model.LineItem;
import com.onionsquare.core.model.Product;

/**
 * 
 * ShoppingCart is a bean with scope session. It serves to store the products
 * selected by customer to purchase in onionsquare.com store. Each http session
 * is associated with a bean of this class and injected to ShoppingCartService.
 * Controller accesses ShoppingCartService to access the ShoppingCart bean. You
 * can create at least one bean in application context, or annotate a wireable
 * bean with @Component annotation. If no annotation is applied then, bean
 * wiring fails.
 * 
 * @author Naresh Adhikari
 * 
 */
@Component
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<Integer, LineItem> lineItemMap;

	public ShoppingCart() {
		this.lineItemMap = new HashMap<Integer, LineItem>();		 
	}

	/**
	 * Adds new lineitem if an item does not exists in cart, otherwise modifies
	 * the quantity of the lineitem in cart.
	 * 
	 * @param productId
	 *            : id of the product that is to be added in cart.
	 * @param quantity
	 *            : quantity of additional product to be added in cart.
	 */
	public void addItemToCart(Product product, int quantity) {
		// check if a key with productId exists in map, if exists,
		// get the lineitem for that key, and edit the lineitem and place in
		// map. Otherwise add key,lineitem in map
		LineItem existingLineItem = this.lineItemMap
				.get(product.getProductId());
		if (existingLineItem != null) {
			// an item already exists in cart.
			// increment the quantity attribute of lineitem & subtotal of the lineitem.
			int previousQuantity = existingLineItem.getQuantity();
			int newQuantity = previousQuantity + quantity;
			//Subtotal =newQuantity*unitPrice. 
			Double newSubTotal=newQuantity*product.getUnitPrice();
			existingLineItem.setSubTotal(newSubTotal);
			existingLineItem.setQuantity(newQuantity);
		} else {
			// item does not exist in cart.
			// Add new line-item corresponding to the item.
			LineItem newLineItem = new LineItem();
			newLineItem.setProduct(product);
			newLineItem.setQuantity(quantity);
			//Update sub-total for the product.
			newLineItem.setSubTotal(quantity*product.getUnitPrice());
			lineItemMap.put(product.getProductId(), newLineItem);
		}
	}

	/**
	 * Updates the quantity of the product of an item existing in cart. If the
	 * quantity is 0, removes the item completely from cart.
	 * 
	 * @param productId
	 *            : product id to be updated.
	 * @param newQuantity
	 *            : new quantity of item
	 */
	public void updateItemsInCart(int productId, int newQuantity) {
		// check if an item exists in shopping cart.
		// If exists update lineitem in cart for productId.
		LineItem existingLineItem = this.lineItemMap.get(productId);
		if (existingLineItem != null) {
			// an item already exists in cart.
			// increment the quantity attribute of lineitem
			if (newQuantity == 0) {
				removeItemFromCart(productId);
			} else {
				existingLineItem.setQuantity(newQuantity);
				Double newSubTotal=newQuantity*existingLineItem.getProduct().getUnitPrice();
				existingLineItem.setSubTotal(newSubTotal);
				
				
			}
		}
	}

	public void updateItemsInCartRemoveOneItem(int productId, int newQuantity) {
		// check if an item exists in shopping cart.
		// If exists update lineitem in cart for productId.
		LineItem existingLineItem = this.lineItemMap.get(productId);
		if (existingLineItem != null) {
			// an item already exists in cart.
			// increment the quantity attribute of lineitem
			int previousQuantity = existingLineItem.getQuantity();
			int newQty = previousQuantity - newQuantity;
			if (newQty > 0) {
				existingLineItem.setQuantity(newQty);
			} else {
				lineItemMap.remove(productId);
			}
		}
	}

	public void removeItemFromCart(int productId) {
		this.lineItemMap.remove(productId);
	}

	public Map<Integer, LineItem> getLineItemMap() {
		return lineItemMap;
	}

	public void setLineItemMap(Map<Integer, LineItem> lineItemMap) {
		this.lineItemMap = lineItemMap;
	}
	/**
	 * Return shopping cart total amount
	 */
	public Double getShoppingCartTotalAmount() {
		Double shoppingCartTotalAmount = 0.0 ;
		
		for(LineItem lineItemInShoppingCart : this.lineItemMap.values()) {
			shoppingCartTotalAmount += lineItemInShoppingCart.getSubTotal() ;
		}
		return shoppingCartTotalAmount ;
	}
	public Integer getTotalItemsInShoppingCart() {
		
		Integer totalNumberOfItem=0 ;
		for(LineItem lineItem :this.lineItemMap.values()) {			
			totalNumberOfItem += lineItem.getQuantity() ;
		}
		return totalNumberOfItem ;
	}
	
	public Double getShoppingCartTotalAmountByStore(int storeId) {
		Double shoppingCartTotalAmount = 0.0 ;
		
		for(LineItem lineItemInShoppingCart : this.lineItemMap.values()) {
			if(lineItemInShoppingCart.getProduct().getStore().getStoreId().equals(storeId))
			    shoppingCartTotalAmount += lineItemInShoppingCart.getSubTotal() ;
		}
		return shoppingCartTotalAmount ;
	}
	
	public Integer getTotalItemsInShoppingCartByStore(int storeId) {
		
		Integer totalNumberOfItem=0 ;
		for(LineItem lineItem :this.lineItemMap.values()) {	
			if(lineItem.getProduct().getStore().getStoreId().equals(storeId))
				totalNumberOfItem += lineItem.getQuantity() ;
		}
		return totalNumberOfItem ;
	}
	
public List<LineItem> getLineItemByStore(int storeId) {
		List<LineItem> lineItemList = new ArrayList<LineItem>();
		Integer totalNumberOfItem=0 ;
		for(LineItem lineItem :this.lineItemMap.values()) {	
			if(lineItem.getProduct().getStore().getStoreId().equals(storeId))
				lineItemList.add(lineItem);
		}
		return lineItemList ;
	}


}
