package com.mycompany.webapp.domain;

import java.beans.Transient;
import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="Subscribes")
@AssociationOverrides({
	@AssociationOverride(name = "pk.course", 
		joinColumns = @JoinColumn(name = "id")),
	@AssociationOverride(name = "pk.user", 
		joinColumns = @JoinColumn(name = "userId")) })
public class Subscribe implements Serializable {
	
	@EmbeddedId	
	private SubscribeId pk=new SubscribeId();

	public Subscribe() {

	}
	
	public SubscribeId getPk() {
		return pk;
	}

	public void setPk(SubscribeId pk) {
		this.pk = pk;
	}

	@Transient
	public Course getCourse() {
		return getPk().getCourse();
	}
 
	public void setCourse(Course course) {
		getPk().setCourse(course);
	}
 
	@Transient
	public User getUser() {
		return getPk().getUser();
	}
 
	public void setUser(User user) {
		getPk().setUser(user);
	}



}
