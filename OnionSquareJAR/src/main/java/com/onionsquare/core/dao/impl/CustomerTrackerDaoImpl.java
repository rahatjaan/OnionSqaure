package com.onionsquare.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onionsquare.core.dao.CustomerTrackerDao;
import com.onionsquare.core.model.Customer;
import com.onionsquare.core.model.CustomerTracker;

@Repository("customerTrackerDao")
public class CustomerTrackerDaoImpl extends AbstractDAO<CustomerTracker, Integer> implements CustomerTrackerDao{

	
	@Autowired
	public CustomerTrackerDaoImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);

		// TODO Auto-generated constructor stub
	}

	public List<CustomerTracker> getViewProductsByCustomerId(int customerId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomerTracker.class);
		if(customerId >0){
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			detachedCriteria.add(Restrictions.eq("customer", customer));	
		}
		 detachedCriteria.addOrder(Order.asc("trackedDate"));

		List<CustomerTracker> customerTrackerList =   findByCriteria(detachedCriteria);
		 if(customerTrackerList==null)
		 {
			 customerTrackerList =  new ArrayList<CustomerTracker>();
			
		 }
		return customerTrackerList;
	}
	
	
	public void updateCustomerTracker(CustomerTracker customerTracker){		
		 saveOrUpdate(customerTracker);		
	}
	
	public CustomerTracker getViewProductsByStoreId(int storeId) {
		CustomerTracker customerTracker=null;
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomerTracker.class);
		detachedCriteria.add(Restrictions.eq("store", storeId));		
		List<CustomerTracker> customerTrackerList =   findByCriteria(detachedCriteria);
		 if(customerTrackerList!=null)
		 {
			 if(customerTrackerList.size()>0)
				 customerTracker = customerTrackerList.get(0);
		 }
	
		return customerTracker;
	}

}
