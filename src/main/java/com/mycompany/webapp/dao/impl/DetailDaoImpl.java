package com.mycompany.webapp.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.webapp.dao.DetailDao;
import com.mycompany.webapp.domain.Detail;

@Repository
public class DetailDaoImpl implements DetailDao {
	
    @Autowired
	private SessionFactory sessionFactory;

	@Override
	public Detail findById(int id) {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Detail.class);
		Detail detail = (Detail) criteria.add(Restrictions.eq("id", id)).uniqueResult();
		return detail;

	}

	@Override
	public void addDetail(Detail detail) {
		sessionFactory.getCurrentSession().save(detail);
		
	}

}
