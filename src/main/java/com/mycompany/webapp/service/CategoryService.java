package com.mycompany.webapp.service;

import java.util.List;
import java.util.Map;

import com.mycompany.webapp.domain.Category;

public interface CategoryService {
	
	public List<Category> getAllCategory();
	
	public Category findByCategory(String category);
	
	public void updateCategory(Category category);
	
	public Map<String, String> getMapAllCategory();

}
