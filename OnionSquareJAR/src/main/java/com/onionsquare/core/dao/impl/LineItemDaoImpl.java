package com.onionsquare.core.dao.impl;

import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.onionsquare.core.dao.LineItemDao;
import com.onionsquare.core.model.LineItem;
import com.onionsquare.core.model.Order;
import com.onionsquare.core.model.Product;

@Repository("lineItemDao")
public class LineItemDaoImpl extends AbstractDAO<LineItem, Integer> implements
		LineItemDao {
	
	@Autowired
	public LineItemDaoImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
 
	public List<LineItem> findLineItemsByOrderId(Integer orderId) {		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LineItem.class);
		Order order = new Order();
		order.setOrderId(orderId);		 
		detachedCriteria.add(Restrictions.eq("order",order));		
		List<LineItem> lineItemList = findByCriteria(detachedCriteria);		 		
		return lineItemList;
	}	
	
	public List<LineItem> findLineItemsByProductId(Integer productId){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LineItem.class);
		Product product = new Product();
		product.setProductId(productId);
		detachedCriteria.add(Restrictions.eq("product", product));
		List<LineItem> lineItemList = findByCriteria(detachedCriteria);
		return lineItemList;
		
	}
}
