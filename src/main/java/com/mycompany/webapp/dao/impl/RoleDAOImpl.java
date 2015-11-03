package com.mycompany.webapp.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.webapp.dao.RoleDAO;
import com.mycompany.webapp.domain.Role;

@Repository
public class RoleDAOImpl implements RoleDAO {
	
    @Autowired
	private SessionFactory sessionFactory;

	@Override
	public Role findByRole(String role) {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Role.class);
		Role roleEntity = (Role) criteria.add(Restrictions.eq("role", role)).uniqueResult();		
		return roleEntity;
	}

	@Override
	public Role findByRoleId(int roleId) {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Role.class);
		Role roleEntity = (Role) criteria.add(Restrictions.eq("roleId", roleId)).uniqueResult();		
		return roleEntity;
	}	

}
