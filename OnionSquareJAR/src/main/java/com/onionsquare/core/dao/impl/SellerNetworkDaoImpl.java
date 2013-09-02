package com.onionsquare.core.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onionsquare.core.dao.SellerNetworkDao;
import com.onionsquare.core.model.Seller;
import com.onionsquare.core.model.SellerNetwork;

@Repository("sellerNetworkDao")
public class SellerNetworkDaoImpl extends AbstractDAO<SellerNetwork,Integer>  implements SellerNetworkDao {
	
	@Autowired
	public SellerNetworkDaoImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}	
	
	public List<SellerNetwork> findAllStoresInSellerNetwork(int sellerId,Boolean status) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SellerNetwork.class);
		Seller seller = new Seller() ;
		seller.setSellerId(sellerId);
		if(status != null) {
			detachedCriteria.add(Restrictions.eq("status", status)); 
		}
		detachedCriteria.add(Restrictions.eq("seller", seller));		
		List<SellerNetwork> categoryList = findByCriteria(detachedCriteria);
		return categoryList ;		 
	}

	@Override
	public List<SellerNetwork> findDeletedItems() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SellerNetwork.class);
		detachedCriteria.add(Restrictions.eq("deleted", true)); 				
		List<SellerNetwork> categoryList = findByCriteria(detachedCriteria);
		return categoryList ;		
	}
	
}
