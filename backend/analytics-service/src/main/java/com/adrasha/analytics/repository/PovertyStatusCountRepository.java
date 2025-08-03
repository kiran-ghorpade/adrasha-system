package com.adrasha.analytics.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.analytics.model.PovertyStatusCount;

public interface PovertyStatusCountRepository extends JpaRepository<PovertyStatusCount, UUID> {

	
}
