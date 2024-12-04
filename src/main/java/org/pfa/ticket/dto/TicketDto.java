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
}
