package org.pfa.ticket.services.implementations;

import lombok.RequiredArgsConstructor;
import org.pfa.ticket.dtos.EventCapacityRequest;
import org.pfa.ticket.dtos.TicketRequest;
import org.pfa.ticket.dtos.TicketResponse;
import org.pfa.ticket.dtos.UserResponse;
import org.pfa.ticket.entities.Ticket;
import org.pfa.ticket.exceptions.EntityNotFoundException;
import org.pfa.ticket.exceptions.UnauthorizedException;
import org.pfa.ticket.mappers.TicketMapper;
import org.pfa.ticket.repository.TicketRepository;
import org.pfa.ticket.services.interfaces.EventServiceClient;
import org.pfa.ticket.services.interfaces.TicketService;
import org.pfa.ticket.services.interfaces.UserServiceClient;
import org.pfa.ticket.utils.KafkaUtils;
import org.pfa.ticket.utils.QrCodeGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserServiceClient userServiceClient;
    private final EventServiceClient eventServiceClient;
    private final TicketMapper ticketMapper;
    private final KafkaUtils kafkaUtils;

    @Override
    public Ticket createTicket(TicketRequest request, String token) throws Exception {
        UserResponse authenticatedUser = userServiceClient.getAuthenticatedUser("Bearer " + token);
        int capacity = eventServiceClient.getEventById(request.eventId()).capacity();
        if (capacity <= 0) {
            throw new UnauthorizedException("Tickets aren't available for this event.");
        }
        Ticket ticket = ticketMapper.toEntity(request);
        ticket.setUserId(authenticatedUser.id());
        Ticket incompleteTicket = ticketRepository.save(ticket);
        incompleteTicket.setQrCode(QrCodeGenerator.generateQrCode(incompleteTicket.getId()));
        Ticket savedTicket = ticketRepository.save(incompleteTicket);
        kafkaUtils.sendTicketMessage(savedTicket, token);
        EventCapacityRequest capacityRequest = new EventCapacityRequest(request.eventId());
        kafkaUtils.sendCapacityMessage(capacityRequest, token);
        return savedTicket;
    }

    @Override
    public List<TicketResponse> getAllTickets(String token) {
        UserResponse authenticatedUser = userServiceClient.getAuthenticatedUser("Bearer " + token);
        List<Ticket> tickets = ticketRepository.findAllByUserId(authenticatedUser.id());
        return tickets.stream()
                .map(ticketMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TicketResponse getTicketById(String id) throws EntityNotFoundException {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket was not found"));
        return ticketMapper.toResponse(ticket);
    }

//    @Override
//    public TicketRequest validateTicket(Long ticketId) {
//        Ticket ticket = ticketRepository.findById(ticketId)
//                .orElseThrow(() -> new RuntimeException("Ticket not found"));
//
//        if (ticket.isValidated()) {
//            throw new RuntimeException("Ticket is already validated!");
//        }
//
//        ticket.setValidated(true);
//        Ticket updatedTicket = ticketRepository.save(ticket);
//
//        // Produce Kafka event
//        String eventMessage = String.format("Ticket Validated: ID=%d, EventID=%s, UserID=%s",
//                updatedTicket.getId(), updatedTicket.getEventId(), updatedTicket.getUserId());
//        kafkaTemplate.send("ticket-events", eventMessage);
//
//        return convertToDto(updatedTicket);
//    }
}
