package com.onionsquare.core.service;

import java.util.List;

import com.onionsquare.core.model.Store;

public interface StoreService {

	/**
	 * Creates a store. Defalult status of store created is active..
	 * 
	 * @param store
	 *            : Store being created
	 * @return Store object recently created.
	 */
	Store createNewStore(Store store);

	/**
	 * Updates Store details/profile for a store.
	 * 
	 * @param store
	 *            : Store with changed store details
	 */
	void updateStoreProfile(Store store);

	/**
	 * List out all stores being created in onionsquare.com
	 * 
	 * @return List of Store objects.
	 */
	List<Store> getAllStores();

	Store getStoreByStoreName(String storeName);

	/**
	 * Return Store created by a seller.
	 * 
	 * @param sellerId
	 *            : id of a seller creating a store.
	 * @return Store object created by the seller with seller id.
	 */
	Store getStoreBySellerId(Integer sellerId);

	/**
	 * Return store details by its id.
	 * 
	 * @param id
	 * @return
	 */

	Store getStoreById(Integer id);

	Store getStoreBySubDomainName(String subDomainName);

	/**
	 * Return all the stores which require email -notifications because of
	 * no-sales activity for 5 months.
	 */
	List<Store> getInactiveStoresForEmailNotification(); 

	/**
	 * Return stores that requires deactivation.
	 * 
	 * @return
	 */
	List<Store> getStoresForDeactivation();
	/**
	 * Return all the active stores in oinionsquare.com
	 * @return
	 */
	List<Store> getAllActiveStores();
	/**
	 * Return all stores which are deactivated due to no-sale activity of the store.
	 * @return
	 */
	List<Store> getAllDeactivatedStores();
}
