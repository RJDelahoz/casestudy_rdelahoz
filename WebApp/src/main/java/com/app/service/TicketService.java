package com.app.service;

import com.app.model.Property;
import com.app.model.Ticket;
import com.app.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

	private final TicketRepository ticketRepository;

	@Autowired
	public TicketService(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	public void addTicket(Ticket ticket) {
		ticketRepository.save(ticket);
	}

	public Ticket getTicketById(long id) {
		Optional<Ticket> ticketOptional = ticketRepository.findById(id);
		return ticketOptional.orElse(null);
	}

	public List<Ticket> getAllOpenTicketsFromProperty(Property property) {
		return ticketRepository.findAllByPropertyAndStatusOrderByTimestampAsc(property, "OPEN");
	}

	public List<Ticket> getAllClosedTicketsFromProperty(Property property) {
		return ticketRepository.findAllByPropertyAndStatusOrderByTimestampAsc(property, "CLOSED");
	}
}
