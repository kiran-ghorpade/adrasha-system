package com.adrasha.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.adrasha.auth.model.Role;
import com.adrasha.auth.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User>{

	Optional<User> findByUsername(String username);
		
	boolean existsByRolesContaining(Role role);
	
}
