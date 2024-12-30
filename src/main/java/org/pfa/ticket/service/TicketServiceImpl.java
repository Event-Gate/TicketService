package org.pfa.ticket.service;

import lombok.RequiredArgsConstructor;
import org.pfa.ticket.dto.TicketDto;
import org.pfa.ticket.model.Ticket;
import org.pfa.ticket.repository.TicketRepository;
import org.pfa.ticket.utils.QrCodeGenerator;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public TicketDto createTicket(TicketDto ticketDto) {
        try {
            Ticket ticket = new Ticket();
            ticket.setEventId(ticketDto.eventId());
            ticket.setUserId(ticketDto.userId());
            ticket.setBookingTime(ticketDto.bookingTime());
            ticket.setValidated(ticketDto.validated());

            Ticket savedTicket = ticketRepository.save(ticket);
            System.out.println(savedTicket);
            // Generate QR code for the ticket
            String qrCode = QrCodeGenerator.generateQrCode(savedTicket.getId().toString());

            savedTicket.setQrCode(qrCode);
            ticketRepository.save(savedTicket);




            // Produce Kafka event
            String eventMessage = String.format("Ticket Created: ID=%d, EventID=%s, UserID=%s",
                    savedTicket.getId(), savedTicket.getEventId(), savedTicket.getUserId());


            kafkaTemplate.send("ticket-events", eventMessage);

            return convertToDto(savedTicket);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating ticket: " + e.getMessage());
        }
    }

    @Override
    public List<TicketDto> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TicketDto getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return convertToDto(ticket);
    }

    @Override
    public TicketDto validateTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (ticket.isValidated()) {
            throw new RuntimeException("Ticket is already validated!");
        }

        ticket.setValidated(true);
        Ticket updatedTicket = ticketRepository.save(ticket);

        // Produce Kafka event
        String eventMessage = String.format("Ticket Validated: ID=%d, EventID=%s, UserID=%s",
                updatedTicket.getId(), updatedTicket.getEventId(), updatedTicket.getUserId());
        kafkaTemplate.send("ticket-events", eventMessage);

        return convertToDto(updatedTicket);
    }

    private TicketDto convertToDto(Ticket ticket) {
        return new TicketDto(
                ticket.getId(),
                ticket.getEventId(),
                ticket.getUserId(),
                ticket.getBookingTime(),
                ticket.isValidated(),
                ticket.getQrCode()
        );
    }
}
