package com.onionsquare.core.util;

public interface OrderStatus {	
	/**
	 * Specifies that an order has recently placed for a store.
	 */
	public static final int NEW = 1 ;
	public static final int CANCELLED = 1 ;
	public static final int PENDING_PAYMENT = 1 ;
	public static final int AWAITING_SHIPMENT = 1 ;
	public static final int REFUNDED = 1 ;
}
