package com.mycompany.webapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.DetailDao;
import com.mycompany.webapp.domain.Detail;
import com.mycompany.webapp.domain.form.CreateForm;
import com.mycompany.webapp.domain.form.UpdateForm;
import com.mycompany.webapp.service.DetailService;

@Service
public class DetailServiceImpl implements DetailService  {

    @Autowired
    private DetailDao detailDao;

	@Override
	@Transactional
	public Detail findById(int id) {
		return detailDao.findById(id);
	}

	@Override
	@Transactional
	public void addDetail(Detail detail) {
		detailDao.addDetail(detail);
		
	}
	
	@Override
	public void defineDetail(Detail detail, CreateForm createForm, String lecturer) {
		detail.setLecturer(lecturer);
		detail.setDescription(createForm.getDescription());
		detail.setLinks(createForm.getLinks());
			
	}

	@Override
	public void defineDetail(Detail detail, UpdateForm updateForm, String lecturer) {
		detail.setLecturer(lecturer);
		detail.setDescription(updateForm.getDescription());
		detail.setLinks(updateForm.getLinks());
		detail.setqSubs(Integer.parseInt(updateForm.getqSubs()));
		detail.setqAtts(Integer.parseInt(updateForm.getqAtts()));	
		
	}
	
}
