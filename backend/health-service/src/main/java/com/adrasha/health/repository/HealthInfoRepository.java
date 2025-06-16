package com.adrasha.health.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrasha.health.model.HealthInfo;


@Repository
public interface HealthInfoRepository extends JpaRepository<HealthInfo, UUID>{

	Optional<HealthInfo> findByMemberId(UUID memberId);
}
