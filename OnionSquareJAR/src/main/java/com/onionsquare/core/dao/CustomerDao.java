/**
 * 
 */
package com.onionsquare.core.dao;

import com.onionsquare.core.model.Customer;

/**
 * Provides basic methods to save, update and retrieve Customer Objects.
 * @author Naresh
 *
 */
public interface CustomerDao extends GenericDAO<Customer, Integer> {
	
	/**
	 * Return a Customer object by by username.
	 * @param userName
	 * @return
	 */
	 Customer findByUserName(String userName);

}
