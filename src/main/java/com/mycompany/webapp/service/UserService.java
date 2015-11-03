package com.mycompany.webapp.service;

import java.util.List;

import com.mycompany.webapp.domain.Role;
import com.mycompany.webapp.domain.User;

public interface UserService {
	
	User findByLogin(String login);
	
	User findByEmail(String email);
	
	String getUserEmail(String login);
	
	void addUser(User user);
	
	void updateUser(User user);
	
	List<String> getEmailsByRole(Role role);
	
	List<User> getUsersByRoles(List<Role> roles);

}
