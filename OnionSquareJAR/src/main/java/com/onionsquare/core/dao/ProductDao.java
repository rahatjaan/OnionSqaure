package com.onionsquare.core.dao;

import java.util.List;

import com.onionsquare.core.model.Product;

public interface ProductDao extends GenericDAO<Product, Integer>{

	List<Product> getProductsByStoreId(Integer storeId, Boolean status, Integer categoryId);	
	 
}
