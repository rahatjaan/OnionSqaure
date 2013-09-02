package com.onionsquare.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onionsquare.core.dao.InvitationDao;
import com.onionsquare.core.model.Invitation;
import com.onionsquare.core.model.Seller;
import com.onionsquare.core.service.InvitationService;
import com.onionsquare.core.util.DateUtil;

@Service
public class InvitationServiceImpl implements InvitationService {

	@Autowired 
	InvitationDao invitationDao;
	
	public void saveInvitation(Invitation invitation) {	
			invitation.setInvitationDate(DateUtil.getCurrentDate());
			invitationDao.save(invitation);		 
	}
	public List<Invitation> getAllInvitationSentBySellerId(Integer sellerId,
			Boolean status) {		 		 
		return invitationDao.findAllInvitationBySellerId(sellerId, status);			   			
	}
	public void updateInvitationStatus(Invitation invitation) {		
		invitationDao.update(invitation);		 
	}
	public Invitation getInvitationById(Integer invitationId) {		
		return invitationDao.findById(invitationId);	
	}
	
	public Double calculatePointsEarnedFromInvitationProgram(Integer sellerId) {
		// TODO Auto-generated method stub
		//This stub must be implemented after orders are created for a store. 
		return null;
	}
	public boolean removeInvitation(int invitationId) {		
		Invitation invitation = new Invitation() ;
		invitation.setInvitationId(invitationId);
		invitationDao.delete(invitation);
		Invitation db_invitation= getInvitationById(invitationId); 
		if(db_invitation == null) {
			return true ;
		}
		return false ;
	}
}
