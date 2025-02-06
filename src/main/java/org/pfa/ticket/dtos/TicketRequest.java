package org.pfa.ticket.dtos;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TicketRequest(
        String eventId,
        LocalDateTime bookingTime,
        boolean isValidated
) {}
