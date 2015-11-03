package com.mycompany.webapp.controller.course;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

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
import com.mycompany.webapp.domain.State;
import com.mycompany.webapp.domain.User;
import com.mycompany.webapp.enums.StatesEnum;
import com.mycompany.webapp.service.AttendService;
import com.mycompany.webapp.service.CourseService;
import com.mycompany.webapp.service.DetailService;
import com.mycompany.webapp.service.MailService;
import com.mycompany.webapp.service.StateService;
import com.mycompany.webapp.service.SubscribeService;
import com.mycompany.webapp.service.UserService;

import freemarker.template.TemplateException;

@Controller
@SessionAttributes("email")
@RequestMapping(value = "/courses/{courseId}/attend")
public class CourseAttendController {
	
    @Autowired
    private DetailService detailService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SubscribeService subscribeService;
    @Autowired
    private AttendService attendService;
    @Autowired
    private StateService stateService;
    @Autowired
    private MailService mailService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView attend(@ModelAttribute("email") String email,
			@PathVariable("courseId") String courseId) {
		
		ModelAndView model = new ModelAndView();
		model.addObject("email", email);
		try {
			int id=Integer.parseInt(courseId);
			Course course=courseService.findById(id);
			List<String> emailsSubscribers=courseService.getEmailsSubscribersForCourse(course);
			List<String> emailsAttendees=courseService.getEmailsAttendeesForCourse(course);				
			if (emailsSubscribers.contains(email)&&
					(!emailsAttendees.contains(email))&&
					((course.getState().getStateId()==StatesEnum.OPEN.getStateId())||
					(course.getState().getStateId()==StatesEnum.READY.getStateId()))) {
				Detail detail=detailService.findById(id);
				model.addObject("course", course);
				model.addObject("detail", detail);
				model.setViewName("attend");				
			} else {
				model.setViewName("attend_forbidden");
			}
		} catch(NumberFormatException e) {
			model.setViewName("attend_unknown");
		} catch (NullPointerException e) {
			model.setViewName("attend_unknown");
		}
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processAttend(@PathVariable("courseId") String courseId, HttpServletRequest request)
			throws MessagingException, IOException, TemplateException {
		int id=Integer.parseInt(courseId);
		Course course=courseService.findById(id);
		String login=request.getUserPrincipal().getName();
		User user=userService.findByLogin(login);
		attendService.addAttend(course, user);
		userService.updateUser(user);
		courseService.updateCourse(course);
		if (course.getAttends().size()==course.getDetail().getqAtts()) {
			State stateReady=stateService.findByStateId(StatesEnum.READY.getStateId());
			course.setState(stateReady);
			courseService.updateCourse(course);
			mailService.sendCourseIsReady(course);
		}
		subscribeService.deleteSubscribe(course, user);
		return "redirect:/courses";
	}

}
