package com.app.controller;

import com.app.model.Organization;
import com.app.model.Property;
import com.app.model.User;
import com.app.service.OrganizationService;
import com.app.service.PropertyService;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

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

	@Transactional
	@RequestMapping(value = "/joinProperty", method = RequestMethod.POST)
	public String joinPropertyFormHandler(@RequestParam("orgName") String orgName,
										  @RequestParam("address") String address,
										  HttpServletRequest request) {
		Optional<Organization> organization =
				organizationService.findOrganizationByName(orgName);
		String username = request.getUserPrincipal().getName();

		if (organization.isPresent()) {
			Optional<Property> optionalProperty =
					propertyService.findPropertyByAddressAndOrgName(address, orgName);

			if (optionalProperty.isPresent()) {
				User user = userService.getUserByUsername(username);
				user.setProperty(optionalProperty.get());
				userService.addUser(user);
			}
		}

		return "redirect:/welcome";
	}


	@RequestMapping("/properties")
	public String propertiesHandler(Model model,
									HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();
		User user = userService.getUserByUsername(username);


		Organization organization = user.getOrganization();
		Set<Property> propertySet = organization.getProperties();
		model.addAttribute("message", HelperClass.greetingHelper(username));
		model.addAttribute("orgName", organization.getName());
		model.addAttribute("propertySet", propertySet);
		return "properties";
	}

	@Transactional
	@RequestMapping("/ViewProperty")
	public String viewPropertyHandler(@RequestParam("id") long id) {

		Optional<Property> optionalProperty = propertyService.findPropertyById(id);

		if (optionalProperty.isPresent()) {
			Property property = optionalProperty.get();

			propertyService.addProperty(property);
		}

		return "property";
	}
}
