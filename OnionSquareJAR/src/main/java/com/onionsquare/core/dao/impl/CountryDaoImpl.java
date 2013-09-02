package com.onionsquare.core.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onionsquare.core.dao.CountryDao;
import com.onionsquare.core.model.Country;

@Repository("countryDao")
public class CountryDaoImpl extends AbstractDAO<Country, Integer> implements
		CountryDao {
	
	@Autowired
	public CountryDaoImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
}
