package com.onionsquare.core.service;

import com.onionsquare.core.model.Address;

public interface AddressService {

	/**
	 * Save shipping or billing address of a Customer or of an Order
	 * 
	 * @param shippingOrBillingAddress
	 *            : Address object representing shipping or Billing address of a
	 *            Customer or of an Order.
	 */
	 void saveShippingOrBillingAddress(Address shippingOrBillingAddress);

	/**
	 * Return shippingAdress object by Address Id.
	 * 
	 * @param shippingAddressId
	 *            : id of shipping Address
	 * @return Address Object representing shipping address.
	 */
	 Address getShippingAddressByAddressId(Integer shippingAddressId);

	/**
	 * Return billingAddress object by Address Id.
	 * 
	 * @param billingAddressId
	 *            : id of a billing Address
	 * @return Address Object representing billing address.
	 */

	 Address getBillingAddressByAddressId(Integer billingAddressId);

	/**
	 * Update address, one can pass shipping or billing address.
	 * 
	 * @param shippingOrBillinAddress
	 *            : Address object
	 */

	void updateShippingOrBillingAddress(Address shippingOrBillinAddress);

	/**
	 * Delete an address from Address table.
	 * 
	 * @param shippingOrBillingAddressId
	 */
	boolean removeShippingOrBillingAddress(Integer shippingOrBillingAddressId);
	/**
	 * Return true is an address with id set in parameter exists in db, otherwise return false
	 * @param addressId: id of the address to check if exists in db.
	 * @return
	 */	
	boolean existsAddress(Integer addressId);
 
}
