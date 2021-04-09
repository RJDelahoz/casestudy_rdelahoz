package com.app.controller;

import com.app.model.Memo;
import com.app.model.Organization;
import com.app.model.Property;
import com.app.model.User;
import com.app.service.OrganizationService;
import com.app.service.PropertyService;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping("/register-property")
	public String getPropertyForm(Model model) {
		model.addAttribute("propertyForm", new Property());
		return "register-property";
	}

	@Transactional
	@RequestMapping(value = "/register-property", method = RequestMethod.POST)
	public String processPropertyForm(HttpServletRequest request,
									  @RequestParam("message") String message,
									  @ModelAttribute("propertyForm") Property property) {
		String username = request.getUserPrincipal().getName();

		User user = userService.getUserByUsername(username);
		Organization organization = user.getOrganization();


		Memo memo = new Memo();
		memo.setSubject("GENERIC");
		memo.setContent(message);
		memo.setProperty(property);

		property.setManagedBy(user.getOrganization());
		property.setMemo(memo);
		organization.getProperties().add(property);

		propertyService.addProperty(property);
		return "redirect:/properties";
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
		if (propertySet.isEmpty()) {
			return "redirect:/register-property";
		} else {
			model.addAttribute("propertySet", propertySet);
			model.addAttribute("orgName", organization.getName());
			model.addAttribute("message", HelperClass.greetingHelper(username));
			return "properties";
		}
	}



	@RequestMapping("/ViewProperty")
	public String viewPropertyHandler(Model model,
									  HttpServletRequest request,
									  Authentication authentication,
									  @RequestParam("id") long id) {

		Optional<Property> optionalProperty = propertyService.findPropertyById(id);
		String username = request.getUserPrincipal().getName();
		String role = authentication.getAuthorities().toString();
		if (optionalProperty.isPresent()) {
			Property property = optionalProperty.get();
			model.addAttribute("authority", role);
			model.addAttribute("user", userService.getUserByUsername(username));
			model.addAttribute("property", property);
		}
		return "property";
	}

	@Transactional
	@RequestMapping("/DeleteProperty")
	public String deletePropertyHandler(@RequestParam("id") long id) {
		Property property = propertyService.deletePropertyById(id);
		System.out.println(property);
		return "property";
	}


}
