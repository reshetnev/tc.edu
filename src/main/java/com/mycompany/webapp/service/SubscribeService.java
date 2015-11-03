package com.mycompany.webapp.service;

import com.mycompany.webapp.domain.Course;
import com.mycompany.webapp.domain.Subscribe;
import com.mycompany.webapp.domain.User;

public interface SubscribeService {
	
	public void deleteSubscribe(Subscribe subscribe);
	
	public void deleteSubscribe(Course course, User user);

}
