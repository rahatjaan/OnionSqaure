package com.onionsquare.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onionsquare.core.dao.AddressDao;
import com.onionsquare.core.dao.OrderDao;
import com.onionsquare.core.model.Address;
import com.onionsquare.core.model.LineItem;
import com.onionsquare.core.model.Order;
import com.onionsquare.core.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);
	
	@Autowired 	OrderDao orderDao;
	@Autowired AddressDao addressDao ;
	
	/**
	 * @TODO apply case when Shipping Address is same as Billing Address.
	 */
	public Order createOrderForStore(Order order) {	 
		logger.info("Saving order:");
		if(order != null) {								
			orderDao.saveOrUpdate(order);		
		}
		return order;
	}

	public void updateOrder(Order order) {
		orderDao.update(order);
	}

	public List<Order> listOrdersByStoreId(Integer storeId,int pageNumber, int pageSize) {	
		return orderDao.findOrdersByStoreId(storeId,pageNumber,pageSize);
	}

	public List<Order> listOrdersByCustomerId(Integer customerId) {
		 return orderDao.findOrdersByCustomerId(customerId);		 
	}

	public Order getOrderByOrderId(Integer orderId) {
		return orderDao.findById(orderId);		 
	}

	public void removeOrderFromStore(Order order) {
		orderDao.delete(order);		
	}

	public List<Double> calculateTotalRevenueForStore(Integer storeId, Date minDate,
			Date maxDate) {		
		List<Order> orderList = listOrdersByStoreId(storeId,minDate,maxDate);
		List<Double> revenue_cost = new ArrayList<Double>();
		Double totalRevenue = 0.0;
		Double cost = 0.0;
		for(Order order: orderList) {
			if(order.getTotalAmount() != null) {
				totalRevenue += order.getTotalAmount();
				cost += order.getProfitToOnionsquare();
			}
		}
		revenue_cost.add(totalRevenue);
		revenue_cost.add(cost);
		return revenue_cost;
	}

	public Integer countOrders(Integer storeId, Integer orderStatusId,
			Integer paymentMethodId, Date minDateCreated, Date maxDateCreated) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Order> listAllOrders() {
		List<Order> orderList = new ArrayList<Order>();
		orderList= orderDao.findAll();
		return orderList;
	}
 
	public List<Order> listOrdersByStoreId(Integer storeId, Date minDate, Date maxDate) {
		return orderDao.findOrdersByStoreId(storeId, minDate, maxDate);
	}
	
	public LineItem calculateLineItemSubtotal(LineItem item) {
		
		Double lineItemSubtotal = 0.0 ;
		if(item.getProduct() != null && item.getQuantity() != null) {
			try{
				Double unitPrice = item.getProduct().getUnitPrice();  
				Integer quantity = item.getQuantity();
				lineItemSubtotal = unitPrice * quantity ;
				item.setSubTotal(lineItemSubtotal);
			}catch(Exception e) {
				logger.error("Excepton caught at calculateLineItemSubtotal"+e.getMessage());
			}
		}
		return item;	 
	}
	/**
	 * @TODO Once customer places orderf  for a store, before confirming payment of the order,
	 * the customer is 
	 */
	public void updateOrderShippingAddress(int orderId, Address shippingAddress){
		
	}
	/**
	 * TotalAmount of an order is sum of the subtotal of lineitems in the order.
	 * TotalAmount excludes shipping costs and othe costs of the order.
	 * @param lineItemList : list of lineitem in the list.
	 * @return
	 */
	public Double getOrderTotalAmount(List<LineItem> lineItemList) {
		Double orderTotalAmount = 0.0 ;
		for(LineItem lineItem : lineItemList) {
			orderTotalAmount += lineItem.getSubTotal() ;			
		}
		return orderTotalAmount; 
	}
	
	public List<Order> findOrdersByFilterCriteria(Order order){
		return orderDao.findOrdersByFilterCriteria(order);
	}
}
