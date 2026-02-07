package com.example.participantservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NamedStoredProcedureQuery(
        name = "Participant.enroll",
        procedureName = "participant_pkg.enroll_participant",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_participant_id", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_program_code", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_user_id", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_status_out", type = String.class)
        }
)
@Entity
@Table(name = "PARTICIPANTS")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARTICIPANT_ID", nullable = false)
    private Long participantId;

    @Column(name = "FIRST_NAME", length = 50)
    private String firstName;

    @Column(name = "LAST_NAME", length = 50)
    private String lastName;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "DOB")
    private LocalDate dob;

    @Column(name = "ENROLLMENT_STATUS", length = 20)
    private String enrollmentStatus;

    @Column(name = "CREATED_BY", length = 50)
    private String createdBy;

    @Column(name = "CREATED_ON", updatable = false)
    private OffsetDateTime createdOn;

    @Column(name = "UPDATED_BY", length = 50)
    private String updatedBy;

    @Column(name = "UPDATED_ON", updatable = true)
    private OffsetDateTime updatedOn;

    @JsonManagedReference
    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    public Participant() {}

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(OffsetDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public OffsetDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(OffsetDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(participantId, that.participantId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(participantId);
    }

    @Override
    public String toString() {
        return "Participant {" +
                "participantId=" + participantId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", enrollmentStatus='" + enrollmentStatus + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdOn=" + createdOn +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
