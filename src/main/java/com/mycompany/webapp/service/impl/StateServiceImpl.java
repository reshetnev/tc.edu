package com.mycompany.webapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.StateDAO;
import com.mycompany.webapp.domain.State;
import com.mycompany.webapp.service.StateService;

@Service
public class StateServiceImpl implements StateService {
	
    @Autowired 
    private StateDAO stateDAO;	

	@Override
	@Transactional
	public State findByBadge(String badge) {
		return stateDAO.findByBadge(badge);
	}

	@Override
	@Transactional
	public State findByStateId(int stateId) {
		return stateDAO.findByStateId(stateId);
	}



}
