package com.app.controller;

import com.app.dao.OrganizationDao;
import com.app.model.Authority;
import com.app.model.Credential;
import com.app.model.Organization;
import com.app.model.User;
import com.app.service.CredentialService;
import com.app.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class BaseController {

	private final CredentialService credentialService;
	private final UserDao userDao;
	private final OrganizationDao organizationDao;

	@Autowired
	public BaseController(CredentialService credentialService, UserDao userDao,
						  OrganizationDao organizationDao) {
		this.credentialService = credentialService;
		this.userDao = userDao;
		this.organizationDao = organizationDao;
	}

	@RequestMapping("/")
	public String getLandingPage() {
		String email = "root@groot.com";
		String username = "root";
		Optional<Credential> optionalCredential = credentialService.getCredentialByUsername(username);

		if (!optionalCredential.isPresent()) {
			Credential credential = new Credential();
			credential.setUsername(username);
			credential.setPassword(new BCryptPasswordEncoder().encode("password"));

			credential.setEnabled(true);
			Authority authority = new Authority();
			authority.setRole("ADMIN");
			authority.setCredential(credential);

			credential.getAuthorities().add(authority);

			User user = new User();
			user.setEmail(email);
			user.setfName("Ricardo");
			user.setlName("Delahoz");
			user.setCredential(credential);

			userDao.addUser(user);
		}

		return "index";
	}

	@RequestMapping("/contactus")
	public String getBlogPage() {
		System.out.println("\n\nContact Us Page\n\n");

		return "support";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage() {
		System.out.println("\n\nLogin Page\n\n");
		return "login";
	}

	@RequestMapping("/multi-user-select")
	public String selectAccountTypeHandler() {
		return "multi-user-select";
	}

	// Get registration page
	@RequestMapping(value = "/register-manager", method = RequestMethod.GET)
	public String registrationManagerHandler(Model model) {
		model.addAttribute("userForm", new User());
		return "register-manager";
	}

	// Save manager
	@RequestMapping(value = "/register-manager", method = RequestMethod.POST)
	public String registerManagerFormHandler(@ModelAttribute("userForm") User user,
											 @RequestParam("orgName") String orgName,
											 @RequestParam("username") String username,
											 @RequestParam("password") String password,
											 @RequestParam("confirmPassword") String confirmPassword) {

		Optional<Credential> optionalCredential = credentialService.getCredentialByUsername(username);
		Optional<Organization> optionalOrganization =
				organizationDao.findOrganizationByName(orgName);
		if (!optionalCredential.isPresent() && !optionalOrganization.isPresent()) {
			Credential credential = new Credential();
			credential.setUsername(username);
			if (password.equals(confirmPassword))
				credential.setPassword(new BCryptPasswordEncoder().encode(password));
			credential.setEnabled(true);

			Authority authority = new Authority();
			authority.setRole("MANAGER");
			authority.setCredential(credential);
			credential.getAuthorities().add(authority);
			user.setCredential(credential);

			Organization organization = new Organization();
			organization.setName(orgName);
			organization.setManagedBy(user);

			user.setOrganization(organization);

			userDao.addUser(user);
		}

		return "redirect:/login";
	}

	// Get registration page
	@RequestMapping(value = "/register-user", method = RequestMethod.GET)
	public String registrationHandler(Model model) {
		model.addAttribute("userForm", new User());
		return "register-user";
	}

	// Save manager
	@RequestMapping(value = "/register-user", method = RequestMethod.POST)
	public String registerFormHandler(@ModelAttribute("userForm") User user,
									  @RequestParam("username") String username,
									  @RequestParam("password") String password,
									  @RequestParam("confirmPassword") String confirmPassword) {

		Optional<Credential> optionalCredential = credentialService.getCredentialByUsername(username);
		if (!optionalCredential.isPresent()) {
			Credential credential = new Credential();
			credential.setUsername(username);
			if (password.equals(confirmPassword)) {
				credential.setPassword(new BCryptPasswordEncoder().encode(password));
			}

			credential.setEnabled(true);
			Authority authority = new Authority();
			authority.setRole("USER");

			authority.setCredential(credential);
			credential.getAuthorities().add(authority);
			user.setCredential(credential);

			userDao.addUser(user);
		}

		return "redirect:/login";
	}

	@RequestMapping("/password-recovery")
	public String getSupportPage() {
		System.out.println("\n\nPassword recovery Page\n\n");
		return "password-recovery";
	}
}
