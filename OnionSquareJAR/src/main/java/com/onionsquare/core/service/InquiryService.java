package com.onionsquare.core.service;

import java.util.List;

import com.onionsquare.core.model.Inquiry;

public interface InquiryService {
	
	public List<Inquiry> findInquiresByCriteria(Inquiry inquiry);
	
	public List<Inquiry> findParentInquiryBySenderOrReceiver(Integer userId, int userType,Boolean inquiryStatus);
	
	public void updateInquiryToParentThread(int sellerId,Inquiry newInquiryToParentThread);

	public boolean closeCurrentInquiryThread(int sellerId, int inquiryIdToClose);

	public Inquiry getInquiryByInquiryId(int inquiryId);
	
	public List<Inquiry> listParentInquiriesPostedBySeller(int sellerId);
	
	public List<Inquiry> findChildrenInquiryByParentInquiryId(int parentInquiryId);
	
	public void saveInquiry(Inquiry inquiry) ;
	
	public List<Inquiry> findParentInquiryBySenderOrReceiverType(Integer userType1, int userType2,Boolean inquiryStatus);
	
	public List<Inquiry> findParentInquiryBySenderOrReceiverManager(Integer userId, int userType1, int userType2,Boolean inquiryStatus); 



}
