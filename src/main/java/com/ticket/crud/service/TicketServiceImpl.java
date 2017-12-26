package com.ticket.crud.service;

import java.util.List;

import com.ticket.crud.model.Ticket;
import com.ticket.crud.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("ticketService")
@Transactional
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	public Ticket findById(Long id) {
		return ticketRepository.findOne(id);
	}

	public Ticket findByName(String name) {
		return ticketRepository.findByName(name);
	}

	public void saveTicket(Ticket ticket) {
		ticketRepository.save(ticket);
	}

	public void updateTicket(Ticket ticket){
		saveTicket(ticket);
	}

	public void deleteTicketById(Long id){
		ticketRepository.delete(id);
	}

	public void deleteAllTickets(){
		ticketRepository.deleteAll();
	}

	public List<Ticket> findAllTickets(){
		return ticketRepository.findAll();
	}

	public boolean isTicketExist(Ticket ticket) {
		return findByName(ticket.getName()) != null;
	}

}
