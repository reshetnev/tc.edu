package com.mycompany.webapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="Details")
public class Detail {
	
    @Id
    @Column(name="id", unique=true, nullable=false)
    @GeneratedValue(generator="gen")
    @GenericGenerator(name="gen", strategy="foreign", parameters=@Parameter(name="property", value="course"))
	private int id;
	
	@OneToOne
	@PrimaryKeyJoinColumn 
	private Course course;
	
	@Column(name="lecturer")
	private String lecturer;
	
	@Column(name="description")
	private String description;
	
	@Column(name="links")
	private String links;
	
	@Column(name="qSubs")
 	private int qSubs;
	
	@Column(name="qAtts")
 	private int qAtts;

	public Detail() {

	}

	public Detail(String lecturer, String description, String links) {
		this.lecturer = lecturer;
		this.description = description;
		this.links = links;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLinks() {
		return links;
	}

	public void setLinks(String links) {
		this.links = links;
	}

	public int getqSubs() {
		return qSubs;
	}

	public void setqSubs(int qSubs) {
		this.qSubs = qSubs;
	}

	public int getqAtts() {
		return qAtts;
	}

	public void setqAtts(int qAtts) {
		this.qAtts = qAtts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((lecturer == null) ? 0 : lecturer.hashCode());
		result = prime * result + ((links == null) ? 0 : links.hashCode());
		result = prime * result + qAtts;
		result = prime * result + qSubs;
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
		Detail other = (Detail) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (lecturer == null) {
			if (other.lecturer != null)
				return false;
		} else if (!lecturer.equals(other.lecturer))
			return false;
		if (links == null) {
			if (other.links != null)
				return false;
		} else if (!links.equals(other.links))
			return false;
		if (qAtts != other.qAtts)
			return false;
		if (qSubs != other.qSubs)
			return false;
		return true;
	}
	
	

}
