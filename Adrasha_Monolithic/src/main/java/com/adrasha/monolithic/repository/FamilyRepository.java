package com.adrasha.monolithic.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.monolithic.model.Family;

public interface FamilyRepository extends JpaRepository<Family, UUID>{

	public Optional<Family> findByHeadMember(UUID memberId);
}
