package com.mycompany.webapp.dao;

import java.util.List;

import com.mycompany.webapp.domain.Category;

public interface CategoryDAO {
	
	public List<Category> getAllCategory();
	
	public Category findByCategory(String category);
	
	public void updateCategory(Category category);

}
