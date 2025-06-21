package com.adrasha.masterdata.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.masterdata.model.HealthCenter;


public interface HealthCenterRepository extends JpaRepository<HealthCenter, UUID> {

	Optional<HealthCenter> findByName(String name);

	boolean existsByName(String name);
}
