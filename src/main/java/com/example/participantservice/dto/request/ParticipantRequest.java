package com.example.participantservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Request object for creating or updating a participant record")
public record ParticipantRequest(

        @Schema(description = "Participant ID (only required for updates)", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Long participantId,

        @Schema(description = "First name", example = "Mark", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "First name is required")
        @Size(max = 50)
        String firstName,

        @Schema(description = "Last name", example = "Lindros", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Last name is required")
        @Size(max = 50)
        String lastName,

        @Schema(description = "Unique email address", example = "mlindros@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @Email(message = "Must be a valid email address")
        @NotBlank(message = "Email is required")
        @Size(max = 100)
        String email,

        @Schema(description = "Date of birth", example = "1990-05-15")
        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        LocalDate dob,

        @Schema(description = "Status for the new enrollment", example = "ACTIVE")
        @NotBlank(message = "Enrollment status is required")
        @Size(max = 20)
        String enrollmentStatus
) {}
