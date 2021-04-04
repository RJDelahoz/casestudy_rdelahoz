package com.app.controller;

import com.app.model.Authority;
import com.app.model.Credential;
import com.app.model.User;
import com.app.service.CredentialService;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class BaseController {


	private final CredentialService credentialService;
	private final UserService userService;

	@Autowired
	public BaseController(CredentialService credentialService, UserService userService) {
		this.credentialService = credentialService;
		this.userService = userService;
	}

	@RequestMapping("/")
	public String getLandingPage() {
		System.out.println("\n\nHome Page\n\n");
		return "index";
	}

	@RequestMapping("/contactus")
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
