package com.onionsquare.core.dao;

import java.util.List;

import com.onionsquare.core.model.Inquiry;

public interface InquiryDao extends GenericDAO<Inquiry, Integer> {

	List<Inquiry> findActiveInquiryPostedBySeller(int sellerId); 
	/**
	 * Return all the parents inquiries sent by senderId passed as parameter.
	 * @param senderId : id of the user who created parent inquiry.
	 * @param isSeller : if the sender is seller then the sender user type is seller otherwise ADMIN.
	 * @param inquiryStatus: new if true otherwise closed.
	 * @return
	 */
	List<Inquiry> findParentInquiries(int senderId,boolean isSeller,Boolean inquiryStatus);
	
	boolean closeCurrentInquiryThread(int userId,Boolean isSeller, int inquiryIdToClose) ;
	/**
	 * Adds a response/update to a parent inquiry posted earlier.
	 * @param userId :user who post new response or update a inquiry.
	 * @param isSeller:  if the user currently updating the inquiry is seller.
	 * @param childInquiry: childInquiry to be added to a parent inquiry set as property parentInquiry
	 */
	void addChildInquiryToParentThread(int userId,Boolean isSeller,Inquiry childInquiry) ;
	/**
	 * List our all the Parent Inquiries posted for admin users with id passed as parameter.
	 * @param adminUserId : admin user id
	 * @param inquiryStatus: status of the inquiry
	 * @return
	 */
	List<Inquiry> findParentInquiriesPostedToAdmin(int adminUserId, Boolean inquiryStatus, int senderUserType);
	
	
	public List<Inquiry> findChildrenInquiryByParentInquiryId(int parentInquiryId);
	
	public void addChildInquiryToParentThreadCustomer(int userId,Boolean isCustomer,Inquiry childInquiry);
	
	public List<Inquiry> findParentInquiriesOfCustomers(int customerId, boolean isCustomer,Boolean inquiryStatus);
	
	public List<Inquiry>findInquiryPostedByGuest();
	
	public List<Inquiry> findInquiresByCriteria(Inquiry inquiry);
	
	public List<Inquiry> findParentInquiryBySenderOrReceiver(Integer userId, int userType,Boolean inquiryStatus);
   
	public List<Inquiry> findParentInquiryBySenderOrReceiverType(Integer userType1, int userType2,Boolean inquiryStatus);

	public List<Inquiry> findParentInquiryBySenderOrReceiverManager(Integer userId, int userType1, int userType2,Boolean inquiryStatus); 


}
