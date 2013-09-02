package com.onionsquare.core.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onionsquare.core.dao.OrderDao;
import com.onionsquare.core.model.Customer;
import com.onionsquare.core.model.Order;
import com.onionsquare.core.model.OrderStatus;
import com.onionsquare.core.model.Report;
import com.onionsquare.core.model.Store;
import com.onionsquare.core.util.DateUtil;

@Repository("orderDao")
@Transactional
public class OrderDaoImpl extends AbstractDAO<Order, Integer> implements
		OrderDao {

	private static final Logger logger = Logger.getLogger(OrderDaoImpl.class);
	@Autowired
	public OrderDaoImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List<Order> findOrdersByStoreId(Integer storeId,int pageNumber,int pageSize) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Order.class);		 
		Store store = new Store();
		store.setStoreId(storeId);
		detachedCriteria.add(Restrictions.eq("store", store));
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setOrderStatusId(3);
		detachedCriteria.add(Restrictions.ne("orderStatus", orderStatus));
		List<Order> orderList = findByCriteria(detachedCriteria,pageNumber,pageSize);
		return orderList;
	}
	 
	public List<Order> findOrdersByCustomerId(Integer customerId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Order.class);
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		detachedCriteria.add(Restrictions.eq("customer", customer));
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setOrderStatusId(3);
		detachedCriteria.add(Restrictions.ne("orderStatus", orderStatus));
		return findByCriteria(detachedCriteria);
	}
	
	/**
	 * @TODO complete the filter criteria.
	 * 
	 */
	public List<Order> findOrdersByStoreId(Integer storeId, Date minDate, Date maxDate) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Order.class);
		Store store = new Store();
		store.setStoreId(storeId);	
		detachedCriteria.add(Restrictions.eq("store", store));
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setOrderStatusId(3);
		detachedCriteria.add(Restrictions.ne("orderStatus", orderStatus));
		
		if(minDate !=null && maxDate == null) {			
			detachedCriteria.add(Restrictions.between("createdDate", minDate, DateUtil.getCurrentDate()));
		}
		else {			
				if(minDate != null && maxDate != null) {
				detachedCriteria.add(Restrictions.between("createdDate", minDate, maxDate));
			}	
		}		
		List<Order> orderList = findByCriteria(detachedCriteria);		
		return orderList;
	}
	
	/**
	 * @TODO complete the filter criteria.
	 * 
	 */
	public List<Order> findOrdersByFilterCriteria(Order order) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Order.class);
		if(order.getStore()!=null)
			detachedCriteria.add(Restrictions.eq("store", order.getStore()));
		if(order.getOrderStatus()!=null){
			detachedCriteria.add(Restrictions.eq("orderStatus", order.getOrderStatus()));
		}else{
			OrderStatus orderStatus = new OrderStatus();
			orderStatus.setOrderStatusId(3);
			detachedCriteria.add(Restrictions.ne("orderStatus", orderStatus));
			
		}
		if(order.getCustomer()!=null)
			detachedCriteria.add(Restrictions.eq("customer", order.getCustomer()));
		if(order.getFromDate() !=null && order.getToDate() == null) {			
			detachedCriteria.add(Restrictions.between("createdDate", order.getFromDate(), DateUtil.getCurrentDate()));
		}
		else {			
				if(order.getFromDate() != null && order.getToDate() != null) {
				detachedCriteria.add(Restrictions.between("createdDate", order.getFromDate(), order.getToDate()));
			}	
		}
			
		List<Order> orderList = findByCriteria(detachedCriteria);		
		return orderList;
	}
	/**
	 * @TODO to be implemented in search module 
	 */
	public Integer countOrders(Integer storeId, Integer orderStatusId,
			Integer paymentMethodId, Date minDateCreated, Date maxDateCreated) {	
			return null ;
	}	
	public Double calculateTotalRevenueForStore(Integer storeId, Date minDate,
			Date maxDate) {
		List<Order> orderList = findOrdersByStoreId(storeId, minDate, maxDate);
		Double totalRevenue = 0.0;
		for(Order order: orderList) {
			if(order.getTotalAmount() != null) {
				totalRevenue += order.getTotalAmount();
			}
		}
		return totalRevenue;
	}
	public List<Report> findStoreRevenue(int storeId,Date minDate, Date maxDate) {	
		
		String quiry="select o.store.storeId, sum(o.totalAmount), count(o),avg(o.totalAmount) from Order o" ;
		StringBuilder queryBuilder = new StringBuilder(quiry) ;		
		queryBuilder.append(" WHERE ");
		queryBuilder.append(" o.store.storeId =:storeId ");
		queryBuilder.append(" GROUP BY o.store.storeId ");		
		Query storeReport=getSession().createQuery(queryBuilder.toString());
		//Set up pagination 
		storeReport.setFirstResult(1);
		storeReport.setMaxResults(10);
		//set parameters in query
		storeReport.setParameter("storeId", storeId);
		return convertListToReportDomain(storeReport.list());
	} 
 
	private List<Report> convertListToReportDomain(List list) {
		List<Report> reportModel = new ArrayList<Report>() ;
		Report report ;
		Iterator iterator =list.iterator(); 
		try {
			while(iterator.hasNext()) {			
				  Object[] row = (Object[])iterator.next();	
				  report = new Report() ;
				  report.setStoreId((Integer) row[0]);
				  report.setTotalAmount((Double)row[1]);
				  report.setTotalCount((Long)row[2]);
				  report.setAverage((Double)row[3]);
				  reportModel.add(report);				  
			}
		}
		catch(Exception e) {
			logger.error("Error during conversion of list to report bean"+e.getMessage());
		}
		return reportModel ;
	}
}
 