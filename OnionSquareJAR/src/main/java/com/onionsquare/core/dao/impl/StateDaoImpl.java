package com.onionsquare.core.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onionsquare.core.dao.StateDao;
import com.onionsquare.core.model.Country;
import com.onionsquare.core.model.State;

@Repository("stateDao")
public class StateDaoImpl extends AbstractDAO<State, Integer> implements
		StateDao {

	@Autowired
	public StateDaoImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public List<State> findStatesByCountryId(Integer countryId) {
		
		DetachedCriteria statesByCountyIdCriteria= DetachedCriteria.forClass(State.class);
		Country country = new Country();
		country.setCountryId(countryId);
		statesByCountyIdCriteria.add(Restrictions.eq("country", country));		
		return findByCriteria(statesByCountyIdCriteria);
	}
}
