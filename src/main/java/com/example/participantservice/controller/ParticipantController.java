package com.example.participantservice.controller;

import com.example.participantservice.dto.request.EnrollmentRequest;
import com.example.participantservice.dto.response.EnrollmentResponse;
import com.example.participantservice.dto.response.ParticipantResponse;
import com.example.participantservice.mapper.EnrollmentMapper;
import com.example.participantservice.mapper.ParticipantMapper;
import com.example.participantservice.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    private final ParticipantService participantService;
    private final ParticipantMapper participantMapper;
    private final EnrollmentMapper enrollmentMapper;

    public ParticipantController(ParticipantService participantService,
                                 ParticipantMapper participantMapper,
                                 EnrollmentMapper enrollmentMapper) {
        this.participantService = participantService;
        this.participantMapper = participantMapper;
        this.enrollmentMapper = enrollmentMapper;
    }

    @GetMapping
    public ResponseEntity<List<ParticipantResponse>> getAllParticipants() {
        //I could have theoretically just returned List<ParticipantDTO>
        return ResponseEntity.ok(participantService.getAllParticipants()
                .stream()
                .map(participantMapper::toResponse)
                .toList());
    }

    @PostMapping("/enrollments")
    public ResponseEntity<String> enroll(@RequestBody EnrollmentRequest request) {
        return ResponseEntity.ok(participantService.processEnrollment(request));
    }

    @GetMapping("/{id}/enrollments/active")
    public ResponseEntity<List<EnrollmentResponse>> getActiveEnrollments(@PathVariable("id") Long participantId) {
        return ResponseEntity.ok(participantService.getActiveEnrollments(participantId)
                .stream()
                .map(enrollmentMapper::toResponse)
                .toList());
    }
}
