package com.mycompany.webapp.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.CategoryDAO;
import com.mycompany.webapp.domain.Category;
import com.mycompany.webapp.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
    @Autowired 
    private CategoryDAO categoryDAO;

	@Override
	@Transactional
	public List<Category> getAllCategory() {
		return categoryDAO.getAllCategory();
	}

	@Override
	@Transactional	
	public Category findByCategory(String category) {
		return categoryDAO.findByCategory(category);
	}

	@Override
	@Transactional
	public void updateCategory(Category category) {
		categoryDAO.updateCategory(category);
		
	}

	@Override
	@Transactional
	public Map<String, String> getMapAllCategory() {
		List<Category> allCategories=getAllCategory();
		Map<String, String> mapCategories = new LinkedHashMap<String, String>();
		for (Category category:allCategories) {
			mapCategories.put(category.getCategory(), category.getCategory()); 
		}
		return mapCategories;
	}

}
