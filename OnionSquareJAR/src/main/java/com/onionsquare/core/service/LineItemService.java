package com.onionsquare.core.service;

import java.util.List;

import com.onionsquare.core.model.LineItem;

public interface LineItemService {
	/**
	 * Return list of lineitems for an order with an id.
	 * 
	 * @param orderId
	 *            : id of an order.
	 * @return
	 */
 
	List<LineItem> listLineItemByOrderId(Integer orderId);

	/**
	 * saves a list of line items.
	 * 
	 * @param lineItemList
	 */
	void saveLineItemList(List<LineItem> lineItemList);

	/**
	 * Return a lineitem object with an id as parameter
	 * 
	 * @param lineItemId
	 *            : id of a lineitem
	 * @return
	 */
	 LineItem getLineItemById(Integer lineItemId);

	/**
	 * Save a lineitem object.
	 * 
	 * @param lineItem
	 */

	void saveLineItem(LineItem lineItem);

	List<LineItem> listAll();
	Integer getTotalItemsInLineItemList(List<LineItem> lineItemList);
}
