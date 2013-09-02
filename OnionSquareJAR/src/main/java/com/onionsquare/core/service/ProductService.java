package com.onionsquare.core.service;

import java.util.List;

import com.onionsquare.core.model.Product;

public interface ProductService {

	/**
	 * Adds a new product in a store.
	 * 
	 * @param newProduct
	 *            : Product item to add in a store.
	 * @return Product item recently added in a store.
	 */
	Product addNewProductInStore(Product newProduct);

	/**
	 * Modifies a product existing in a store.
	 * 
	 * @param product
	 *            : Product being store
	 * @return
	 */
	Product updateProductInStore(Product product);

	List<Product> getAllProducts();

	/**
	 * Return a product in store by its id
	 * 
	 * @param productId
	 * @return
	 */
	Product getProductById(Integer productId);

	/**
	 * Return a list of products with the specified parameters. If status is
	 * null, returns active as well as inactive products. If categoryId is null
	 * , the all the products in specified store is retrieved.
	 * 
	 * @param storeId
	 *            : id of a store to which product belongs to.
	 * @param status
	 *            : status of Product.
	 * @param categoryId
	 *            : id of the category to in which product belongs
	 * @return
	 */
	List<Product> getProductsByStoreId(Integer storeId, Boolean status,
			Integer categoryId);

	/**
	 * Deletes a product from db.
	 * 
	 * @param product
	 *            : Product object to be deleted
	 */
	void removeProduct(Product product);
	
	List<Product>  getTopSellsProductsByStoreId(Integer storeId,Boolean status ,Integer categoryId,int pageNo , int pageSize);
	
	public List<Product> getProductListByPageNoAndPageSize(int pageNo, int pageSize, int storeId,int categoryId);

}
