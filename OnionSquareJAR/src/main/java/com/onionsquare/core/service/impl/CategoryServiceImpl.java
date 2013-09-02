package com.onionsquare.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onionsquare.core.dao.CategoryDao;
import com.onionsquare.core.model.Category;
import com.onionsquare.core.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryDao categoryDao;

	public Category addNewCategory(Category category) {
		Integer categoryId = (Integer) categoryDao.save(category);
		category.setCategoryId(categoryId);
		return category;
	}

	public List<Category> listAllCategoriesByStoreId(Integer storeId) {		
		return categoryDao.findAllCatagoryByStoreId(storeId);
	}

	public void updateCategory(Category category) {
	
		categoryDao.update(category);
	}

	public Category getCategoryById(Integer categoryId) {
	 
		return categoryDao.findById(categoryId);
	}

	public void removeCategory(Category category) {
		categoryDao.delete(category);
	}
}
