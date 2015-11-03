package com.mycompany.webapp.controller.course;

import java.io.IOException;
import java.util.LinkedHashMap;
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

import com.mycompany.webapp.domain.Category;
import com.mycompany.webapp.domain.Course;
import com.mycompany.webapp.domain.Detail;
import com.mycompany.webapp.domain.Role;
import com.mycompany.webapp.domain.State;
import com.mycompany.webapp.domain.form.UpdateForm;
import com.mycompany.webapp.enums.RolesEnum;
import com.mycompany.webapp.enums.StatesEnum;
import com.mycompany.webapp.service.CategoryService;
import com.mycompany.webapp.service.CourseService;
import com.mycompany.webapp.service.DetailService;
import com.mycompany.webapp.service.MailService;
import com.mycompany.webapp.service.RoleService;
import com.mycompany.webapp.service.StateService;
import com.mycompany.webapp.service.UserService;

import freemarker.template.TemplateException;

@Controller
@SessionAttributes("email")
@RequestMapping(value = "/courses/{courseId}/update")
public class CourseUpdateController {
	
    @Autowired
    private DetailService detailService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private StateService stateService;
    @Autowired
    private RoleService roleService;    
    @Autowired
    private MailService mailService;
    
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView update(HttpServletRequest request,	@PathVariable("courseId") String courseId) {
		
		ModelAndView model = new ModelAndView();
		String login=request.getUserPrincipal().getName();
		String email=userService.getUserEmail(login);
		model.addObject("email", email);		
		try {
			int id=Integer.parseInt(courseId);
			Course course=courseService.findById(id);
			Detail detail=detailService.findById(id);
			if ((email.compareTo(detail.getLecturer())==0)&&
					((course.getState().getStateId()==StatesEnum.DRAFT.getStateId())||
					(course.getState().getStateId()==StatesEnum.REJECTED.getStateId()))) {
				UpdateForm updateForm=new UpdateForm(); 
				updateForm.setName(detail.getCourse().getName());
				updateForm.setCategory(course.getCategory().getCategory());
				updateForm.setDescription(detail.getDescription());
				updateForm.setLinks(detail.getLinks());
				updateForm.setqSubs(String.valueOf(detail.getqSubs()));
				updateForm.setqAtts(String.valueOf(detail.getqAtts()));					
				Map<String, String> mapCategories = new LinkedHashMap<String, String>();
				mapCategories.put("","");
				mapCategories.putAll(categoryService.getMapAllCategory());
				model.addObject("course", course);
				model.addObject("updateForm", updateForm);
				model.addObject("mapCategories", mapCategories);				
				model.setViewName("update");
			} else {
				model.setViewName("update_forbidden");
			}
		} catch(NumberFormatException e) {
			model.setViewName("update_unknown");
		} catch (NullPointerException e) {
			model.setViewName("update_unknown");
		}
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processUpdate(@Valid UpdateForm updateForm, BindingResult result,
			@ModelAttribute("email") String email, Model model,
			@PathVariable("courseId") String courseId, HttpServletRequest request) throws IOException, TemplateException, MessagingException {

		int id=Integer.parseInt(courseId);			
		Course course=courseService.findById(id);		
		if (result.hasErrors()) {
			Map<String, String> mapCategories = new LinkedHashMap<String, String>();
			mapCategories.put("","");
			mapCategories.putAll(categoryService.getMapAllCategory());
			model.addAttribute("course", course);
			model.addAttribute("email", email);
			model.addAttribute("mapCategories", mapCategories);			
			return "update";
		} else {
			course.setName(updateForm.getName());			
			Detail detail=detailService.findById(id);
			detailService.defineDetail(detail, updateForm, email);
			Category category=categoryService.findByCategory(updateForm.getCategory());
			String badge = request.getParameter("badge");
			State stateReview=stateService.findByBadge(badge);
			State stateProposal=stateService.findByStateId(StatesEnum.PROPOSAL.getStateId());
			courseService.updateCourse(course, detail, stateReview, stateProposal, category);
			if (course.getState().getStateId()==StatesEnum.PROPOSAL.getStateId()) {
				Role roleKM = roleService.findByRoleId(RolesEnum.KNOWLEDGE_MANAGER.getRoleId());
				List<String> emailsKM=userService.getEmailsByRole(roleKM);
				Role roleDM = roleService.findByRoleId(RolesEnum.DEPARTMENT_MANAGER.getRoleId());
				List<String> emailsDM=userService.getEmailsByRole(roleDM);
				String owner=request.getUserPrincipal().getName();
				mailService.sendCourseAnnouncementMail(emailsKM, emailsDM, email, owner, updateForm, courseId);
			}
		}
		return "redirect:/courses";
	}

}
