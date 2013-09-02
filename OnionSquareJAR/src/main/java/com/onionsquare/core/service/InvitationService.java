package com.onionsquare.core.service;

import java.util.List;

import com.onionsquare.core.model.Invitation;

public interface InvitationService {

	/**
	 * sends invitation to a seller
	 * 
	 * @param invitation
	 * @return
	 */
	void saveInvitation(Invitation invitation);

	/**
	 * List out all the invitation sent by a seller.
	 * 
	 * @param sellerId
	 *            : id of the seller
	 * @param status
	 *            : invitation status
	 * @return
	 */

	List<Invitation> getAllInvitationSentBySellerId(Integer sellerId,
			Boolean status);

	/**
	 * After an invitation is accepted by a seller, it is updated in the store
	 * of invitation sender
	 * 
	 * @param invitation
	 *            : updated invitation
	 * @return
	 */
	 void updateInvitationStatus(Invitation invitation);

	/**
	 * Calculates total points earned by a seller from an invitation program. A
	 * seller earns points from each sale made by the invited seller as a result
	 * of invitation.
	 * 
	 * @param sellerId
	 * @return
	 */
	Double calculatePointsEarnedFromInvitationProgram(Integer sellerId);
	/**
	 * Removes invitation from db.
	 * @param invitationId: id of invitation to be removed/deleted.
	 * @return
	 */
	boolean removeInvitation(int invitationId);
	/**
	 * Return an invitation by its id.
	 * @param invitationId
	 * @return
	 */
	Invitation getInvitationById(Integer invitationId) ;

}
