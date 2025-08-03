package com.adrasha.user.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adrasha.core.model.Role;
import com.adrasha.user.model.RoleRequest;



@Repository
public interface RoleRequestRepository extends JpaRepository<RoleRequest, UUID>{

	List<RoleRequest> findByUserId(UUID userId);

    @Query("SELECT status AS key, COUNT(hr) AS value FROM RoleRequest hr GROUP BY status")
    Map<Role, Long> countByStatus();
}
