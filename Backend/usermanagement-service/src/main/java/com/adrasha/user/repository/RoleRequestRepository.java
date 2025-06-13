package com.adrasha.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrasha.user.model.RoleRequest;


@Repository
public interface RoleRequestRepository extends JpaRepository<RoleRequest, UUID>{

	Optional<RoleRequest> findByUserId(UUID userId);
}
