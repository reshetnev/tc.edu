package com.mycompany.webapp.controller.course;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.webapp.domain.Course;
import com.mycompany.webapp.domain.form.SearchForm;
import com.mycompany.webapp.service.CategoryService;
import com.mycompany.webapp.service.CourseService;
import com.mycompany.webapp.service.DetailService;
import com.mycompany.webapp.service.UserService;


@Controller
@SessionAttributes("email")
@RequestMapping(value = "/mycourses")
public class MyCoursesController {
	
    @Autowired
    private CourseService courseService;
    @Autowired
    private DetailService detailService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView myCourses(@ModelAttribute("email") String email, HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		List<Course> myCourses = courseService.getMyCourses(email);		
		SearchForm searchForm = new SearchForm();
		Map<String, String> mapCategories = new LinkedHashMap<String, String>();
		mapCategories.put("All","All");
		mapCategories.putAll(categoryService.getMapAllCategory());
		String login=request.getUserPrincipal().getName();		
		String role=userService.findByLogin(login).getRole().getRole();
		int flagU=0; 
		if (role.compareTo("User")==0) {
			flagU=1;
		}
		model.addObject("email", email);		
		model.addObject("myCourses", myCourses);
		model.addObject("searchForm", searchForm);
		model.addObject("mapCategories", mapCategories);		
		model.addObject("flagU", flagU);
		model.setViewName("mycourses");
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView filteredMyCourses(@ModelAttribute("email") String email, 
			@Valid SearchForm searchForm, HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		List<Course> myCourses = new ArrayList<Course>();		
		Map<String, String> mapCategories = new LinkedHashMap<String, String>();
		mapCategories.put("All","All");
		mapCategories.putAll(categoryService.getMapAllCategory());
		String category=searchForm.getCategory();
		if (category.compareTo("All")==0) {
			myCourses = courseService.getMyCourses(email);
		} else {
			myCourses = courseService.getMyCoursesByCategory(email, category);
		}
		String login=request.getUserPrincipal().getName();		
		String role=userService.findByLogin(login).getRole().getRole();
		int flagU=0; 
		if (role.compareTo("User")==0) {
			flagU=1;
		}
		model.addObject("email", email);
		model.addObject("myCourses", myCourses);
		model.addObject("mapCategories", mapCategories);
		model.addObject("flagU", flagU);		
		model.setViewName("mycourses");
		return model;
	}

}
