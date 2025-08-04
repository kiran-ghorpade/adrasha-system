package com.adrasha.analytics.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.analytics.model.AgeGroupCount;
import com.adrasha.core.model.AgeGroup;

public interface AgeGroupCountRepository extends JpaRepository<AgeGroupCount, UUID> {

//	@Query("SELECT r FROM AgeGroupCount r Order By r.createdAt DESC LIMIT 1")
//	Instant getLastRecord();
//
//	AgeGroupCount findByLastCreatedAt(Instant lastCreatedAt);
	
	AgeGroupCount findTopByAgeGroupOrderByCreatedAtDesc(AgeGroup group);	
	
	List<AgeGroupCount> findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(UUID ashaId, Instant start, Instant end);
}
