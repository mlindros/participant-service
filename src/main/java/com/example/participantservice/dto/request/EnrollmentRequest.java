package com.example.participantservice.dto.request;

public record EnrollmentRequest(
    Long participantId,
    String programCode,
    String userId
) {}
