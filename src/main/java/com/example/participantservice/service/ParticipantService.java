package com.example.participantservice.service;

import com.example.participantservice.domain.entity.Participant;
import com.example.participantservice.dto.request.EnrollmentRequest;
import com.example.participantservice.dto.request.ParticipantRequest;
import com.example.participantservice.dto.response.EnrollmentResponse;
import com.example.participantservice.dto.response.ParticipantResponse;
import com.example.participantservice.exception.EnrollmentException;
import com.example.participantservice.exception.ParticipantException;
import com.example.participantservice.mapper.EnrollmentMapper;
import com.example.participantservice.mapper.ParticipantMapper;
import com.example.participantservice.repository.ParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.participantservice.domain.constant.AppConstants.*;

@Service
public class ParticipantService {

    private static final Logger log = LoggerFactory.getLogger(ParticipantService.class);

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;
    private final EnrollmentMapper enrollmentMapper;

    public ParticipantService(ParticipantRepository participantRepository,
                              ParticipantMapper participantMapper,
                              EnrollmentMapper enrollmentMapper) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
        this.enrollmentMapper = enrollmentMapper;
    }

    public List<ParticipantResponse> getAll() {
        return participantRepository.findAll()
                .stream()
                .map(participantMapper::toResponse)
                .toList();
    }

    public ParticipantResponse getById(Long participantId) {
        return participantRepository.findById(participantId)
                .map(participantMapper::toResponse)
                .orElseThrow(() -> new ParticipantException(RECORD_NOT_FOUND));
    }

    @Transactional
    public ParticipantResponse create(ParticipantRequest participantRequest) {
        if (participantRepository.existsByEmail(participantRequest.email())) {
            throw new ParticipantException(EMAIL_EXISTS);
        } else {
            Participant participant = participantMapper.toEntity(participantRequest);
            return participantMapper.toResponse(participantRepository.save(participant));
        }
    }

    @Transactional
    public ParticipantResponse updateById(Long participantId, ParticipantRequest participantRequest) {
        return participantRepository.findById(participantId)
                .map(existing -> {
                    participantMapper.updateEntity(participantRequest, existing);
                    return participantMapper.toResponse(participantRepository.save(existing));
                })
                .orElseThrow(() -> new ParticipantException(RECORD_NOT_FOUND));
    }

    @Transactional
    public void deleteById(Long participantId) {
        if (!participantRepository.existsById(participantId)) {
            throw new ParticipantException(RECORD_NOT_FOUND);
        }

        participantRepository.deleteById(participantId);
    }

    @Transactional
    public String processEnrollment(EnrollmentRequest request) {
        log.info("Attempting to enroll participant {} in program {}", request.participantId(), request.programCode());

        String status = participantRepository.enrollParticipant(request.participantId(),
                                                                request.programCode(),
                                                                request.userId());

        log.info("Enrollment result for participant {}: {}", request.participantId(), status);

        if (status.equals(SUCCESS)) {
            return status;
        } else {
            throw new EnrollmentException(status);
        }
    }

    public List<EnrollmentResponse> getActiveEnrollments(Long participantId) {
        return participantRepository.findById(participantId)
                .map(participant -> participant.getEnrollments().stream()
                        .filter(e -> e.getExpirationDate() != null && e.getExpirationDate().isAfter(LocalDate.now()))
                        .map(enrollmentMapper::toResponse)
                        .toList())
                .orElseThrow(() -> new ParticipantException((RECORD_NOT_FOUND)));
    }

    public List<ParticipantResponse> findByStatus(String status) {
        return participantRepository.findByStatus(status)
                .stream()
                .map(participantMapper::toResponse)
                .toList();
    }
}
