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
import com.mycompany.webapp.enums.StatesEnum;
import com.mycompany.webapp.service.CourseService;
import com.mycompany.webapp.service.DetailService;
import com.mycompany.webapp.service.MailService;

import freemarker.template.TemplateException;

@Controller
@SessionAttributes("email")
@RequestMapping(value = "/courses/{courseId}/notify")
public class CourseNotifyController {
	
    @Autowired
    private DetailService detailService;
    @Autowired
    private MailService mailService;
    @Autowired
    private CourseService courseService;
    
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView notify(@PathVariable("courseId") String courseId,
			@ModelAttribute("email") String email) {
		
		ModelAndView model = new ModelAndView();
		model.addObject("email", email);		
		try {
			int id=Integer.parseInt(courseId);
			Course course = courseService.findById(id);
			List<String> emailsAttendeesNotEvaluated=courseService.getEmailsAttendeesNotEvaluatedForCourse(course);			
			Detail detail=detailService.findById(id);
			if ((course.getState().getStateId()==StatesEnum.FINISHED.getStateId())&&
					(detail.getLecturer().compareTo(email)==0)) {
				model.addObject("course", course);
				model.addObject("emailsAttendeesNotEvaluated", emailsAttendeesNotEvaluated);			
				model.setViewName("notify");								
			} else {
				model.setViewName("notify_forbidden");
			}
		} catch(NumberFormatException e) {
			model.setViewName("notify_unknown");
		} catch (NullPointerException e) {
			model.setViewName("notify_unknown");
		}
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processNotify(@PathVariable("courseId") String courseId)
			throws MessagingException, IOException, TemplateException {
		
		int id=Integer.parseInt(courseId);
		Course course=courseService.findById(id);
		List<String> emailsAttendeesNotEvaluated=courseService.getEmailsAttendeesNotEvaluatedForCourse(course);
		mailService.sendCourseNeedsVote(emailsAttendeesNotEvaluated, course);
		return "redirect:/courses";
	}

}

