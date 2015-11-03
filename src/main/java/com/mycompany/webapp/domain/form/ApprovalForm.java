package com.mycompany.webapp.domain.form;

import org.hibernate.validator.constraints.NotBlank;

public class ApprovalForm {
	
	@NotBlank
	private String decision;
	
//	@NotBlank
	private String reason;

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}	
	
	

}
