package com.onionsquare.core.service;

import java.util.List;

import com.onionsquare.core.model.Inquiry;
import com.onionsquare.core.model.Seller;

public interface SellerService {

	/**
	 * Check if seller exists with provided username
	 * 
	 * @param seller
	 *            : New Seller
	 * @return Seller : recently signed up seller
	 */
	Seller signUpSeller(Seller seller);

	/**
	 * Updates a profile of a seller.
	 * 
	 * @param newProfile
	 *            : new profile of a seller
	 */
	void updateSeller(Seller newSellerProfile);

	/**
	 * Return a list of seller with a given status.
	 * 
	 * @return list of seller with status supplied.
	 * @param sellerStatus
	 *            is the boolean representation of seller's status.
	 */
	List<Seller> listAllSeller(Boolean sellerStatus);

	/**
	 * Return true is a seller exists in database otherwise return false.
	 * 
	 * @param seller
	 *            : seller to check if exists in database
	 * @return flag true or false depending on the existence of a seller in
	 *         database.
	 */

	boolean isSellerExists(Seller seller);

	/**
	 * Return Seller object with seller's user name
	 * 
	 * @Parm sellerName: seller's user name.
	 */

	Seller getSellerBySellerName(String sellerName);

	/**
	 * Return Seller by seller id
	 */
	Seller getSellerBySellerId(Integer sellerId);
	/**
	 * Removes a seller from db. Returns true if the seller is removed successfully, otherwise returns false.
	 * @param sellerId
	 * @return
	 */
	boolean removeSellerBySellerId(int sellerId);
	/**
	 * Return a list of sellers who are refered/invited by a seller with an id passed as parameter.
	 * @param invitingSellerId: id of a seller inviting sellers.
	 * @return
	 */
	
	List<Seller> getSellersInvitedBySeller(Integer invitingSellerId); 
	/**
	 * Post an inquiry by a seller with seller id 
	 * @Param sellerId: id of a selle who posts this inquiry passed in parameter.
	 * @param inquiryFromSeller : Inquiry object to be posted.
	 */
	void sendInquiryToWebMaster(int selleId, Inquiry inquiryFromSeller);
	/**
	 * If parent inquiry is not null, add inquiry to current thread. Otherwise adds the inquiry as new inquiry.
	 * @param sellerId
	 * @param newInquiryToParentThread
	 */	
	void updateInquiryToParentThread(int sellerId, Inquiry newInquiryToParentThread) ;
	/**
	 * Closes a current thread. A closed thread is not longer updated by seller or admin.
	 * @param sellerId
	 * @param inquiryIdToClose
	 * @return
	 */
	boolean closeCurrentInquiryThread(int sellerId,int inquiryIdToClose); 
	/**
	 * Returns list of inquiries posted by seller with id passed as parameter.
	 * @param sellerId id of the seller posting inquiry
	 * @return
	 */
	public List<Inquiry> listParentInquiriesPostedBySeller(int sellerId);	
	
	public Inquiry getInquiryByInquiryId(int inquiryId) ;
	
	public List<Inquiry> findChildrenInquiryByParentInquiryId(int inquiryId);

}
