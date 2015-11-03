package com.mycompany.webapp.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
 
@Entity
@Table(name = "Users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userId")
	private int userId;
	
	@Column(name="login")	
 	private String login;
	
	@Column(name="email")	
 	private String email;
	
	@Column(name="password")	
	private String password;

	@ManyToOne
	@JoinColumn(name="roleId")
	private Role role;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="pk.user", cascade=CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Subscribe> subscribes=new ArrayList<Subscribe>();
	
	@OneToMany
	(fetch = FetchType.EAGER, mappedBy="pk.user", cascade=CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Attend> attends=new ArrayList<Attend>();

	public User() {

	}

	public User(String login, String email, String password) {
		this.login = login;
		this.email = email;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Subscribe> getSubscribes() {
		return subscribes;
	}

	public void setSubscribes(List<Subscribe> subscribes) {
		this.subscribes = subscribes;
	}

	public List<Attend> getAttends() {
		return attends;
	}

	public void setAttends(List<Attend> attends) {
		this.attends = attends;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attends == null) ? 0 : attends.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result
				+ ((subscribes == null) ? 0 : subscribes.hashCode());
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (attends == null) {
			if (other.attends != null)
				return false;
		} else if (!attends.equals(other.attends))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (subscribes == null) {
			if (other.subscribes != null)
				return false;
		} else if (!subscribes.equals(other.subscribes))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}


 
}
