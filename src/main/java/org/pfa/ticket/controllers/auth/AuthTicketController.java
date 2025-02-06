package org.pfa.ticket.controllers.auth;

import lombok.RequiredArgsConstructor;
import org.pfa.ticket.dtos.TicketRequest;
import org.pfa.ticket.dtos.TicketResponse;
import org.pfa.ticket.entities.Ticket;
import org.pfa.ticket.exceptions.EntityNotFoundException;
import org.pfa.ticket.services.interfaces.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequiredArgsConstructor @RequestMapping("/api/auth/tickets")
public class AuthTicketController {
    private final TicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketRequest request, @RequestHeader("Authorization") String header) throws Exception {
        String token = header.startsWith("Bearer ") ? header.substring(7) : header;
        return new ResponseEntity<>(ticketService.createTicket(request, token), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTickets(@RequestHeader("Authorization") String header) {
        String token = header.startsWith("Bearer ") ? header.substring(7) : header;
        return new ResponseEntity<>(ticketService.getAllTickets(token), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable String id) throws EntityNotFoundException {
        return new ResponseEntity<>(ticketService.getTicketById(id), HttpStatus.OK);
    }

//    @PutMapping("/validate/{id}")
//    public ResponseEntity<TicketRequest> validateTicket(@PathVariable Long id) {
//        TicketRequest validatedTicket = ticketService.validateTicket(id);
//        if (validatedTicket != null) {
//            return ResponseEntity.ok(validatedTicket);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
}
