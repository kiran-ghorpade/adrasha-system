package com.adrasha.user.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrasha.user.model.RoleRequest;



@Repository
public interface RoleRequestRepository extends JpaRepository<RoleRequest, UUID>{

	List<RoleRequest> findByUserId(UUID userId);
}
