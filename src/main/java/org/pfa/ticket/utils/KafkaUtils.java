package org.pfa.ticket.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pfa.ticket.dtos.EventCapacityRequest;
import org.pfa.ticket.entities.Ticket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor @Slf4j
public class KafkaUtils {
    private final KafkaTemplate<String, Ticket> kafkaTicketTemplate;
    private final KafkaTemplate<String, EventCapacityRequest> kafkaCapacityTemplate;

    @Value("${spring.kafka.topic.ticket-name}")
    private String ticketTopic;

    @Value("${spring.kafka.topic.capacity-name}")
    private String capacityTopic;

    public void sendTicketMessage(Ticket ticket, String token) {
        Message<Ticket> message = MessageBuilder
            .withPayload(ticket)
            .setHeader(KafkaHeaders.TOPIC, ticketTopic)
            .setHeader(KafkaHeaders.KEY, ticket.getId())
            .setHeader("Authorization", "Bearer ticket" + token)
            .build();

        kafkaTicketTemplate.send(message)
            .whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Ticket sent successfully");
                } else {
                    log.error("Failed to send ticket", ex);
                }
            });
    }

    public void sendCapacityMessage(EventCapacityRequest capacityRequest, String token) {
        Message<EventCapacityRequest> message = MessageBuilder
                .withPayload(capacityRequest)
                .setHeader(KafkaHeaders.TOPIC, capacityTopic)
                .setHeader(KafkaHeaders.KEY, capacityRequest.eventId())
                .setHeader("Authorization", "Bearer " + token)
                .build();

        kafkaCapacityTemplate.send(message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Event sent successfully");
                    } else {
                        log.error("Failed to send event", ex);
                    }
                });
    }
}
