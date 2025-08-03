package com.adrasha.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adrasha.data.model.Family;

@Repository
public interface FamilyRepository extends JpaRepository<Family, UUID> {

	public Optional<Family> findByHeadMemberId(UUID memberId);

	//	Analytics

	@Query("SELECT f.povertyStatus, COUNT(f) FROM Family f GROUP BY f.povertyStatus")
	List<Object[]> countByPovertyStatus();

}
