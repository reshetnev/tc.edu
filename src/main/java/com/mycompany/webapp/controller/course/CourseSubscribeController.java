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
import com.mycompany.webapp.domain.Subscribe;
import com.mycompany.webapp.domain.User;
import com.mycompany.webapp.enums.StatesEnum;
import com.mycompany.webapp.service.CourseService;
import com.mycompany.webapp.service.DetailService;
import com.mycompany.webapp.service.MailService;
import com.mycompany.webapp.service.StateService;
import com.mycompany.webapp.service.UserService;

import freemarker.template.TemplateException;

@Controller
@SessionAttributes("email")
@RequestMapping(value = "/courses/{courseId}/subscribe")
public class CourseSubscribeController {
	
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
	public ModelAndView subscribe(HttpServletRequest request,
			@PathVariable("courseId") String courseId) {
		
		ModelAndView model = new ModelAndView();
		String login=request.getUserPrincipal().getName();
		String email=userService.getUserEmail(login);
		model.addObject("email", email);
		try {
			int id=Integer.parseInt(courseId);
			Course course=courseService.findById(id);			
			List<String> emails=courseService.getEmailsSubscribersForCourse(course);
			if ((!emails.contains(email))&&
					((course.getState().getStateId()==StatesEnum.NEW.getStateId())||
					(course.getState().getStateId()==StatesEnum.OPEN.getStateId())||
					(course.getState().getStateId()==StatesEnum.READY.getStateId()))) {
				Detail detail=detailService.findById(id);
				model.addObject("course", course);
				model.addObject("detail", detail);
				model.setViewName("subscribe");
			} else {
				model.setViewName("subscribe_forbidden");
			}
		} catch(NumberFormatException e) {
			model.setViewName("subscribe_unknown");
		} catch (NullPointerException e) {
			model.setViewName("subscribe_unknown");
		}
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processSubscribe(@PathVariable("courseId") String courseId, HttpServletRequest request)
			throws MessagingException, IOException, TemplateException {
		int id=Integer.parseInt(courseId);
		Course course=courseService.findById(id);
		String login=request.getUserPrincipal().getName();
		User user=userService.findByLogin(login);
		Subscribe subscribe=new Subscribe();
		State stateOpen=stateService.findByStateId(StatesEnum.OPEN.ordinal()+1);
		courseService.updateCourse(course, subscribe, user);
		List<String> emailsSubscribers=courseService.getEmailsSubscribersForCourse(course);
		if ((course.getSubscribes().size()==course.getDetail().getqSubs())&&
				(course.getState().getStateId()==StatesEnum.NEW.getStateId())) {
			course.setState(stateOpen);
			mailService.sendCourseIsOpen(emailsSubscribers, course);
			courseService.updateCourse(course);			
		}
		return "redirect:/courses";
	}
	
}
