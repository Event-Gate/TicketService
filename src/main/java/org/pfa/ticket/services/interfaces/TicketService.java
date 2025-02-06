package org.pfa.ticket.services.interfaces;

import org.pfa.ticket.dtos.TicketRequest;
import org.pfa.ticket.dtos.TicketResponse;
import org.pfa.ticket.entities.Ticket;
import org.pfa.ticket.exceptions.EntityNotFoundException;

import java.util.List;

public interface TicketService {
    Ticket createTicket(TicketRequest ticketRequest, String token) throws Exception;
    List<TicketResponse> getAllTickets(String token);
    TicketResponse getTicketById(String id) throws EntityNotFoundException;
//    TicketRequest validateTicket(Long ticketId);
}
