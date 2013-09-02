package com.onionsquare.core.service;

import java.util.List;

import com.onionsquare.core.model.Admin;
import com.onionsquare.core.model.Inquiry;

public interface AdminService {

	/**
	 * Creates an admin user with the specified user name and role.
	 * @param adminUser
	 */
	void createAdminUser(Admin adminUser) ;
	/**
	 * Updates/Modifies admin user existing in db. It even modifies username and password of the admin user. 
	 * @param updatedAdminUser
	 */
	void updateAdminUser(Admin updatedAdminUser);
	
	/**
	 * Return list of admin users created 
	 * @return
	 */
	public List<Admin> listAdminUsers();
	/**
	 * Return an admin by admin id passed as parameter.
	 * @param adminId: id of admin to find.
	 * @return
	 */
	public Admin getAdminUserById(int adminId);
	/**
	 * List out all posted to admin with admin id.
	 * @param adminUserId : currently logged in admin user id.
	 * @param inquiryStatus : status of the inquiry
	 * @return List of inquiries sent to admin.
	 */
	 List<Inquiry> listParentInquiriesPostedBySellers(int adminUserId);
	 /**
	  * Admin reads the posted inquiry and Updates the inquiry status to 'closed' to mark as read inquiry.
	  * @param inquiryId
	  * @param adminId
	  * @return true if the status is successfully update otherwise return false.
	  */
	 public boolean updateInquiry(int inquiryId,int adminId);
	 /**
	  * Return admin object with username passed as parameter.
	  * @param userName
	  * @return
	  */
	 Admin getAdminByUserName(String userName);
	 

	 public Inquiry getInquiryByInquiryId(int inquiryId); 


	 public List<Inquiry> listParentInquiriesPostedByCustomer(int adminUserId);
	 
	 public List<Inquiry> findChildrenInquiryByParentInquiryId(int parentInquiry);
	 
	 public void sendInquiryFromWebMaster(Inquiry inquiry);
	 
	 public void addChildInquiryToParentThread(int userId,Boolean isSeller,Inquiry childInquiry);
	 
	 public void addChildInquiryToParentThreadCustomer(int userId,Boolean isCustomer,Inquiry childInquiry);
	 
	 public List<Inquiry> listParentInquiriesPostedByGuest();
	 
	 public  List<Admin> getAdminByRoleName(String roleName);
	 
	 public List<Admin> getAdminListExcludingLoginAdmin(int adminId);

}
