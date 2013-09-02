package com.onionsquare.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onionsquare.core.dao.AddressDao;
import com.onionsquare.core.dao.AdminDao;
import com.onionsquare.core.dao.CustomerDao;
import com.onionsquare.core.dao.CustomerTrackerDao;
import com.onionsquare.core.dao.InquiryDao;
import com.onionsquare.core.model.Address;
import com.onionsquare.core.model.Admin;
import com.onionsquare.core.model.Customer;
import com.onionsquare.core.model.CustomerTracker;
import com.onionsquare.core.model.Inquiry;
import com.onionsquare.core.model.Product;
import com.onionsquare.core.service.AddressService;
import com.onionsquare.core.service.CustomerTrackerService;
import com.onionsquare.core.service.ProductService;
import com.onionsquare.core.util.DateUtil;
import com.onionsquare.core.util.OnionSquareConstants;

@Service
public class CustomerTrackerServiceImpl implements	CustomerTrackerService {
	
	@Autowired
	private CustomerTrackerDao customerTrackerDao ;	
	
	@Autowired
	private ProductService productService;
	
	
	public List<Product> getViewProductsByCustomerId(int customerId){
		List<CustomerTracker> customerTrackerList;
		List<Product> productList = new ArrayList<Product>();
		Product product;
		customerTrackerList = customerTrackerDao.getViewProductsByCustomerId(customerId);
		for(CustomerTracker customerTracker:customerTrackerList){
			product =  customerTracker.getProduct();
			if(!productList.contains(product))
				productList.add(product);
		}

       
	  return productList;	
	}
	
	public void updateCustomerTracker(CustomerTracker customerTracker){
		customerTrackerDao.updateCustomerTracker(customerTracker);
		
	}
	
	public CustomerTracker getViewProductsByStoreId(int storeId){
		return customerTrackerDao.getViewProductsByStoreId(storeId);
		
	}


	

	
}
