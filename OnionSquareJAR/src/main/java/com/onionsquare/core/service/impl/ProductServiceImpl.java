package com.onionsquare.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onionsquare.core.dao.LineItemDao;
import com.onionsquare.core.dao.OrderDao;
import com.onionsquare.core.dao.ProductDao;
import com.onionsquare.core.model.Category;
import com.onionsquare.core.model.LineItem;
import com.onionsquare.core.model.Product;
import com.onionsquare.core.model.Store;
import com.onionsquare.core.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private LineItemDao lineItemDao;
	
	

	public Product addNewProductInStore(Product newProduct) {
		
		if (newProduct != null) {
			Integer productId = (Integer) productDao.save(newProduct);			 
			return newProduct;
		}
		return null;
	}

	public Product updateProductInStore(Product product) {
		if(product !=null) {
			productDao.update(product);
			return product ;
		}
		return null;
	}

	public List<Product> getAllProducts() {
		return productDao.findAll(); 		
	}
	
	public Product getProductById(Integer productId) {
		return productDao.findById(productId);
	}
	
	public List<Product> getProductsByStoreId(Integer storeId, Boolean status, Integer categoryId) {
		return productDao.getProductsByStoreId(storeId, status, categoryId);			
	}
	public void removeProduct(Product product) {
		productDao.delete(product);
	}
	
	public List<Product>  getTopSellsProductsByStoreId(Integer storeId,Boolean status ,Integer categoryId, int pageNo , int pageSize){
	   List<Product> productList = productDao.getProductsByStoreId( storeId, status , categoryId);
	   if(productList==null)
		   productList = new ArrayList<Product>();
	   for(Product product:productList){
		  double total_amount = 0.0;
		  List<LineItem> lineItemList =  lineItemDao.findLineItemsByProductId(product.getProductId());
		  for(LineItem lineItem:lineItemList){
			  total_amount += lineItem.getSubTotal();			  
		  }
		   product.setTotal_order_amount(total_amount);
	   }
	    Collections.sort(productList, new ProductComparator());
	    int fromIndex = (pageNo*pageSize);
	    int toIndex = ((pageNo+1)*pageSize) ;
	    if(fromIndex < productList.size() && toIndex < productList.size())
	       productList =  productList.subList(fromIndex, toIndex);
	    else if (fromIndex < productList.size() && toIndex > productList.size())
	    	productList = productList.subList(fromIndex, productList.size());
	   return productList;
	}
	
	public static class ProductComparator implements Comparator<Product> {
	      @Override
	      public int compare(Product product1, Product product2) {
	         int f = product2.getTotal_order_amount().compareTo(product1.getTotal_order_amount());
	         return (f != 0) ? f : product1.getProductName().compareTo(product2.getProductName());
	      }
	  }
	@Transactional
	public List<Product> getProductListByPageNoAndPageSize(int pageNo, int pageSize,int storeId, int categoryId){
		List<Product> productList =  productDao.findAll(pageNo, pageSize,storeId, categoryId);
		if(productList==null)
			productList = new ArrayList<Product>();	
	    return productList;
    }

}
