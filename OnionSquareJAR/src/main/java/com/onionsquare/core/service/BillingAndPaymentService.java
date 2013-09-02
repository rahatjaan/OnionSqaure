package com.onionsquare.core.service;

public interface BillingAndPaymentService {
	
	Double getPointsEarnedBySellerFromInvitationProgram(Integer sellerId);
	
	/**
	 * Takes monthly  sales of a store and return the payment to be made by a store in a particular month.
	 * @param monthlySalesOfStore: monthly sales amount made by store.
	 * @return
	 */
	Double getTotalPayableFeeByStore(Double monthlySalesOfStore) ;
	
	/**
	 * Return points earned by a store for the total sales amount made by the store invited by a seller.
	 * @param totalSalesAmount: total sales made by a seller
	 * @return
	 */
	Double convertSalesOfStoreToPoints(Double totalSalesAmount);
	/**
	 * Return discount got by a seller for the monthly points earned from invitation program. 
	 * If monthly points earned passed as parameter is greater than 2 then maximum discount obtained is 2. Otherwise 
	 * the discount obtained is monthly points earned.
	 * @param monthlyPointsEarned
	 * @return
	 */
	Double getDiscountAmountObtainedFromInvitationProgram(Double monthlyPointsEarned);
	/**
	 * Return total payable amount of a store with id passed as parameter, it is the amount obtained after reduction of the discount 
	 * amount obtained from  invitation program.
	 * @param sellerId :id of a seller
	 * @return
	 */
	Double getTotalPayaleFeeAfterDiscountFromInvitationProgram(Integer sellerId); 
}
