package com.onionsquare.core.service;

import java.util.ArrayList;
import java.util.List;

import com.onionsquare.core.model.Address;
import com.onionsquare.core.model.Customer;
import com.onionsquare.core.model.Inquiry;

public interface CustomerService {
	/**
	 * Save a new customer. Implemented for sign up of new customer in onionsquare.com
	 * @param newCustomer: Customer object to be saved.
	 */
	void signUpCustomer(Customer newCustomer);
	
	/**
	 * Return all the customer available in database. If no customer is found, returns a list with 0 size.
	 * @return List of customer objects.
	 */
	List<Customer> listAllCustomer() ;
	/**
	 * Return a Custome by customer id
	 * @Param 
	 */
	Customer getCustomerByCustomerId(Integer customerId);
	
	void updateCustomer(Customer customer) ;
	/**
	 * If shipping address does not exists, Saves a shipping address of customer with customer id passed as parameterUpdates ShippingAddress of a customer.
	 * otherwise updates a existing shipping addresss.
	 * @param customerId: id of a customer whose shipping address is to be saved or updated
	 * @param shippingAddress: Address object representing a shipping address of a customer with id passed as parameter
	 */
	void saveOrUpdateCustomerShippingAddress(int customerId, Address shippingAddress);	 
	/**
	 * If billing address does not exists, Saves a billing address of customer with customer id passed as parameterUpdates ShippingAddress of a customer.
	 * otherwise updates a existing billing address.
	 * @param customerId: id of a customer whose billing address is to be saved or updated
	 * @param shippingAddress: Address object representing a billing address of a customer with id passed as parameter
	 */
	void saveOrUpdateCustomerBillingAddress(int customerId, Address billingAddress);
	/**
	 * Return shipping address of a customer by customer id passed as parameter
	 * @param customerId
	 * @return
	 */
	Address getShippingAddressByCustomerId(int customerId) ;
	/**
	 *It is second implementation of saveOrUpdateCustomerShippingAddress.
	 *If shipping address does not exists, Saves a shipping address of customer with customer id passed as parameterUpdates ShippingAddress of a customer.
	 * otherwise updates a existing shipping addresss.
	 * @param customerId: Integer id of a customer whose shipping address is to be saved or updated (Object)
	 * @param shippingAddress: Address object representing a shipping address of a customer with id passed as parameter	
	 */
	 void saveOrUpdateCustomerShippingAddress(Integer customerId,Address shippingAddress);
	 
	 Customer getCustomerByUserName(String userName);
	 
	 public void sendInquiryToWebMaster(int customerId, Inquiry inquiryFromCustomer);
	 
	 public List<Inquiry> findParentInquiriesOfCustomers(int customerId, boolean isCustomer,Boolean inquiryStatus);
	 
	 public Inquiry getInquiryByInquiryId(int inquiryId);
	 
	 public List<Inquiry> findChildrenInquiryByParentInquiryId(int parentInquiry);
	 
	 public void updateInquiryToParentThread(int customerId,Inquiry newInquiryToParentThread);
 
}
