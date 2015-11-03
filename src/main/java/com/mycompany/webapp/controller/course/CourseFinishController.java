package com.mycompany.webapp.controller.course;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.webapp.domain.Course;
import com.mycompany.webapp.domain.Detail;
import com.mycompany.webapp.domain.State;
import com.mycompany.webapp.enums.StatesEnum;
import com.mycompany.webapp.service.CourseService;
import com.mycompany.webapp.service.DetailService;
import com.mycompany.webapp.service.MailService;
import com.mycompany.webapp.service.StateService;
import com.mycompany.webapp.service.UserService;

import freemarker.template.TemplateException;

@Controller
@SessionAttributes("email")
@RequestMapping(value = "/courses/{courseId}/finish")
public class CourseFinishController {
	
    @Autowired
    private DetailService detailService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StateService stateService;
    @Autowired
    private MailService mailService;
    
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView finish(HttpServletRequest request,	@PathVariable("courseId") String courseId) {
		
		ModelAndView model = new ModelAndView();
		String login=request.getUserPrincipal().getName();
		String email=userService.getUserEmail(login);
		model.addObject("email", email);		
		try {
			int id=Integer.parseInt(courseId);
			Course course=courseService.findById(id);
			Detail detail=detailService.findById(id);
			if ((email.compareTo(detail.getLecturer())==0)&&
					(course.getState().getStateId()==StatesEnum.IN_PROGRESS.getStateId())) {
				model.addObject("course", course);
				model.setViewName("finish");
			} else {
				model.setViewName("finish_forbidden");
			}
		} catch(NumberFormatException e) {
			model.setViewName("finish_unknown");
		} catch (NullPointerException e) {
			model.setViewName("finish_unknown");
		}
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processFinish(@PathVariable("courseId") String courseId)
			throws MessagingException, IOException, TemplateException {
		int id=Integer.parseInt(courseId);
		Course course=courseService.findById(id);
		State stateFinished=stateService.findByStateId(StatesEnum.FINISHED.getStateId());
		course.setState(stateFinished);
		courseService.updateCourse(course);
		List<String> emailsAttendees=courseService.getEmailsAttendeesForCourse(course);
		mailService.sendCourseFinished(emailsAttendees, course);
		return "redirect:/courses";
	}

}
