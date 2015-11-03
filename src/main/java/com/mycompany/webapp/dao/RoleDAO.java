package com.mycompany.webapp.dao;

import com.mycompany.webapp.domain.Role;

public interface RoleDAO {
	
	public Role findByRole(String role);
	
	public Role findByRoleId(int roleId);

}
