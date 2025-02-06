package org.pfa.ticket.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ticket {
    @Id
    private String id;
    private String eventId;
    private String userId;
    private LocalDateTime bookingTime;
    private boolean isValidated;
    private String qrCode;
}
