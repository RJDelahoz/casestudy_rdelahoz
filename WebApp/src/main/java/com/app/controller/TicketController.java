package com.app.controller;

import com.app.dao.PropertyDao;
import com.app.dao.TicketDao;
import com.app.dao.UserDao;
import com.app.model.Property;
import com.app.model.Ticket;
import com.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class TicketController {

	private final UserDao userDao;
	private final PropertyDao propertyDao;
	private final TicketDao ticketDao;

	@Autowired
	public TicketController(UserDao userDao, PropertyDao propertyDao, TicketDao ticketDao) {
		this.userDao = userDao;
		this.propertyDao = propertyDao;
		this.ticketDao = ticketDao;
	}

	@RequestMapping("/my-tickets")
	public String userOpenTicketsHandler(Model model,
										 HttpServletRequest request,
										 Authentication authentication) {
		String[] credentials = HelperClass.getUsernameAndRole(request, authentication);

		Optional<User> optionalUser = userDao.findUserByUsername(credentials[0]);
		String role = credentials[1];
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			List<Ticket> tickets = ticketDao.getAllOpenTicketsCreatedByUser(user);

			model.addAttribute("user", user);
			model.addAttribute("authority", role);
			model.addAttribute("userTickets", tickets);
		}

		return "tickets";
	}
	@RequestMapping("/my-tickets-closed")
	public String userClosedTicketsHandler(Model model,
										   HttpServletRequest request,
										   Authentication authentication) {
		String[] credentials = HelperClass.getUsernameAndRole(request, authentication);

		Optional<User> optionalUser = userDao.findUserByUsername(credentials[0]);
		String role = credentials[1];
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			List<Ticket> tickets = ticketDao.getAllClosedTicketsCreatedByUser(user);

			model.addAttribute("user", user);
			model.addAttribute("authority", role);
			model.addAttribute("userTickets", tickets);
		}
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
		Property property = propertyDao.getPropertyById(id);
		List<Ticket> tickets = ticketDao.getAllOpenTicketsFromProperty(property);

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
		Property property = propertyDao.getPropertyById(id);
		List<Ticket> tickets = ticketDao.getAllClosedTicketsFromProperty(property);

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
		Ticket ticket = ticketDao.getTicketById(id);

		model.addAttribute("user", username);
		model.addAttribute("authority", role);
		model.addAttribute("ticket", ticket);

		return "view-ticket";
	}

	@RequestMapping("/ticketAction")
	public ModelAndView createTicketHandler(HttpServletRequest request,
											@RequestParam("description") String description) {
		Optional<User> optionalUser = userDao.findUserByUsername(request.getUserPrincipal().getName());

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			Ticket ticket = new Ticket();

			ticket.setCreatedBy(user);
			ticket.setProperty(user.getProperty());
			ticket.setStatus("OPEN");
			ticket.setDescription(description);
			ticket.setTimestamp(new Date());

			ticketDao.addTicket(ticket);
		}
		return new ModelAndView("redirect:/my-tickets");
	}

	@RequestMapping("/update-ticket-status")
	public ModelAndView updateTicketStatus(@RequestParam("id") long id) {
		Ticket ticket = ticketDao.getTicketById(id);

		if (ticket.getStatus().equals("OPEN")) {
			ticket.setStatus("CLOSED");
		} else {
			ticket.setStatus("OPEN");
		}
		ticketDao.updateTicket(ticket);
		return new ModelAndView("redirect:/ticket-center?id=" + ticket.getProperty().getId());
	}
}
