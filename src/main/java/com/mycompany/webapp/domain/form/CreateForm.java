package com.mycompany.webapp.domain.form;

import org.hibernate.validator.constraints.NotBlank;

public class CreateForm {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String category;
	
	@NotBlank
	private String description;
	
	@NotBlank
	private String links;

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
	
	

}
