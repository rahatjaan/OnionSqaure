package com.onionsquare.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onionsquare.core.dao.StateDao;
import com.onionsquare.core.model.State;
import com.onionsquare.core.service.StateService;

@Service
public class StateServiceImpl implements StateService {

	@Autowired
	StateDao stateDao;

	public State saveState(State state) {
		stateDao.save(state);
		return state; 
	}

	public List<State> getStatesByCountryId(int countryId) {
		
		List<State> stateList= stateDao.findStatesByCountryId(countryId);
		if(stateList==null)
			stateList = new ArrayList<State>();
		return stateList;
	}
}
