package com.mycompany.webapp.controller.course;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.webapp.domain.Course;
import com.mycompany.webapp.domain.Detail;
import com.mycompany.webapp.domain.Role;
import com.mycompany.webapp.domain.User;
import com.mycompany.webapp.enums.RolesEnum;
import com.mycompany.webapp.enums.StatesEnum;
import com.mycompany.webapp.service.CourseService;
import com.mycompany.webapp.service.DetailService;
import com.mycompany.webapp.service.MailService;
import com.mycompany.webapp.service.RoleService;
import com.mycompany.webapp.service.UserService;

import freemarker.template.TemplateException;


@Controller
@SessionAttributes("email")
@RequestMapping(value = "/courses/{courseId}/delete")
public class CourseDeleteController {
	
    @Autowired
    private CourseService courseService;
    @Autowired
    private DetailService detailService;
    @Autowired
    private MailService mailService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView delete(@ModelAttribute("email") String email,
			@PathVariable("courseId") String courseId) {
		
		ModelAndView model = new ModelAndView();
		model.addObject("email", email);
		try {
			int id=Integer.parseInt(courseId);
			Course course=courseService.findById(id);
			Detail detail=detailService.findById(id);			
			if (((course.getState().getStateId()==StatesEnum.DRAFT.getStateId())||
					(course.getState().getStateId()==StatesEnum.REJECTED.getStateId()))&&
					(detail.getLecturer().compareTo(email)==0)) {
				model.addObject("course", course);
				model.setViewName("delete");				
			}  else {				
				model.setViewName("delete_forbidden");
			}
		} catch(NumberFormatException e) {
			model.setViewName("delete_unknown");
		} catch (NullPointerException e) {
			model.setViewName("delete_unknown");
		}
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processDelete(@PathVariable("courseId") String courseId,
			@ModelAttribute("email") String email)
					throws IOException, TemplateException, MessagingException {
	
		int id=Integer.parseInt(courseId);
		Course course=courseService.findById(id);
		Detail detail=detailService.findById(id);		
		if (course.getState().getStateId()==StatesEnum.REJECTED.getStateId()) {
			Role roleKM = roleService.findByRoleId(RolesEnum.KNOWLEDGE_MANAGER.getRoleId());
			Role roleDM = roleService.findByRoleId(RolesEnum.DEPARTMENT_MANAGER.getRoleId());
			List<String> emailsKM=userService.getEmailsByRole(roleKM);
			List<String> emailsDM=userService.getEmailsByRole(roleDM);
			User owner=userService.findByEmail(email);
			mailService.sendCourseDeletedMail(emailsKM, emailsDM, owner, course, detail);
		}
		courseService.deleteCourse(course);
		return "redirect:/courses";
	}
	
}
