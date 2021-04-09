package com.app.repo;

import com.app.model.Property;
import com.app.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	List<Ticket> findAllByPropertyAndStatusOrderByTimestampAsc(Property property, String status);

}
