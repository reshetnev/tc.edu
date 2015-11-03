package com.mycompany.webapp.controller.course;

import java.util.List;

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
import com.mycompany.webapp.service.UserService;

@Controller
@SessionAttributes("email")
@RequestMapping(value = "/courses/{courseId}/participants")
public class CourseParticipantsController {
	
    @Autowired
    private DetailService detailService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getParticipants(@PathVariable("courseId") String courseId,
			@ModelAttribute("email") String email) {
		
		ModelAndView model = new ModelAndView();
		model.addObject("email", email);		
		try {
			int id=Integer.parseInt(courseId);
			Course course = courseService.findById(id);
			List<String> emailsSubscribers=courseService.getEmailsSubscribersForCourse(course);
			List<String> emailsAttendees=courseService.getEmailsAttendeesForCourse(course);			
			Detail detail=detailService.findById(id);
			if ((course.getState().getStateId()==StatesEnum.NEW.getStateId())||
					(course.getState().getStateId()==StatesEnum.OPEN.getStateId())||
					(course.getState().getStateId()==StatesEnum.READY.getStateId())||
					(course.getState().getStateId()==StatesEnum.IN_PROGRESS.getStateId())||
					(course.getState().getStateId()==StatesEnum.FINISHED.getStateId())) {
				model.addObject("course", course);
				model.addObject("detail", detail);
				model.addObject("emailsSubscribers", emailsSubscribers);			
				model.addObject("emailsAttendees", emailsAttendees);
				model.setViewName("participants");
			} else {
				model.setViewName("participants_forbidden");
			}
		} catch(NumberFormatException e) {
			model.setViewName("participants_unknown");
		} catch (NullPointerException e) {
			model.setViewName("participants_unknown");
		}

		return model;
	}	

}
