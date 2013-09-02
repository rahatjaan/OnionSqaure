package com.onionsquare.core.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onionsquare.core.dao.AddressDao;
import com.onionsquare.core.model.Address;

@Repository("addressDao")
public class AddressDaoImpl extends AbstractDAO<Address, Integer> implements AddressDao {
	
	@Autowired
	public AddressDaoImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	/**
	 * @TODO 
	 * @param orderId
	 * @return
	 */ 
	public Address getShippingAddressByOrderId(Integer orderId) {
 		return null;
	} 
}
