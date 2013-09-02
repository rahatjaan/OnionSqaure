package com.onionsquare.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onionsquare.core.dao.AdminDao;
import com.onionsquare.core.dao.InquiryDao;
import com.onionsquare.core.dao.SellerDao;
import com.onionsquare.core.model.Admin;
import com.onionsquare.core.model.Inquiry;
import com.onionsquare.core.model.Seller;
import com.onionsquare.core.service.SellerService;
import com.onionsquare.core.util.DateUtil;
import com.onionsquare.core.util.OnionSquareConstants;

@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerDao sellerDao;
	@Autowired
	private InquiryDao inquiryDao;
	@Autowired
	private AdminDao adminDao;

	public Seller signUpSeller(Seller seller) {
		Integer id = (Integer) sellerDao.save(seller);
		seller.setSellerId(id);
		return seller;
	}

	public List<Seller> listAllSeller(Boolean sellerStatus) {
		return sellerDao.findSellersByStatus(sellerStatus);
	}

	public boolean isSellerExists(Seller seller) {
		Seller existingSeller = sellerDao.findById(seller.getSellerId());
		if (existingSeller != null) {
			return false;
		}
		return true;
	}

	public void updateSeller(Seller newSellerProfile) {
		if (newSellerProfile != null) {
			newSellerProfile.setModifiedDate(new Date());
			sellerDao.update(newSellerProfile);
		}
	}

	public Seller getSellerBySellerName(String sellerName) {
		Seller seller;
		if (sellerName != null) {
			seller = sellerDao.findSellerByUserName(sellerName);
			return seller;
		}
		return null;
	}

	public Seller getSellerBySellerId(Integer sellerId) {
		return sellerDao.findById(sellerId);
	}

	public boolean removeSellerBySellerId(int sellerId) {
		Seller seller = new Seller();
		seller.setSellerId(sellerId);
		sellerDao.delete(seller);
		Seller db_seller = getSellerBySellerId(sellerId);
		if (db_seller == null) {
			return true;
		}
		return false;
	}

	public List<Seller> getSellersInvitedBySeller(Integer invitingSellerId) {
		return sellerDao.findSellersInvitedBySeller(invitingSellerId);

	}

	public void sendInquiryToWebMaster(int sellerId, Inquiry inquiryFromSeller) {

		if (inquiryFromSeller != null) {
			inquiryFromSeller.setSenderUserType(OnionSquareConstants.USER_TYPE_SELLER);
			//Set parent inquiry id to 0,
			Inquiry parentInquiry = new Inquiry();
			parentInquiry.setInqueryId(OnionSquareConstants.PARENT_INQUIRY_ID);			
			inquiryFromSeller.setParentInquiry(parentInquiry);
			// Get id of a logged in seller from parameter..
			inquiryFromSeller.setSenderUserId(sellerId);
			inquiryFromSeller.setInquiryStatus(OnionSquareConstants.INQUIRY_STATUS_NEW);
			
			inquiryFromSeller.setReceiverUserType(OnionSquareConstants.USER_TYPE_ADMIN);
			Admin admin = adminDao.findAdminByRole(OnionSquareConstants.ROLE_ADMIN);			
			inquiryFromSeller.setReceiverUserId(admin.getId());
			
			inquiryFromSeller.setPostedDate(DateUtil.getCurrentDate());			
			inquiryDao.save(inquiryFromSeller);
		}
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

	
}
