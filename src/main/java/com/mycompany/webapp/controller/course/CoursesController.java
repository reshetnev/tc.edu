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
@RequestMapping(value = "/courses")
public class CoursesController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private DetailService detailService;
    @Autowired
    private CategoryService categoryService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView allCourses(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		
		String login=request.getUserPrincipal().getName();
		String email=userService.getUserEmail(login);
		List<Course> courses = courseService.getAllCourses();
		SearchForm searchForm = new SearchForm();
		Map<String, String> mapCategories = new LinkedHashMap<String, String>();
		mapCategories.put("All","All");
		mapCategories.putAll(categoryService.getMapAllCategory());
		String role=userService.findByLogin(login).getRole().getRole();
		int flagU=0; 
		if (role.compareTo("User")==0) {
			flagU=1;
		}
		model.addObject("role", role);		
		model.addObject("email", email);
		model.addObject("courses", courses);		
		model.addObject("searchForm", searchForm);		
		model.addObject("mapCategories", mapCategories);		
		model.addObject("flagU", flagU);
		model.setViewName("courses");
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView filteredCourses(@ModelAttribute("email") String email, 
			@Valid SearchForm searchForm, HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		List<Course> courses = new ArrayList<Course>();		
		Map<String, String> mapCategories = new LinkedHashMap<String, String>();
		mapCategories.put("All","All");
		mapCategories.putAll(categoryService.getMapAllCategory());
		String category=searchForm.getCategory();
		if (category.compareTo("All")==0) {
			courses = courseService.getAllCourses();
		} else {
			courses = courseService.getCoursesByCategory(category);
		}
		String login=request.getUserPrincipal().getName();		
		String role=userService.findByLogin(login).getRole().getRole();
		int flagU=0; 
		if (role.compareTo("User")==0) {
			flagU=1;
		}
		model.addObject("role", role);		
		model.addObject("email", email);		
		model.addObject("courses", courses);		
		model.addObject("mapCategories", mapCategories);		
		model.addObject("flagU", flagU);
		model.setViewName("courses");
		return model;
	}
	

}

