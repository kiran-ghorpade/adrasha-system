package com.adrasha.user.repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adrasha.core.model.Role;
import com.adrasha.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

	Optional<User> findByAdharNumber(String adharNumber);
	
    @Query("SELECT role AS key, COUNT(hr) AS value FROM User hr JOIN hr.roles role GROUP BY role")
    Map<Role, Long> countByRoles();
//    List<Object[]> countByRoles();
}
