package com.mycompany.webapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.SubscribeDAO;
import com.mycompany.webapp.domain.Course;
import com.mycompany.webapp.domain.Subscribe;
import com.mycompany.webapp.domain.User;
import com.mycompany.webapp.service.SubscribeService;

@Service
public class SubscribeServiceImpl implements SubscribeService {
	
    @Autowired 
    private SubscribeDAO subscribeDAO;

	@Override
	@Transactional
	public void deleteSubscribe(Subscribe subscribe) {
		subscribeDAO.deleteSubscribe(subscribe);
		
	}

	@Override
	@Transactional
	public void deleteSubscribe(Course course, User user) {
		Subscribe subscribe=new Subscribe();
		subscribe.setCourse(course);
		subscribe.setUser(user);
		course.getSubscribes().remove(subscribe);
		user.getSubscribes().remove(subscribe);
		deleteSubscribe(subscribe);
		
	}

}
