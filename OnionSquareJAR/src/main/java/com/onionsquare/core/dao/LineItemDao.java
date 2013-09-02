package com.onionsquare.core.dao;

import java.util.List;

import com.onionsquare.core.model.LineItem;

public interface LineItemDao extends GenericDAO<LineItem, Integer> {
	/**
	 * Return a list of 0 or more LineItem objects fetched by OrderId
	 * @param orderId: order id of 
	 * @return
	 */

	List<LineItem> findLineItemsByOrderId(Integer orderId);
	/**
	 * returns ist of lineitem whose productId equal to 
	 * @param productId
	 * @return
	 */
	public List<LineItem> findLineItemsByProductId(Integer productId);

}
