package com.example.participantservice.dto.response;

import java.time.LocalDate;

public record ParticipantResponse(
    Long participantId,
    String firstName,
    String lastName,
    String email,
    LocalDate dob,
    String enrollmentStatus
) {}