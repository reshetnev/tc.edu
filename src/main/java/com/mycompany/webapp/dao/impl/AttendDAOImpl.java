package com.mycompany.webapp.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.webapp.dao.AttendDAO;
import com.mycompany.webapp.domain.Attend;

@Repository
public class AttendDAOImpl implements AttendDAO {
	
    @Autowired
	private SessionFactory sessionFactory;	

	@Override
	public void update(Attend attend) {
		sessionFactory.getCurrentSession().update(attend);
		
	}

}
