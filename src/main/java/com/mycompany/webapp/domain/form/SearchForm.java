package com.mycompany.webapp.domain.form;

import org.hibernate.validator.constraints.NotBlank;

public class SearchForm {
	
	@NotBlank
	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
