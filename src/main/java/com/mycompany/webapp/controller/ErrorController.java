package com.mycompany.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("email")
public class ErrorController {
	
	@RequestMapping(value = "/**")
	@ExceptionHandler(Exception.class)
	public String handleNotFoundPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "redirect:/courses";		
	}
	
	@RequestMapping(value = "/forbidden")
	public String handleForbiddenPage(@ModelAttribute("email") String email, Model model) {
		model.addAttribute("email", email);
		return "forbidden";
	}

}
