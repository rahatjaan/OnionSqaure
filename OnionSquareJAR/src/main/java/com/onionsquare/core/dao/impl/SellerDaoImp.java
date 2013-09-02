package com.onionsquare.core.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onionsquare.core.dao.SellerDao;
import com.onionsquare.core.model.Seller;

@Repository("sellerDao")
public class SellerDaoImp extends AbstractDAO<Seller, Integer> implements
		SellerDao {

	/**
	 * @param sessionFactory
	 *            : it is auto-injected with @Autowired annotation by Spring
	 */
	@Autowired
	public SellerDaoImp(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/*
	 * @see
	 * com.onionsquare.core.dao.SellerDao#findSellerByUserName(java.lang.String)
	 */
	public Seller findSellerByUserName(String userName) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Seller.class);
		detachedCriteria.add(Restrictions.eq("username", userName));
		List<Seller> sellerList = findByCriteria(detachedCriteria);
		if (sellerList != null && sellerList.size() > 0) {
			return sellerList.get(0);
		}
		return null;
	}

	public List<Seller> findSellersByStatus(Boolean status) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Seller.class);
		detachedCriteria.add(Restrictions.eq("status", status));
		List<Seller> sellerList = findByCriteria(detachedCriteria);
		return sellerList;
	}

	public List<Seller> findSellersInvitedBySeller(Integer invitingSellerId) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Seller.class);
		
		if(invitingSellerId != null) {
			Seller refererSeller = new Seller() ;
			refererSeller.setSellerId(invitingSellerId);
			detachedCriteria.add(Restrictions.eq("refererSeller", refererSeller));
		}
		List<Seller> sellerList = findByCriteria(detachedCriteria);
		return sellerList;	 
	}
}
