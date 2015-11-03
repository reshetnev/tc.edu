package com.mycompany.webapp.controller.course;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mycompany.webapp.domain.Category;
import com.mycompany.webapp.domain.Course;
import com.mycompany.webapp.domain.Detail;
import com.mycompany.webapp.domain.State;
import com.mycompany.webapp.domain.form.CreateForm;
import com.mycompany.webapp.enums.StatesEnum;
import com.mycompany.webapp.service.CategoryService;
import com.mycompany.webapp.service.CourseService;
import com.mycompany.webapp.service.DetailService;
import com.mycompany.webapp.service.StateService;
import com.mycompany.webapp.service.UserService;

@Controller
@SessionAttributes("email")
@RequestMapping(value = "/courses/create")
public class CourseCreateController {
	
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private StateService stateService;
    @Autowired
    private DetailService detailService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String create(Model model, @ModelAttribute("email") String email) {

		Map<String, String> mapCategories = new LinkedHashMap<String, String>();
		mapCategories.put("","");
		mapCategories.putAll(categoryService.getMapAllCategory());
		CreateForm createForm = new CreateForm();		
		model.addAttribute("email", email);
		model.addAttribute("createForm", createForm);
		model.addAttribute("mapCategories", mapCategories);
		return "create";
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public String processCreate(@Valid CreateForm createForm, BindingResult result,
			@ModelAttribute("email") String email, Model model) {
		
		if (result.hasErrors()) {
			Map<String, String> mapCategories = new LinkedHashMap<String, String>();
			mapCategories.put("","");
			mapCategories.putAll(categoryService.getMapAllCategory());
			model.addAttribute("email", email);			
			model.addAttribute("mapCategories", mapCategories);
			return "create";
		} else {
			Course course=new Course();
			Detail detail=new Detail();			
			detailService.defineDetail(detail, createForm, email);
			course.setName(createForm.getName());
			State state=stateService.findByStateId(StatesEnum.DRAFT.getStateId());
			Category category=categoryService.findByCategory(createForm.getCategory());			
			courseService.addCourse(course, detail, state, category);
			categoryService.updateCategory(category);
		}
		
		return "redirect:/courses";
	}

}
