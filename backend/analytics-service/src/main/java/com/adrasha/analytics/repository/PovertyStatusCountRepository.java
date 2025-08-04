package com.adrasha.analytics.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.analytics.model.PovertyStatusCount;
import com.adrasha.core.model.PovertyStatus;

public interface PovertyStatusCountRepository extends JpaRepository<PovertyStatusCount, UUID> {

	PovertyStatusCount findTopByPovertyStatusOrderByCreatedAtDesc(PovertyStatus povertyStatus);	

	List<PovertyStatusCount> findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(UUID ashaId, Instant start, Instant end);

}
