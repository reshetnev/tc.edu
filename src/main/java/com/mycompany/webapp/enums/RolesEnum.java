package com.mycompany.webapp.enums;

public enum RolesEnum {
	
	KNOWLEDGE_MANAGER, DEPARTMENT_MANAGER, LECTOR, USER;
	
	public int getRoleId()
    {
		int roleId;
        switch (this) {
        	case KNOWLEDGE_MANAGER: roleId=1; break;
        	case DEPARTMENT_MANAGER: roleId=2; break;
        	case LECTOR: roleId=3; break;
        	case USER: roleId=4; break;
        	default: roleId=0;
        }
        return roleId;
    }

}
