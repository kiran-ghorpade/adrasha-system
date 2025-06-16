package com.adrasha.family.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.family.model.Member;


public interface MemberRepository extends JpaRepository<Member, UUID>{

	Optional<Member> findByAdharNumber(String adharNumber);
}
