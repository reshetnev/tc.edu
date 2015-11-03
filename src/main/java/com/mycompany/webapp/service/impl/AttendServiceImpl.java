package com.mycompany.webapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.AttendDAO;
import com.mycompany.webapp.domain.Attend;
import com.mycompany.webapp.domain.Course;
import com.mycompany.webapp.domain.User;
import com.mycompany.webapp.service.AttendService;

@Service
public class AttendServiceImpl implements AttendService {
	
    @Autowired
    private AttendDAO attendDAO;

	@Override
	@Transactional
	public void update(Attend attend) {
		attendDAO.update(attend);
		
	}
	
	@Override
	@Transactional
	public void addAttend(Course course, User user) {
		Attend attend=new Attend();
		attend.setCourse(course);
		attend.setUser(user);
		attend.setGrade(0);
		course.getAttends().add(attend);
		user.getAttends().add(attend);
	}	

	@Override
	@Transactional
	public void saveGrade(Course course, User user, int grade) {
		Attend attend=new Attend();
		attend.setCourse(course);
		attend.setUser(user);
		attend.setGrade(grade);
		update(attend);
		
	}



}
