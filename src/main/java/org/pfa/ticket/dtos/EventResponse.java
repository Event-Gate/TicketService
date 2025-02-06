package org.pfa.ticket.dtos;

import lombok.Builder;
import org.pfa.ticket.enums.Status;

@Builder
public record EventResponse(
    Status status,
    int capacity
) {}