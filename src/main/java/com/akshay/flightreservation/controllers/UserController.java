package com.akshay.flightreservation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.akshay.flightreservation.entities.User;
import com.akshay.flightreservation.repos.UserRepository;
import com.akshay.flightreservation.services.SecurityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private SecurityService securityService;
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@RequestMapping("/showReg")
	public String showRegistrationPage() {
		logger.info("Inside showRegistrationPage()");
		return "login/registerUser";
	}
	
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String register(@ModelAttribute("user") User user) {
		logger.info("Inside register()" + user);
		System.out.println(user.getPassword());
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		return "login/login";

	}
	
	@RequestMapping("/showLogin")
	public String showLoginPage() {
		logger.info("Inside showLogin() ");
		return "login/login";
	}
	
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam("email") String email,@RequestParam("password") String password,ModelMap model) {
		
		boolean loginResponse = securityService.login(email, password);
		logger.info("Inside Login() and email is : "+email);
		/*
		User user = userRepository.findByEmail(email);
		if(user.getPassword().equals(password)) {
			return "findFlights";
		}
		else {
			model.addAttribute("msg","Invalid User Name or Password");
			return "login/login";
		}
		*/
		if(loginResponse) {
			return "findFlights";
		}
		else {
			model.addAttribute("msg","Invalid User Name or Password");
			return "login/login";
		}
	}
}
