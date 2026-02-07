package com.example.participantservice.repository;

import com.example.participantservice.domain.entity.ProgramType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramTypeRepository extends JpaRepository<ProgramType, String> {
}
