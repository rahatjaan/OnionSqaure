package com.onionsquare.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onionsquare.core.dao.LineItemDao;
import com.onionsquare.core.model.LineItem;
import com.onionsquare.core.service.LineItemService;

@Service
public class LineItemServiceImpl implements LineItemService {
	
	@Autowired
	LineItemDao lineItemDao ;	
	
	
	public List<LineItem> listLineItemByOrderId(Integer orderId) {	 
			return lineItemDao.findLineItemsByOrderId(orderId);			
	}
	public void saveLineItemList(List<LineItem> lineItemList){			 		
		lineItemDao.saveOrUpdateAll(lineItemList);
	}
	
	public LineItem getLineItemById(Integer lineItemId) {		 
			return lineItemDao.findById(lineItemId);	 
	}
	public  void saveLineItem(LineItem lineItem) {
		lineItemDao.save(lineItem);
	}
	public List<LineItem> listAll() {
		List<LineItem> lineItemList = new ArrayList<LineItem>() ;
		lineItemList=lineItemDao.findAll();
		return lineItemList;
	}
	public void removeLineItem(Integer lineItemId) {
		LineItem lineItem = new LineItem();
		lineItem.setLineItemId(lineItemId);
		lineItemDao.delete(lineItem);
	}
	public Integer getTotalItemsInLineItemList(List<LineItem> lineItemList) {
		
		Integer totalNumberOfItem=0 ;
		for(LineItem li : lineItemList) {
			totalNumberOfItem += li.getQuantity() ;
		}
		return totalNumberOfItem ;
	}
}
