package com.example.participantservice.service;

import com.example.participantservice.domain.entity.Enrollment;
import com.example.participantservice.domain.entity.Participant;
import com.example.participantservice.dto.request.EnrollmentRequest;
import com.example.participantservice.exception.EnrollmentException;
import com.example.participantservice.repository.ParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.participantservice.domain.constant.AppConstants.SUCCESS;

@Service
public class ParticipantService {

    private static final Logger log = LoggerFactory.getLogger(ParticipantService.class);

    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    @Transactional
    public String processEnrollment(EnrollmentRequest request) {
        log.info("Attempting to enroll participant {} in program {}", request.participantId(), request.programCode());

        String status = participantRepository.enrollParticipant(request.participantId(),
                                                                request.programCode(),
                                                                request.userId());

        if (status.equals(SUCCESS)) {
            log.info("Enrollment result for participant {}: {}", request.participantId(), status);
            return status;
        } else {
            log.info("Enrollment result for participant {}: {}", request.participantId(), status);
            throw new EnrollmentException(status);
        }
    }

    public List<Enrollment> getActiveEnrollments(Long participantId) {
        return participantRepository.findById(participantId)
                .map(participant -> participant.getEnrollments().stream()
                        .filter(e -> e.getExpirationDate() != null && e.getExpirationDate().isAfter(LocalDate.now()))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException(("Participant not found")));
    }
}
