package com.adrasha.analytics.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.analytics.model.NcdCount;

public interface NCDCountRepository extends JpaRepository<NcdCount, UUID> {

	NcdCount findTopByNcdNameOrderByCreatedAtDesc(String ncdName);
	
	List<NcdCount> findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(UUID ashaId, Instant start, Instant end);

}
