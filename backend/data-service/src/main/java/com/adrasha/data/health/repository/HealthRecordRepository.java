package com.adrasha.data.health.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adrasha.data.health.model.HealthRecord;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, UUID> {

	Optional<HealthRecord> findByMemberId(UUID memberId);

	// Analytics
	@Query("SELECT COUNT(h) FROM HealthRecord h WHERE h.isPregnant = true")
	Long countPregnant();

    @Query("SELECT ncd, COUNT(hr) FROM HealthRecord hr JOIN hr.NCDList ncd GROUP BY ncd")
    List<Object[]> countByNcd();

	List<HealthRecord> findByIsPregnantTrue();

    List<HealthRecord> findByNcdListNotEmpty();}
