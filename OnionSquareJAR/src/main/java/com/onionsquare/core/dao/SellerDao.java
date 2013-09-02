package com.onionsquare.core.dao;

import java.util.List;

import com.onionsquare.core.model.Seller;


public interface SellerDao extends GenericDAO<Seller, Integer> {
	
	/**
	 * This method is used for seller login. 
	 * @param userName String representation of seller's username
	 * @return Seller object.
	 */
	Seller findSellerByUserName(String userName);
	
	/**
	 * Find seller by status
	 * @param status
	 * @return
	 */
	List<Seller> findSellersByStatus(Boolean status);
	/**
	 * Return a list of sellers refered/invited by a seller with id passed as parameter
	 * @param invitingSellerId: seller id.
	 * @return
	 */
	List<Seller> findSellersInvitedBySeller(Integer invitingSellerId);
}
