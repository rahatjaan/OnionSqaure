package com.onionsquare.core.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onionsquare.core.dao.OrderDao;
import com.onionsquare.core.dao.SellerDao;
import com.onionsquare.core.dao.StoreDao;
import com.onionsquare.core.model.Seller;
import com.onionsquare.core.model.Store;
import com.onionsquare.core.service.BillingAndPaymentService;
import com.onionsquare.core.util.OnionSquareConstants;

@Service
public class BillingAndPaymentServiceImpl implements BillingAndPaymentService {

	@Autowired
	SellerDao sellerDao ;	
	@Autowired 
	StoreDao storeDao ;	
	@Autowired
	OrderDao orderDao ;
	
	/**
	 * Calculates points earned by a seller from Invitation Program, with seller id passed as parameter.
	 */
	public Double getPointsEarnedBySellerFromInvitationProgram(Integer sellerId) {
		//Get all the sellers Si invited by a seller with id sellerId
		List<Seller> invitedSellerList = sellerDao.findSellersInvitedBySeller(sellerId);
		List<Store> invitedStores ;
		Double totalRevenueEarnedByStore =0.0 ;
		for(Seller invitedSeller: invitedSellerList) {
			//Get all stores owned by a seller Si
			invitedStores  =  storeDao.findStoresBySellerId(invitedSeller.getSellerId());
			for(Store invitedStore: invitedStores ){		
				//get total revenue Ti earned by each store Si & sum up the total revenue earned
				totalRevenueEarnedByStore += orderDao.calculateTotalRevenueForStore(invitedStore.getStoreId(), null,null) ;				
			}
		}
		//calculate points Pi earned from each revenue Ti.
		//Sum all the points Pi earned by each seller.
		return convertSalesOfStoreToPoints(totalRevenueEarnedByStore);
		
	}
	public Double convertSalesOfStoreToPoints(Double totalSalesAmount) {
		return (OnionSquareConstants.SALES_TO_POINT_CONVERSION_FACTOR * totalSalesAmount)/100 ;
	}
	public Double getTotalPayableFeeByStore(Double monthlySalesOfStore) {
		
		return (OnionSquareConstants.RATE_OF_CHARGE_FOR_STORE * monthlySalesOfStore )/100 ;
	}
	public Double getDiscountAmountObtainedFromInvitationProgram(Double monthlyPointsEarned) {
		if(monthlyPointsEarned > 0 && monthlyPointsEarned < OnionSquareConstants.MAX_DISCOUNT_ALLOWED_FROM_INVITATION ) {
			return monthlyPointsEarned;
		}
		else {
			return OnionSquareConstants.MAX_DISCOUNT_ALLOWED_FROM_INVITATION ;
		}
	}	
	public Double getTotalPayaleFeeAfterDiscountFromInvitationProgram(Integer sellerId) {
		
		//Obtain total sales of a store.
		Double monthlySalesOfStore = 0.0 ;
		//Obtain total payable fee of the seller from sales made by his/her store
		Double totalPayableFeeByStoreBeforeDiscount = getTotalPayableFeeByStore(monthlySalesOfStore);
		//Obtain points earned by seller from invitation program
		Double monthlyPointsEarned=getPointsEarnedBySellerFromInvitationProgram(sellerId);
		//Obtain discount obtained by the seller from points earned from invitation program.
		Double discountAmountObtainedFromInvitationProgram =getDiscountAmountObtainedFromInvitationProgram(monthlyPointsEarned);		
		return totalPayableFeeByStoreBeforeDiscount - discountAmountObtainedFromInvitationProgram;
	}
}
