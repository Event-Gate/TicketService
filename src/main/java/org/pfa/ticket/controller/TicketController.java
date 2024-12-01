package org.pfa.ticket.controller;


import org.pfa.ticket.dto.TicketDto;
import org.pfa.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public TicketDto createTicket(@RequestBody TicketDto ticketDto) {
        return ticketService.createTicket(ticketDto);
    }

    @GetMapping
    public List<TicketDto> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public TicketDto getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }
    @PutMapping("/validate/{id}")
    public TicketDto validateTicket(@PathVariable Long id) {
        return ticketService.validateTicket(id);
    }   
}