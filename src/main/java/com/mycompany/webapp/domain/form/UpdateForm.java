package com.mycompany.webapp.domain.form;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class UpdateForm {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String category;
	
	@NotBlank
	private String description;
	
	@NotBlank
	private String links;
	
	@Range(min = 1, max = 10)
	private String qSubs;
	
	@Range(min = 1, max = 10)
	private String qAtts;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getqSubs() {
		return qSubs;
	}

	public void setqSubs(String qSubs) {
		this.qSubs = qSubs;
	}

	public String getqAtts() {
		return qAtts;
	}

	public void setqAtts(String qAtts) {
		this.qAtts = qAtts;
	}
	
	

}
