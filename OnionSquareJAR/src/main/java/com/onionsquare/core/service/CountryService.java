package com.onionsquare.core.service;

import java.util.List;

import com.onionsquare.core.model.Country;

public interface CountryService {

	/**
	 * Return a country by country id passed as parameter. Return null if it does not exist.
	 * @param countryId
	 * @return
	 */
	public Country getCountryById(int countryId) ;
	/**
	 * Saves a country.
	 * @param country
	 */
	public void saveCountry(Country country);
	/**
	 * Save or update a list of country passed as parameter.
	 * @param countryList
	 */
	public void saveCountryList(List<Country> countryList);
	
	public List<Country> getCountryList();
}
