package com.example.participantservice.repository;

import com.example.participantservice.domain.entity.Participant;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Procedure(name = "Participant.enroll")
    public String enrollParticipant(
            @Param("p_participant_id") Long participantId,
            @Param("p_program_code") String programCode,
            @Param("p_user_id") String userId
    );
}
