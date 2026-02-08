package com.example.participantservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Participant details returned after a successful query or update")
public record ParticipantResponse(

    @Schema(description = "The unique identifier of the participant", example = "1")
    Long participantId,

    @Schema(description = "The first name of the participant", example = "Mark")
    String firstName,

    @Schema(description = "The last name of the participant", example = "Lindros")
    String lastName,

    @Schema(description = "Primary email address used for enrollment notifications", example = "mlindros@gmail.com")
    String email,

    @Schema(description = "Date of birth in YYYY-MM-DD format", example = "1990-05-15")
    LocalDate dob,

    @Schema(description = "Current lifecycle status of the participant", example = "ACTIVE", allowableValues = {"ACTIVE", "INACTIVE", "PENDING"})
    String enrollmentStatus
) {}