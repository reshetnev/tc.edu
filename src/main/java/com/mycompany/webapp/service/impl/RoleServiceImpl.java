package com.mycompany.webapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.RoleDAO;
import com.mycompany.webapp.domain.Role;
import com.mycompany.webapp.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
    @Autowired
    private RoleDAO roleDAO;	

	@Override
	@Transactional	
	public Role findByRole(String role) {
		return roleDAO.findByRole(role);
		
	}

	@Override
	@Transactional
	public Role findByRoleId(int roleId) {
		return roleDAO.findByRoleId(roleId);
	}


}
