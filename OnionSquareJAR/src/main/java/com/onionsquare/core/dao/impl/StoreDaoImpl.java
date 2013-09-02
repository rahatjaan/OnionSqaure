package com.onionsquare.core.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onionsquare.core.dao.StoreDao;
import com.onionsquare.core.model.Seller;
import com.onionsquare.core.model.Store;
import com.onionsquare.core.util.DateUtil;
import com.onionsquare.core.util.OnionSquareConstants;

@Repository("storeDao")
public class StoreDaoImpl extends AbstractDAO<Store, Integer> implements
		StoreDao {
	
	private static final String STORE = "store";
	private static final String STORE_NAME = "storeName"; 
	private static final String STORE_STATUS= "store.storeStatus" ;
	private static final String STORE_CREATED_DATE= "store.createdDate" ;
	private static final String EMAIL_STATUS ="store.emailStatus";
	private static final String DEACTIVATED_DATE="deactivatedDate";

	@Autowired
	public StoreDaoImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List<Store> findStoresBySellerId(Integer sellerId) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Store.class);
		Seller seller = new Seller();
		seller.setSellerId(sellerId);
		detachedCriteria.add(Restrictions.eq("seller", seller));
		List<Store> storeList = new ArrayList<Store>();
		storeList = findByCriteria(detachedCriteria);
		return storeList;
	}

	public Store findStoreByStoreName(String storeName) {
		if (storeName != null & storeName != "") {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(Store.class);
			detachedCriteria.add(Restrictions.eq(STORE_NAME, storeName));
			detachedCriteria.add(Restrictions.eq("storeStatus", true));
			List<Store> storeList = findByCriteria(detachedCriteria);
			if (storeList != null && storeList.size() > 0) {
				return storeList.get(0);
			}
		}
		return null;
	}

	public Store getStoreBySubDomainName(String subDomainName) {

		if (subDomainName != null & subDomainName != "") {

			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(Store.class);
			detachedCriteria.add(Restrictions
					.eq("subDomainName", subDomainName));
			detachedCriteria.add(Restrictions.eq("storeStatus", true));
			List<Store> storeList = findByCriteria(detachedCriteria);
			if (storeList != null && storeList.size() > 0) {
				return storeList.get(0);
			}
		}
		return null;
	}

	public List<Store> findNoSalesStores(Integer monthBeforeEmail) {	
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Store.class,STORE);			 
		detachedCriteria.add(Restrictions.le(STORE_CREATED_DATE, DateUtil.addMonths(new Date(), -monthBeforeEmail)));		
		detachedCriteria.add(Restrictions.eq(STORE_STATUS, true));	
		detachedCriteria.createCriteria("orderList","order", CriteriaSpecification.LEFT_JOIN).add(Restrictions.isNull("order.orderId"));//use isNull required
		return findByCriteria(detachedCriteria);	
	}

	public List<Store> findStoresRequiringDeactivation() {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Store.class,STORE);			 
		detachedCriteria.add(Restrictions.le(STORE_CREATED_DATE, DateUtil.addMonths(new Date(), -(OnionSquareConstants.LIMIT_FOR_NO_SALE_ACTIVITY_AFTER_EMAIL_NOTIFICATION + OnionSquareConstants.LIMIT_FOR_NO_SALES_ACTIVITY))));		
		detachedCriteria.add(Restrictions.eq(STORE_STATUS, true));	
		detachedCriteria.add(Restrictions.eq(EMAIL_STATUS, true));
		detachedCriteria.createCriteria("orderList","order", CriteriaSpecification.LEFT_JOIN).add(Restrictions.isNull("order.orderId"));//use isNull required
		return findByCriteria(detachedCriteria);		 
	}	
	public List<Store> findStoresByStatus(Boolean storeStatus, Boolean emailStatus) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Store.class,STORE);	
		if(storeStatus != null){
			detachedCriteria.add(Restrictions.eq(STORE_STATUS, storeStatus));	
		}
		if(emailStatus != null) {
			detachedCriteria.add(Restrictions.eq(EMAIL_STATUS, emailStatus));
			detachedCriteria.add(Restrictions.gt(DEACTIVATED_DATE,DateUtil.getNullDate())) ;
		}	
		return findByCriteria(detachedCriteria);
	}
	 
	public List<Store> findNoSalesActivityStores() { 	
		Query noSalesStores =getSession().createQuery("from Store as Store LEFT JOIN Store.orderList as Order WHERE Order.orderId Is Null");
		List<Store> storeList=noSalesStores.list();
		return storeList ;		
	}	
}
