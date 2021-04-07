package com.app.controller;

import com.app.model.*;
import com.app.service.CredentialService;
import com.app.service.OrganizationService;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

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

		String username = request.getUserPrincipal().getName();
		String role = authentication.getAuthorities().toString();

		User user = userService.getUserByUsername(username);
		if (role.contains("ADMIN")) {
			return "redirect:/admin";
		}

		if (role.contains("MANAGER")) {
			Optional<Organization> optionalOrganization =
					organizationService.findByManagerUserName(username);

			if (optionalOrganization.isPresent()) {
				if (optionalOrganization.get().getProperties().isEmpty()) {
					model.addAttribute("propertyForm", new Property());
					return "register-property";
				} else {
					model.addAttribute("orgPropertiesListBean",
							optionalOrganization.get().getProperties());
				}
			}
		} else if (role.contains("USER")) {
			Set<Property> properties = user.getProperties();
			if (properties.isEmpty()) {
				System.out.println(username + " is not registered under any properties.");
			} else {
				model.addAttribute("userPropertiesListBean",
						properties);
			}
		}

		model.addAttribute("authority", role);
		model.addAttribute("welcomeMessage", HelperClass.greetingHelper(username));
		return "welcome";
	}

	@RequestMapping("/ticket-center")
	public String ticketCenterHandler(HttpServletRequest request,
									  Authentication authentication) {
		String username = request.getUserPrincipal().getName();
		String role = authentication.getAuthorities().toString();

		User user = userService.getUserByUsername(username);
		if (role.contains("MANAGER")) {
			System.out.println("THESE ARE THE TICKET REQUEST FOR ALL THE PROPERTIES UNDER YOU ORG.");
		}else if (role.contains("USER")) {
			System.out.println("THESE ARE THE TICKET REQUEST FOR ALL THE PROPERTIES YOU ARE A PART OF.");
		}
		return "ticket-center";
	}

}
