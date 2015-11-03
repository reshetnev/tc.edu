package com.mycompany.webapp.controller.course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
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
import com.mycompany.webapp.domain.Role;
import com.mycompany.webapp.domain.State;
import com.mycompany.webapp.domain.User;
import com.mycompany.webapp.domain.form.ApprovalForm;
import com.mycompany.webapp.enums.RolesEnum;
import com.mycompany.webapp.enums.StatesEnum;
import com.mycompany.webapp.service.CourseService;
import com.mycompany.webapp.service.DetailService;
import com.mycompany.webapp.service.MailService;
import com.mycompany.webapp.service.RoleService;
import com.mycompany.webapp.service.StateService;
import com.mycompany.webapp.service.UserService;

import freemarker.template.TemplateException;

@Controller
@SessionAttributes("email")
@RequestMapping(value = "/courses/{courseId}/approve")
public class CourseApproveController {
	
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private DetailService detailService;
    @Autowired
    private StateService stateService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MailService mailService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView approve(@PathVariable("courseId") String courseId,
			HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		String login=request.getUserPrincipal().getName();
		String email=userService.getUserEmail(login);
		model.addObject("email", email);
		try {
			int id=Integer.parseInt(courseId);
			Course course=courseService.findById(id);
			Detail detail=detailService.findById(id);
			if (course.getState().getStateId()==StatesEnum.PROPOSAL.getStateId()) {
				List<Role> roles=new ArrayList<Role>();
				Role roleKM = roleService.findByRoleId(RolesEnum.KNOWLEDGE_MANAGER.getRoleId());
				roles.add(roleKM);
				Role roleDM = roleService.findByRoleId(RolesEnum.DEPARTMENT_MANAGER.getRoleId());
				roles.add(roleDM);
				List<User> users=userService.getUsersByRoles(roles);
				ApprovalForm approvalForm=new ApprovalForm();
				Map<String, String> mapDecisionsAll=courseService.getMapDecisionsAll();
				Map<String, String> mapDecisionsKM=courseService.getMapDecisionsKM(course);
				Map<String, String> mapDecisionsDM=courseService.getMapDecisionsDM(course);
				model.addObject("mapDecisionsAll", mapDecisionsAll);
				model.addObject("mapDecisionsKM", mapDecisionsKM);
				model.addObject("mapDecisionsDM", mapDecisionsDM);
				model.addObject("approvalForm", approvalForm);
				model.addObject("users", users);
				model.addObject("course", course);			
				model.addObject("detail", detail);
				model.setViewName("approve");				
			} else {
				model.setViewName("approve_forbidden");
			}
		} catch(NumberFormatException e) {
			model.setViewName("approve_unknown");
		} catch (NullPointerException e) {
			model.setViewName("approve_unknown");
		}
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processApprove(@Valid ApprovalForm approvalForm, BindingResult result,
			@ModelAttribute("email") String email, Model model, HttpServletRequest request,
			@PathVariable("courseId") String courseId)
					throws IOException, TemplateException, MessagingException {
		
		int id=Integer.parseInt(courseId);
		Course course=courseService.findById(id);
		Detail detail=detailService.findById(id);
		String login=request.getUserPrincipal().getName();
		User user=userService.findByLogin(login);
		Role roleKM = roleService.findByRoleId(RolesEnum.KNOWLEDGE_MANAGER.getRoleId());
		Role roleDM = roleService.findByRoleId(RolesEnum.DEPARTMENT_MANAGER.getRoleId());
		Role roleU = roleService.findByRoleId(RolesEnum.USER.ordinal()+1);
		if (result.hasErrors()) {
			List<Role> roles=new ArrayList<Role>();
			roles.add(roleKM);
			roles.add(roleDM);
			List<User> users=userService.getUsersByRoles(roles);
			Map<String, String> mapDecisionsAll=courseService.getMapDecisionsAll();
			Map<String, String> mapDecisionsKM=courseService.getMapDecisionsKM(course);
			Map<String, String> mapDecisionsDM=courseService.getMapDecisionsDM(course);
			model.addAttribute("mapDecisionsAll", mapDecisionsAll);
			model.addAttribute("mapDecisionsKM", mapDecisionsKM);
			model.addAttribute("mapDecisionsDM", mapDecisionsDM);
			model.addAttribute("email", email);
			model.addAttribute("course", course);			
			model.addAttribute("detail", detail);
			model.addAttribute("users", users);
			return "approve";
		} else {
			courseService.updateCourse(course, user, approvalForm);
			List<String> emailsKM=userService.getEmailsByRole(roleKM);
			List<String> emailsDM=userService.getEmailsByRole(roleDM);
			List<String> emailsU=userService.getEmailsByRole(roleU);
			String emailOwner=detail.getLecturer();
			User owner=userService.findByEmail(emailOwner);
			mailService.sendCourseApprovalUpdateMail(owner, emailsKM, emailsDM, login, course, approvalForm);
			State stateNew=stateService.findByStateId(StatesEnum.NEW.ordinal()+1);
			State stateRejected=stateService.findByStateId(StatesEnum.REJECTED.getStateId());
			courseService.updateCourse(course, stateNew, stateRejected);
			if (course.getState().getStateId()==StatesEnum.NEW.getStateId()) {
				mailService.sendNewCourseAddedMail(emailsU, owner, emailsKM, emailsDM, course, detail, courseId);
			}
			if (course.getState().getStateId()==StatesEnum.REJECTED.getStateId()) {
				mailService.sendCourseRejectedMail(owner, emailsKM, emailsDM, course, courseId);
			}
		}
		return "redirect:/courses";
	}

}
