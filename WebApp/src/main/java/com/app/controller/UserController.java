package com.app.controller;

import com.app.dao.UserDao;
import com.app.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class UserController {

	private final UserDao userDao;

	@Autowired
	public UserController(UserDao userDao) {
		this.userDao = userDao;
	}

	@RequestMapping("/welcome")
	public String welcomeHandler(Model model,
								 Authentication authentication,
								 HttpServletRequest request) {
		String[] credentials = HelperClass.getUsernameAndRole(request, authentication);
		Optional<User> optionalUser = userDao.getUserByUsername(credentials[0]);
		String role = credentials[1];

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();

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
		return "/403";
	}
}
