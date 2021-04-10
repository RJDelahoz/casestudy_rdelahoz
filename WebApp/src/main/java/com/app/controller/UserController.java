package com.app.controller;

import com.app.model.*;
import com.app.service.CredentialService;
import com.app.service.OrganizationService;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class UserController {

	private final CredentialService credentialService;
	private final UserService userService;
	private final OrganizationService organizationService;

	@Autowired
	public UserController(CredentialService credentialService, UserService userService,
						  OrganizationService organizationService) {
		this.credentialService = credentialService;
		this.userService = userService;
		this.organizationService = organizationService;
	}

	@RequestMapping("/welcome")
	public String welcomeHandler(Model model,
									   Authentication authentication,
									   HttpServletRequest request) {
		String[] credentials = HelperClass.getUsernameAndRole(request, authentication);
		User user = userService.getUserByUsername(credentials[0]);
		String role = credentials[1];

		Property property = user.getProperty();
		if (property != null) {
			model.addAttribute("authority", role);
			model.addAttribute("user", user);
			model.addAttribute("property", property);

			return "property";
		} else {
			return "request-access";
		}
	}


}
