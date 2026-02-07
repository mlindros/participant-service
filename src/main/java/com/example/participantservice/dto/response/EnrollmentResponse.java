package com.example.participantservice.dto.response;

import java.time.LocalDate;

public record EnrollmentResponse(
    Long enrollmentId,
    LocalDate startDate,
    LocalDate expirationDate,
    String programName,
    String programCode,
    Integer eligibilityAge
) {}