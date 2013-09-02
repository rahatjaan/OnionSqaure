package com.onionsquare.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onionsquare.core.dao.SellerNetworkDao;
import com.onionsquare.core.model.Seller;
import com.onionsquare.core.model.SellerNetwork;
import com.onionsquare.core.service.SellerNetworkService;

/**
 * @author Naresh
 * 
 */
@Service
public class SellerNetworkServiceImpl implements SellerNetworkService {

	@Autowired
	SellerNetworkDao sellerNetworkDao;

	public void addStoreInNetwork(SellerNetwork sellerNetwork) {
		sellerNetworkDao.save(sellerNetwork);
	}

	public List<SellerNetwork> listAllStoresInSellerNetwork(int sellerId,
			Boolean status) {
		return sellerNetworkDao.findAllStoresInSellerNetwork(sellerId, status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.onionsquare.core.service.SellerNetworkService#getSellerInNetworkById
	 * (int)
	 */
	public SellerNetwork getSellerInNetworkById(int sellerNetworkId) {
		return sellerNetworkDao.findById(sellerNetworkId);
	}

	public void removeSellerFromSellerNetworkProgram(SellerNetwork sellerNetwork) {
		sellerNetworkDao.delete(sellerNetwork);
	}

	public void updateSellerNetwork(SellerNetwork sellerNetwork) {
		sellerNetworkDao.update(sellerNetwork);
	}

	public List<SellerNetwork> getAllDeletedItems() {
		return sellerNetworkDao.findDeletedItems();

	}

}
