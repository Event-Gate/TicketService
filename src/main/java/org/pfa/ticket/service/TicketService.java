package org.pfa.ticket.service;

import org.pfa.ticket.dto.TicketDto;

import java.util.List;

public interface TicketService {
    TicketDto createTicket(TicketDto ticketDto);
    List<TicketDto> getAllTickets();
    TicketDto getTicketById(Long id);
    TicketDto validateTicket(Long ticketId);
}
