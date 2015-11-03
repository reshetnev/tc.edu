package com.mycompany.webapp.domain.form;

import org.hibernate.validator.constraints.Range;

public class EvaluateForm {
	
	@Range(min = 1, max = 5)
	private String grade;

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	

}
