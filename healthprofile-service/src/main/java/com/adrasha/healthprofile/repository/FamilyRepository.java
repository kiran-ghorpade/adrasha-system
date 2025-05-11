package com.adrasha.healthprofile.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.healthprofile.model.Family;

public interface FamilyRepository extends JpaRepository<Family, UUID>{

	public Optional<Family> findByHeadMember(UUID memberId);
}
