package com.onionsquare.core.dao;

import java.util.List;

import com.onionsquare.core.model.Invitation;

public interface InvitationDao extends GenericDAO<Invitation, Integer>{
	
	/**
	 * Returns a list of all the invitations sent by a seller with id sellerId
	 * @param sellerId: id of a seller 
	 * @param Status : status of a invitation.
	 * @return
	 */
	 List<Invitation> findAllInvitationBySellerId(Integer sellerId,Boolean Status);

}
