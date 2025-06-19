package com.adrasha.data.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrasha.data.model.HealthRecord;


@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, UUID>{

	Optional<HealthRecord> findByMemberId(UUID memberId);
}
