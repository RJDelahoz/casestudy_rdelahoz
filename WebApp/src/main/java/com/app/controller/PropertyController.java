package com.app.controller;

import com.app.dao.OrganizationDao;
import com.app.dao.PropertyDao;
import com.app.dao.UserDao;
import com.app.model.Memo;
import com.app.model.Organization;
import com.app.model.Property;
import com.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PropertyController {

	private final UserDao userDao;
	private final OrganizationDao organizationDao;
	private final PropertyDao propertyDao;

	@Autowired
	public PropertyController(UserDao userDao, OrganizationDao organizationDao,
							  PropertyDao propertyDao) {
		this.userDao = userDao;
		this.organizationDao = organizationDao;
		this.propertyDao = propertyDao;
	}

	@RequestMapping("/register-property")
	public String getPropertyForm(Model model) {
		model.addAttribute("propertyForm", new Property());
		return "register-property";
	}

	@RequestMapping(value = "/register-property", method = RequestMethod.POST)
	public String processPropertyForm(HttpServletRequest request,
									  @RequestParam("message") String message,
									  @ModelAttribute("propertyForm") Property property) {
		String username = request.getUserPrincipal().getName();

		Optional<User> optionalUser = userDao.getUserByUsername(username);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			Organization organization = user.getOrganization();

			Memo memo = new Memo();
			memo.setSubject("Welcome!");
			memo.setContent(message);
			memo.setProperty(property);

			property.setManagedBy(user.getOrganization());
			property.setMemo(memo);
			organization.getProperties().add(property);

			propertyDao.addProperty(property);
		}
		return "redirect:/properties";
	}

	@RequestMapping(value = "/joinProperty", method = RequestMethod.POST)
	public String joinPropertyFormHandler(@RequestParam("orgName") String orgName,
										  @RequestParam("address") String address,
										  HttpServletRequest request) {
		Optional<Organization> organization =
				organizationDao.findOrganizationByName(orgName);
		String username = request.getUserPrincipal().getName();

		if (organization.isPresent()) {
			Optional<Property> optionalProperty =
					propertyDao.findPropertyByAddressAndOrgName(address, orgName);

			if (optionalProperty.isPresent()) {
				Optional<User> optionalUser = userDao.getUserByUsername(username);
				if (optionalUser.isPresent()) {
					User user = optionalUser.get();
					user.setProperty(optionalProperty.get());
					userDao.updateUser(user);
				}
			}
		}
		return "redirect:/welcome";
	}

	@RequestMapping("/properties")
	public String propertiesHandler(Model model,
									HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();
		Optional<User> optionalUser = userDao.getUserByUsername(username);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();

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
		return "/403";
	}

	@RequestMapping("/view-property")
	public String viewPropertyHandler(Model model,
									  HttpServletRequest request,
									  Authentication authentication,
									  @RequestParam("id") long id) {

		Optional<Property> optionalProperty = propertyDao.findPropertyById(id);
		String username = request.getUserPrincipal().getName();
		String role = authentication.getAuthorities().toString();

		if (optionalProperty.isPresent()) {
			Property property = optionalProperty.get();
			model.addAttribute("authority", role);
			model.addAttribute("user", userDao.getUserByUsername(username));
			model.addAttribute("property", property);
		}

		return "property";
	}

	@Transactional
	@RequestMapping("/delete-property")
	public String deletePropertyHandler(@RequestParam("id") long id) {
		Property property = propertyDao.getPropertyById(id);
		propertyDao.deleteProperty(property);
		return "properties";
	}

	@RequestMapping("/residents")
	public String residentTableHandler(Model model,
									   @RequestParam("id") long id,
									   @RequestParam("page") Optional<Integer> page) {

		Property property = propertyDao.getPropertyById(id);
		List<User> residents = userDao.findAllByProperty(property);

		int currentPage = page.orElse(1);

		Page<User> userPage = userDao.getUsersPaginated(PageRequest
				.of(currentPage - 1, 5), residents);

		model.addAttribute("residentPage", userPage);

		int totalPages = userPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("address", property.getAddress());
		return "residents";
	}
}
