package com.onionsquare.core.dao.impl;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Session;
import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.onionsquare.core.dao.InquiryDao;
import com.onionsquare.core.model.Inquiry;
import com.onionsquare.core.util.DateUtil;
import com.onionsquare.core.util.OnionSquareConstants;

@Repository("inquiryDao")
public class InquiryDaoImpl extends AbstractDAO<Inquiry, Integer> implements
		InquiryDao {
	
	@Autowired 
	public InquiryDaoImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	private final String PARENT_INQUERY ="parentInquiry";
	private final String SENDER_USER_ID ="senderUserId";
	private final String SENDER_USER_TYPE="senderUserType";
	private final String INQUIRY_STATUS = "inquiryStatus";
	private final String RECEIVER_USER_ID="receiverUserId";
	private final String RECEIVER_USER_TYPE="receiverUserType";
	private final String INQUIRY_POSTED_DATE="postedDate";
	
		
	public List<Inquiry> findActiveInquiryPostedBySeller(int sellerId) {		
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inquiry.class);		
		detachedCriteria.add(Restrictions.eq("inquiryStatus", OnionSquareConstants.INQUIRY_STATUS_NEW));	
		
		Inquiry parentInquiry = new Inquiry();
		parentInquiry.setInqueryId(OnionSquareConstants.PARENT_INQUIRY_ID);
		detachedCriteria.add(Restrictions.eq(PARENT_INQUERY, parentInquiry));
		detachedCriteria.add(Restrictions.eq(SENDER_USER_ID, sellerId));
		detachedCriteria.add(Restrictions.eq(SENDER_USER_TYPE, OnionSquareConstants.USER_TYPE_SELLER));
		detachedCriteria.addOrder(Order.asc(INQUIRY_POSTED_DATE));
		return findByCriteria(detachedCriteria);		
	}
	public List<Inquiry> findChildrenInquiryByParentInquiryId(int parentInquiryId) {		
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inquiry.class);		
		detachedCriteria.add(Restrictions.eq(INQUIRY_STATUS, OnionSquareConstants.INQUIRY_STATUS_NEW));		
		Inquiry parentInquiry = new Inquiry();
		parentInquiry.setInqueryId(parentInquiryId);
		detachedCriteria.add(Restrictions.eq(PARENT_INQUERY, parentInquiry));	
		detachedCriteria.addOrder(Order.asc(INQUIRY_POSTED_DATE));
		return findByCriteria(detachedCriteria);		
	}
	public List<Inquiry> findParentInquiries(int senderId, boolean isSeller,Boolean inquiryStatus) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inquiry.class);		
		if(inquiryStatus != null) {
			detachedCriteria.add(Restrictions.eq(INQUIRY_STATUS,inquiryStatus));
		}
		//Set this inquiry as parent inquiry.
		Inquiry parentInquiry = new Inquiry();
		parentInquiry.setInqueryId(OnionSquareConstants.PARENT_INQUIRY_ID);
		detachedCriteria.add(Restrictions.eq(PARENT_INQUERY, parentInquiry));
		if(isSeller) {
			detachedCriteria.add(Restrictions.eq(SENDER_USER_ID, senderId));
			detachedCriteria.add(Restrictions.eq(SENDER_USER_TYPE, OnionSquareConstants.USER_TYPE_SELLER));				
		}
		else {
			 detachedCriteria.add(Restrictions.eq(SENDER_USER_TYPE, OnionSquareConstants.USER_TYPE_ADMIN));	
				detachedCriteria.add(Restrictions.eq(SENDER_USER_ID, senderId));

		}
		detachedCriteria.addOrder(Order.asc(INQUIRY_POSTED_DATE));
		return findByCriteria(detachedCriteria);
	}	
	
	public List<Inquiry> findParentInquiriesOfCustomers(int customerId, boolean isCustomer,Boolean inquiryStatus) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inquiry.class);		
		if(inquiryStatus != null) {
			detachedCriteria.add(Restrictions.eq(INQUIRY_STATUS,inquiryStatus));
		}
		//Set this inquiry as parent inquiry.
		Inquiry parentInquiry = new Inquiry();
		parentInquiry.setInqueryId(OnionSquareConstants.PARENT_INQUIRY_ID);
		detachedCriteria.add(Restrictions.eq(PARENT_INQUERY, parentInquiry));
		detachedCriteria.add(Restrictions.eq(SENDER_USER_ID, customerId));
		if(isCustomer) {				 
			detachedCriteria.add(Restrictions.eq(SENDER_USER_TYPE, OnionSquareConstants.USER_TYPE_CUSTOMER));				
		}
		else {
			 detachedCriteria.add(Restrictions.eq(SENDER_USER_TYPE, OnionSquareConstants.USER_TYPE_ADMIN));				 
		}
		detachedCriteria.addOrder(Order.asc(INQUIRY_POSTED_DATE));
		return findByCriteria(detachedCriteria);
	}
	public boolean closeCurrentInquiryThread(int userId,Boolean isSeller, int inquiryIdToClose) {
		Inquiry dbInquiry = findById(inquiryIdToClose);
		//if inquiry exists close it and return true.Otherwise return false.
		if (dbInquiry != null) {
			dbInquiry.setInquiryStatus(OnionSquareConstants.INQUIRY_STATUS_CLOSED);
			dbInquiry.setClosedDate(DateUtil.getCurrentDate());
			saveOrUpdate(dbInquiry);
			return true;
		} else {
			return false;
		}
	}
	public void addChildInquiryToParentThread(int userId,Boolean isSeller,Inquiry childInquiry) {

		Inquiry parentInquiry = childInquiry.getParentInquiry();
		parentInquiry = findById(parentInquiry.getInqueryId());
		if (parentInquiry != null) {			
			// Update to same parent thread.
			// Get id of a logged in seller from parameter..
			childInquiry.setSenderUserId(userId);
			childInquiry.setInquiryStatus(OnionSquareConstants.INQUIRY_STATUS_NEW);
			
			childInquiry.setPostedDate(DateUtil.getCurrentDate());
			
			if(isSeller) {
				childInquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_SELLER);
				childInquiry.setReceiverUserType(OnionSquareConstants.USER_TYPE_ADMIN);
				if(parentInquiry.getReceiverUserType()==OnionSquareConstants.USER_TYPE_ADMIN)
					childInquiry.setReceiverUserId(parentInquiry.getReceiverUserId());
				else
					childInquiry.setReceiverUserId(parentInquiry.getSenderUserId());
			}else{
				childInquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_ADMIN);
				childInquiry.setReceiverUserType(OnionSquareConstants.USER_TYPE_SELLER);
				if(parentInquiry.getReceiverUserType()==OnionSquareConstants.USER_TYPE_SELLER)
					childInquiry.setReceiverUserId(parentInquiry.getReceiverUserId());
				else
					childInquiry.setReceiverUserId(parentInquiry.getSenderUserId());
			}
			saveOrUpdate(childInquiry);
		} 
	}
	
	public void addChildInquiryToParentThreadCustomer(int userId,Boolean isCustomer,Inquiry childInquiry) {

		Inquiry parentInquiry = childInquiry.getParentInquiry();
		parentInquiry = findById(parentInquiry.getInqueryId());
		if (parentInquiry != null) {			
			// Update to same parent thread.
			// Get id of a logged in seller from parameter..
			childInquiry.setSenderUserId(userId);
			childInquiry.setInquiryStatus(OnionSquareConstants.INQUIRY_STATUS_NEW);
			childInquiry.setPostedDate(DateUtil.getCurrentDate());
			
			if(isCustomer) {
				childInquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_CUSTOMER);
				childInquiry.setReceiverUserType(OnionSquareConstants.USER_TYPE_ADMIN);
				if(parentInquiry.getReceiverUserType()==OnionSquareConstants.USER_TYPE_ADMIN)
					childInquiry.setReceiverUserId(parentInquiry.getReceiverUserId());
				else
					childInquiry.setReceiverUserId(parentInquiry.getSenderUserId());
			}else{
				childInquiry.setSenderUserType(OnionSquareConstants.USER_TYPE_ADMIN);
				childInquiry.setReceiverUserType(OnionSquareConstants.USER_TYPE_CUSTOMER);
				if(parentInquiry.getReceiverUserType()==OnionSquareConstants.USER_TYPE_CUSTOMER)
					childInquiry.setReceiverUserId(parentInquiry.getReceiverUserId());
				else
					childInquiry.setReceiverUserId(parentInquiry.getSenderUserId());
			}
			saveOrUpdate(childInquiry);
		} 
	}
	public List<Inquiry> findParentInquiriesPostedToAdmin(int adminUserId, Boolean inquiryStatus, int senderUserType) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inquiry.class);		
		if(inquiryStatus != null) {
			detachedCriteria.add(Restrictions.eq(INQUIRY_STATUS,inquiryStatus));
		}
		if(senderUserType>0)
		{
			detachedCriteria.add(Restrictions.eq(SENDER_USER_TYPE,senderUserType));

		}
		//Set this inquiry as parent inquiry.
		Inquiry parentInquiry = new Inquiry();
		parentInquiry.setInqueryId(OnionSquareConstants.PARENT_INQUIRY_ID);
		detachedCriteria.add(Restrictions.eq(PARENT_INQUERY, parentInquiry));
		
		detachedCriteria.add(Restrictions.eq(RECEIVER_USER_ID, adminUserId));
		detachedCriteria.add(Restrictions.eq(RECEIVER_USER_TYPE, OnionSquareConstants.USER_TYPE_ADMIN));
		//Order by date asc.
		detachedCriteria.addOrder(Order.asc(INQUIRY_POSTED_DATE));
		return findByCriteria(detachedCriteria);
	}
	
	public List<Inquiry>findInquiryPostedByGuest(){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inquiry.class);
		detachedCriteria.add(Restrictions.eq(SENDER_USER_TYPE, OnionSquareConstants.USER_TYPE_GUEST));
		detachedCriteria.add(Restrictions.eq(RECEIVER_USER_TYPE, OnionSquareConstants.USER_TYPE_ADMIN));
		return findByCriteria(detachedCriteria);

	}
	
	
	public List<Inquiry> findInquiresByCriteria(Inquiry inquiry) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inquiry.class);
		List<Inquiry> inquiryList = new ArrayList<Inquiry>();
		if(inquiry!=null){
		if(inquiry.getInquiryStatus() != null) {
			detachedCriteria.add(Restrictions.eq(INQUIRY_STATUS,inquiry.getInquiryStatus()));
		}
		
		if(inquiry.getParentInquiry()!=null){
			detachedCriteria.add(Restrictions.eq(PARENT_INQUERY, inquiry.getParentInquiry()));

		}
		
		if(inquiry.getSenderUserId()>0 && inquiry.getSenderUserId()!=null){
			detachedCriteria.add(Restrictions.eq(SENDER_USER_ID, inquiry.getSenderUserId()));

		}
		
		if(inquiry.getReceiverUserId()>0 && inquiry.getReceiverUserId()!=null){
			detachedCriteria.add(Restrictions.eq(RECEIVER_USER_ID, inquiry.getReceiverUserId()));				

		}
		
		if(inquiry.getSenderUserType()>0 && inquiry.getSenderUserType()!=null ){
			detachedCriteria.add(Restrictions.eq(SENDER_USER_TYPE, inquiry.getSenderUserType()));				

		}
		
		if(inquiry.getReceiverUserType()>0 && inquiry.getReceiverUserType()!=null){
			detachedCriteria.add(Restrictions.eq(RECEIVER_USER_TYPE, inquiry.getReceiverUserType()));				

		}
	
		detachedCriteria.addOrder(Order.asc(INQUIRY_POSTED_DATE));
		return findByCriteria(detachedCriteria);
		}
		
		return inquiryList;
		
	}
	
	public List<Inquiry> findParentInquiryBySenderOrReceiver(Integer userId, int userType,Boolean inquiryStatus) {
		  DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inquiry.class);  
		  if(inquiryStatus != null) {
		   detachedCriteria.add(Restrictions.eq(INQUIRY_STATUS,inquiryStatus));
		  }    
		  if(userType>0)
		  {
		   detachedCriteria.add(
		     Restrictions.disjunction()
		     .add(Restrictions.conjunction()
		       .add(Restrictions.eq(SENDER_USER_TYPE, userType))
		       .add(Restrictions.eq(SENDER_USER_ID, userId))
		       )
		     .add(Restrictions.conjunction()
		       .add(Restrictions.eq(RECEIVER_USER_TYPE, userType))
		       .add(Restrictions.eq(RECEIVER_USER_ID, userId))
		       ));
		  }
		  //Set this inquiry as parent inquiry.
		  Inquiry parentInquiry = new Inquiry();
		  parentInquiry.setInqueryId(OnionSquareConstants.PARENT_INQUIRY_ID);
		  detachedCriteria.add(Restrictions.eq(PARENT_INQUERY, parentInquiry));
		  //Order by date asc.
		  detachedCriteria.addOrder(Order.asc(INQUIRY_POSTED_DATE));
		  return findByCriteria(detachedCriteria);
		 }
	
	public List<Inquiry> findParentInquiryBySenderOrReceiverManager(Integer userId, int userType1, int userType2,Boolean inquiryStatus) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inquiry.class);
		if(inquiryStatus != null) {
		   detachedCriteria.add(Restrictions.eq(INQUIRY_STATUS,inquiryStatus));
		  }    
		  if(userType1>0)
		  {
		   detachedCriteria.add(
		     Restrictions.disjunction()
		     .add(Restrictions.conjunction()
		       .add(Restrictions.eq(SENDER_USER_TYPE, userType1))
		       .add(Restrictions.eq(SENDER_USER_ID, userId))
		       .add(Restrictions.disjunction()
		    		   .add(Restrictions.eq(RECEIVER_USER_TYPE,userType2))
		    		   .add(Restrictions.eq(RECEIVER_USER_TYPE, userType1)))
		       )
		     .add(Restrictions.conjunction()
		       .add(Restrictions.eq(RECEIVER_USER_TYPE, userType1))
		       .add(Restrictions.eq(RECEIVER_USER_ID, userId))
		       .add(Restrictions.disjunction()
		    		   .add(Restrictions.eq(SENDER_USER_TYPE,userType2))
		    		   .add(Restrictions.eq(SENDER_USER_TYPE, userType1))
		    	)
		      )
		    );
		  }
		  //Set this inquiry as parent inquiry.
		  Inquiry parentInquiry = new Inquiry();
		  parentInquiry.setInqueryId(OnionSquareConstants.PARENT_INQUIRY_ID);
		  detachedCriteria.add(Restrictions.eq(PARENT_INQUERY, parentInquiry));
		  //Order by date asc.
		  detachedCriteria.addOrder(Order.asc(INQUIRY_POSTED_DATE));
		  return findByCriteria(detachedCriteria);
		 }
	
	public List<Inquiry> findParentInquiryBySenderOrReceiverType(Integer userType1, int userType2,Boolean inquiryStatus) {
		  DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inquiry.class);  
		  if(inquiryStatus != null) {
		   detachedCriteria.add(Restrictions.eq(INQUIRY_STATUS,inquiryStatus));
		  }    
		  if(userType1>0 && userType2>0){
		   detachedCriteria.add(
		     Restrictions.disjunction()
		     .add(Restrictions.conjunction()
		       .add(Restrictions.eq(SENDER_USER_TYPE, userType1))
		       .add(Restrictions.eq(RECEIVER_USER_TYPE, userType2))
		       )
		     .add(Restrictions.conjunction()
		       .add(Restrictions.eq(SENDER_USER_TYPE, userType2))
		       .add(Restrictions.eq(RECEIVER_USER_TYPE, userType1))
		       ));
		  }
		  
		  //Set this inquiry as parent inquiry.
		  Inquiry parentInquiry = new Inquiry();
		  parentInquiry.setInqueryId(OnionSquareConstants.PARENT_INQUIRY_ID);
		  detachedCriteria.add(Restrictions.eq(PARENT_INQUERY, parentInquiry));
		  //Order by date asc.
		  detachedCriteria.addOrder(Order.asc(INQUIRY_POSTED_DATE));
		  return findByCriteria(detachedCriteria);
		 }
	
}
