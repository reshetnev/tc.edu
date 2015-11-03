package com.mycompany.webapp.domain.form;

import org.hibernate.validator.constraints.NotBlank;

public class LoginForm {
	
	@NotBlank
	private String login;
	
	@NotBlank
	private String password;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
