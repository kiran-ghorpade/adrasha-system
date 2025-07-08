package com.adrasha.masterdata.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.masterdata.model.Location;



public interface LocationRepository extends JpaRepository<Location, UUID>{

	Optional<Location> findByPincode(String pincode);
	
	boolean existsByPincode(String pincode);
	
}
