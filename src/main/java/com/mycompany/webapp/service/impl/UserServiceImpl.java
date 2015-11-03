package com.mycompany.webapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.UserDAO;
import com.mycompany.webapp.domain.Role;
import com.mycompany.webapp.domain.User;
import com.mycompany.webapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
    private UserDAO userDAO;
    
	@Override
    @Transactional	
	public User findByLogin(String login) {
		return userDAO.findByLogin(login);
	}
	
	@Override
    @Transactional
	public User findByEmail(String email) {
		return userDAO.findByEmail(email);
	}	
    
	@Override
    @Transactional
	public String getUserEmail(String login) {
		String email=findByLogin(login).getEmail();
		return email;
	}

	@Override
    @Transactional
	public void addUser(User user) {
		userDAO.addUser(user);
		
	}

	@Override
    @Transactional
	public void updateUser(User user) {
		userDAO.updateUser(user);
		
	}

	@Override
    @Transactional
	public List<String> getEmailsByRole(Role role) {
		List<String> emails=new ArrayList<String>();
		List<User> users=role.getUsers();
		for (User user:users) {
			emails.add(user.getEmail());
		}
		return emails;
	}

	@Override
    @Transactional	
	public List<User> getUsersByRoles(List<Role> roles) {
		List<User> users=new ArrayList<User>();
		for (Role role:roles) {
			users.addAll(role.getUsers());
		}
		return users;
	}



   

}
