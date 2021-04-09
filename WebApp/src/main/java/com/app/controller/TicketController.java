package com.app.controller;

import com.app.model.Ticket;
import com.app.model.User;
import com.app.service.PropertyService;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Controller
public class TicketController {

	private final UserService userService;
	private final PropertyService propertyService;

	@Autowired
	public TicketController(UserService userService, PropertyService propertyService) {
		this.userService = userService;
		this.propertyService = propertyService;
	}

	@RequestMapping("/ticket-center")
	public String userTicketCenterHandler(Model model,
										  HttpServletRequest request,
										  Authentication authentication) {
		String[] credentials = HelperClass.getUsernameAndRole(request, authentication);

		User user = userService.getUserByUsername(credentials[0]);
		String role = credentials[1];
		if (role.contains("USER")) {
			System.out.println("GRABBING TICKETS FROM USER");
			Set<Ticket> tickets = user.getTickets();

			model.addAttribute("user", user);
			model.addAttribute("authority", role);
			model.addAttribute("propertyTickets", tickets);
		}
		return "ticket-center";
	}

	@RequestMapping("/ticket-center/property")
	public String managerTicketCenterHandler(Model model,
											 HttpServletRequest request,
											 Authentication authentication,
											 @RequestParam("id") long id) {

		String[] credentials = HelperClass.getUsernameAndRole(request, authentication);

		String username = credentials[0];
		String role = credentials[1];
		if (role.contains("MANAGER")) {
			System.out.println("GRABBING TICKETS FROM PROPERTY");
			Set<Ticket> tickets = propertyService.getPropertyById(id).getTickets();

			model.addAttribute("message", "Signed in as: " + username);
			model.addAttribute("authority", role);
			model.addAttribute("propertyTickets", tickets);
		}
		return "ticket-center";
	}


}
