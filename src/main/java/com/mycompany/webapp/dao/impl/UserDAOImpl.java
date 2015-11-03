package com.mycompany.webapp.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.webapp.dao.UserDAO;
import com.mycompany.webapp.domain.User;
 
@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
	private SessionFactory sessionFactory;
 
	@Override
	public User findByLogin(String login) {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(User.class);
		User user = (User) criteria.add(Restrictions.eq("login", login)).uniqueResult();
		return user;
	}
	
	@Override
	public User findByEmail(String email) {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(User.class);
		User user = (User) criteria.add(Restrictions.eq("email", email)).uniqueResult();
		return user;
	}	

	@Override
	public void addUser(User user) {
		sessionFactory.getCurrentSession().save(user);
		
	}

	@Override
	public void updateUser(User user) {
		sessionFactory.getCurrentSession().update(user);
		
	}


 
}
