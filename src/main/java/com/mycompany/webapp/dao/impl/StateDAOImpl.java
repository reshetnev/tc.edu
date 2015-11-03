package com.mycompany.webapp.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.webapp.dao.StateDAO;
import com.mycompany.webapp.domain.State;

@Repository
public class StateDAOImpl implements StateDAO {
	
    @Autowired
	private SessionFactory sessionFactory;	

	@Override
	public State findByBadge(String badge) {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(State.class);
		State state = (State) criteria.add(Restrictions.eq("badge", badge)).uniqueResult();		
		return state;
	}

	@Override
	public State findByStateId(int stateId) {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(State.class);
		State state = (State) criteria.add(Restrictions.eq("stateId", stateId)).uniqueResult();		
		return state;
	}

}
