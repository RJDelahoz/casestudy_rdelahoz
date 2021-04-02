package com.app.controller;

import com.app.model.Credential;
import com.app.model.User;
import com.app.repo.UserRepository;
import com.app.service.PostService;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Controller
public class BaseController {

	@RequestMapping("/")
	public String getLandingPage() {
		System.out.println("\n\nHome Page\n\n");
		return "index";
	}

	@RequestMapping("/support")
	public String getBlogPage() {
		System.out.println("\n\nContact Us Page\n\n");
		return "support";
	}

	@RequestMapping("/login")
	public String getLoginPage() {
		System.out.println("\n\nLogin Page\n\n");
		return "login";
	}

	@RequestMapping("/register")
	public String getRegistrationPage() {
		System.out.println("\n\nRegistration Page\n\n");
		return "registration";
	}

	@RequestMapping("/password-recovery")
	public String getSupportPage() {
		System.out.println("\n\nPassword recovery Page\n\n");
		return "password-recovery";
	}

}
