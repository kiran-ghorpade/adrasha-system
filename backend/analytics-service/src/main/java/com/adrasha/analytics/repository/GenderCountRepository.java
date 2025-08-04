package com.adrasha.analytics.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.analytics.model.GenderCount;
import com.adrasha.core.model.Gender;

public interface GenderCountRepository extends JpaRepository<GenderCount, UUID> {

	GenderCount findTopByGenderOrderByCreatedAtDesc(Gender gender);	
	
	List<GenderCount> findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(UUID ashaId, Instant start, Instant end);

}
