package com.onionsquare.core.util;

public interface OnionSquareConstants {

	String ADMIN_TYPE 		= "admin";
	String SELLER_TYPE 		= "seller";
	String CUSTOMER_TYPE 	= "customer";
	
	String ROLE_ADMIN 		= "ROLE_ADMIN";
	String ROLE_SELLER 		= "ROLE_SELLER";
	String ROLE_CUSTOMER 	= "ROLE_CUSTOMER";
	String ROLE_MANAGER     =  "ROLE_MANAGER";
	
//	String BASE_URL			= "http://localhost:8080/OnionSquareWAR/";
//	String UPLOAD_URL       =  "D:/imageupload/";


	String BASE_URL			= "http://www.onionsquare.com/";
	String UPLOAD_URL       =  "/home/onion123/onionuploads/";

	
//	String PAYPAL_EMAIL     =  "kops513-facilitator@gmail.com";
//	String PAYPAL_EMAIL     =  "onionsquare12-facilitator@gmail.com";
	int MAX_PRODUCT_IMAGE_UPLOAD = 3;
	
/**
 * MODULE BILLING AND PAYMENT
 */
	double SALES_TO_POINT_CONVERSION_FACTOR		= 0.5 ;
	double RATE_OF_CHARGE_FOR_STORE = 8 ;
	double MAX_DISCOUNT_ALLOWED_FROM_INVITATION=2 ;
	/**
	 * Specifies the number of months for no-sale activity of store after creation. After a store
	 * crosses this limit then an email subject no-sale activity is send to stores.
	 */
	final int LIMIT_FOR_NO_SALES_ACTIVITY=1 ;
	/**
	 * Specifies the extra number of months after email notification after which stores are deactivated for no-sale
	 * activity.
	 */
	final int LIMIT_FOR_NO_SALE_ACTIVITY_AFTER_EMAIL_NOTIFICATION =1 ;
	
/**
 * MODULE SELLER-CUSTOMER INQUIRY 
 */

	public final int USER_TYPE_SELLER = 1;
	public final int USER_TYPE_CUSTOMER = 3;
	public final int USER_TYPE_ADMIN= 2;
	public final int USER_TYPE_GUEST = 4;
	public final int USER_TYPE_MANAGER= 5;

	public final Boolean INQUIRY_STATUS_NEW = true;	 
	public final Boolean INQUIRY_STATUS_CLOSED=false;
	public final int PARENT_INQUIRY_ID=0 ;
	
	
	/**Menu
	 * 
	 */
	public final String HOME = "HOME";
    public final String LOGIN = "LOGIN";
    public final String CUSTOMER_HOME = "CUSTOMER HOME";
    public final String SIGNUP = "SIGN UP";
    public final String UPDATE_PROFILE = "UPDATE PROFILE";
    public final String CHANGE_PASSWORD = "CHANGE PASSWORD";
    public final String ORDER = "ORDER";
    public final String ORDER_DETAIL ="ORDER DETAIL";
    public final String INQUIRY = "INQUIRY";
    public final String BILLING_SHIPPING_DETAIL = "BILLING AND SHIPPING DETAIL";
    public final String VIEW_CART = "VIEW CART";
    public final String PROCEED_TO_CHECKOUT = "PROCEED TO CHECKOUT";
    public final String VIEWED_PRODUCTS = "VIEWED PRODUCTS";
    public final String MESSAGE_DETAIL = "MESSAGE DETAIL";
    public final String MESSAGES = "MESSAGES";
    public final String NEW_MESSAGE = "NEW MESSAGE";
    


}
