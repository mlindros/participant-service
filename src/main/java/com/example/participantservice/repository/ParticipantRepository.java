package com.example.participantservice.repository;

import com.example.participantservice.domain.entity.Participant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Procedure(name = "Participant.enroll")
    public String enrollParticipant(
            @Param("p_participant_id") Long participantId,
            @Param("p_program_code") String programCode,
            @Param("p_user_id") String userId
    );

    public boolean existsByEmail(String email);

    @Query("""
           select p from Participant p where lower(p.enrollmentStatus) like lower(concat('%', :status, '%'))
           """)
    public List<Participant> findByStatus(@Param("status") String status);
}
