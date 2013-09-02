/**
 * 
 */
package com.onionsquare.core.dao;

import java.util.List;

import com.onionsquare.core.model.SellerNetwork;

/**
 * @author Naresh
 *
 */
public interface SellerNetworkDao extends GenericDAO<SellerNetwork, Integer> {
	
	/**
	 * List all stores in seller network program of a seller with id passed as parameter.
	 * If status supplied is null, then, return all stores in network program, return active stores in network
	 * for status=true and return inactive stores in network for status=false.
	 * @param sellerId: seller id 
	 * @param statsu: Boolean value to specify the status of the stores in network to be retrieved
	 */	
	List<SellerNetwork> findAllStoresInSellerNetwork(int selleId,Boolean status);

	List<SellerNetwork> findDeletedItems();

}
