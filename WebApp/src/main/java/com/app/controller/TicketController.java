package com.app.controller;

import com.app.model.Property;
import com.app.model.Ticket;
import com.app.model.User;
import com.app.service.PropertyService;
import com.app.service.TicketService;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class TicketController {

	private final UserService userService;
	private final PropertyService propertyService;
	private final TicketService ticketService;

	@Autowired
	public TicketController(UserService userService, PropertyService propertyService, TicketService ticketService) {
		this.userService = userService;
		this.propertyService = propertyService;
		this.ticketService = ticketService;
	}

	@RequestMapping("/my-tickets")
	public String userOpenTicketsHandler(Model model,
										  HttpServletRequest request,
										  Authentication authentication) {
		String[] credentials = HelperClass.getUsernameAndRole(request, authentication);

		User user = userService.getUserByUsername(credentials[0]);
		String role = credentials[1];
		List<Ticket> tickets = ticketService.getAllOpenTicketsCreatedByUser(user);

		model.addAttribute("user", user);
		model.addAttribute("authority", role);
		model.addAttribute("userTickets", tickets);

		return "tickets";
	}
	@RequestMapping("/my-tickets-closed")
	public String userClosedTicketsHandler(Model model,
										  HttpServletRequest request,
										  Authentication authentication) {
		String[] credentials = HelperClass.getUsernameAndRole(request, authentication);

		User user = userService.getUserByUsername(credentials[0]);
		String role = credentials[1];
		List<Ticket> tickets = ticketService.getAllClosedTicketsCreatedByUser(user);

		model.addAttribute("user", user);
		model.addAttribute("authority", role);
		model.addAttribute("userTickets", tickets);

		return "tickets-closed";
	}

	@RequestMapping("/ticket-center")
	public String managerOpenTicketCenterHandler(Model model,
											 HttpServletRequest request,
											 Authentication authentication,
											 @RequestParam("id") long id) {
		String[] credentials = HelperClass.getUsernameAndRole(request, authentication);

		String username = credentials[0];
		String role = credentials[1];
		Property property = propertyService.getPropertyById(id);
		List<Ticket> tickets = ticketService.getAllOpenTicketsFromProperty(property);

		model.addAttribute("message", "Signed in as: " + username);
		model.addAttribute("propertyId", id);
		model.addAttribute("propertyAddress", property.getAddress());
		model.addAttribute("authority", role);
		model.addAttribute("propertyTickets", tickets);

		return "ticket-center";
	}
	@RequestMapping("/ticket-center-closed")
	public String managerClosedTicketCenterHandler(Model model,
											 HttpServletRequest request,
											 Authentication authentication,
											 @RequestParam("id") long id) {
		String[] credentials = HelperClass.getUsernameAndRole(request, authentication);

		String username = credentials[0];
		String role = credentials[1];
		Property property = propertyService.getPropertyById(id);
		List<Ticket> tickets = ticketService.getAllClosedTicketsFromProperty(property);

		System.out.println(property);
		model.addAttribute("message", "Signed in as: " + username);
		model.addAttribute("propertyId", id);
		model.addAttribute("propertyAddress", property.getAddress());
		model.addAttribute("authority", role);
		model.addAttribute("propertyTickets", tickets);

		return "ticket-center-closed";
	}

	@RequestMapping("/ticket")
	public String viewTicket(Model model,
							 HttpServletRequest request,
							 Authentication authentication,
							 @RequestParam("id") long id) {
		String[] credentials = HelperClass.getUsernameAndRole(request, authentication);

		String username = credentials[0];
		String role = credentials[1];
		Ticket ticket = ticketService.getTicketById(id);

		model.addAttribute("user", username);
		model.addAttribute("authority", role);
		model.addAttribute("ticket", ticket);

		return "view-ticket";
	}

	@RequestMapping("/ticketAction")
	public ModelAndView createTicketHandler(HttpServletRequest request,
									  @RequestParam("description") String description) {
		User user = userService.getUserByUsername(request.getUserPrincipal().getName());

		Ticket ticket = new Ticket();

		ticket.setCreatedBy(user);
		ticket.setProperty(user.getProperty());
		ticket.setStatus("OPEN");
		ticket.setDescription(description);
		ticket.setTimestamp(new Date());

		ticketService.addTicket(ticket);

		return new ModelAndView("redirect:/my-tickets");
	}

	@Transactional
	@RequestMapping("/update-ticket-status")
	public ModelAndView updateTicketStatus(@RequestParam("id") long id) {
		Ticket ticket = ticketService.getTicketById(id);

		if (ticket.getStatus().equals("OPEN")) {
			ticket.setStatus("CLOSED");
		} else {
			ticket.setStatus("OPEN");
		}

		ticketService.addTicket(ticket);

		return new ModelAndView("redirect:/ticket-center?id=" + ticket.getProperty().getId());
	}
}
