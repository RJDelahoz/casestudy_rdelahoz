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
	public ModelAndView welcomeHandler(Model model,
									   Authentication authentication,
									   HttpServletRequest request) {

		String username = request.getUserPrincipal().getName();
		String role = authentication.getAuthorities().toString();
		User user = userService.getUserByUsername(username);

		model.addAttribute("authority", role);
		Property property = user.getProperty();
		if (property == null) {
			return new ModelAndView("request-access");
		} else {
			model.addAttribute("userProperty", property);
			model.addAttribute("welcomeMessage", HelperClass.greetingHelper(username));
			return new ModelAndView("redirect:/ViewProperty?id=" + property.getId());
		}
	}


}
