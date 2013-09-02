package com.onionsquare.core.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.onionsquare.core.dao.StoreDao;
import com.onionsquare.core.model.Store;
import com.onionsquare.core.service.StoreService;
import com.onionsquare.core.util.DateUtil;
import com.onionsquare.core.util.OnionSquareConstants;

@Service
public class StoreServiceImpl implements StoreService {

	private static final Logger logger = Logger.getLogger(StoreServiceImpl.class);
	@Autowired
	StoreDao storeDao;

	public Store createNewStore(Store store) {
		store.setCreatedDate(DateUtil.getCurrentDate());
		Integer storeId = (Integer) storeDao.save(store);
		store.setStoreId(storeId);
		return store;
	}

	public Store getStoreById(Integer id) {
		return storeDao.findById(id);
		 
	}

	public List<Store> getAllStores() {
		return storeDao.findAll();
	}

	public Store getStoreByStoreName(String storeName) {
		return storeDao.findStoreByStoreName(storeName);
	}

	public Store getStoreBySellerId(Integer sellerId) {
		List<Store> storeList=storeDao.findStoresBySellerId(sellerId);
		if(storeList.size() > 0) {
			return storeList.get(0);
		}
		else {
			return null;
		}
	}

	public void updateStoreProfile(Store newStoreProfile) {
		newStoreProfile.setModifiedDate(DateUtil.getCurrentDate());
		storeDao.update(newStoreProfile);
	}
	public Store getStoreBySubDomainName(String subDomainName) {
		
		return  storeDao.getStoreBySubDomainName(subDomainName);		
	}

	public List<Store> getInactiveStoresForEmailNotification() {	
 		return storeDao.findNoSalesStores(OnionSquareConstants.LIMIT_FOR_NO_SALES_ACTIVITY);
	}	
 
	public List<Store> getStoresForDeactivation() {
		return storeDao.findStoresRequiringDeactivation() ;		 
	} 
	public List<Store> getAllActiveStores() {
		return storeDao.findStoresByStatus(true, null);
	}
	public List<Store> getAllDeactivatedStores() {
		return storeDao.findStoresByStatus(false,true);
	}
}
