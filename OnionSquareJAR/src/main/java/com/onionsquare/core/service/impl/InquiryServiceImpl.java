package com.onionsquare.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onionsquare.core.dao.InquiryDao;
import com.onionsquare.core.model.Inquiry;
import com.onionsquare.core.service.InquiryService;

@Service
public class InquiryServiceImpl implements InquiryService{
	
	@Autowired
	private InquiryDao inquiryDao;
	
	private List<Inquiry> inquiryList;

	@Override
	public List<Inquiry> findInquiresByCriteria(Inquiry inquiry) {
		inquiryList = inquiryDao.findInquiresByCriteria(inquiry);
		if(inquiryList==null)
			inquiryList = new ArrayList<Inquiry>();			
		return inquiryList;
	}

	@Override
	public List<Inquiry> findParentInquiryBySenderOrReceiver(Integer userId,int userType, Boolean inquiryStatus) {
		inquiryList = inquiryDao.findParentInquiryBySenderOrReceiver(userId, userType, inquiryStatus);
		if(inquiryList==null)
			inquiryList = new ArrayList<Inquiry>();		
		return inquiryList;
	}
	
	
	public void updateInquiryToParentThread(int sellerId,Inquiry newInquiryToParentThread) {
		inquiryDao.addChildInquiryToParentThread(sellerId, true, newInquiryToParentThread);	
	}

	public boolean closeCurrentInquiryThread(int sellerId, int inquiryIdToClose) {
		return inquiryDao.closeCurrentInquiryThread(sellerId, true, inquiryIdToClose);
	}

	public Inquiry getInquiryByInquiryId(int inquiryId) {
		return inquiryDao.findById(inquiryId);
	}
	
	public List<Inquiry> listParentInquiriesPostedBySeller(int sellerId) {
		return inquiryDao.findParentInquiries(sellerId, true, true);		 
	} 
	
	public List<Inquiry> findChildrenInquiryByParentInquiryId(int parentInquiryId){
		List<Inquiry> inquiryList  = inquiryDao.findChildrenInquiryByParentInquiryId(parentInquiryId);
		if(inquiryList==null)
			inquiryList = new ArrayList<Inquiry>();
		return inquiryList;
	}
	
	public void saveInquiry(Inquiry inquiry) {	
		
		inquiryDao.save(inquiry);
	}
	
	public List<Inquiry> findParentInquiryBySenderOrReceiverType(Integer userType1, int userType2,Boolean inquiryStatus){
		List<Inquiry> inquiryList  = inquiryDao.findParentInquiryBySenderOrReceiverType(userType1, userType2, inquiryStatus);
		if(inquiryList==null)
			inquiryList = new ArrayList<Inquiry>();
		return inquiryList;
	}
	
	public List<Inquiry> findParentInquiryBySenderOrReceiverManager(Integer userId, int userType1, int userType2,Boolean inquiryStatus) {
		List<Inquiry> inquiryList  = inquiryDao.findParentInquiryBySenderOrReceiverManager(userId, userType1, userType2, inquiryStatus);
		if(inquiryList==null)
			inquiryList = new ArrayList<Inquiry>();
		return inquiryList;
	}

}
