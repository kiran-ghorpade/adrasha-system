package com.adrasha.masterdata.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.masterdata.model.NCD;

public interface NCDRepository extends JpaRepository<NCD, UUID>{
	
	Optional<NCD> findByName(String name);
	
	boolean existsByName(String name);

}
