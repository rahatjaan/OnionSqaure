/**
 * 
 */
package com.onionsquare.core.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.onionsquare.core.dao.StoreDao;
import com.onionsquare.core.model.Store;
import com.onionsquare.core.util.DateUtil;


public interface OnionSquareSchedulingService {
 
	/**
	 * Find out all stores with no sales activity and updates the status to inactive. 
	 * This method is called once at midnight every day.
	 */
	 void deactivateStoresWithNoSalesActivity();
	 
	 /**
	  * Send email to all the store with no sales activity for more than 5 months.
	  * This method is called once at midnight every day.
	  */
	 void sendEmailToInactiveStore() ;	 
	 /**
	  * Resets the profit from Invitation Program of a store to nill. This method is called on first day of every month at midnight.
	  */
	 void resetProfitFromInvitationProgram();	 
 }
