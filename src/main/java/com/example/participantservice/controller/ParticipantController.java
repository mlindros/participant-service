package com.example.participantservice.controller;

import com.example.participantservice.dto.request.EnrollmentRequest;
import com.example.participantservice.dto.request.ParticipantRequest;
import com.example.participantservice.dto.response.EnrollmentResponse;
import com.example.participantservice.dto.response.ErrorResponse;
import com.example.participantservice.dto.response.ParticipantResponse;
import com.example.participantservice.service.ParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/participants")
@Tag(name = "Participant Management", description = "APIs for managing participant data and enrollments")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Operation(summary = "Create a new participant")
    @ApiResponse(responseCode = "201", description = "Participant created successfully")
    @PostMapping()
    public ResponseEntity<ParticipantResponse> create(@Valid @RequestBody ParticipantRequest participantRequest) {
        return ResponseEntity.status(CREATED).body(participantService.create(participantRequest));
    }

    @Operation(
            summary = "Get all participants",
            description = "Retrieves a complete list of all participants currently registered in the system."
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all participants")
    @GetMapping
    //@PreAuthorize("hasAuthority('SCOPE_email')")
    //@PreAuthorize("authentication.tokenAttributes['email_verified'] == true")
    @PreAuthorize("authentication.tokenAttributes['email'] != null")
    public ResponseEntity<List<ParticipantResponse>> getAll(/*org.springframework.security.core.Authentication auth*/) {
        //System.out.println(">>> AUTHORITIES: " + auth.getAuthorities());
        //I could have theoretically just returned List<ParticipantDTO>
        return ResponseEntity.ok(participantService.getAll()
                .stream()
                .toList());
    }

    @Operation(
            summary = "Get participant by ID",
            description = "Retrieves detailed information for a single participant using their unique ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the participant"),
            @ApiResponse(responseCode = "404", description = "Participant not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{participantId}")
    public ResponseEntity<ParticipantResponse> getById(
            @Parameter(description = "The unique ID of the participant", example = "1")
            @PathVariable Long participantId) {
        return ResponseEntity.ok(participantService.getById(participantId));
    }

    @Operation(
            summary = "Update an existing participant",
            description = "Updates the profile information for a participant matching the provided ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participant updated successfully"),
            @ApiResponse(responseCode = "404", description = "Participant not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{participantId}")
    public ResponseEntity<ParticipantResponse> update(
            @Parameter(description = "ID of the participant to update", example = "1")
            @PathVariable Long participantId,
            @Valid @RequestBody ParticipantRequest participantRequest) {
        return ResponseEntity.ok(participantService.updateById(participantId, participantRequest));
    }

    @Operation(
            summary = "Delete a participant",
            description = "Permanently removes a participant record from the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Participant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Participant not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{participantId}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the participant to delete", example = "1")
            @PathVariable Long participantId) {
        participantService.deleteById(participantId);
        return ResponseEntity.noContent().build();
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

    @Operation(
            summary = "Get active enrollments for a participant",
            description = "Retrieves all currently active (non-expired) program enrollments for the specified participant ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved active enrollments"),
            @ApiResponse(responseCode = "404", description = "Participant not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{participantId}/enrollments/active")
    public ResponseEntity<List<EnrollmentResponse>> getActiveEnrollments(
            @Parameter(description = "ID of the participant", example = "1")
            @PathVariable Long participantId) {
        return ResponseEntity.ok(participantService.getActiveEnrollments(participantId)
                .stream()
                .toList());
    }

    @Operation(summary = "Search participants by status",
            description = "Returns a list of participants that match the provided status string (case-insensitive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "400", description = "Invalid status parameter provided")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ParticipantResponse>> findByStatus(
            @Parameter(description = "Status to filter by (e.g., ACTIVE, INACTIVE)", example = "ACTIVE")
            @RequestParam(value = "status") String status) {

        return ResponseEntity.ok(participantService.findByStatus(status));
    }
}
