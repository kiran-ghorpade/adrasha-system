package com.adrasha.masterdata.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrasha.masterdata.model.HealthCenter;


@Repository
public interface HealthCenterRepository extends JpaRepository<HealthCenter, UUID>{
	Optional<HealthCenter> findByName(String name); 
}
