package com.adrasha.data.family.repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adrasha.core.model.LocalityType;
import com.adrasha.core.model.PovertyStatus;
import com.adrasha.data.family.model.Family;

@Repository
public interface FamilyRepository extends JpaRepository<Family, UUID> {

	public Optional<Family> findByHeadMemberId(UUID memberId);

	//	Analytics

	@Query("SELECT f.povertyStatus AS key, COUNT(f) AS value FROM Family f GROUP BY f.povertyStatus")
	Map<PovertyStatus, Long> countByPovertyStatus();

	@Query("SELECT f.localityType AS key, COUNT(f) AS value FROM Family f GROUP BY f.localityType")
	Map<LocalityType, Long> countByLocality();

}
