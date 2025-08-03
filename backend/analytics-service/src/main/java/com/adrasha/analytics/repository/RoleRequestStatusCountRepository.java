package com.adrasha.analytics.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.analytics.model.RoleRequestStatusCount;

public interface RoleRequestStatusCountRepository extends JpaRepository<RoleRequestStatusCount, UUID> {

	
}
