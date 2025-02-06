package org.pfa.ticket.mappers;

import org.pfa.ticket.dtos.TicketRequest;
import org.pfa.ticket.dtos.TicketResponse;
import org.pfa.ticket.entities.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public Ticket toEntity(TicketRequest ticketRequest) {
        return Ticket.builder()
                .eventId(ticketRequest.eventId())
                .bookingTime(ticketRequest.bookingTime())
                .isValidated(ticketRequest.isValidated())
                .build();
    }

    public TicketRequest toRequest(Ticket ticket) {
        return TicketRequest.builder()
                .eventId(ticket.getEventId())
                .bookingTime(ticket.getBookingTime())
                .isValidated(ticket.isValidated())
                .build();
    }

    public TicketResponse toResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .eventId(ticket.getEventId())
                .bookingTime(ticket.getBookingTime())
                .isValidated(ticket.isValidated())
                .qrCode(ticket.getQrCode())
                .build();
    }
}
