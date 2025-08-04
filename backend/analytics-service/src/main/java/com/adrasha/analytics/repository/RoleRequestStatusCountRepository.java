package com.adrasha.analytics.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.analytics.model.RoleRequestStatusCount;
import com.adrasha.core.model.RequestStatus;

public interface RoleRequestStatusCountRepository extends JpaRepository<RoleRequestStatusCount, UUID> {

	RoleRequestStatusCount findTopByStatusOrderByCreatedAtDesc(RequestStatus status);	

	List<RoleRequestStatusCount> findByCreatedAtBetweenOrderByCreatedAtAsc(Instant start, Instant end);

}
