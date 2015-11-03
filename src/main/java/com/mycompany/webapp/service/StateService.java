package com.mycompany.webapp.service;

import com.mycompany.webapp.domain.State;

public interface StateService {
	
	public State findByBadge(String badge);
	
	public State findByStateId(int stateId);
	


}
