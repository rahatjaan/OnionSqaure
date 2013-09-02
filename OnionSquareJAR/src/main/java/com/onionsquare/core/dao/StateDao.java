package com.onionsquare.core.dao;

import java.util.List;

import com.onionsquare.core.model.State;

public interface StateDao extends GenericDAO<State, Integer> {

	/**
	 * Return a list of states by country id passed as parameter.
	 * @param countryId
	 * @return
	 */
	List<State> findStatesByCountryId(Integer countryId);	 
}
