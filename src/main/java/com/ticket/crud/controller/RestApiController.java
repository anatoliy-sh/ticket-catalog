package com.ticket.crud.controller;

import java.util.List;

import com.ticket.crud.model.Ticket;
import com.ticket.crud.service.TicketService;
import com.ticket.crud.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	TicketService ticketService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Tickets---------------------------------------------

	@RequestMapping(value = "/tickets/", method = RequestMethod.GET)
	public ResponseEntity<List<Ticket>> listAllTickets() {
		List<Ticket> tickets = ticketService.findAllTickets();
		if (tickets.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
	}

	// -------------------Retrieve Single Ticket------------------------------------------

	@RequestMapping(value = "/tickets/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getTicket(@PathVariable("id") long id) {
		logger.info("Fetching Ticket with id {}", id);
		Ticket ticket = ticketService.findById(id);
		if (ticket == null) {
			logger.error("Ticket with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Ticket with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Ticket>(ticket, HttpStatus.OK);
	}

	// -------------------Create a Ticket-------------------------------------------

	@RequestMapping(value = "/tickets/", method = RequestMethod.POST)
	public ResponseEntity<?> createTicket(@RequestBody Ticket ticket, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Ticket : {}", ticket);

		if (ticketService.isTicketExist(ticket)) {
			logger.error("Unable to create. A Ticket with name {} already exist", ticket.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Ticket with name " + 
			ticket.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		ticketService.saveTicket(ticket);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/ticket/{id}").buildAndExpand(ticket.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Ticket ------------------------------------------------

	@RequestMapping(value = "/tickets/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateTicket(@PathVariable("id") long id, @RequestBody Ticket ticket) {
		logger.info("Updating Ticket with id {}", id);

		Ticket currentTicket = ticketService.findById(id);

		if (currentTicket == null) {
			logger.error("Unable to update. Ticket with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Ticket with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentTicket.setName(ticket.getName());
		currentTicket.setCost(ticket.getCost());
		currentTicket.setDescription(ticket.getDescription());
		currentTicket.setDate(ticket.getDate());
		currentTicket.setStatus(ticket.getStatus());

		ticketService.updateTicket(currentTicket);
		return new ResponseEntity<Ticket>(currentTicket, HttpStatus.OK);
	}

	// ------------------- Delete a Ticket-----------------------------------------

	@RequestMapping(value = "/tickets/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTicket(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Ticket with id {}", id);

		Ticket ticket = ticketService.findById(id);
		if (ticket == null) {
			logger.error("Unable to delete. Ticket with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Ticket with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		ticketService.deleteTicketById(id);
		return new ResponseEntity<Ticket>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Tickets-----------------------------

	@RequestMapping(value = "/tickets/", method = RequestMethod.DELETE)
	public ResponseEntity<Ticket> deleteAllTickets() {
		logger.info("Deleting All Tickets");

		ticketService.deleteAllTickets();
		return new ResponseEntity<Ticket>(HttpStatus.NO_CONTENT);
	}

}