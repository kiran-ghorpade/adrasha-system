package com.adrasha.family.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrasha.family.model.Family;
import com.adrasha.family.model.Member;

@Repository
public interface FamilyRepository extends JpaRepository<Family, UUID>{

	public Optional<Family> findByHeadMember(Member member);
}
