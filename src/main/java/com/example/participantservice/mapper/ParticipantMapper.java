package com.example.participantservice.mapper;

import com.example.participantservice.domain.entity.Participant;
import com.example.participantservice.dto.request.ParticipantRequest;
import com.example.participantservice.dto.response.ParticipantResponse;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMapper {

    public Participant toEntity(ParticipantRequest request) {
        if (request == null) {
            return null;
        }

        Participant participant = new Participant();
        participant.setParticipantId(request.participantId());
        participant.setFirstName(request.firstName());
        participant.setLastName(request.lastName());
        participant.setEmail(request.email());
        participant.setDob(request.dob());
        participant.setEnrollmentStatus(request.enrollmentStatus());

        return participant;
    }

    public void updateEntity(ParticipantRequest request, Participant existingEntity) {
        if (request == null || existingEntity == null) {
            return;
        }

        existingEntity.setFirstName(request.firstName());
        existingEntity.setLastName(request.lastName());
        existingEntity.setEmail(request.email());
        existingEntity.setDob(request.dob());
        existingEntity.setEnrollmentStatus(request.enrollmentStatus());
    }

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
