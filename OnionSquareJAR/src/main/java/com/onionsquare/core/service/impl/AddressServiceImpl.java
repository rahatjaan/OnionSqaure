package com.onionsquare.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onionsquare.core.dao.AddressDao;
import com.onionsquare.core.dao.OrderDao;
import com.onionsquare.core.model.Address;
import com.onionsquare.core.model.Order;
import com.onionsquare.core.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressDao addressDao; 
	
	@Autowired
	OrderDao orderDao;
	
	public void saveShippingOrBillingAddress(Address shippingOrBillingAddress){
		addressDao.save(shippingOrBillingAddress);		 
	}	
	
	public Address getShippingAddressByAddressId(Integer shippingAddressId) {
		return addressDao.findById(shippingAddressId);
	}
	public Address getBillingAddressByAddressId(Integer billingAddressId ) {
		return addressDao.findById(billingAddressId);
	}
	public void updateShippingOrBillingAddress(Address shippingOrBillinAddress) {
		addressDao.update(shippingOrBillinAddress);
	}
	public boolean removeShippingOrBillingAddress(Integer shippingOrBillingAddressId) {
		
		Address address = new Address();
		address.setAddressId(shippingOrBillingAddressId);
		addressDao.delete(address);
		if(addressDao.findById(shippingOrBillingAddressId) ==null) {
			return true;
		}
		return false;
	}
	
	public Address getShippingAddressByOrderId(Integer orderId) {
		Order order = orderDao.findById(orderId);
		if(order != null) {
			return order.getShippingAddress();
		}
		return null ;
	}
	
	public boolean existsAddress(Integer addressId) {		
		//find by id
		if(addressId != null) {
			if(getShippingAddressByAddressId(addressId) != null ) {
				return true;
			}
			return false ;
		}	
		return false ;
	}
}
