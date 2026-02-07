package com.example.participantservice.dto.response;

import java.time.LocalDateTime;

public record ErrorResponse (
    String status,
    String message,
    LocalDateTime time
) {}
