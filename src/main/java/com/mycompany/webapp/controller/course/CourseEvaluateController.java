package com.mycompany.webapp.controller.course;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.webapp.domain.Course;
import com.mycompany.webapp.domain.Detail;
import com.mycompany.webapp.domain.User;
import com.mycompany.webapp.domain.form.EvaluateForm;
import com.mycompany.webapp.enums.StatesEnum;
import com.mycompany.webapp.service.AttendService;
import com.mycompany.webapp.service.CourseService;
import com.mycompany.webapp.service.DetailService;
import com.mycompany.webapp.service.UserService;

@Controller
@SessionAttributes("email")
@RequestMapping(value = "/courses/{courseId}/evaluate")
public class CourseEvaluateController {
	
    @Autowired
    private DetailService detailService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private AttendService attendService;
    
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView evaluate(@ModelAttribute("email") String email,
			@PathVariable("courseId") String courseId) {
		
		ModelAndView model = new ModelAndView();
		model.addObject("email", email);		
		try {
			int id=Integer.parseInt(courseId);
			Course course=courseService.findById(id);
			List<String> emailsAttendees=courseService.getEmailsAttendeesForCourse(course);
			int grade=courseService.getGrade(course, email);			
			if ((emailsAttendees.contains(email))&&(grade==0)&&
					course.getState().getStateId()==StatesEnum.FINISHED.getStateId()) {
				Detail detail=detailService.findById(id);
				EvaluateForm evaluateForm=new EvaluateForm();
				model.addObject("course", course);				
				model.addObject("detail", detail);				
				model.addObject("evaluateForm", evaluateForm);
				model.setViewName("evaluate");				
			} else {
				model.setViewName("evaluate_forbidden");
			}
		} catch(NumberFormatException e) {
			model.setViewName("evaluate_unknown");
		} catch (NullPointerException e) {
			model.setViewName("evaluate_unknown");
		}
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processEvaluate(@PathVariable("courseId") String courseId, HttpServletRequest request,
			@Valid EvaluateForm evaluateForm, BindingResult result, Model model,
			@ModelAttribute("email") String email) {

		model.addAttribute("email", email);		
		int id=Integer.parseInt(courseId);
		Course course=courseService.findById(id);
		Detail detail=detailService.findById(id);
		model.addAttribute("course", course);
		model.addAttribute("detail", detail);		
		try {
			if (evaluateForm.getGrade().isEmpty())
				return "evaluate_error_empty";
			int grade=Integer.parseInt(evaluateForm.getGrade());
			if (result.hasErrors()) {
				return "evaluate";
			} else {
				String login=request.getUserPrincipal().getName();
				User user=userService.findByLogin(login);
				attendService.saveGrade(course,user,grade);
			}
		} catch (NumberFormatException e) {
			return "evaluate_error_int";
		}
		return "redirect:/courses";
	}	

}
