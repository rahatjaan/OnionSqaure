package com.onionsquare.core.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onionsquare.core.dao.CustomerDao;
import com.onionsquare.core.model.Admin;
import com.onionsquare.core.model.Customer;

@Repository("customerDao")
public class CustomerDaoImpl extends AbstractDAO<Customer, Integer> implements
		CustomerDao {
	
	@Autowired
	public CustomerDaoImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	public Customer findByUserName(String userName) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class);
		detachedCriteria.add(Restrictions.eq("userName", userName));		
		List<Customer> customerList = findByCriteria(detachedCriteria);
		if (customerList != null && customerList.size() > 0)
			return customerList.get(0);
		return null;
	}
}
