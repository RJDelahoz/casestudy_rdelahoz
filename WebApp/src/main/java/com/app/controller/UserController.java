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
		if (role.contains("ADMIN")) {
			return new ModelAndView("redirect:/admin");
		}

		if (role.contains("MANAGER")) {
			Optional<Organization> optionalOrganization =
					organizationService.findByManagerUserName(username);

			if (optionalOrganization.isPresent()) {
				if (optionalOrganization.get().getProperties().isEmpty()) {
					model.addAttribute("propertyForm", new Property());
					return new ModelAndView("register-property");
				} else {
					model.addAttribute("orgPropertiesListBean",
							optionalOrganization.get().getProperties());
					optionalOrganization.get().getProperties().forEach(System.out::println);
					return new ModelAndView("redirect:/properties");
				}
			}
		} else if (role.contains("USER")) {
			if (user.getProperty() == null) {
				return new ModelAndView("request-access");
			} else {
				model.addAttribute("userProperty", user.getProperty());
			}
		}

		model.addAttribute("authority", role);
		model.addAttribute("welcomeMessage", HelperClass.greetingHelper(username));
		return new ModelAndView("welcome");
	}

	@RequestMapping("/ticket-center")
	public String ticketCenterHandler(Model model,
									  HttpServletRequest request,
									  Authentication authentication) {
		String username = request.getUserPrincipal().getName();
		String role = authentication.getAuthorities().toString();

		User user = userService.getUserByUsername(username);
		if (role.contains("MANAGER")) {
			model.addAttribute("");
		} else if (role.contains("USER")) {
			System.out.println("THESE ARE THE TICKET REQUEST FOR ALL THE YOU ARE A PART OF.");
		}
		return "ticket-center";
	}

}
