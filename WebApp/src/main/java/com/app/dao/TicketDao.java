package com.app.dao;

import com.app.model.Property;
import com.app.model.Ticket;
import com.app.model.User;

import java.util.List;

public interface TicketDao {

	// Create
	void addTicket(Ticket ticket);

	// Retrieve
	Ticket getTicketById(long id);

	List<Ticket> getAllOpenTicketsFromProperty(Property property);

	List<Ticket> getAllClosedTicketsFromProperty(Property property);

	List<Ticket> getAllOpenTicketsCreatedByUser(User user);

	List<Ticket> getAllClosedTicketsCreatedByUser(User user);

	// Update
	boolean updateTicket(Ticket ticket);

	// Delete
	boolean deleteTicketById(long id);
}
