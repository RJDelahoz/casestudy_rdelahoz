package com.app.service;

import com.app.dao.TicketDao;
import com.app.model.Property;
import com.app.model.Ticket;
import com.app.model.User;
import com.app.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketService implements TicketDao {

	private final TicketRepository ticketRepository;

	@Autowired
	public TicketService(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	@Override
	public void addTicket(Ticket ticket) {
		ticketRepository.save(ticket);
	}

	@Override
	public Ticket getTicketById(long id) {
		Optional<Ticket> ticketOptional = ticketRepository.findById(id);
		return ticketOptional.orElse(null);
	}

	@Override
	public List<Ticket> getAllOpenTicketsFromProperty(Property property) {
		return ticketRepository.findAllByPropertyAndStatusOrderByTimestampAsc(property, "OPEN");
	}

	@Override
	public List<Ticket> getAllClosedTicketsFromProperty(Property property) {
		return ticketRepository.findAllByPropertyAndStatusOrderByTimestampAsc(property, "CLOSED");
	}

	@Override
	public List<Ticket> getAllOpenTicketsCreatedByUser(User user) {
		return ticketRepository.findAllByCreatedByAndStatusOrderByTimestampAsc(user, "OPEN");
	}

	@Override
	public List<Ticket> getAllClosedTicketsCreatedByUser(User user) {
		return ticketRepository.findAllByCreatedByAndStatusOrderByTimestampAsc(user, "CLOSED");
	}

	@Override
	public boolean updateTicket(Ticket ticket) {
		Optional<Ticket> optionalTicket = ticketRepository.findById(ticket.getId());
		if (optionalTicket.isPresent()) {
			ticketRepository.save(ticket);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteTicketById(long id) {
		Optional<Ticket> optionalTicket = ticketRepository.findById(id);
		if (optionalTicket.isPresent()) {
			ticketRepository.delete(optionalTicket.get());
			return true;
		}
		return false;
	}


}
