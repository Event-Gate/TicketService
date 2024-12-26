package org.pfa.ticket.controller;

import lombok.RequiredArgsConstructor;
import org.pfa.ticket.dto.TicketDto;
import org.pfa.ticket.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto ticketDto) {
        TicketDto createdTicket = ticketService.createTicket(ticketDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    @GetMapping
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        List<TicketDto> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets); // Returns 200 OK with the list of tickets
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getTicketById(@PathVariable Long id) {
        TicketDto ticket = ticketService.getTicketById(id);
        if (ticket != null) {
            return ResponseEntity.ok(ticket); // Returns 200 OK with the ticket
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Returns 404 if the ticket is not found
        }
    }

    @PutMapping("/validate/{id}")
    public ResponseEntity<TicketDto> validateTicket(@PathVariable Long id) {
        TicketDto validatedTicket = ticketService.validateTicket(id);
        if (validatedTicket != null) {
            return ResponseEntity.ok(validatedTicket); // Returns 200 OK with the validated ticket
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Returns 404 if the ticket is not found
        }
    }
}
