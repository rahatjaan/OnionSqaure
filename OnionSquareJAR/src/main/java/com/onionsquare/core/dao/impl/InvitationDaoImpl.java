package com.onionsquare.core.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.onionsquare.core.dao.InvitationDao;
import com.onionsquare.core.model.Invitation;
import com.onionsquare.core.model.Seller;

@Repository("invitationDao")
public class InvitationDaoImpl extends AbstractDAO<Invitation, Integer>
		implements InvitationDao {

	@Autowired
	public InvitationDaoImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List<Invitation> findAllInvitationBySellerId(Integer sellerId,Boolean status) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Invitation.class);			
		Seller invitingSeller = new Seller();
		invitingSeller.setSellerId(sellerId);
		if (status != null) {
			detachedCriteria.add(Restrictions.eq("invitationStatus", status));
		}
		detachedCriteria.add(Restrictions.eq("seller", invitingSeller));
		List<Invitation> initationList = findByCriteria(detachedCriteria);		
		return initationList;
	}

}
