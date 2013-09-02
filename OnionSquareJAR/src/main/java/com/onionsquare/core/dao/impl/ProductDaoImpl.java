package com.onionsquare.core.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onionsquare.core.dao.ProductDao;
import com.onionsquare.core.model.Category;
import com.onionsquare.core.model.Product;
import com.onionsquare.core.model.Store;

@Repository("productDao")
public class ProductDaoImpl extends AbstractDAO<Product, Integer> implements
		ProductDao {	
	
	@Autowired
	public ProductDaoImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	 
	public List<Product> getProductsByStoreId(Integer storeId, Boolean productStatus,Integer categoryId) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);
		if(categoryId !=null && categoryId>0) {
			Category category= new Category();
			category.setCategoryId(categoryId);
			detachedCriteria.add(Restrictions.eq("category",category));
		}				
		Store store = new Store();
		store.setStoreId(storeId);
		detachedCriteria.add(Restrictions.eq("store", store));			
		
		List<Product> productList = findByCriteria(detachedCriteria);
		return productList;		
	}	
	


}
