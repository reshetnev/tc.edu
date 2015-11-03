package com.mycompany.webapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.webapp.dao.CourseDAO;
import com.mycompany.webapp.domain.Course;


@Repository
public class CourseDAOImpl implements CourseDAO {

    @Autowired
	private SessionFactory sessionFactory;
    
	@Override
	public Course findById(int id) {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Course.class);
		Course course = (Course) criteria.add(Restrictions.eq("id", id)).uniqueResult();		
		return course;
	}
	
	@Override
	public void addCourse(Course course) {
		sessionFactory.getCurrentSession().save(course);	
	}
	
	@Override
	public void updateCourse(Course course) {
		sessionFactory.getCurrentSession().update(course);
	}
	
	@Override
	public void deleteCourse(Course course) {
		sessionFactory.getCurrentSession().delete(course);
		
	}	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Course> getAllCourses() {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Course.class);
		return criteria.list();
	}



}