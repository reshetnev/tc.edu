package com.mycompany.webapp.service;

import com.mycompany.webapp.domain.Attend;
import com.mycompany.webapp.domain.Course;
import com.mycompany.webapp.domain.User;

public interface AttendService {
	
	public void update(Attend attend);
	
	public void addAttend(Course course, User user);
	
	public void saveGrade(Course course, User user, int grade);

}
