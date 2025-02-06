package org.pfa.ticket.dtos;

import lombok.Builder;

@Builder
public record UserResponse(
        String id,
        String fullName,
        String email,
        boolean isSeller
) {}
