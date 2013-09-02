package com.onionsquare.core.service;

import java.util.List;

import com.onionsquare.core.model.CustomerTracker;
import com.onionsquare.core.model.Product;



public interface CustomerTrackerService {
	
	public List<Product> getViewProductsByCustomerId(int customerId);
	
	public void updateCustomerTracker(CustomerTracker customerTracker);
	
	public CustomerTracker getViewProductsByStoreId(int storeId);

 
}
