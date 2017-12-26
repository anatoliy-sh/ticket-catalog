package com.ticket.crud.service;


import com.ticket.crud.model.Ticket;

import java.util.List;

public interface TicketService {
	
	Ticket findById(Long id);

	Ticket findByName(String name);

	void saveTicket(Ticket ticket);

	void updateTicket(Ticket ticket);

	void deleteTicketById(Long id);

	void deleteAllTickets();

	List<Ticket> findAllTickets();

	boolean isTicketExist(Ticket ticket);
}