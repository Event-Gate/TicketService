package org.pfa.ticket.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public record TicketDto(
        Long id,
        String eventId,
        String userId,
        LocalDateTime bookingTime,
        boolean validated,
        String qrCode
) {
    public TicketDto {
        if (eventId == null || eventId.isBlank()) {
            throw new IllegalArgumentException("Event ID cannot be null or empty");
        }
    }
}
