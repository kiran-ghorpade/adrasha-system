package com.adrasha.data.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrasha.data.model.Family;

@Repository
public interface FamilyRepository extends JpaRepository<Family, UUID>{

	public Optional<Family> findByHeadMemberId(UUID memberId);
}
