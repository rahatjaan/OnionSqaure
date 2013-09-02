/**
 * Provides basic methods to add Stores in a seller's network.
 */

package com.onionsquare.core.service;

import java.util.List;

import com.onionsquare.core.model.SellerNetwork;

public interface SellerNetworkService {
	
	/**
	 * Add a store in a seller's network program.
	 * @param sellerNetwork: a seller to be added in a network,
	 */
	void addStoreInNetwork(SellerNetwork sellerNetwork) ;
	/**
	 * List all seller's in seller network program of a seller with id passed as parameter.
	 * @param sellerId: seller id 
	 */	
	List<SellerNetwork> listAllStoresInSellerNetwork(int sellerId,Boolean status);
	/**
	 * Return a SellerNetwork object with id passed as parameter.
	 * @param sellerNetworkId: id of sellernetwork.
	 */	
	SellerNetwork getSellerInNetworkById(int sellerNetworkId); 
	
	/**
	 * Removes a store form a seller's network program.
	 * @param sellerNetworkId: id of a sellernetwork.
	 */	
	 void removeSellerFromSellerNetworkProgram(SellerNetwork sellerNetwork);
	
	/**
	 * updates a sellernetwork object.
	 * @param sellerNetwork
	 */
	 void updateSellerNetwork(SellerNetwork sellerNetwork); 
	 /**
	  * Retrieve all the deleted items
	  */
	 List<SellerNetwork> getAllDeletedItems();
		
	

}
