package com.adrasha.analytics.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.analytics.model.PregnancyCount;

public interface PregnancyCountRepository extends JpaRepository<PregnancyCount, UUID> {

	
}
