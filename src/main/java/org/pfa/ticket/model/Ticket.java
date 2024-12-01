package org.pfa.ticket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eventId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @Column(nullable = false)
    private boolean isValidated;


    @Column(nullable = true, columnDefinition = "TEXT")
    private String qrCode;

}
