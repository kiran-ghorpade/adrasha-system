package com.adrasha.user.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrasha.user.model.RoleRequest;
import com.adrasha.core.model.RequestStatus;





@Repository
public interface RoleRequestRepository extends JpaRepository<RoleRequest, UUID>{

	List<RoleRequest> findByUserId(UUID userId);

    boolean existsByUserIdAndStatus(UUID userId, RequestStatus status);
}
