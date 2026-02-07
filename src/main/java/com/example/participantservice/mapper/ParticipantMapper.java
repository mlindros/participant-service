package com.example.participantservice.mapper;

import com.example.participantservice.domain.entity.Participant;
import com.example.participantservice.dto.response.ParticipantResponse;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMapper {

    public ParticipantResponse toResponse(Participant entity) {
        if (entity == null) {
            return null;
        }

        return new ParticipantResponse(
            entity.getParticipantId(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getEmail(),
            entity.getDob(),
            entity.getEnrollmentStatus());
    }
}
