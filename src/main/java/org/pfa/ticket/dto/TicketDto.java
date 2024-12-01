package org.pfa.ticket.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor @AllArgsConstructor
public class TicketDto {

    private Long id;
    private String eventId;
    private String userId;
    private LocalDateTime bookingTime;
    private boolean validated;
    private String qrCode;


}
