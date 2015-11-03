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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
	
@Entity
@Table(name="Courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="stateId")
	private State state;	
	
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;
	
	@OneToOne(mappedBy="course", cascade=CascadeType.ALL)
	private Detail detail;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="pk.course", cascade=CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Subscribe> subscribes=new ArrayList<Subscribe>();
	
	@OneToMany
	(fetch = FetchType.EAGER, mappedBy="pk.course", cascade=CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Attend> attends=new ArrayList<Attend>();
	
	@Column(name="voteKM")
 	private int voteKM;
	
	@Column(name="voteDM")
 	private int voteDM;
	
	@Column(name="reasonKM")
 	private String reasonKM;
	
	@Column(name="reasonDM")
 	private String reasonDM;
	
	public Course() {
		
	}

	public Course(String name) {
		this.name = name;
	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Detail getDetail() {
		return detail;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getVoteKM() {
		return voteKM;
	}

	public void setVoteKM(int voteKM) {
		this.voteKM = voteKM;
	}

	public int getVoteDM() {
		return voteDM;
	}

	public void setVoteDM(int voteDM) {
		this.voteDM = voteDM;
	}

	public String getReasonKM() {
		return reasonKM;
	}

	public void setReasonKM(String reasonKM) {
		this.reasonKM = reasonKM;
	}

	public String getReasonDM() {
		return reasonDM;
	}

	public void setReasonDM(String reasonDM) {
		this.reasonDM = reasonDM;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attends == null) ? 0 : attends.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((reasonDM == null) ? 0 : reasonDM.hashCode());
		result = prime * result
				+ ((reasonKM == null) ? 0 : reasonKM.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result
				+ ((subscribes == null) ? 0 : subscribes.hashCode());
		result = prime * result + voteDM;
		result = prime * result + voteKM;
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
		Course other = (Course) obj;
		if (attends == null) {
			if (other.attends != null)
				return false;
		} else if (!attends.equals(other.attends))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (detail == null) {
			if (other.detail != null)
				return false;
		} else if (!detail.equals(other.detail))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (reasonDM == null) {
			if (other.reasonDM != null)
				return false;
		} else if (!reasonDM.equals(other.reasonDM))
			return false;
		if (reasonKM == null) {
			if (other.reasonKM != null)
				return false;
		} else if (!reasonKM.equals(other.reasonKM))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (subscribes == null) {
			if (other.subscribes != null)
				return false;
		} else if (!subscribes.equals(other.subscribes))
			return false;
		if (voteDM != other.voteDM)
			return false;
		if (voteKM != other.voteKM)
			return false;
		return true;
	}
	

	
}


