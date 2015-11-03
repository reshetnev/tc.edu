package com.mycompany.webapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.webapp.dao.CategoryDAO;
import com.mycompany.webapp.domain.Category;

@Repository
public class CategoryDAOImpl implements CategoryDAO {
	
    @Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAllCategory() {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Category.class);
		return criteria.list();
	}

	@Override
	public Category findByCategory(String category) {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Category.class);
		Category categoryEntity = (Category) criteria.add(Restrictions.eq("category", category)).uniqueResult();		
		return categoryEntity;
	}

	@Override
	public void updateCategory(Category category) {
		sessionFactory.getCurrentSession().update(category);
		
	}

}
