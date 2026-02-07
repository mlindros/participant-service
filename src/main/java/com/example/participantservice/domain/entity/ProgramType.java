package com.example.participantservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PROGRAM_TYPES")
public class ProgramType {

    @Id
    @Column(name = "PROGRAM_CODE", nullable = false, length = 20)
    private String programCode;

    @Column(name = "PROGRAM_NAME", nullable = false, length = 100)
    private String programName;

    @Column(name = "ELIGIBILITY_AGE")
    private Integer eligibilityAge;

    @Column(name = "CREATED_BY", length = 50)
    private String createdBy;

    @Column(name = "CREATED_ON", updatable = false)
    private OffsetDateTime createdOn;

    @Column(name = "UPDATED_BY", length = 50)
    private String updatedBy;

    @Column(name = "UPDATED_ON", updatable = true)
    private OffsetDateTime updatedOn;

    @JsonManagedReference
    @OneToMany(mappedBy = "programType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    public ProgramType() {}

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Integer getEligibilityAge() {
        return eligibilityAge;
    }

    public void setEligibilityAge(Integer eligibilityAge) {
        this.eligibilityAge = eligibilityAge;
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
        ProgramType that = (ProgramType) o;
        return Objects.equals(programCode, that.programCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(programCode);
    }

    @Override
    public String toString() {
        return "ProgramType{" +
                "programCode='" + programCode + '\'' +
                ", programName='" + programName + '\'' +
                ", eligibilityAge=" + eligibilityAge +
                ", createdBy='" + createdBy + '\'' +
                ", createdOn=" + createdOn +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
