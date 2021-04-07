package com.app.controller;

import com.app.model.Organization;
import com.app.model.Property;
import com.app.model.User;
import com.app.service.OrganizationService;
import com.app.service.PropertyService;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PropertyController {

	private final UserService userService;
	private final OrganizationService organizationService;
	private final PropertyService propertyService;

	@Autowired
	public PropertyController(UserService userService, OrganizationService organizationService,
							  PropertyService propertyService) {
		this.userService = userService;
		this.organizationService = organizationService;
		this.propertyService = propertyService;
	}

	// Save User
	@RequestMapping(value = "/property", method = RequestMethod.POST)
	public String registerFormHandler(@ModelAttribute("propertyForm") Property property,
									  HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();

		User user = userService.getUserByUsername(username);
		Organization organization = user.getOrganization();


		property.setManagedBy(user.getOrganization());
		organization.getProperties().add(property);

		propertyService.addProperty(property);
		return "redirect:/welcome";
	}
}
