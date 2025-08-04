package com.adrasha.analytics.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.analytics.model.AliveStatusCount;
import com.adrasha.core.model.AliveStatus;

public interface AliveStatusCountRepository extends JpaRepository<AliveStatusCount, UUID> {

	AliveStatusCount findTopByAliveStatusOrderByCreatedAtDesc(AliveStatus aliveStatus);
	
	List<AliveStatusCount> findByAshaIdAndCreatedAtBetweenOrderByCreatedAtAsc(UUID ashaId, Instant start, Instant end);

}
