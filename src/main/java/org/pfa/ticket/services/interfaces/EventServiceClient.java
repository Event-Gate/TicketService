package org.pfa.ticket.services.interfaces;

import org.pfa.ticket.dtos.EventResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "event-service")
public interface EventServiceClient {
    @GetMapping("/api/public/events/{eventId}")
    EventResponse getEventById(@PathVariable String eventId);
}