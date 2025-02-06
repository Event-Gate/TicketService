package org.pfa.ticket.services.interfaces;

import org.pfa.ticket.dtos.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/api/auth/users/me")
    UserResponse getAuthenticatedUser(@RequestHeader("Authorization") String token);
}