package com.mycompany.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("email")
public class LogoutController {
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(@ModelAttribute("email") String email) {
		ModelAndView model=new ModelAndView("logout");
		model.addObject("email", email);
		return model;
	}

}
