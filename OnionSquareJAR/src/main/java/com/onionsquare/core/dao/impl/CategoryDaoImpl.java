package com.onionsquare.core.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onionsquare.core.dao.CategoryDao;
import com.onionsquare.core.model.Category;
import com.onionsquare.core.model.Store;

@Repository("categoryDao")
public class CategoryDaoImpl extends AbstractDAO<Category, Integer> implements
		CategoryDao {

	@Autowired
	public CategoryDaoImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List<Category> findAllCatagoryByStoreId(Integer storeId) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Category.class);
		Store store = new Store();
		store.setStoreId(storeId);
		detachedCriteria.add(Restrictions.eq("store", store));		
		List<Category> categoryList = findByCriteria(detachedCriteria);
		return categoryList ;
	}

	
}
