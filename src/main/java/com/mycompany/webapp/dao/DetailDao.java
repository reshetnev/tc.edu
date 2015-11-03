package com.mycompany.webapp.dao;

import com.mycompany.webapp.domain.Detail;

public interface DetailDao {
	
	public Detail findById(int id);
	
	public void addDetail(Detail detail);

}
