package com.mycompany.webapp.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycompany.webapp.domain.form.LoginForm;

@Controller
public class LoginController {

	@Autowired
	private AbstractAuthenticationProcessingFilter authenticationFilter;

	private FilterChain chain = new FilterChain() { 
		@Override
		public void doFilter(final ServletRequest request, final ServletResponse response)
				throws IOException, ServletException {
		}
	};
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model, HttpServletRequest request) {
		LoginForm loginForm=new LoginForm();
		model.addAttribute("loginForm", loginForm);
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String validateUser(@Valid LoginForm loginForm, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (bindingResult.hasErrors()) {
			return "login";
		}
		authenticationFilter.doFilter(request, response, chain);
		return null;
	}
	
	@RequestMapping(value = "/login_unknown", method = RequestMethod.GET)
	public String loginUnknow(@Valid LoginForm loginForm, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (bindingResult.hasErrors()) {
			return "login_unknown";
		}
		authenticationFilter.doFilter(request, response, chain);
		return null;
	}

}
