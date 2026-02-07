package com.example.participantservice.mapper;

import com.example.participantservice.domain.entity.Enrollment;
import com.example.participantservice.dto.response.EnrollmentResponse;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

    public EnrollmentResponse toResponse(Enrollment entity) {
        if (entity == null) {
            return null;
        }

        return new EnrollmentResponse(
            entity.getEnrollmentId(),
            entity.getStartDate(),
            entity.getExpirationDate(),
            entity.getProgramType().getProgramName(),
            entity.getProgramType().getProgramCode(),
            entity.getProgramType().getEligibilityAge());
    }
}
