package com.onionsquare.core.dao;

import java.util.List;

import com.onionsquare.core.model.Category;

public interface CategoryDao extends GenericDAO<Category, Integer> {
	
	/**
	 * Returns list of the categories created on a store specified store-id
	 * @param storeId: store id of a store.
	 * @return Category object list.
	 */
	 List<Category> findAllCatagoryByStoreId(Integer storeId) ;
	
}
