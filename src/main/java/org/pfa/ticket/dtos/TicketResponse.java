package org.pfa.ticket.dtos;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TicketResponse(
        String id,
        String eventId,
        LocalDateTime bookingTime,
        boolean isValidated,
        String qrCode
) {}
