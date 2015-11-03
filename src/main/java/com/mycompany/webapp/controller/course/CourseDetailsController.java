package com.mycompany.webapp.controller.course;

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
import com.mycompany.webapp.domain.User;
import com.mycompany.webapp.enums.RolesEnum;
import com.mycompany.webapp.enums.StatesEnum;
import com.mycompany.webapp.service.CourseService;
import com.mycompany.webapp.service.DetailService;
import com.mycompany.webapp.service.UserService;


@Controller
@SessionAttributes("email")
public class CourseDetailsController {
	
    @Autowired
    private DetailService detailService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
	
	@RequestMapping(value = "/courses/{courseId}", method = RequestMethod.GET)
	public ModelAndView detailCourses(@PathVariable("courseId") String courseId,
			HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		String login=request.getUserPrincipal().getName();
		String email=userService.getUserEmail(login);
		model.addObject("email", email);		
		try {
			int id=Integer.parseInt(courseId);
			Course course=courseService.findById(id);
			Detail detail=detailService.findById(id);
			User user=userService.findByLogin(login);
			double mark=courseService.getMark(course);
			model.addObject("course", course);			
			model.addObject("detail", detail);
			model.addObject("mark", mark);
			if ((course.getState().getStateId()==StatesEnum.NEW.getStateId())||
					(course.getState().getStateId()==StatesEnum.OPEN.getStateId())||
					(course.getState().getStateId()==StatesEnum.READY.getStateId())||
					(course.getState().getStateId()==StatesEnum.IN_PROGRESS.getStateId())||
					(course.getState().getStateId()==StatesEnum.FINISHED.getStateId())) {
				model.setViewName("detail");
			} else if ((course.getState().getStateId()==StatesEnum.DRAFT.getStateId())&&
					(detail.getLecturer().compareTo(email)==0)) {
				model.setViewName("detail");				
			} else if (((course.getState().getStateId()==StatesEnum.PROPOSAL.getStateId())||
					(course.getState().getStateId()==StatesEnum.REJECTED.getStateId()))&&
					((detail.getLecturer().compareTo(email)==0)||
					(user.getRole().getRoleId()==RolesEnum.KNOWLEDGE_MANAGER.getRoleId())||
					(user.getRole().getRoleId()==RolesEnum.DEPARTMENT_MANAGER.getRoleId()))) {
				model.setViewName("detail");				
			} else {
				model.setViewName("detail_forbidden");
			}
		} catch(NumberFormatException e) {
			model.setViewName("detail_unknown");
		} catch (NullPointerException e) {
			model.setViewName("detail_unknown");
		}
		return model;
	}

}
