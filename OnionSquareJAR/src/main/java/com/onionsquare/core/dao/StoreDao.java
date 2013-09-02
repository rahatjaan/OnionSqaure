package com.onionsquare.core.dao;

import java.util.Date;
import java.util.List;

import com.onionsquare.core.model.Store;
import com.onionsquare.core.util.OnionSquareConstants;

/**
 * Releases Store specific data access and persist methods.
 * @author 
 *
 */
public interface StoreDao extends GenericDAO<Store, Integer> {
	
	/**
	 * Return all the stores owned by a Seller. 
	 * @param sellerId: id of the seller who ownes a store.
	 * @return List of Store information owned by a seller.
	 */
	List<Store> findStoresBySellerId(Integer sellerId) ;
	
	/**
	 * Return Store object with store name passed as parameter
	 * @param storeName: String value of name of the store
	 * @return Store object fetched by storename passed as parameter.
	 */
	Store findStoreByStoreName(String storeName);
	/**
	 * Return store by subdomain name
	 * @param subDomainName
	 * @return
	 */	
	Store getStoreBySubDomainName(String subDomainName);
	/**
	 * Return all the active stores which has been active for 5 months and has no sales activity.
	 * Retrieve active stores created before noOfMonthsBeingInactive passed as parameter and has made no sales activity.
	 * @param noOfMonthsBeingInactive: no of months being inactive.
	 * @return
	 */

	List<Store> findNoSalesStores(Integer monthBeforeEmail);
	/**
	 * Return a list of stores which requires deactivation because of no-sales activity for the limit constants defined in   
	 * {@link com.onionsquare.comre.util.OnionSquareConstants}
	 * @return
	 */
	List<Store> findStoresRequiringDeactivation();
	/**
	 * Return stores with the stores status and email status passed as parameter.
	 * @param storeStatus : status of the store. Store retrieved is active is it is true otherwise inactive store is retrieved.
	 * @param emailStatus : specifies that if email notification for no-sales activity is sent.
	 * @return
	 */
	List<Store> findStoresByStatus(Boolean storeStatus, Boolean emailStatus) ;
	
	/**
	 * Test
	 * @return
	 */
	List<Store> findNoSalesActivityStores();
}
