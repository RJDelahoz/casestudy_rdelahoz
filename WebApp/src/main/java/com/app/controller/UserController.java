package com.app.controller;

import com.app.model.Credential;
import com.app.model.User;
import com.app.service.CredentialService;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class UserController {

	private final CredentialService credentialService;
	private final UserService userService;

	@Autowired
	public UserController(CredentialService credentialService, UserService userService) {
		this.credentialService = credentialService;
		this.userService = userService;
	}

	@RequestMapping("/welcome")
	public String welcomeHandler(Model model, HttpServletRequest request) {

		return "welcome";
	}

	@RequestMapping("/ticket-center")
	public String ticketCenterHandler(HttpServletRequest request) {

		return "ticket-center";
	}

}
