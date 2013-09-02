package com.onionsquare.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onionsquare.core.dao.AdminDao;
import com.onionsquare.core.dao.InquiryDao;
import com.onionsquare.core.model.Admin;
import com.onionsquare.core.model.Inquiry;
import com.onionsquare.core.service.AdminService;
import com.onionsquare.core.util.DateUtil;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDao adminDao;
	@Autowired
	InquiryDao inquiryDao ;
	
	private List<Inquiry> inquiryList;

	public void createAdminUser(Admin adminUser) {
		if (adminUser != null) {
			// Check if adminUser exists in db.
			Admin dbAdmin = adminDao.findByUserName(adminUser.getUsername());
			if (dbAdmin == null) {
				adminUser.setCreatedDate(DateUtil.getCurrentDate());
				adminDao.save(adminUser);
			}
		}
	}

	public void updateAdminUser(Admin updatedAdminUser) {
		
			adminDao.update(updatedAdminUser);
		
	}
	public Admin getAdminByUserName(String userName) {
		String userRole=null ;
		return adminDao.findAdminByUserNameAndRole(userName, userRole);
	}
	public Admin getAdminUserById(int adminId) {
		return adminDao.findById(adminId);
	}

	public List<Admin> listAdminUsers() {
		return adminDao.findAll();
	}
	
	public Inquiry getInquiryById(int inquiryId) {
		return inquiryDao.findById(inquiryId);
	}
	
	
	public List<Inquiry> listParentInquiriesPostedBySellers(int adminUserId){		 
		return inquiryDao.findParentInquiriesPostedToAdmin(adminUserId, true,1) ;		 
	}
	
	public List<Inquiry> listParentInquiriesPostedByGuest(){		 
		inquiryList= inquiryDao.findInquiryPostedByGuest() ;	
		if(inquiryList==null)
			inquiryList = new ArrayList<Inquiry>();
		return inquiryList;
	}
	
	public boolean updateInquiry(int inquiryId,int adminId) {
		int userId=adminId ;
		Boolean isSeller=false;
		int inquiryIdToClose=inquiryId ;
		return inquiryDao.closeCurrentInquiryThread(userId, isSeller, inquiryIdToClose);
	}


	public Inquiry getInquiryByInquiryId(int inquiryId) {

        Inquiry inquiry = inquiryDao.findById(inquiryId);        
        
		return inquiry;
	}


	public List<Inquiry> listParentInquiriesPostedByCustomer(int adminUserId) {
		return inquiryDao.findParentInquiriesPostedToAdmin(adminUserId, true,3) ;		 

	}
	
	
	public List<Inquiry> findChildrenInquiryByParentInquiryId(int parentInquiry){
		inquiryList = inquiryDao.findChildrenInquiryByParentInquiryId(parentInquiry);
		if(inquiryList==null)
			inquiryList = new ArrayList<Inquiry>();
		return inquiryList;
	}
	
	public void sendInquiryFromWebMaster(Inquiry inquiry) {	
			
			inquiryDao.save(inquiry);
		}
	
	public void addChildInquiryToParentThread(int userId,Boolean isSeller,Inquiry childInquiry){
		inquiryDao.addChildInquiryToParentThread(userId, isSeller, childInquiry);
	}
	
	public void addChildInquiryToParentThreadCustomer(int userId,Boolean isCustomer,Inquiry childInquiry){
		inquiryDao.addChildInquiryToParentThreadCustomer(userId, isCustomer, childInquiry);
	}
	
	public List<Admin> getAdminByRoleName(String roleName) {
		String userRole=roleName ;
		List<Admin> adminList= adminDao.findAllAdminByRole(userRole);
		if(adminList==null)
			adminList = new ArrayList<Admin>();
		return adminList;
	}
	
	 public List<Admin> getAdminListExcludingLoginAdmin(int adminId){
		 List<Admin> adminList;
		 adminList= adminDao.getAdminListExcludingLoginAdmin(adminId);
		 if(adminList==null)
			 adminList = new ArrayList<Admin>();
		 return adminList;
	 }

	
}
