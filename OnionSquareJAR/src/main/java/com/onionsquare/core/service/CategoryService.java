package com.onionsquare.core.service;

import java.util.List;

import com.onionsquare.core.model.Category;

public interface CategoryService {

	/**
	 * Creates a new category in a store.
	 * 
	 * @param category
	 *            : new category being added in a store.
	 * @return Category object recently added in a store
	 */
	Category addNewCategory(Category category);

	/**
	 * Return list of categories that are added in a store.
	 * 
	 * @param storeId
	 *            : store id of a store for which categorylist is being fetched.
	 */

	List<Category> listAllCategoriesByStoreId(Integer storeId);

	/**
	 * Updates a category in store.
	 * 
	 * @param category
	 */
	void updateCategory(Category category);

	/**
	 * Returns a category by it sid.
	 * 
	 * @param categoryId
	 *            : id of a category supplied as parameter.
	 * @return Category object of specified id as parameter.
	 */
	Category getCategoryById(Integer categoryId);

	/**
	 * Removes the category from database.
	 * 
	 * @param category
	 *            : Category object to delete
	 * @return Category object recently deleted.
	 */

	void removeCategory(Category category);

}
