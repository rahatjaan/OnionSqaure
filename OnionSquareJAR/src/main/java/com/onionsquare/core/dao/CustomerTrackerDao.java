package com.onionsquare.core.dao;

import java.util.List;

import com.onionsquare.core.model.CustomerTracker;

public interface CustomerTrackerDao extends GenericDAO<CustomerTracker, Integer>{
	
	public List<CustomerTracker> getViewProductsByCustomerId(int customerId);
	
	public void updateCustomerTracker(CustomerTracker customerTracker);
	
	public CustomerTracker getViewProductsByStoreId(int storeId);
	

}
