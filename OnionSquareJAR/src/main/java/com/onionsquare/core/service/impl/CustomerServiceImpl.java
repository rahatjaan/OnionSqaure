package com.onionsquare.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onionsquare.core.dao.AddressDao;
import com.onionsquare.core.dao.AdminDao;
import com.onionsquare.core.dao.CustomerDao;
import com.onionsquare.core.dao.InquiryDao;
import com.onionsquare.core.model.Address;
import com.onionsquare.core.model.Admin;
import com.onionsquare.core.model.Customer;
import com.onionsquare.core.model.Inquiry;
import com.onionsquare.core.service.AddressService;
import com.onionsquare.core.util.DateUtil;
import com.onionsquare.core.util.OnionSquareConstants;

@Service
public class CustomerServiceImpl implements
		com.onionsquare.core.service.CustomerService {
	
	@Autowired
	CustomerDao customerDao ;	
	@Autowired
	AddressDao addressDao ;	
	@Autowired
	private InquiryDao inquiryDao;
	@Autowired
	private AdminDao adminDao;
	
	public void signUpCustomer(Customer newCustomer) {
		newCustomer.setCreatedDate(DateUtil.getCurrentDate());
		customerDao.save(newCustomer);
	}
	public List<Customer> listAllCustomer() {			
		return customerDao.findAll() ;	
	}
	
	public Customer getCustomerByCustomerId(Integer customerId) {		
		return customerDao.findById(customerId);
	}
	public void updateCustomer(Customer customer) {
		customer.setModifiedDate(DateUtil.getCurrentDate());
		customerDao.update(customer);
	}
	
	@Transactional
	public void saveOrUpdateCustomerShippingAddress(int customerId, Address shippingAddress) {			 
		//if address does not exists in db then save, otherwise update.
		Address existingAddress= addressDao.findById(shippingAddress.getAddressId());
		if(existingAddress==null) {
			//begin save.
			addressDao.save(shippingAddress);
			Customer dbCustomer = getCustomerByCustomerId(customerId);			 
			dbCustomer.setShippingAddress(shippingAddress);			
			customerDao.update(dbCustomer);
		}else {
			//update
			addressDao.update(shippingAddress);
		}
	} 
	@Transactional
	public void saveOrUpdateCustomerBillingAddress(int customerId, Address billingAddress) {		
		 
		//if address does not exists in db then save, otherwise update.
		Address existingAddress= addressDao.findById(billingAddress.getAddressId());

		if(existingAddress==null) {
			//begin save.
			addressDao.save(billingAddress);
			Customer customer = getCustomerByCustomerId(customerId);
			customer.setBillingAddress(billingAddress);
			customerDao.update(customer);
		}else {
			//update
			addressDao.update(billingAddress);
		}
	}
	@Transactional
	public void saveOrUpdateCustomerShippingAddress(Integer customerId,Address shippingAddress) {
		//if address does not exists in db then save, otherwise update.
		Customer customer=getCustomerByCustomerId(customerId);
		if(customer.getShippingAddress() != null) {
			//update address
			addressDao.update(shippingAddress);
		}else {
			//save address
			//update customer
			addressDao.save(shippingAddress);			 
			customer.setShippingAddress(shippingAddress);
			updateCustomer(customer);
		}
	}
	
	public void sendInquiryToWebMaster(int customerId, Inquiry inquiryFromCustomer) {

		if (inquiryFromCustomer != null) {
			inquiryFromCustomer.setSenderUserType(OnionSquareConstants.USER_TYPE_CUSTOMER);
			//Set parent inquiry id to 0,
			Inquiry parentInquiry = new Inquiry();
			parentInquiry.setInqueryId(OnionSquareConstants.PARENT_INQUIRY_ID);			
			inquiryFromCustomer.setParentInquiry(parentInquiry);
			// Get id of a logged in seller from parameter..
			inquiryFromCustomer.setSenderUserId(customerId);
			inquiryFromCustomer.setInquiryStatus(OnionSquareConstants.INQUIRY_STATUS_NEW);
			
			inquiryFromCustomer.setReceiverUserType(OnionSquareConstants.USER_TYPE_ADMIN);
			Admin admin = adminDao.findAdminByRole(OnionSquareConstants.ROLE_ADMIN);			
			inquiryFromCustomer.setReceiverUserId(admin.getId());
			
			inquiryFromCustomer.setPostedDate(DateUtil.getCurrentDate());			
			inquiryDao.save(inquiryFromCustomer);
		}
	}
	public Address getShippingAddressByCustomerId(int customerId){
		Customer customer = new Customer();
		customer =getCustomerByCustomerId(customerId) ;
		return customer.getShippingAddress();
	}
	
	public Customer getCustomerByUserName(String userName) {		
		return customerDao.findByUserName(userName);
	}	
	
	public List<Inquiry> listParentInquiriesPostedByCustomer(int adminUserId) {
		return inquiryDao.findParentInquiriesPostedToAdmin(adminUserId, true,3) ;		 

	}
	
	public List<Inquiry> findParentInquiriesOfCustomers(int customerId, boolean isCustomer,Boolean inquiryStatus){
		List<Inquiry> inquiryList = inquiryDao.findParentInquiriesOfCustomers(customerId, true, null);
		if(inquiryList==null)
			inquiryList = new ArrayList<Inquiry>();
		return inquiryList;
	}
	
	public Inquiry getInquiryByInquiryId(int inquiryId) {

        Inquiry inquiry = inquiryDao.findById(inquiryId);        
        
		return inquiry;
	}
	
	public List<Inquiry> findChildrenInquiryByParentInquiryId(int parentInquiry){
		List<Inquiry> inquiryList = inquiryDao.findChildrenInquiryByParentInquiryId(parentInquiry);
		if(inquiryList==null)
			inquiryList = new ArrayList<Inquiry>();
		return inquiryList;
	}
	
	public void updateInquiryToParentThread(int customerId,Inquiry newInquiryToParentThread) {
		inquiryDao.addChildInquiryToParentThreadCustomer(customerId, true, newInquiryToParentThread);	
	}
	
}
