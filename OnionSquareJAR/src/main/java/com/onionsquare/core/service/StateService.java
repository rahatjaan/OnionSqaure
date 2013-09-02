package com.onionsquare.core.service;

import java.util.List;

import com.onionsquare.core.model.State;

public interface StateService {
	
	/**
	 * Saves a state.
	 * @param state : state being saved.
	 * @param
	 * @return
	 */
	State saveState(State state);

	/**
	 * Return list of states by country id passed as parameter.
	 * @param countryId
	 * @return
	 */
	List<State> getStatesByCountryId(int countryId) ;
}
