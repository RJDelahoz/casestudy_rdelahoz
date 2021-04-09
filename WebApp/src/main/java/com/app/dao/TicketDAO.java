package com.app.dao;

import com.app.model.Property;
import com.app.model.Ticket;

import java.util.List;

public interface TicketDAO {

	// Create
	void addTicket(Ticket ticket);

	// Retrieve
	Ticket getTicketById(long id);

	List<Ticket> getAllTicketsUnderProperty(Property property);

	List<Ticket> getAllOpenTicketsUnderProperty(Property property);
	List<Ticket> getAllClosedTicketsUnderProperty(Property property);

	// Update
	void updateTicket(Ticket ticket);

	void updateTicketStatus(String status);

	// Delete
	void deleteTicketById(long id);

	void deleteTicket(Ticket ticket);
}
