package com.adrasha.familyservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.familyservice.model.Member;

public interface MemberRepository extends JpaRepository<Member, UUID>{

}
