package com.onionsquare.core.dao;

import com.onionsquare.core.model.Address;

public interface AddressDao extends GenericDAO<Address, Integer> {

	Address getShippingAddressByOrderId(Integer orderId);
}
