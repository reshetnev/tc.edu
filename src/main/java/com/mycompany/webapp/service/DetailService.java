package com.mycompany.webapp.service;

import com.mycompany.webapp.domain.Detail;
import com.mycompany.webapp.domain.form.CreateForm;
import com.mycompany.webapp.domain.form.UpdateForm;

public interface DetailService {
	
	public Detail findById(int id);
	
	public void addDetail(Detail detail);
	
	public void defineDetail(Detail detail, CreateForm createForm, String lecturer);
	
	public void defineDetail(Detail detail, UpdateForm updateForm, String lecturer);

}
