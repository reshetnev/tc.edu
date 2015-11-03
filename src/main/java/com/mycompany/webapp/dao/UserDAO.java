package com.mycompany.webapp.dao;

import com.mycompany.webapp.domain.User;

public interface UserDAO {
	 
	User findByLogin(String login);
	
	User findByEmail(String email);
		
	void addUser(User user);
	
	void updateUser(User user);
 
}


