package com.mycompany.webapp.service;

import com.mycompany.webapp.domain.Role;

public interface RoleService {
	
	public Role findByRole(String role);
	
	public Role findByRoleId(int roleId);


}
