package com.mycompany.webapp.dao;

import com.mycompany.webapp.domain.State;

public interface StateDAO {
	
	public State findByBadge(String badge);
	
	public State findByStateId(int stateId);


}
