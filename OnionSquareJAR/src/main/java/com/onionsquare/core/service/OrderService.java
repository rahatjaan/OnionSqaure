package com.onionsquare.core.service;

import java.util.Date;
import java.util.List;

import com.onionsquare.core.model.Customer;
import com.onionsquare.core.model.Order;

public interface OrderService {
	/**
	 *
	 * Saves an order with shipping address. If shipping address id is null, saves new shipping address in db and
	 * then save order. If shipping address id is not null, it saves only order.
	 * 	
	 * 
	 * @param order
	 * @return
	 */
	Order createOrderForStore(Order order);

	/**
	 * Updates an order if it exists otherwise creates an order.
	 * 
	 * @param order
	 * @return
	 */
	void updateOrder(Order order);

	List<Order> listOrdersByStoreId(Integer storeId,int pageNumber,int pageSize);

	List<Order> listOrdersByCustomerId(Integer customerId);

	Order getOrderByOrderId(Integer orderId);

	/**
	 * Deletes an order form store.
	 * 
	 * @param order
	 */
	void removeOrderFromStore(Order order);

	/**
	 * Calculates total Revenue for a store between date interval
	 * 
	 * @param storeId
	 *            : id of a store
	 * @param minDate
	 *            : minimum date of order creation.
	 * @param maDate
	 *            : maximum date of order creation.
	 * @return
	 */
	List<Double> calculateTotalRevenueForStore(Integer storeId, Date minDate,
			Date maxDate);

	/**
	 * List out all the orders between minDate and maxDate of creation
	 * 
	 * @param storeId
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
	List<Order> listOrdersByStoreId(Integer storeId, Date minDate, Date maxDate);

	/**
	 * Counts orders for a store.
	 * 
	 * @param storeId
	 *            : id of a stores
	 * @param orderStatusId
	 *            : orderStatusId
	 * @param paymentMethodId
	 *            : payment method id
	 * @param minDateCreated
	 *            : minimum date of creation
	 * @param maxDateCreated
	 *            : maximum date of creation.
	 * @return
	 */
	Integer countOrders(Integer storeId, Integer orderStatusId,
			Integer paymentMethodId, Date minDateCreated, Date maxDateCreated);

	/**
	 * 
	 * @return
	 */
	List<Order> listAllOrders();
	
	public List<Order> findOrdersByFilterCriteria(Order order);
}
