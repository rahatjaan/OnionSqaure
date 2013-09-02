/**
 * 
 */
package com.onionsquare.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onionsquare.core.dao.SellerDao;
import com.onionsquare.core.dao.StoreDao;
import com.onionsquare.core.model.Seller;
import com.onionsquare.core.model.Store;
import com.onionsquare.core.service.OnionSquareSchedulingService;
import com.onionsquare.core.util.DateUtil;
import com.onionsquare.core.util.OnionSquareConstants;

@Service
public class OnionSquareSchedulingServiceImpl implements OnionSquareSchedulingService {

	@Autowired
	StoreDao storeDao;
	@Autowired
	SellerDao sellerDao ;
	@Autowired
	MailSender mailSender ;
	
	org.slf4j.Logger logger= LoggerFactory.getLogger(OnionSquareSchedulingServiceImpl.class);

 
	@PostConstruct
	//@Scheduled(cron = "0 0/1 * 1/1 * ?")	 //Call every one minutes
	@Scheduled(cron="0 0 12 1/1 * ? ") 	//Call at midnight every day
	@Transactional
	public void sendEmailToInactiveStore() {synchronized (OnionSquareSchedulingServiceImpl.class) {		
	 
		logger.debug("Job started at" +new Date()+" By the thread  "+Thread.currentThread().getName());
		//Find out all stores with no-sale activity for no-of-months since created.
		List<Store> inActiveStoreList=getInactiveStoresForEmailNotification();	
		List<Store> storesReceivingEmail = new ArrayList<Store>();
		for(Store inActiveStore: inActiveStoreList) {
			//Get the store's email
			
			sendEmailNotification(inActiveStore.getSeller().getUsername(),"OnionSquare.com");
			inActiveStore.setEmailStatus(true);
			storesReceivingEmail.add(inActiveStore);			
			//Send email to the stores 						
		}	
		storeDao.saveOrUpdateAll(storesReceivingEmail);		
		logger.debug("Job Ended at" +new Date()+" By the thread  "+Thread.currentThread().getName());			
		}
	}
//	@Scheduled(cron = "0 0/1 * 1/1 * ?")	 //Call every one minutes
	@Scheduled(cron="	0 0 12 1/1 * ? ") 	//Call at midnight every day
	@Transactional
	public void deactivateStoresWithNoSalesActivity(){ 
		//GET Those stoes with status=active and no sales activity for n months & email-notification has been sent & crosses n-months after email is sent are deactiated.
		logger.info("Fetching all stores needed deactivation on " + DateUtil.getCurrentDate());
		List<Store> storeWithNoSaleActivity = getStoresForDeactivation();
		//Deactivate each stores
		for(Store store: storeWithNoSaleActivity) {
			store.setStoreStatus(false);
			store.setDeactivatedDate(DateUtil.getCurrentDate());
		}
		//Update each of the store in db.
		logger.info("Deactivating stores.");
		storeDao.saveOrUpdateAll(storeWithNoSaleActivity);
	}
	@Scheduled(cron="0 0 12 1 1/1 ? ") //Call on mid night of first day of every month E.g. 	Saturday, June 1, 2013 12:00 PM, 2.	Monday, July 1, 2013 12:00 PM
	@Transactional
	public void resetProfitFromInvitationProgram() {
		//get all active sellers
		List<Seller> activeSellerList = sellerDao.findSellersByStatus(true);
		for(Seller seller: activeSellerList) {
			seller.setInvitationProfit(0.0);			
		}
		//reset invitation_profit of all the sellers
		sellerDao.saveOrUpdateAll(activeSellerList);
	}
	
	public List<Store> getStoresForDeactivation() {
		return storeDao.findStoresRequiringDeactivation() ;		 
	}
	
	public List<Store> getInactiveStoresForEmailNotification() {	
 		return storeDao.findNoSalesStores(OnionSquareConstants.LIMIT_FOR_NO_SALES_ACTIVITY);
	}	
	public void sendEmailNotification(String from, String to) {
		try{
		logger.info("Sending email to "+to);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("OnionSquare Registration <no-reply@onionsquare.com>");
		message.setTo(to);
		message.setSubject("Onionsquare | No Sales Activity Email Notification");
		message.setText("We have noticed no sales activity of your store since 5 months. We suggest you to take appropriate actions to continue your store. Your store will be deactivated after one months of this email if we notice no sales of your store during this period. Thank you.");
		logger.info("Sending no sales activity email notification to "+ to);
		mailSender.send(message);
		logger.info("Email sent to"+to);
		}catch(Exception e){
			logger.info("Mail cannot be send due to internal error");
		}
	}
}
