package com.onionsquare.core.dao;

import java.util.Date;
import java.util.List;

import com.onionsquare.core.model.Order;
import com.onionsquare.core.model.Report;

public interface OrderDao extends GenericDAO<Order, Integer> {

	/**
	 * 
	 * @param storeId
	 *            : store id passed as parameter.
	 * @param pageNumber
	 *            : page number requested to fetch.
	 * @param pageSize
	 *            : number of rows per page
	 * @return
	 */
	List<Order> findOrdersByStoreId(Integer storeId, int pageNumber,
			int pageSize);

	List<Order> findOrdersByCustomerId(Integer customerId);

	/**
	 * Return a list of orders received by a store with id passed in parameter
	 * between min date and max date.
	 * 
	 * @param storeId
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
	List<Order> findOrdersByStoreId(Integer storeId, Date minDate, Date maxDate);

	/**
	 * Return total revenue earned by a store with id passed as parameter
	 * between min date and max date passed as parameter.
	 * 
	 * @param storeId
	 *            : id of store
	 * @param minDate
	 *            : min date
	 * @param maxDate
	 *            : max date.
	 * @return total revenue earned by the store between time period minDate and
	 *         max Date.
	 */
	Double calculateTotalRevenueForStore(Integer storeId, Date minDate,
			Date maxDate);

	List<Report> findStoreRevenue(int storeId, Date minDate, Date maxDate);
	
	public List<Order> findOrdersByFilterCriteria(Order order);

}