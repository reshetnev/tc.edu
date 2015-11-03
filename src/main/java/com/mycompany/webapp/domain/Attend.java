package com.mycompany.webapp.domain;

import java.beans.Transient;
import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="Attends")
@AssociationOverrides({
	@AssociationOverride(name = "pk.course", 
		joinColumns = @JoinColumn(name = "id")),
	@AssociationOverride(name = "pk.user", 
		joinColumns = @JoinColumn(name = "userId")) })
public class Attend implements Serializable {
	
	@EmbeddedId	
	private AttendId pk=new AttendId();
	
	@Column(name="grade")
 	private int grade;

	public Attend() {

	}
	
	public AttendId getPk() {
		return pk;
	}

	public void setPk(AttendId pk) {
		this.pk = pk;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
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
