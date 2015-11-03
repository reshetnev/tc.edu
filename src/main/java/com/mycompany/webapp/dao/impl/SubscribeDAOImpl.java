package com.mycompany.webapp.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.webapp.dao.SubscribeDAO;
import com.mycompany.webapp.domain.Subscribe;

@Repository
public class SubscribeDAOImpl implements SubscribeDAO {
	
    @Autowired
	private SessionFactory sessionFactory;

	@Override
	public void deleteSubscribe(Subscribe subscribe) {
		sessionFactory.getCurrentSession().delete(subscribe);
		
	}
    
    

}
