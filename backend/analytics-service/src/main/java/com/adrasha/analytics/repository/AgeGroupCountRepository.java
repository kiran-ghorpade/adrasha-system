package com.adrasha.analytics.repository;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adrasha.analytics.model.AgeGroupCount;

public interface AgeGroupCountRepository extends JpaRepository<AgeGroupCount, UUID> {

	@Query("SELECT r FROM AgeGroupCount r Order By r.createdAt DESC LIMIT 1")
	Instant getLastRecord();

//	AgeGroupCount getOneByCreatedAt(Instant lastCreatedAt);

	
}
