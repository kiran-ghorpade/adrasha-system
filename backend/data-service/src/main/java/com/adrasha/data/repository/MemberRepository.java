package com.adrasha.data.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.data.model.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {

	boolean existsByAdharNumber(String adharNumber);

	void deleteByFamilyId(UUID familyId);

}
