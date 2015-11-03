package com.mycompany.webapp.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.UserDAO;
import com.mycompany.webapp.domain.Role;
 
@Service
public class MyUserDetailsService implements UserDetailsService {
	
    // Init. Logger 
    private static final Logger log = Logger.getLogger(MyUserDetailsService.class);
	
    @Autowired
	private UserDAO userDAO;
 
	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String login) 
			throws UsernameNotFoundException {
		UserDetails userDetails;
		com.mycompany.webapp.domain.User user = userDAO.findByLogin(login);
		if (user==null) {
			log.error("Username "+login+" not exist.");
		}
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());
		userDetails=buildUserForAuthentication(user, authorities);
		return userDetails;
	}
 
	// com.mycompany.webapp.Entity.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(com.mycompany.webapp.domain.User user, 
		List<GrantedAuthority> authorities) {
		return new User(user.getLogin(), user.getPassword(), true, true, true, true, authorities);
	}
 
	private List<GrantedAuthority> buildUserAuthority(Role userRole) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}
 
}
