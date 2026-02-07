package com.example.participantservice.controller;

import com.example.participantservice.dto.request.EnrollmentRequest;
import com.example.participantservice.dto.response.EnrollmentResponse;
import com.example.participantservice.dto.response.ErrorResponse;
import com.example.participantservice.dto.response.ParticipantResponse;
import com.example.participantservice.mapper.EnrollmentMapper;
import com.example.participantservice.mapper.ParticipantMapper;
import com.example.participantservice.service.ParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
@Tag(name = "Participant Management", description = "APIs for managing participant data and enrollments")
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

    @Operation(summary = "Enroll a participant", description = "Enrolls a participant into a specific program using the provided details.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully enrolled",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Participant or Program not found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "409", description = "Participant already enrolled in this program",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Participant does not meet eligibility age",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
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
