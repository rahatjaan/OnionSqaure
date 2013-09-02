package com.onionsquare.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onionsquare.core.dao.CountryDao;
import com.onionsquare.core.model.Country;
import com.onionsquare.core.service.CountryService;
@Service
public class CountryServiceImpl implements CountryService {
	
	@Autowired 
	CountryDao countryDao ;
	
	public Country getCountryById(int countryId) {
		return countryDao.findById(countryId);
	}
	public void saveCountry(Country country) {
		countryDao.save(country);
	}
	public void saveCountryList(List<Country> countryList) {
		countryDao.saveOrUpdateAll(countryList);
	}
	
	public List<Country> getCountryList(){
		
		List<Country> countryList = countryDao.findAll();
		if(countryList==null)
			countryList = new ArrayList<Country>();
		return countryList;
		
	}
	
}
